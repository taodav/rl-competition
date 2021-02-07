require File.dirname(__FILE__) + '/../test_helper'
require 'leaderboard_controller'

# Re-raise errors caught by the controller.
class LeaderboardController; def rescue_action(e) raise e end; end

class LeaderboardControllerTest < Test::Unit::TestCase
  def setup
    @controller = LeaderboardController.new
    @request    = ActionController::TestRequest.new
    @response   = ActionController::TestResponse.new
  end

  # Replace this with your real tests.
  def test_truth
    assert true
  end
end
