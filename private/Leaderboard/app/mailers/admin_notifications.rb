class AdminNotifications < ActionMailer::Base
  
  def invalid_result_file(result_file, sent_at = Time.now)
    setup
    subject   "[RL-Competition] Invalid Result File"
    body      :result_file => result_file
    to_admins
  end

  protected

    def setup(sent_at = Time.now)
      from       'brian@rl-competition.org'
      sent_on    sent_at
      #@headers    = {}
    end

    def to_admins
      admins = Admin.find(:all)
      emails = admins.collect { |admin| admin.email }
      recipients emails
    end
    
end
