//
//  GameBoardTests.m
//  Clueless
//
//  Created by John Gilday on 11/27/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import "GameBoardTests.h"
#import "GameBoard.h"
#import "Player.h"
#import "Room.h"

@implementation GameBoardTests

-(void) testProfPlumMayMoveFromStartSpotToHallway
{
    // GIVEN,
    // a GameBoard with Professor Plum on his start spot
    Player *joe = [[Player alloc] init];
    joe.location = ProfPlumID;
    
    GameBoard *gameBoard = [[GameBoard alloc] initWithPlayers:[[NSArray alloc] initWithObjects:joe, nil]];
    
    // WHEN,
    // system asks board where ProfPlum can go
    NSArray *possibleSpaces = [gameBoard getPossibleMoves:joe];
    
    // THEN,
    // system returns HallwaySpace between Study and Library
    STAssertTrue(1 == [possibleSpaces count], @"Should only be one possible space");
    STAssertTrue([[[possibleSpaces objectAtIndex:0] spaceId] isEqualToString:@"Library-Study"], @"Professor Plum may only go to this one hallway space");
}

-(void) testPlayerMayNotMoveToOccupiedHallway
{
    // GIVEN,
    // a GameBoard with Professor Plum on his start spot and some other player, jack, in the adjacent hallway
    Player *joe = [[Player alloc] init];
    joe.location = ProfPlumID;
    
    Player *jack = [[Player alloc] init];
    jack.location = @"Library-Study";
    
    GameBoard *gameBoard = [[GameBoard alloc] initWithPlayers:[[NSArray alloc] initWithObjects:joe, jack, nil]];
    
    // WHEN,
    // system asks board where Professor Plum may move
    NSArray *possibleSpaces = [gameBoard getPossibleMoves:joe];
    
    // THEN,
    // system returns that there are no possible spots
    STAssertTrue(0 == [possibleSpaces count], @"Should be no possible moves");
}

-(void) testPlayerMayUseSecretPassage
{
    // GIVEN,
    // a GameBoard with Professor Plum in the Study
    Player *sam = [[Player alloc] init];
    sam.location = Lounge;
    
    GameBoard *gameBoard = [[GameBoard alloc] initWithPlayers:[[NSArray alloc] initWithObjects:sam, nil]];
    
    // WHEN,
    // system queries what spots professor plum may move to
    NSArray *possibleSpaces = [gameBoard getPossibleMoves:sam];
    
    // THEN,
    // system returns Kitchen as one of those rooms
    STAssertTrue(3 == [possibleSpaces count], @"Should be 3 choices, the hallways and the passage");
    BOOL canGoToKitchen = NO;
    for(GameBoardSpace *space in possibleSpaces)
    {
        if([space.spaceId isEqualToString:Conservatory]) canGoToKitchen = YES;
    }
    STAssertTrue(canGoToKitchen, @"Kitchen should be in the list of possible moves");
}

@end
