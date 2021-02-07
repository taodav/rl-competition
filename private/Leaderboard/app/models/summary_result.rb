class SummaryResult < ActiveRecord::Base
  belongs_to :team_key
  
  def self.compile_results(key)
    number_of_mdps = MdpResult.count(:conditions => {:team_key_id => key.id})
    episodes = MdpResult.calculate(:sum,:episodes,:conditions => {:team_key_id => key.id})
    total_return = MdpResult.calculate(:sum,:total_return,:conditions => {:team_key_id => key.id})
    total_steps = MdpResult.calculate(:sum,:total_steps,:conditions => {:team_key_id => key.id})
    return SummaryResult.new(:team_key => key, 
                              :episodes => episodes, 
                              :total_return => total_return, 
                              :total_steps => total_steps, 
                              :number_of_mdps => number_of_mdps)
  end
end
