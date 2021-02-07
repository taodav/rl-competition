class PackagesController < ApplicationController
  
  before_filter :admin_login_required, :except => [:show]
  before_filter :team_login_required, :only => [:show]
  
  def index
    @packages = Package.find(:all, :conditions => {:parent_id => nil})
  end
  
  def new
    @package = Package.new
  end
  
  def create
    @package = Package.new(params[:package])
    if @package.save
      flash[:notice] = "Package was successfully created"
      redirect_to packages_path
    else
      flash[:notice] = "There was an error creating the package"
      render :action => 'new'
    end
  end
  
  def show
    if params[:id] == "1"
      params[:id] = Package.maximum("revision")
    end
    @package = Package.find_by_revision(params[:id])
    #send_file '/Users/mark/Pictures/180px-Girrobot.JPG', :type => 'image/jpeg', :disposition => 'inline'
    @download = Download.find_or_create_by_team_id_and_revision(current_team.id,params[:id])
    send_file @package.full_filename, :type => 'application/x-gzip'
    #send_file '/var/www/apps/rl-comp_packages/rl-competition-B497.tar.gz', :type => 'application/x-gzip'
  end
  
  def destroy
    @package = Package.find(params[:id])
    @package.destroy
    flash[:notice] = "Package successfully deleted"
    redirect_to packages_path
  end
  
end
