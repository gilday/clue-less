//
//  Room.h
//  Clueless
//
//  Created by John Gilday on 11/23/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import "GameBoardSpace.h"

@interface Room : GameBoardSpace

FOUNDATION_EXPORT NSString *const Study;
FOUNDATION_EXPORT NSString *const Hall;

- (Room*) initWithId: (NSString*) roomId;

@end
