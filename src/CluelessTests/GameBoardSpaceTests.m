//
//  GameBoardSpaceTests.m
//  Clueless
//
//  Created by John Gilday on 11/23/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import "GameBoardSpaceTests.h"
#import "GameBoardSpace.h"
#import "Room.h"
#import "HallwaySpace.h"

@implementation GameBoardSpaceTests

- (void) testHallwayInitialization
{
    Room *study = [[Room alloc] initWithId: Study];
    Room *hall = [[Room alloc] initWithId: Hall];
    HallwaySpace *hallwayStudyHall = [[HallwaySpace alloc] initWithRoomsToConnect:study ConnectsTo:hall];
    
    BOOL isHallwayNamedCorrectly = [[hallwayStudyHall spaceId] isEqualToString:@"Study-Hall"];
    STAssertTrue(isHallwayNamedCorrectly, @"Hallway does not have correct name");
    
    STAssertTrue([[hallwayStudyHall navigationTargets] containsObject:study], @"Hallway doesn't contain study");
    STAssertTrue([[hallwayStudyHall navigationTargets] containsObject:hall], @"Hallway doesn't contain study");
    STAssertTrue([[study navigationTargets] containsObject:hallwayStudyHall], @"Study doesn't contain hallway");
    STAssertTrue([[hall navigationTargets] containsObject:hallwayStudyHall], @"Hall doesn't contain hallway");
}

@end
