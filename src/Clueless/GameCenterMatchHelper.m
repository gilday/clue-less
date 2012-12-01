//
//  GameCenterMatchHelper.m
//  Clueless
//
//  Created by John Gilday on 11/28/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import "GameCenterMatchHelper.h"

@protocol GameCenterMatchHelperDelegate

// When game center is starting a brand new match
-(void) BeginMatch;
// When game center has new match data to display, but it is another player's turn
-(void) DisplayMatch;
// When game center needs this player to participate
-(void) TakeTurn;
// When the game is over
-(void) EndGame;
// When something happens to a match that this player is currently a part of but
// is not currently looking at. Probably the least important
-(void) NotifyUser;

@end

@implementation GameCenterMatchHelper

@synthesize isAuthenticated;

static GameCenterMatchHelper *instance = nil;
+ (GameCenterMatchHelper *) singleton {
    if (!instance) {
        instance = [[GameCenterMatchHelper alloc] init];
    }
    return instance;
}

- (id)init {
    if ((self = [super init])) {
        NSNotificationCenter *nc =
        [NSNotificationCenter defaultCenter];
        [nc addObserver:self
               selector:@selector(authenticationChanged)
                   name:GKPlayerAuthenticationDidChangeNotificationName
                 object:nil];
    }
    return self;
}

- (void)authenticationChanged {
    
    if ([GKLocalPlayer localPlayer].isAuthenticated &&
        !isAuthenticated) {
        NSLog(@"Authentication changed: player authenticated.");
        isAuthenticated = TRUE;
    } else if (![GKLocalPlayer localPlayer].isAuthenticated &&
               isAuthenticated) {
        NSLog(@"Authentication changed: player not authenticated");
        isAuthenticated = FALSE;
    }
    
}

-(MatchState*) findTestMatch
{
    Player *scarlet = [[Player alloc] init];
    scarlet.character = [ClueCharacterFactory msScarlet];
    scarlet.location = MsScarletID;
    
    Player *plum = [[Player alloc] init];
    plum.character = [ClueCharacterFactory profPlum];
    plum.location = ProfPlumID;
    
    MatchState *state = [[MatchState alloc] init];
    state.players = [[NSArray alloc] initWithObjects:scarlet, plum, nil];
    state.currentPlayer = [state.players objectAtIndex:0];
    
    return state;
}

@end
