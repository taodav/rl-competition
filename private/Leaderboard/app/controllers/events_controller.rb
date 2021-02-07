class EventsController < ApplicationController
  
  before_filter :admin_login_required
  
  def index
    if admin_authorized?
      @events = Event.find(:all)
    else
      @events = Event.find(:all, conditions => "id < 7")
    end
  end
  
  def new
    @event = Event.new
  end
  
  def create
    @event = Event.new(params[:event])
    if @event.save!
      flash[:notice] = "Event created successfully"
      redirect_to events_path
    else
      flash[:error] = "There was an error creating the event"
      render :action => 'new'
    end
  end
  
  def edit
    @event = Event.find(params[:id])
  end
  
  def update
    @event = Event.find(params[:id])
    if @event.update_attributes(params[:event])
      flash[:notice] = "Event updated successfully"
      redirect_to events_path
    else
      flash[:error] = "There was an error updating the event"
      render :action => 'edit'
    end
  end

end
