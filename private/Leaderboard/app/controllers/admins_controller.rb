class AdminsController < ApplicationController
  
  before_filter :admin_login_required

  # render new.rhtml
  def new
  end

  def create
    @admin = Admin.new(params[:admin])
    @admin.save!
    self.current_admin = @admin
    redirect_back_or_default('/')
    flash[:notice] = "Thanks for signing up!"
  rescue ActiveRecord::RecordInvalid
    render :action => 'new'
  end

end
