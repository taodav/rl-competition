require 'digest/sha1'
require 'md5'
class Team < ActiveRecord::Base
  # Virtual attribute for the unencrypted password
  attr_accessor :password
  
  belongs_to  :leader, :class_name => 'Member', :foreign_key => :leader_id
  has_one     :leader, :class_name => 'Member', :foreign_key => :team_id, :conditions => "is_leader = true"
  has_many    :members, :dependent => :destroy
  has_many    :team_keys, :dependent => :destroy
  has_many    :keys_availables, :dependent => :destroy
  has_many    :max_scores, :dependent => :destroy
  has_many    :downloads, :dependent => :destroy

  validates_presence_of     :login, :title
  validates_presence_of     :password,                   :if => :password_required?
  validates_presence_of     :password_confirmation,      :if => :password_required?
  validates_length_of       :password, :within => 4..40, :if => :password_required?, :allow_nil => true, :if => Proc.new{|team| team.password != ''}
  validates_confirmation_of :password,                   :if => :password_required?
  validates_length_of       :title,    :within => 3..200, :allow_nil => true, :if => Proc.new{|team| team.title != ''}
  validates_length_of       :url,      :within => 9..300, :allow_nil => true, :if => Proc.new{|team| team.url != ''}
  validates_length_of       :login,    :within => 3..40, :allow_nil => true, :if => Proc.new{|team| team.login != ''}
  validates_uniqueness_of   :login, :title, :case_sensitive => false
  #validates_associated :members
  
  before_save :encrypt_password
  before_save :fix_url
  after_update :save_members
  
  # prevents a user from submitting a crafted form that bypasses activation
  # anything else you want your user to change should be added here.
  attr_accessible :title, :login, :url, :password, :password_confirmation, :member_attributes
  
  def member_attributes=(member_attributes)
    member_attributes.each do |attributes|
      if attributes[:id].blank?
        members.build(attributes)
      else
        member = members.detect { |m| m.id == attributes[:id].to_i }
        member.attributes = attributes
      end
    end
  end
  
  # Authenticates a user by their login name and unencrypted password.  Returns the user or nil.
  def self.authenticate(login, password)
    u = find_by_login(login) # need to get the salt
    u && u.authenticated?(password) ? u : nil
  end
  
  def self.authenticate_for_run(login,password)
    t = Team.authenticate(login,password)
    t && t.verified? ? t : nil
  end

  # Encrypts some data with the salt.
  def self.encrypt(password, salt)
    Digest::SHA1.hexdigest("--#{salt}--#{password}--")
  end

  # Encrypts the password with the user salt
  def encrypt(password)
    self.class.encrypt(password, salt)
  end

  def authenticated?(password)
    crypted_password == encrypt(password)
  end

  def remember_token?
    remember_token_expires_at && Time.now.utc < remember_token_expires_at 
  end

  # These create and unset the fields required for remembering users between browser closes
  def remember_me
    remember_me_for 2.weeks
  end

  def remember_me_for(time)
    remember_me_until time.from_now.utc
  end

  def remember_me_until(time)
    self.remember_token_expires_at = time
    self.remember_token            = encrypt("#{title}--#{remember_token_expires_at}")
    save(false)
  end

  def forget_me
    self.remember_token_expires_at = nil
    self.remember_token            = nil
    save(false)
  end
  
  def set_access_key
    self.access_key = MD5.hexdigest((object_id + rand(255)).to_s)
  end
  
  def verifies?(key)
    if self.access_key == key
      self.access_key = nil
      self.save
      return true
    else
      return false
    end
  end
  
  def verified?
    self.access_key.nil? and self.verified
  end

  protected
  
    # before filter 
    def encrypt_password
      return if password.blank?
      self.salt = Digest::SHA1.hexdigest("--#{Time.now.to_s}--#{login}--") if new_record?
      self.crypted_password = encrypt(password)
    end
    
    def fix_url
      if url == '':
        url = nil
      end
    end
    
    def password_required?
      crypted_password.blank? || !password.blank?
    end
 
    def save_members
      members.each do |m|
        if m.should_destroy?
          m.destroy
        else
          m.save(false)
        end
      end
    end
end
