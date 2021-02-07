require File.dirname(__FILE__) + '/../test_helper'
require 'result_file_controller'

# Re-raise errors caught by the controller.
class ResultFileController; def rescue_action(e) raise e end; end

class ResultFileControllerTest < Test::Unit::TestCase
  def setup
    @controller = ResultFileController.new
    @request    = ActionController::TestRequest.new
    @response   = ActionController::TestResponse.new
  end

  # Replace this with your real tests.
  def test_truth
    assert true
  end
end
