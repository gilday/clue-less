//
//  ViewController.m
//  Clueless
//
//  Created by Johnathan Gilday on 10/27/12.
//  Copyright (c) 2012 Johnathan Gilday. All rights reserved.
//

#import "TitleViewController.h"
#import "GameCenterMatchHelper.h"

@interface TitleViewController ()

@end

@implementation TitleViewController

@synthesize gameCenterButton;

GameCenterMatchHelper *gcHelper;

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    gcHelper = [GameCenterMatchHelper singleton];
	
    [gcHelper authenticateLocalUser:self];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)openGameCenterClick:(id)sender {
    [gcHelper findMatchWithMinPlayers:2 maxPlayers:6 viewController:self];
}

- (IBAction)endTurn:(id)sender {
    GKTurnBasedMatch *match = [gcHelper currentMatch];
    int currentPlayerIndex = [match.participants indexOfObject:match.currentParticipant];
    int nextPlayerIndex = (currentPlayerIndex + 1) % match.participants.count;
    GKTurnBasedParticipant *nextPlayer = [match.participants objectAtIndex:nextPlayerIndex];
    [match endTurnWithNextParticipants:[[NSArray alloc] initWithObjects:nextPlayer, nil] turnTimeout:3600 matchData: [@"Hello World" dataUsingEncoding:NSUTF8StringEncoding] completionHandler:nil];
    NSLog(@"Sent turn to next player");
}
@end
