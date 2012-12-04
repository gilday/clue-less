class Player < ActiveRecord::Base
  attr_accessible :gamertag, :password, :password_confirmation
  attr_protected :password_digest
  has_secure_password
  
  validates :gamertag, presence: true, uniqueness: true
  validates_presence_of :password, :on => :create
  
  has_many :participants
  has_many :matches, through: :participants
  
  def as_json(options={})
    options[:except] ||= [:password_digest]
    super(options)
  end
end
