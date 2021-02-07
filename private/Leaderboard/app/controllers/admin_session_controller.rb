# This controller handles the login/logout function of the site.  
class AdminSessionController < ApplicationController

  # render new.rhtml
  def new
  end

  def create
    self.current_admin = Admin.authenticate(params[:login], params[:password])
    if admin_logged_in?
      if params[:remember_me] == "1"
        self.current_admin.remember_me
        cookies[:auth_token] = { :value => self.current_admin.remember_token , :expires => self.current_admin.remember_token_expires_at }
      end
      redirect_back_or_default(admin_teams_path)
      flash[:notice] = "Logged in successfully"
    else
      render :action => 'new'
    end
  end

  def destroy
    self.current_admin.forget_me if admin_logged_in?
    cookies.delete :auth_token
    reset_session
    flash[:notice] = "You have been logged out."
    redirect_back_or_default(teams_path)
  end
end
