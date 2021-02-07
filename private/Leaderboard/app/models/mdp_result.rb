class MdpResult < ActiveRecord::Base
  belongs_to :team_key
  
  validates_presence_of :team_key_id
  validates_associated :team_key
  
  
  def self.parse_result_file(team_key, result_file)
    section = 0
    exp_stats = {}
    summary_stats = {}
    f = StringIO.new(result_file.attachment_data)
      Zlib::GzipReader.new(f).each_line do |line|
        line = line.strip.downcase
        if line =~ /#####/
          section = section + 1
        elsif section == 0
          key,value = line.split(':',2)
          exp_stats[key] = value
        #elsif section == 1
        elsif section == 2
          stats = line.split
          for stat in stats do
            key,value = stat.split(':',2)
            summary_stats[key] = value
          end
        end
      end
    mdp_id = exp_stats["mdp"]
    team_name = exp_stats["username"]
    event_id = exp_stats["event"].to_i + 1
    episodes = summary_stats["episodes"]
    total_return = summary_stats["return"]
    total_steps = summary_stats["steps"]
    
    team = Team.find_by_login(team_name)
    event = Event.find(event_id)
    
    if team.id != team_key.team.id || event.id != team_key.event.id
      return nil
    end
    
    mdp_result = MdpResult.new(:mdp_id => mdp_id,
                                    :team_key => team_key,
                                    :episodes => episodes,
                                    :total_return => total_return,
                                    :total_steps => total_steps)
    return mdp_result
  end
end
