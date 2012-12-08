class AddColumnParticipantIdToMatch < ActiveRecord::Migration
  def up
    add_column :matches, :current_player, :string
  end
  
  def down
    remove_column :matches, :current_player
  end
end
