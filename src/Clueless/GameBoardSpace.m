//
//  GameBoardSpace.m
//  Clueless
//
//  Created by John Gilday on 11/23/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import "GameBoardSpace.h"

@implementation GameBoardSpace
    
@synthesize spaceId;
@synthesize navigationTargets;

-(GameBoardSpace*) init
{
    self = [super init];
    self.navigationTargets = [[NSMutableArray alloc] init];
    
    return self;
}

@end
