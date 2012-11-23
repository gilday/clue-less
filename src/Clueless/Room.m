//
//  Room.m
//  Clueless
//
//  Created by John Gilday on 11/23/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import "Room.h"

@implementation Room

NSString *const Study = @"Study";
NSString *const Hall = @"Hall";

- (Room*) initWithId:(NSString *)roomId
{
    self = [super init];
    
    self.spaceId = roomId;
    
    return self;
}

@end
