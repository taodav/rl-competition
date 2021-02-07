require 'mongrel_cluster/recipes'

set :application, "Leaderboard"
set :domain,      "rlai.cs.ualberta.ca"
#set :repository,  "http://#{domain}/rlai_repo/rl_libraryNEW/#{application}"
set :repository,  "http://brian.tannerpages.com/rlcomplibrary/trunk/Leaderboard"
set :deploy_to,   "/var/www/apps/#{application}"
set :user,        "btanner"
set :rails_env,   "production"
set :use_sudo,    true
set :mongrel_conf, "#{current_path}/config/mongrel_cluster.yml"

# If you aren't deploying to /u/apps/#{application} on the target
# servers (which is the default), you can specify the actual location
# via the :deploy_to variable:
# set :deploy_to, "/var/www/#{application}"

# If you aren't using Subversion to manage your source code, specify
# your SCM below:
# set :scm, :subversion

role :app, domain
role :web, domain
role :db,  domain, :primary => true

task :before_restart do
#  run "chgrp -R mongrel /var/www/apps/#{application}/shared"
#  run "chmod -R g+rxw /var/www/apps/#{application}/shared"
#  run "chgrp -R mongrel /var/www/apps/#{application}/current"
#  run "chgrp -R mongrel /var/www/apps/#{application}/releases"
end
