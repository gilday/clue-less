//
//  GameBoard.h
//  Clueless
//
//  Created by John Gilday on 11/23/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GameBoardSpace.h"

@interface GameBoard : NSObject

@property NSMutableDictionary *spaces;

//- (GameBoardSpace*) getSpaceWithId: (NSString*) spaceId;

@end
