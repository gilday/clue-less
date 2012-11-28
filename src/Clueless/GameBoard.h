//
//  GameBoard.h
//  Clueless
//
//  Created by John Gilday on 11/23/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GameBoardSpace.h"
#import "Player.h"

@interface GameBoard : NSObject

@property NSMutableDictionary *spaces;
@property NSArray *players;

-(GameBoard*) initWithPlayers: (NSArray*) pplayers;

-(NSArray*) getPossibleMoves: (Player*) player;

@end
