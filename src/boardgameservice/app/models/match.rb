class Match < ActiveRecord::Base
  attr_accessible :match_data, :max_players, :message, :min_players, :status
  
  has_many :participants
  has_many :players, through: :participants
  
  def as_json(options={})
    options[:include] ||= { :participants => { :include => { :player => {except: :password_digest }} } }
    super(options)
  end
end
