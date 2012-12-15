class PlayersController < ApplicationController
  
  respond_to :json
  
  # GET /players
  # GET /players.json
  def index
    @players = Player.all
    respond_with @players 
  end

  # POST /players
  # POST /players.json
  def create
    @player = Player.new(params[:player])

    if @player.save
      head :created
    else 
      respond_with @player.errors, status: :unprocessable_entity 
    end
  end
  
  # GET /players/:player_id
  # GET /players/:player_id.json
  def show
    @player = Player.find_by_id(params[:id])
    respond_with @player
  end
  
end
