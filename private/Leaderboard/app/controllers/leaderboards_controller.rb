class LeaderboardsController < ApplicationController
    
  def index
    @events = Event.find(:all)
  end
  
  def show
    @event = Event.find(params[:id])
    @limit = params[:limit]
    if @event.id < 7 or admin_authorized?
      @max_scores = MaxScore.find_all_by_event_id(@event.id, :order => "score DESC", :limit => params[:limit])
    else
      @max_scores = []
    end
    respond_to do |format|
      format.html { render :action => 'show', :layout => nil }
      format.js { render :partial => 'leaderboard' }
    end
  end
  
end
