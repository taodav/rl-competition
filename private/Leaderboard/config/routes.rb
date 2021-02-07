ActionController::Routing::Routes.draw do |map|

  map.connect "score_board/:action/:id", :controller => 'score_board'

  map.connect '', :controller => 'teams', :action => 'index'

  map.resources :teams, :collection => {:forgot_password => :get, :send_password => :put}, 
              :member => {:welcome => :get, :edit_password => :get, :update_password => :put}
  map.resource :session
  map.resource :phone_home_authentication

  map.resources :admins
  map.resource :admin_session
  map.resources :admin_teams, :collection => {:announce => :get, :send_announcement => :put, :delete_team => :post},
                              :member => {:verify => :put, :request_changes => :get, :reject => :get}

  map.resources :packages
  map.resources :downloads
  map.resources :mdp_results
  map.resources :summary_results, :name_prefix => 'all_'
  map.resources :leaderboards
  map.resources :result_files
  map.resources :events do |events|
    events.resource :keys, :collection => {:trials_left => :get}
    events.resources :summary_results
  end
  
  map.resources :keys_available, :collection => {:search => :get, :edit_available => :post}
  
  map.resources :keys do |keys|
    keys.resources :results, :collection => {:mdps_remaining => :get, :get_jar => :get}
  end
  
  map.signup '/signup', :controller => 'teams', :action => 'new'
  map.login  '/login', :controller => 'session', :action => 'new'
  map.logout '/logout', :controller => 'session', :action => 'destroy'

end
