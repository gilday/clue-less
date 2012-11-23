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

-(id) init
{
    self = [super init];
    
    self.spaces = [self createSpaces];
    
    return self;
}

-(NSMutableDictionary*) createSpaces
{
    NSMutableDictionary *spaces = [[NSMutableDictionary alloc] init];
    
    Room *study = [[Room alloc] initWithId: Study];
    Room *hall = [[Room alloc] initWithId: Hall];
    HallwaySpace *hallwayStudyHall = [[HallwaySpace alloc] initWithRoomsToConnect:study ConnectsTo:hall];
    
    return spaces;
}

@end
