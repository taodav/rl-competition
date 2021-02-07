class PhoneHomeAuthenticationController < ApplicationController

  # isAuthenticated
  # (login,password) => true || false
  # Status codes: 200 OK => true
  #               401 UNAUTHORIZED => false
  def create
    if (params[:login].nil? || params[:password].nil?)
      params[:login], params[:password] = get_auth_data
    end
    @team = Team.authenticate(params[:login], params[:password])
    if @team
      render :nothing => true
    else
      render :nothing => true, :status => 401
    end
  end

end
