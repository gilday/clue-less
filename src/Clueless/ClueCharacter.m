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

static ClueCharacter *MsScarlet;
+(ClueCharacter*) msScarlet
{
    if(!MsScarlet)
        MsScarlet = [[ClueCharacter alloc] init: MsScarletID];
    
    return MsScarlet;
}

-(ClueCharacter*) init:(NSString *)name
{
    self.name = name;
    
    return self;
}

@end
