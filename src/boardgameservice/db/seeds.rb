# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ name: 'Chicago' }, { name: 'Copenhagen' }])
#   Mayor.create(name: 'Emanuel', city: cities.first)

player1 = Player.new(gamertag: "player1", password: "password", password_confirmation: "password")
match = Match.new(status: "Matchmaking", message: "Wait for others", min_players: 2, max_players: 6)
player1.matches << match
player1.save
match.save
