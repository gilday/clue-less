//
//  ClueCharacter.m
//  Clueless
//
//  Created by Johnathan Gilday on 11/12/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import "ClueCharacter.h"

@implementation ClueCharacter

NSString *const MsScarletID = @"MsScarlet";
NSString *const ColMustardID = @"ColMustard";
NSString *const MrsWhiteID = @"MrsWhite";
NSString *const MrGreenID = @"MrGreen";
NSString *const MrsPeacock = @"MrsPeacock";
NSString *const ProfPlum = @"ProfPlum";

-(ClueCharacter*) initWithNameAndColor:(NSString *)name AndColor: (UIColor*)color
{
    self.name = name;
    self.color = color;
    
    return self;
}

@end
