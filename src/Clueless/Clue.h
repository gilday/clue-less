//
//  Clue.h
//  Clueless
//
//  Created by John Gilday on 11/23/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Clue : NSObject

FOUNDATION_EXPORT NSString *const WeaponType;
FOUNDATION_EXPORT NSString *const RoomType;
FOUNDATION_EXPORT NSString *const CharacterType;

@property NSString* name;
@property NSString* type;

@end
