//
//  GameCenterMatchHelper.h
//  Clueless
//
//  Created by John Gilday on 11/28/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <GameKit/GameKit.h>
#import <UIKit/UIKit.h>
#import "MatchState.h"
#import "Player.h"
#import "ClueCharacterFactory.h"

@protocol GameCenterMatchHelperDelegate

// When game center is starting a brand new match
-(void) beginMatch: (GKTurnBasedMatch*) match;
// When game center has new match data to display, but it is another player's turn
-(void) displayMatch: (GKTurnBasedMatch*) match;
// When game center needs this player to participate
-(void) takeTurn: (GKTurnBasedMatch*) match;
// When the game is over
-(void) endMatch: (GKTurnBasedMatch*) match;
// When something happens to a match that this player is currently a part of but
// is not currently looking at. Probably the least important
-(void) sendNotice: (NSString*) notice forMatch: (GKTurnBasedMatch*) match;

@end

@interface GameCenterMatchHelper : NSObject <GKTurnBasedMatchmakerViewControllerDelegate, GKTurnBasedEventHandlerDelegate>

@property BOOL isAuthenticated;
@property GKTurnBasedMatch *currentMatch;
@property id<GameCenterMatchHelperDelegate> delegate;

+(GameCenterMatchHelper*) singleton;

-(MatchState*) findNewTestMatch;

-(void) authenticateLocalUser: (UIViewController*) titleViewController;

- (void)findMatchWithMinPlayers:(int)minPlayers
                     maxPlayers:(int)maxPlayers
                 viewController:(UIViewController *)viewController;

@end
