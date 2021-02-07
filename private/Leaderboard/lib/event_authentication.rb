module EventAuthentication
  protected

  # Returns true or false if the team is logged in.
  # Preloads @current_team with the team model if they're logged in.
  def valid_token?
    current_event_token != :false
  end

  # Accesses the current team from the session.  Set it to :false if login fails
  # so that future calls do not hit the database.
  def current_event_token
    @current_event_token ||= (token_from_session || :false)
  end

  # Store the given team in the session.
  def current_event_token=(new_token)
    session["#{@event.name}_token".to_sym] = (new_token.nil? || new_token.is_a?(Symbol)) ? nil : new_token.id
    @current_event_token = new_token
  end
  
  # Check if the team is authorized
  #
  # Override this method in your controllers if you want to restrict access
  # to only a few actions or if you want to check if the team
  # has the correct rights.
  #
  # Example:
  #
  #  # only allow nonbobs
  #  def authorized?
  #    current_team.login != "bob"
  #  end
  def token_authorized?
    valid_token?
  end

  # Filter method to enforce a login requirement.
  #
  # To require logins for all actions, use this in your controllers:
  #
  #   before_filter :login_required
  #
  # To require logins for specific actions, use this in your controllers:
  #
  #   before_filter :login_required, :only => [ :edit, :update ]
  #
  # To skip this in a subclassed controller:
  #
  #   skip_before_filter :login_required
  #
  def token_required
    token_authorized? || auto_access_denied
  end
  
  # Redirect as appropriate when an access request fails.
  #
  # The default action is to redirect to the login screen.
  #
  # Override this method in your controllers if you want to have special
  # behavior in case the team is not authorized
  # to access the requested action.  For example, a popup window might
  # simply close itself.
  def auto_access_denied
    render :nothing => true, :status => 401
    false
  end
  
  # Called from #current_user.  First attempt to login by the user id stored in the session.
  def token_from_session
    sym = "#{@event.name}_token".to_sym
    self.current_event_token = Token.find_by_id(session[sym]) if session[sym]
  end
  
  def expire_token
    sym = "#{@event.name}_token".to_sym
    session[sym] = nil
  end
  
end