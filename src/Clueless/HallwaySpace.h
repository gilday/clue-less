//
//  HallwaySpace.h
//  Clueless
//
//  Created by John Gilday on 11/23/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import "GameBoardSpace.h"
#import "Room.h"

@interface HallwaySpace : GameBoardSpace

- (HallwaySpace*) initWithRoomsToConnect: (Room*) room1 ConnectsTo: (Room*) room2;

@end
