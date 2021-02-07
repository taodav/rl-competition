class TeamNotifications < ActionMailer::Base
  
  def welcome(team, send_at = Time.now)
    setup
    subject    "[RL-Competition] Thanks for signing up!"
    body       :team => team
    to_team(team)
  end
  
  def password(team, url, sent_at = Time.now)
    setup
    subject    "[RL-Competition] Please change your team password"
    body       :team => team, :url => url
    to_team(team)
  end
    
  def verified(team, sent_at = Time.now)
    setup
    subject    "[RL-Competiton] Your team has been verified!"
    body       :team => team
    to_team(team)
  end
  
  def change_request(team, changes='',sent_at = Time.now)
    setup
    subject    "[RL-Competiton] Your team registration is incomplete"
    body       :team => team, :changes => changes
    to_team(team)
  end
  
  def rejected(team, reason="", sent_at = Time.now)
    setup
    subject    "[RL-Competiton] Your team has been rejected"
    body       :team => team, :reason => reason
    to_team(team)
  end
  
  def announcement(team, announcement, sent_at = Time.now)
    setup
    subject   "[RL-Competition] Announcement"
    body      :team => team, :announcement => announcement
    to_team(team)
  end

protected

  def setup(sent_at = Time.now)
    from       'brian@rl-competition.org'
    sent_on    sent_at
    #@headers    = {}
  end

  def to_team(team)
    emails = team.members.collect { |member| member.email }
    recipients emails
  end

end
