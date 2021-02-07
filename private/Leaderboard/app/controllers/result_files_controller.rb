class ResultFilesController < ApplicationController
  before_filter :admin_login_required
  
  def show
    @result_file = ResultFile.find_by_team_key_id(params[:id])
    send_file @result_file.full_filename, :type => 'application/x-gzip'
  end
  
end
