class AdminTeamsController < ApplicationController

  before_filter :admin_login_required

  def index
    @teams = Team.find(:all, :conditions => {:verified => false})
    @all_teams = Team.find(:all, :order => "title").map {|u| [u.title, u.id] }
  end
  
  def announce
  end
  
  def send_announcement
    @teams = Team.find(:all)
    for team in @teams do
      TeamNotifications.deliver_announcement(team,params[:announcement])
    end
    render :nothing => true
  end
  
  def verify
    @team = Team.find(params[:id])
    @team.verified = true
    @team.save!
    flash[:notice] = "#{@team.title} was successfully verified"
    TeamNotifications.deliver_verified(@team)
    redirect_to admin_teams_path
  end
  
  def request_changes
    @admin_team = Team.find(params[:id])
  end
  
  # make a change request
  def update
    @team = Team.find(params[:id])
    @team.needs_change = true
    @team.save!
    flash[:notice] = "Changes to #{@team.title} successfully requested"
    TeamNotifications.deliver_change_request(@team,params[:changes])
    redirect_to admin_teams_path
  end
  
  def reject
    @team = Team.find(params[:id])
  end
  
  def destroy
    @team = Team.find(params[:id])
    @team.destroy
    flash[:notice] = "#{@team.title} was successfully rejected"
    TeamNotifications.deliver_rejected(@team,params[:reason])
    redirect_to admin_teams_path
  end
  
  def delete_team
    team = params[:team]
    @team = Team.find(team[:id])
    @team.destroy
    flash[:notice] = "#{@team.title} was successfully deleted"
    redirect_to admin_teams_path
  end
end
