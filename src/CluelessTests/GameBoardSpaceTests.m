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
    
    [[hallwayStudyHall spaceId] isEqualToString:@"Study-Hall"];
}

@end
