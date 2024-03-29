module AuthenticatedTestHelper
  # Sets the current team in the session from the team fixtures.
  def login_as(team)
    @request.session[:team] = team ? teams(team).id : nil
  end

  def authorize_as(user)
    @request.env["HTTP_AUTHORIZATION"] = user ? "Basic #{Base64.encode64("#{users(user).login}:test")}" : nil
  end

  # taken from edge rails / rails 2.0.  Only needed on Rails 1.2.3
  def assert_difference(expressions, difference = 1, message = nil, &block)
    expression_evaluations = [expressions].flatten.collect{|expression| lambda { eval(expression, block.binding) } } 
    
    original_values = expression_evaluations.inject([]) { |memo, expression| memo << expression.call }
    yield
    expression_evaluations.each_with_index do |expression, i|
      assert_equal original_values[i] + difference, expression.call, message
    end
  end

  # taken from edge rails / rails 2.0.  Only needed on Rails 1.2.3
  def assert_no_difference(expressions, message = nil, &block)
    assert_difference expressions, 0, message, &block
  end
end