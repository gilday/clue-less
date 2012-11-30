//
//  ClueCharacter.h
//  Clueless
//
//  Created by Johnathan Gilday on 11/12/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ClueCharacter : NSObject 

FOUNDATION_EXPORT NSString *const MsScarletID;
FOUNDATION_EXPORT NSString *const ColMustardID;
FOUNDATION_EXPORT NSString *const MrsWhiteID;
FOUNDATION_EXPORT NSString *const MrGreenID;
FOUNDATION_EXPORT NSString *const MrsPeacockID;
FOUNDATION_EXPORT NSString *const ProfPlumID;
    
@property NSString* name;
@property UIColor* color;

-(ClueCharacter*) initWithNameAndColor: (NSString*) name AndColor: (UIColor*) color;

@end
