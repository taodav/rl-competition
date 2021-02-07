require File.dirname(__FILE__) + '/../test_helper'
require 'mdp_results_controller'

# Re-raise errors caught by the controller.
class MDPResultsController; def rescue_action(e) raise e end; end

class MDPResultsControllerTest < Test::Unit::TestCase
  def setup
    @controller = MDPResultsController.new
    @request    = ActionController::TestRequest.new
    @response   = ActionController::TestResponse.new
  end

  # Replace this with your real tests.
  def test_truth
    assert true
  end
end
