//
//  GameCenterMatchHelper.m
//  Clueless
//
//  Created by John Gilday on 11/28/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import "GameCenterMatchHelper.h"

@implementation GameCenterMatchHelper

@synthesize isAuthenticated;
@synthesize currentMatch;
@synthesize delegate;

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

UIViewController *presentingViewController;

- (void) authenticateLocalUser: (UIViewController*) titleViewController {
    presentingViewController = titleViewController;
    NSLog(@"Authenticating local user...");
    
    GKLocalPlayer *localPlayer = [GKLocalPlayer localPlayer];
    localPlayer.authenticateHandler = ^(UIViewController *viewController, NSError *error){
        if(viewController != nil) {
            [presentingViewController presentViewController:viewController animated:YES completion:nil];
        }
        [GKTurnBasedEventHandler sharedTurnBasedEventHandler].delegate = self;
    };
}

- (void)findMatchWithMinPlayers:(int)minPlayers
                     maxPlayers:(int)maxPlayers
                 viewController:(UIViewController *)viewController {
    presentingViewController = viewController;
    GKMatchRequest *request = [[GKMatchRequest alloc] init];
    request.minPlayers = minPlayers;
    request.maxPlayers = maxPlayers;
    
    GKTurnBasedMatchmakerViewController *mmvc =
    [[GKTurnBasedMatchmakerViewController alloc] initWithMatchRequest:request];
    mmvc.turnBasedMatchmakerDelegate = self;
    mmvc.showExistingMatches = YES;
    
    [presentingViewController presentViewController:mmvc animated:YES completion:nil];
}

-(MatchState*) findNewTestMatch
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

#pragma mark GKTurnBasedEventHandlerDelegate

// This method does not fire when the local player is invited to a game!
// This method fires when local player uses GameCenter to invite people to a match
// so that the app may display "you've invited these people" or something like that
-(void) handleInviteFromGameCenter:(NSArray *)playersToInvite {
    NSLog(@"EVENT HANDLER: You've invited friends");
    [presentingViewController dismissViewControllerAnimated:YES completion:nil];
    GKMatchRequest *request = [[GKMatchRequest alloc] init];
    request.playersToInvite = playersToInvite;
    request.minPlayers = 2;
    request.maxPlayers = 6;
    GKTurnBasedMatchmakerViewController *matchmaker = [[GKTurnBasedMatchmakerViewController alloc] initWithMatchRequest:request];
    matchmaker.showExistingMatches = NO;
    matchmaker.turnBasedMatchmakerDelegate = self;
    [presentingViewController presentViewController:matchmaker animated:YES completion:nil];
}

// Fires when any player takes their turn
-(void) handleTurnEventForMatch:(GKTurnBasedMatch *)match didBecomeActive:(BOOL)didBecomeActive {
    if([match.matchID isEqualToString:currentMatch.matchID]) {
        if([match.currentParticipant.playerID isEqualToString:[GKLocalPlayer localPlayer].playerID]) {
            NSLog(@"EVENT HANDLER: it's your turn in the current match");
            self.currentMatch = match;
            [delegate takeTurn:match];
        } else {
            NSLog(@"EVENT HANDLER: someone took their turn but you're not up yet");
            self.currentMatch = match;
            [delegate displayMatch:match];
        }
    } else {
        if([match.currentParticipant.playerID isEqualToString:[GKLocalPlayer localPlayer].playerID]) {
            [delegate sendNotice:@"It's your turn in another match!" forMatch:match];
        }
    }
}

-(void) handleMatchEnded:(GKTurnBasedMatch *)match {
    NSLog(@"EVENT HANDLER: GameCenter says the match has ended");
}

#pragma mark GKTurnBasedMatchmakerViewControllerDelegate

-(void) turnBasedMatchmakerViewController:(GKTurnBasedMatchmakerViewController *)viewController didFindMatch:(GKTurnBasedMatch *)match {
    [presentingViewController dismissViewControllerAnimated:YES completion:nil];
    self.currentMatch = match;
    GKTurnBasedParticipant *firstParticipant = [match.participants objectAtIndex:0];
    if(firstParticipant.lastTurnDate) {
        NSLog(@"Found existing match");
        if([match.currentParticipant.playerID isEqualToString:[GKLocalPlayer localPlayer].playerID]) {
            NSLog(@"It's your turn");
            [delegate takeTurn:match];
        } else {
            NSLog(@"It's %@'s turn", match.currentParticipant.playerID);
            [delegate displayMatch:match];
        }
    } else {
        NSLog(@"Found new match");
        [delegate beginMatch:match];
    };
}

-(void) turnBasedMatchmakerViewControllerWasCancelled:(GKTurnBasedMatchmakerViewController *)viewController {
    [presentingViewController dismissViewControllerAnimated:YES completion:nil];
}

-(void) turnBasedMatchmakerViewController:(GKTurnBasedMatchmakerViewController *)viewController didFailWithError:(NSError *)error {
    [presentingViewController dismissViewControllerAnimated:YES completion:nil];
    NSLog(@"Error finding match: %@", error.localizedDescription);
}

-(void) turnBasedMatchmakerViewController:(GKTurnBasedMatchmakerViewController *)viewController playerQuitForMatch:(GKTurnBasedMatch *)match {
    // Don't dismiss the Game Center view controller so that user may do additional things besides quit match
    NSLog(@"%@ quit match %@", match.currentParticipant, match);
    // When a player quits, we'll want to just end the game and update the match to let everyone know it's over
    // Not worried about this quite yet - we just won't support quitting for now. No quitters!
}

@end
