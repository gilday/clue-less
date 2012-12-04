# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended to check this file into your version control system.

ActiveRecord::Schema.define(:version => 20121031012315) do

  create_table "matches", :force => true do |t|
    t.string   "status"
    t.text     "message"
    t.text     "match_data"
    t.integer  "min_players"
    t.integer  "max_players"
    t.datetime "created_at",  :null => false
    t.datetime "updated_at",  :null => false
  end

  create_table "participants", :force => true do |t|
    t.integer  "player_id"
    t.integer  "match_id"
    t.string   "status"
    t.string   "match_outcome"
    t.datetime "created_at",    :null => false
    t.datetime "updated_at",    :null => false
  end

  add_index "participants", ["match_id"], :name => "index_participants_on_match_id"
  add_index "participants", ["player_id"], :name => "index_participants_on_player_id"

  create_table "players", :force => true do |t|
    t.string   "gamertag"
    t.string   "password_digest"
    t.datetime "created_at",      :null => false
    t.datetime "updated_at",      :null => false
  end

end
