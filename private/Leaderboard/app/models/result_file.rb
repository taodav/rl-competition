class ResultFile < ActiveRecord::Base
  belongs_to :team_key
  
  validates_presence_of :team_key
  validates_associated :team_key
  
  acts_as_attachment :storage => :file_system, :file_system_path => 'rl-comp_results'
  
  before_save :set_filename
  
  def full_filename(thumbnail = nil)
    if self.filename.nil?
      self.set_filename
    end
    File.join(PACKAGE_BASE_PATH, attachment_options[:file_system_path], thumbnail_name_for(thumbnail))
  end
  
protected

  def set_filename
    self.filename = "event_#{self.team_key.event.id}_team_#{self.team_key.team.login}_#{Time.now}"
  end

end
