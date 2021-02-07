class MaxScore < ActiveRecord::Base
  belongs_to :team
  belongs_to :event
  belongs_to :summary_result
  
  def self.update_max(summary_result)
    event = summary_result.team_key.event
    team = summary_result.team_key.team
    ms = MaxScore.find_or_initialize_by_team_id_and_event_id(team.id,event.id)
    if ms.new_record?
      ms.summary_result = summary_result
      if event.name == "Mountain Car" or event.name == "Testing_MountainCar" or event.name == "Polyathalon" or event.name == "Testing_Polyathalon"
        ms.score = summary_result.episodes
      elsif ["Helicopter", "RTS", "Tetris", "Keepaway"].include?(event.name)
        ms.score = summary_result.total_return
      end
      ms.save
    else
      if event.name == "Mountain Car" or event.name == "Testing_MountainCar" or event.name == "Polyathalon" or event.name == "Testing_Polyathalon"
        if summary_result.episodes > ms.summary_result.episodes
          ms.summary_result = summary_result
          ms.score = summary_result.episodes
          ms.save
        end
      elsif ["Helicopter", "RTS", "Tetris", "Keepaway"].include?(event.name)
        if summary_result.total_return > ms.summary_result.total_return
          ms.summary_result = summary_result
          ms.score = summary_result.total_return
          ms.save
        end
      end
    end
  end
end
