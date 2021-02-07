require File.dirname(__FILE__) + '/../test_helper'
require 'admin_teams_controller'

# Re-raise errors caught by the controller.
class AdminTeamsController; def rescue_action(e) raise e end; end

class AdminTeamsControllerTest < Test::Unit::TestCase
  def setup
    @controller = AdminTeamsController.new
    @request    = ActionController::TestRequest.new
    @response   = ActionController::TestResponse.new
  end

  # Replace this with your real tests.
  def test_truth
    assert true
  end
end
