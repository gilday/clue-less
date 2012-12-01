//
//  Player.h
//  Clueless
//
//  Created by Johnathan Gilday on 11/12/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import <GameKit/GameKit.h>
#import <Foundation/Foundation.h>
#import "ClueCharacter.h"

@interface Player : NSObject

@property GKTurnBasedParticipant* participant;
// The ID of the room that this player currently occupies
@property NSString* location;
@property ClueCharacter* character;

@end
