# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ name: 'Chicago' }, { name: 'Copenhagen' }])
#   Mayor.create(name: 'Emanuel', city: cities.first)

player1 = Player.new(email: "testPlayer@example.com", password: "password", password_confirmation: "password")
player1.save
player2 = Player.new(email: "testPlayer2@example.com", password: "password", password_confirmation: "password")
player2.save
match = Match.new(status: "Matchmaking", message: "Wait for others", min_players: 2, max_players: 6)
#match.save
#participant = match.participants.create(status: "playing", player_id: player1.id)
match.players << player1 
match.players << player2
match.current_player = player1.email
match.save
