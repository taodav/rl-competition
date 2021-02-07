class MdpResultObserver < ActiveRecord::Observer

  def after_save(mdp_result)
    key = mdp_result.team_key
    if key.event.mdps_per_key - key.current_mdp == 0
      summary_result = SummaryResult.compile_results(key)
      summary_result.save!
    end
  end
end
