class CreateMatches < ActiveRecord::Migration
  def change
    create_table :matches do |t|
      t.string :status
      t.text :message
      t.text :match_data
      t.integer :min_players
      t.integer :max_players

      t.timestamps
    end
  end
end
