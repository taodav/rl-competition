class KeysAvailableController < ApplicationController
  before_filter :admin_login_required
  
  def search
    @teams = Team.find(:all, :order => 'title ASC')
    @events = Event.find(:all, :order => 'name ASC')
    @keys_availables = KeysAvailable.find(:all, :include => [:team, :event], :order => 'teams.title ASC')
  end
  
  def edit_available
    @team = Team.find(params[:keys_available][:team])
    if @team.verified
      @keys_available = KeysAvailable.find_or_create(params[:keys_available][:team],params[:keys_available][:event])
    else
      @keys_available = nil
    end
  end
  
  def update
    @keys_available = KeysAvailable.find(params[:id])
    if @keys_available.update_attributes(params[:keys_available])
      flash[:notice] = "Keys available updated successfully"
      redirect_to admin_teams_path
    else
      flash[:error] = "There was an error updating the keys available"
      render :action => 'edit_available', :keys_available => {:event => @keys_available.event.id, :team => @keys_available.team.id}
    end
  end

end
