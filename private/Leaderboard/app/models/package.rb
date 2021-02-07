class Package < ActiveRecord::Base
  acts_as_attachment :storage => :file_system, :file_system_path => 'rl-comp_packages'
  
  def full_filename(thumbnail = nil)
    File.join(PACKAGE_BASE_PATH, attachment_options[:file_system_path], thumbnail_name_for(thumbnail))
  end
end
