class ChangeGamertagInPlayers < ActiveRecord::Migration
  
  def change
    rename_column :players, :gamertag, :email
  end
end
