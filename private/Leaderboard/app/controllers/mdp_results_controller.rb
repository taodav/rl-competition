class MdpResultsController < ApplicationController
  
  before_filter :admin_login_required, :except => [:show]
  
  def index
    @mdp_results = MdpResult.find(:all)
  end
  
  def show
    @mdp_results = MdpResult.find_all_by_team_key_id(params[:id])
  end
end
