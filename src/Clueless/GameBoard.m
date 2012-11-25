//
//  GameBoard.m
//  Clueless
//
//  Created by John Gilday on 11/23/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import "GameBoard.h"
#import "GameBoardSpace.h"
#import "Room.h"
#import "HallwaySpace.h"

@implementation GameBoard

@synthesize spaces;

-(id) init
{
    self = [super init];
    
    [self createSpaces];
    
    return self;
}

-(void) createSpaces
{
    spaces = [[NSMutableDictionary alloc] init];
    
    Room *study = [[Room alloc] initWithId: Study];
    Room *hall = [[Room alloc] initWithId: Hall];
    HallwaySpace *hallway = [[HallwaySpace alloc] initWithRoomsToConnect:study ConnectsTo:hall];
    [self addGameBoardSpace:study];
    [self addGameBoardSpace:hall];
    [self addGameBoardSpace:hallway];
    
    Room *lounge = [[Room alloc] initWithId: Lounge];
    hallway = [[HallwaySpace alloc] initWithRoomsToConnect:hall ConnectsTo:lounge];
    [self addGameBoardSpace:lounge];
    [self addGameBoardSpace:hallway];
    
    Room *diningRoom = [[Room alloc] initWithId: DiningRoom];
    hallway = [[HallwaySpace alloc] initWithRoomsToConnect:lounge ConnectsTo:diningRoom];
    [self addGameBoardSpace:diningRoom];
    [self addGameBoardSpace:hallway];

    Room *kitchen = [[Room alloc] initWithId: Kitchen];
    hallway = [[HallwaySpace alloc] initWithRoomsToConnect:kitchen ConnectsTo:diningRoom];
    [self addGameBoardSpace:kitchen];
    [self addGameBoardSpace:hallway];
    
    Room *ballRoom = [[Room alloc] initWithId:BallRoom];
    hallway = [[HallwaySpace alloc] initWithRoomsToConnect:ballRoom ConnectsTo:kitchen];
    [self addGameBoardSpace:ballRoom];
    [self addGameBoardSpace:hallway];

    Room *conservatory = [[Room alloc] initWithId:Conservatory];
    hallway = [[HallwaySpace alloc] initWithRoomsToConnect:conservatory ConnectsTo:ballRoom];
    [self addGameBoardSpace:conservatory];
    [self addGameBoardSpace:hallway];

    Room *library = [[Room alloc] initWithId:Library];
    hallway = [[HallwaySpace alloc] initWithRoomsToConnect:library ConnectsTo:conservatory];
    [self addGameBoardSpace:library];
    [self addGameBoardSpace:hallway];
    hallway = [[HallwaySpace alloc] initWithRoomsToConnect:library ConnectsTo:study];
    [self addGameBoardSpace:hallway];
    
    // Add billiard room in the middle
    Room *billiardRoom = [[Room alloc] initWithId: BilliardRoom];
    [self addGameBoardSpace:billiardRoom];
    hallway = [[HallwaySpace alloc] initWithRoomsToConnect:hall ConnectsTo:billiardRoom];
    [self addGameBoardSpace:hallway];
    hallway = [[HallwaySpace alloc] initWithRoomsToConnect:billiardRoom ConnectsTo:library];
    [self addGameBoardSpace:hallway];
    hallway = [[HallwaySpace alloc] initWithRoomsToConnect:ballRoom ConnectsTo:billiardRoom];
    [self addGameBoardSpace:hallway];
    hallway = [[HallwaySpace alloc] initWithRoomsToConnect:diningRoom ConnectsTo:billiardRoom];
    [self addGameBoardSpace:hallway];
    
    // Create secret passages
    [kitchen connectSpace:study];
    [conservatory connectSpace:lounge];
}

-(void) addGameBoardSpace: (GameBoardSpace*) space
{
    [self.spaces setValue:space forKey:space.spaceId];
}

@end
