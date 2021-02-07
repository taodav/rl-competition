# Filters added to this controller apply to all controllers in the application.
# Likewise, all the methods added will be available for all controllers.

class ApplicationController < ActionController::Base
  # Pick a unique cookie name to distinguish our session data from others'
  session :session_key => '_Leaderboard_session_id'
  
  filter_parameter_logging :password
  
  # Be sure to include AuthenticationSystem in Application Controller instead
  include AuthenticatedSystem
end
