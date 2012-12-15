class Participant < ActiveRecord::Base
  attr_accessible :match_id, :match_outcome, :player_id, :status
  
  belongs_to :player
  belongs_to :match
  
end
