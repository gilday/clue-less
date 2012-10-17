# Accusation Manager Use Cases #

Name: Player makes correct accusation
Actor: player
Preconditions: Player is in a game and it is his/her turn.
Postconditions: System informs all participants that this player has won the game. The game's status is changed to 'finished' and the system records the winner.


Name: Player makes false accusation
Actor: player
Preconditions: Player is in a game and it is his/her turn
Postconditions: Player is no longer a contender. System informs all participants that this player has lost and play continues with the next contender


Name: One contender remains
Actor: system
Precondition: All participants but one have made false accusations and are therefore no longer candidates.
Postcondition: System informs all participants that the sole remaining candidate won the game. The game's status is changed to 'finished' and the system records the winner
