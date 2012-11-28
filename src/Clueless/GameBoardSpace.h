//
//  GameBoardSpace.h
//  Clueless
//
//  Created by John Gilday on 11/23/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface GameBoardSpace : NSObject

@property NSString* spaceId;

// A list of other GameBoardSpaces to which a player can navigate
// from this space
@property NSMutableArray* navigationTargets;

- (GameBoardSpace*) initWithId: (NSString*) spaceId;

-(void) connectSpace: (GameBoardSpace*) otherSpace;

@end
