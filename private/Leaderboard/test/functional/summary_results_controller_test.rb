require File.dirname(__FILE__) + '/../test_helper'
require 'summary_results_controller'

# Re-raise errors caught by the controller.
class SummaryResultsController; def rescue_action(e) raise e end; end

class SummaryResultsControllerTest < Test::Unit::TestCase
  def setup
    @controller = SummaryResultsController.new
    @request    = ActionController::TestRequest.new
    @response   = ActionController::TestResponse.new
  end

  # Replace this with your real tests.
  def test_truth
    assert true
  end
end
