class SummaryResultsController < ApplicationController
  
  before_filter :admin_login_required, :except => [:show]
  
  def index
    @summary_results = SummaryResult.find(:all)
  end
  
  def show
    if params[:id].to_i == current_team.id and (params[:event_id] and params[:event_id].to_i < 7) or admin_authorized?
      @keys = TeamKey.find_all_by_team_id_and_event_id(params[:id],params[:event_id].to_i, :include => [:summary_result])
    else
      @keys = [] # TeamKey.find_all_by_team_id(params[:id], :include => [:summary_result])
    end
  end
  
end
