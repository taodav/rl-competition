class KeysController < ApplicationController
  
  before_filter :load_event
  
  # Key Available
  # (login, password, event_id)
  # status code: 200 OK => yes
  #              401 UNAUTHORIZED => no
  # Checks if there are any more keys left this week (k keys per week, reset to k every week).
  # 
  def new
    if (params[:login].nil? || params[:password].nil?)
      params[:login], params[:password] = get_auth_data
    end
    # keys_available is actually called twice here, once by valid? and once explicitly...
    # should optimize.. store keys_available in @key object during validation? DONE
    @key = TeamKey.new(:login => params[:login], :password => params[:password], :event => @event)
    if @key.valid? || @key.keys_available == 0
      render :text => "#{@key.keys_available}\n"
    else
      render :nothing => true, :status => 401
    end
  end

  # MakeKey
  # (login, password, event_id)
  # status code: 200 OK => yes
  #              401 UNAUTHORIZED => no
  # Checks if there are any more keys left this week (k keys per week, reset to k every week).
  # If a key is available, creates it and sends back the key_value 
  #  (long string of random characters, used for id)
  #
  def create
    if (params[:login].nil? || params[:password].nil?)
      params[:login], params[:password] = get_auth_data
    end
    @key = TeamKey.new(:login => params[:login], :password => params[:password], :event => @event)
    if @key.save
      render :action => 'create', :layout => false
    else
      render :nothing => true, :status => 401
    end
  end
  
protected

  def load_event
    @event = Event.find(params[:event_id])
  end

end
