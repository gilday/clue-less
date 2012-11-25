//
//  HallwaySpace.m
//  Clueless
//
//  Created by John Gilday on 11/23/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import "HallwaySpace.h"

@implementation HallwaySpace

- (HallwaySpace*) initWithRoomsToConnect:(Room *)room1 ConnectsTo:(Room *)room2
{
    self = [super init];
    
    // Create ID from room IDs
    NSMutableString *idFromRooms = [[NSMutableString alloc] initWithString:room1.spaceId];
    [idFromRooms appendString:@"-"];
    [idFromRooms appendString:room2.spaceId];
    self.spaceId = idFromRooms;
    
    // Set up navigation links
    [self connectSpace:room1];
    [self connectSpace:room2];
    
    return self;
}

@end
