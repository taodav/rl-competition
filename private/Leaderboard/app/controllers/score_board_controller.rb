class ScoreBoardController < ApplicationController

  def index
    @events = Event.find(:all)
  end

  def show
    @episode = params[:episode].to_i
    @event_id = params[:event].to_i
    @scores = ScoreBoardResult.find_all_by_event_id_and_episode_num(@event_id, @episode,
                                                                    :order => 'score_board_results.score DESC',
                                                                    :include => [:team,:event],
                                                                    :limit => 10 )
    if @scores.size > 0
      @event_name = @scores[0].event.name
      render :partial => "show"
    else
      @event_name = "NO EVENT SPECIFIED"
      render :nothing => true
    end
  end
end
