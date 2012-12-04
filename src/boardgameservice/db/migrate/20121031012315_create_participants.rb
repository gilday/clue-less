class CreateParticipants < ActiveRecord::Migration
  def change
    create_table :participants do |t|
      t.integer :player_id
      t.integer :match_id
      t.string :status
      t.string :match_outcome

      t.timestamps
    end
    add_index :participants, :player_id
    add_index :participants, :match_id
  end
end
