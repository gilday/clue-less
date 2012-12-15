class MatchesController < ApplicationController
  
  respond_to :json
  
  # GET /matches
  # GET /matches.json
  def index
    @matches = Match.all
    respond_with @matches;
  end

  def show
    @match = Match.find_by_id(params[:id])
    respond_with @match
  end

  def create
  end

  def update
    @match = Match.find_by_id(params[:id])
    if @match.update_attributes(params[:match])
      head :ok
    else 
      head :bad_request
    end
  end

  def delete
  end
end
