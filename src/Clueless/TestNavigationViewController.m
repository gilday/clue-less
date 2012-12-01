//
//  TestNavigationViewController.m
//  Clueless
//
//  Created by John Gilday on 11/29/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import "TestNavigationViewController.h"
#import "GameBoard.h"

@interface TestNavigationViewController ()

@end

@implementation TestNavigationViewController

@synthesize playerLocationText;
@synthesize navigationField;

MatchState *state;
GameBoard *gameBoard;

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    state = [[GameCenterMatchHelper singleton] findNewTestMatch];
    gameBoard = [[GameBoard alloc] initWithPlayers:state.players];
    
    [self updatePlayerLocationText];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void) updatePlayerLocationText
{
    NSMutableString *playersStatus = [[NSMutableString alloc] init];
    
    for(Player *player in state.players)
    {
        [playersStatus appendFormat:@"Player %@ is at %@\n", [player.character name], player.location];
    }
    
    playerLocationText.text = playersStatus;
}

- (IBAction)navigationButton:(UIButton *)sender {
    NSString *newLocationForCurrentPlayer = navigationField.text;
    Player *currentPlayer = [state currentPlayer];
    
    NSLog(@"Going to ask if player at %@ can move to %@", currentPlayer.location, newLocationForCurrentPlayer);
    
    if([gameBoard isPlayerAbleToMove:currentPlayer toSpace:newLocationForCurrentPlayer])
    {
        [currentPlayer setLocation:newLocationForCurrentPlayer];
        [state endTurn];
    }
    else
    {
        NSLog(@"Cannot move there!");
    }
    
    
    [self updatePlayerLocationText];
}
@end
