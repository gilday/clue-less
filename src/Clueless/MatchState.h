//
//  MatchState.h
//  Clueless
//
//  Created by Johnathan Gilday on 11/12/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GameBoard.h"
#import "Player.h"

// MatchState objects hold enough information about a match of Clueless to inform
// the exact state of the match. These objects are serializable: can be written to
// a string of bytes to be sent to Game Center. It follows that this class needs to
// be able to instantiate new MatchState objects from a stream of bytes representing
// a serialized MatchState object
@interface MatchState : NSObject <NSCoding>

// List of all GKTurnBasedParticipant
@property NSArray* players;
// Reference to current GKTurnBasedParticipant
@property Player* currentPlayer;
// A reference to a gameboard for this match. This should not need to be serialized since it
// doesn't persist match specific data
@property GameBoard* gameBoard;

-(void) endTurn;

@end
