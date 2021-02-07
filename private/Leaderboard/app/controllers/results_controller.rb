
class ResultsController < ApplicationController
  
  before_filter :get_login
  # All methods in this class require a valid key (and corresponding login and pass)
  before_filter :check_key
  
  before_filter :check_runs, :only => [:new, :get_jar]
  
  # Gets info about the next jar. One per line:
  # a) event id
  # b) jar file name
  # c) mdp_id
  # d) number of steps to run
  # e) log frequency
  #Update btanner May 29 2008
  #We're doing a hack where the testing files are just proving files offset by 50, so need
  #to update the current_mdp that we are SENDING here and in get_jar
  def new
    current_mdp = @key.current_mdp
    if @key.event.name.include? "Testing_"
      current_mdp = current_mdp + 50
    end

    jar_name = "#{@key.event.short_name}PMDP"
    jar_name += "#{current_mdp}.#{@key.event.extension}"

    render :text => "#{@key.event.id}\n#{jar_name}\n#{current_mdp}\n#{@key.event.num_steps}\n#{@key.event.log_frequency}\n"
  end
  
  # Gets the next jar, if there is one left
  # Increments the current_mdp, so that next request will give the next jar
  # If max mdps for this key has been reached then 401 UNAUTHORIZED
  def get_jar
    current_mdp = @key.current_mdp
    txtEventExtension="";
    if @key.event.name.include? "Testing_"
      current_mdp = current_mdp + 50
    end
    @key.current_mdp = @key.current_mdp + 1
    @key.save!
    dir = "/var/www/apps/rl-comp_jars/"
    if !params[:rlVizVersion].nil? and !params[:provingAppVersion].nil? and !params[:phoneHomeVersion].nil?
       if params[:rlVizVersion] == "1.21"
        dir = dir + "release-3"
      elsif params[:rlVizVersion] == "1.3"
        dir = dir + "release-jan25"
      else
        dir = dir + "release-2"
      end
    else
      dir = dir + "release-1"
    end
    jar_name = "#{@key.event.short_name}PMDP"
    jar_name += "#{current_mdp}.#{@key.event.extension}"

    send_file "#{dir}/#{jar_name}"
  end
  
  # Outputs one integer: Number of mdps (jars) left with this key
  def mdps_remaining
    render :text => "#{@key.event.mdps_per_key - @key.current_mdp}\n"
  end
  
  # Retrieve results from client
  # First just store in a file (with a db entry to remember that file)
  # If that works then parse the file and store the actual data in the db
  # If parsing fails, 401 UNAUTHORIZED is returned, but the file still has been
  # saved.
  def create
    @result_file = ResultFile.new(:content_type => 'application/x-gzip', 
                                        :team_key => @key,
                                        :uploaded_data => params[:results])
    if @result_file.save
      render :nothing => true
    else
      render :nothing => true, :status => 401
    end
  end

protected

  def get_login
    if (params[:login].nil? || params[:password].nil?)
      params[:login], params[:password] = get_auth_data
    end
  end
  
  # Make sure the key is valid and corresponds with login and password
  def check_key
    # invalid key => 404 NOT FOUND, not authenticated => 401 NOT AUTHORIZED
    @key = TeamKey.authenticate(params[:login],params[:password],params[:key_id])
    return @key.nil? ? access_denied : true
  rescue RuntimeError
    render :nothing => true, :status => 404
  end
  
  def access_denied
    render :nothing => true, :status => 401
    return false
  end
  
  def check_runs
    if @key.event.mdps_per_key == @key.current_mdp
      render :nothing => true, :status => 401
      return false
    end
    return true
  end

end
