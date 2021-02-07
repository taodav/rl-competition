class KeysAvailable < ActiveRecord::Base
  belongs_to :team
  belongs_to :event
  
  validates_presence_of :team_id
  validates_presence_of :event_id
  validates_associated :team
  validates_associated :event
  validates_uniqueness_of :event_id, :scope => :team_id
  
  def self.find_or_create(team_id,event_id)
    keys_available = KeysAvailable.find_or_initialize_by_team_id_and_event_id(team_id,event_id)
    if keys_available.new_record?
      keys_available.num = keys_available.event.keys_per_week
      keys_available.save
    end
    return keys_available
  end
  
  def self.create_or_update(team,event)
    d = Time.now.next_week(:saturday) - 7.days
    if d > Time.now
      d = d - 7.days
    end
    keys_available = KeysAvailable.find_or_initialize_by_team_id_and_event_id(team.id,event.id)
    if keys_available.new_record? || (keys_available.updated_at < d && event.renew_weekly)
      keys_available.num = event.keys_per_week
    end
    keys_available.save
    return keys_available
  end

end
