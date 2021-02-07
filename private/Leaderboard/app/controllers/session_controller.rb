# This controller handles the login/logout function of the site.  
class SessionController < ApplicationController

  # render new.rhtml
  def new
  end

  def create
    self.current_team = Team.authenticate(params[:login], params[:password])
    if team_logged_in?
      if params[:remember_me] == "1"
        self.current_team.remember_me
        cookies[:auth_token] = { :value => self.current_team.remember_token , :expires => self.current_team.remember_token_expires_at }
      end
      redirect_back_or_default(teams_path)
      flash[:notice] = "Logged in successfully"
    else
      flash[:error] = "Invalid login or password"
      render :action => 'new'
    end
  end

  def destroy
    self.current_team.forget_me if team_logged_in?
    cookies.delete :auth_token
    reset_session
    flash[:notice] = "You have been logged out."
    redirect_back_or_default(teams_path)
  end
end
