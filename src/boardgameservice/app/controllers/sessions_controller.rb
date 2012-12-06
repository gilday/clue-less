class SessionsController < ApplicationController

  respond_to :json
  skip_before_filter :require_login

  # POST /session
  def create
    player = Player.find_by_email(params[:email])
    if player && player.authenticate(params[:password])
      session[:player_id] = player.id
      head :created
    else
      head :bad_request 
    end
  end
  
  # DELETE /session
  def destroy
    session[:player_id] = nil
    head :ok
  end
end