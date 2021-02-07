require 'md5'
class TeamKey < ActiveRecord::Base
  # virtual attributes for login and password
  attr_accessor :login, :password
  
  belongs_to :team
  belongs_to :event
  has_one :summary_result, :dependent => :destroy
  has_many :result_files, :dependent => :destroy
  has_many :mdp_results, :dependent => :destroy
  
  validates_presence_of :login, :password, :on => :create
  validates_presence_of :event_id
  validates_associated :event
  validates_associated :team
  # more validations below in the validate method
  
  # Create the actual key. Long string sent back to client to identify themselves
  before_create :generate_key_value
  before_create :decrement_keys_available
  
  # When trying to use a key (get a new mdp, or submit results)
  # make sure the key corresponds with the login/password
  def self.authenticate(login, password, key_value)
    k = find_by_key_value(key_value)
    raise "InvalidKey" if k.nil?
    k && Team.authenticate(login, password) == k.team ? k : nil
  end
  
  # Stored from validation just for optimization (used in KeyAvailable)
  def keys_available
    if @keys_available.nil?
      return 0
    else
      return @keys_available.num
    end
  end
  
protected

  def validate_on_create
    errors.add("team", "does not authenticate") unless team_authenticates?
    if team
      errors.add_to_base("key not available") unless key_available?
    end
  end

  def team_authenticates?
    team = Team.authenticate_for_run(login,password)
    if team.nil?
      return false
    end
    self.team_id = team.id
    return true
  end
  
  # check if a key can be created
  # key can be created if there are less than k since last saturday
  # ie. k keys per week, gets reset to k every saturday
  # here k = MAX_KEYS_PER_WEEK (a global, set in config/environment.rb)
  def key_available?
    if team.nil? || event.nil? || team.verified == 0
      return false
    end
    @keys_available = KeysAvailable.create_or_update(team,event)
    return @keys_available.num > 0
    # active keys: keys that have been distributed this week
    #active_keys = TeamKey.find_all_by_team_id_and_event_id(team.id,event.id, 
    #          :conditions => [ "created_at > ?", Time.now.beginning_of_week - 2.days ])
    #@keys_available =  event.keys_per_week - active_keys.size
    #return @keys_available > 0
  end

  def generate_key_value
    self.key_value = MD5.hexdigest((object_id + rand(255)).to_s) + login + event.id.to_s
  end
  
  def decrement_keys_available
    if key_available?
      @keys_available.num = @keys_available.num - 1
      @keys_available.save
    end
  end

end
