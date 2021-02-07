class DownloadsController < ApplicationController
  
  before_filter :admin_login_required
  
  def index
    @teams = Team.find(:all)
  end
  
end
