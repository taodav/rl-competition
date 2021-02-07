class TeamsController < ApplicationController

  before_filter :team_login_required, :only => [:edit, :update, :update_password]

  def index
    @teams = Team.find(:all, :conditions => {:verified => true}, :include => :members)
    @package = Package.find(:first, :limit => 1, :order => 'created_at DESC')
    @events = Event.find(:all, :conditions => 'id < 7')
  end

  # render new.rhtml
  def new
    @team = Team.new
    @team.members.build(:is_leader => true)
  end

  def create
    @team = Team.new(params[:team])
    @team.verified = false
    @team.save!
    self.current_team = @team
    flash[:notice] = "Thanks for signing up!"
    TeamNotifications.deliver_welcome(@team)
    redirect_to welcome_team_path(@team)
  rescue ActiveRecord::RecordInvalid
    render :action => 'new'
  end
  
  def welcome
    @team = Team.find(params[:id])
  end
  
  def forgot_password
  end
  
  def send_password
    @team = Team.find_by_title(params[:title]) || Team.find_by_login(params[:title])
    if !@team
      flash.now[:notice] = "Team or login name not found. Please ensure you have entered the correct information."
      render :action => 'forgot_password'
    else 
      @team.set_access_key
      @team.save!
      TeamNotifications.deliver_password(@team, edit_password_team_url(:id => @team.id, :access_key => @team.access_key))
    end
  end
  
  def edit_password
    @team = Team.find(params[:id])
    if !@team.verifies?(params[:access_key])
      return self.access_denied
    else
      self.current_team = @team
    end
  end
  
  def update_password
    if params[:id].to_i != current_team.id
      return self.access_denied
    end
    @team = Team.find(params[:id])
    if @team.update_attributes(params[:team])
      flash[:notice] = "Password successfully changed"
      redirect_to teams_path
    else
      self.access_denied
    end
  end
  
  def edit
    if params[:id].to_i != current_team.id
      return self.access_denied
    end
    @team = Team.find(params[:id])
  end
  
  def update
    if params[:id].to_i != current_team.id
      return self.access_denied
    end
    @team = Team.find(params[:id])
    if @team.update_attributes(params[:team])
      flash[:notice] = "#{@team.title} was successfully updated"
      @team.needs_change = false
      @team.save!
      redirect_to teams_path
    else
      render :action => 'edit'
    end
  end

end
