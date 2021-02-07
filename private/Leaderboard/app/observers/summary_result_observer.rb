class SummaryResultObserver < ActiveRecord::Observer
  
  def after_save(summary_result)
    MaxScore.update_max(summary_result)
  end
  
end
