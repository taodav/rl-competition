class ResultFileObserver < ActiveRecord::Observer
  
  def after_save(result_file)
    key = result_file.team_key
    mdp_result = MdpResult.parse_result_file(key, result_file)
    if !mdp_result.save
      #mail admins
      AdminNotifications.deliver_invalid_result_file(@result_file)
    end
  end

end
