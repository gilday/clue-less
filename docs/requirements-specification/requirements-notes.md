Clue-less Software Requirements Specification

The Butler Did It

# Glossary #
This will serve to clarify the language used in the requirements

1. Candidate: a player in a game who has yet to make a false accusation and is therefore a candidate to win the game.
2. Participant: a player in a game who has made a false accusation and is therefore participating in the game to disprove the suggestions and accusations of the remaining candidates.
3. Active player: a player, who is still a contender, and it is currently this player's turn.

# Common Preconditions #

Name: Player is in game
ID: P01
Actor: player
Preconditions:
Conditions: User is authenticated and player is playing a game

Name: Contender's turn
ID: P02
Actor: player
Preconditions: P01
Conditions: The player is still a contender in the game and it is the player's turn.

# Use Cases #

## Playing the Game ##

Name: Player navigates board
ID: U01
Actor: player
Preconditions: P02. The player's pawn is adjacent to a room or vacant hallway. 
Postconditions: Player has moved to an adjacent space or has decided to forfeit this navigation and is therefore in the same square.

Name: Player makes a suggestion
ID: U02
Actor: all players
Preconditions: P02. Player is in a room.
Postconditions: Other players have been given a chance to disprove the suggestion in a serial operation iterating in a clockwise direction from the player making the suggestion. All players have been notified about the disproving players' ability or inability to provide a clue to the suggester. If a disproving player has revealed a clue to the suggester, the system makes the suggester aware of the clue.

Name: Player responds to a suggestion
ID: U05
Actor: disproving player
Preconditions: P01. Another player in the game has made a suggestion. This player (the one disproving the suggestion) is dequeued from the disproval queue.
Postconditions: The disproving player has revealed a clue to disprove the suggestion. All other players are made aware that this player has disproved the suggestion. The suggester is made aware of the clue revealed. The suggestion round completes and play returns to the suggester. 

Name: Player is unable to disprove a suggestion
ID: U06
Actor: disproving player
Preconditions: P01. Another player in the game has made a suggestion. This player (the one disproving the suggestion) is dequeued from the disproval queue. 
Postcondition: The disproving player must pass on the disproval. All other players are made aware that this player could not disprove the suggestion. If the disproval queue is not empty, this process repeats with the next player. Otherwise, play returns to the suggester.

Name: Player makes a correct accusation
ID: U03
Actor: accuser
Precondition: P02. Player makes an accusation to the system and the system verifies it is correct
Postcondition: The accuser is the winner of the game and all players are made aware. The game terminates

Name: Player makes an incorrect accusation
ID: U04
Actor: accuser
Precondition: P02. Player makes an accusation to the system and the system denies it. 
Postcondition: The accuser is no longer a contender and cannot win the game. The accuser is made a participant. If there is only one more contender, that player is the winner and the game terminates; otherwise, play continues with the next player.

## Forming a Game ##
