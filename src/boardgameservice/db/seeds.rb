# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ name: 'Chicago' }, { name: 'Copenhagen' }])
#   Mayor.create(name: 'Emanuel', city: cities.first)

player1 = Player.new(email: "player1@test.com", password: "password", password_confirmation: "password")
player1.save
player2 = Player.new(email: "player2@test.com", password: "password", password_confirmation: "password")
player2.save
player3 = Player.new(email: "player3@test.com", password: "password", password_confirmation: "password")
player3.save
match = Match.new(status: "Matchmaking", message: "Wait for others", min_players: 3, max_players: 6)
#match.save
#participant = match.participants.create(status: "playing", player_id: player1.id)
match.players << [player1, player2, player3]
match.current_player = player1.email
match.save
