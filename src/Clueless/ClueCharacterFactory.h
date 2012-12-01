//
//  ClueCharacterFactory.h
//  Clueless
//
//  Created by John Gilday on 11/22/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ClueCharacter.h"

@interface ClueCharacterFactory : NSObject

+(ClueCharacter*) msScarlet;
+(ClueCharacter*) profPlum;
+(ClueCharacter*) colMustard;
+(ClueCharacter*) mrsWhite;

@end
