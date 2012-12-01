//
//  GameCenterMatchHelper.h
//  Clueless
//
//  Created by John Gilday on 11/28/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <GameKit/GameKit.h>
#import "MatchState.h"
#import "Player.h"
#import "ClueCharacterFactory.h"

@interface GameCenterMatchHelper : NSObject 

@property BOOL isAuthenticated;

+(GameCenterMatchHelper*) singleton;

-(MatchState*) findTestMatch;

@end
