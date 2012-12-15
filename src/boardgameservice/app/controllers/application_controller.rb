class ApplicationController < ActionController::Base
  
  before_filter :require_login
  
  def require_login
    head :unauthorized unless session[:player_id]
  end
end
