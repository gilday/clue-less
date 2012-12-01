//
//  ClueCharacterFactory.m
//  Clueless
//
//  Created by John Gilday on 11/22/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import "ClueCharacterFactory.h"
#import "ClueCharacter.h"

@implementation ClueCharacterFactory

static ClueCharacter *MsScarlet;
+(ClueCharacter*) msScarlet
{
    if(!MsScarlet)
        MsScarlet = [[ClueCharacter alloc] initWithNameAndColor:MsScarletID AndColor:[UIColor redColor]];
    
    return MsScarlet;
}

static ClueCharacter *ProfPlum;
+(ClueCharacter*) profPlum
{
    if(!ProfPlum)
        ProfPlum = [[ClueCharacter alloc] initWithNameAndColor:ProfPlumID AndColor:[UIColor purpleColor]];
    
    return ProfPlum;
}

static ClueCharacter *ColMustard;
+(ClueCharacter*) colMustard
{
    if(!ColMustard)
        ColMustard = [[ClueCharacter alloc] initWithNameAndColor:ColMustardID AndColor:[UIColor yellowColor]];
    
    return ColMustard;
}

static ClueCharacter *MrsWhite;
+(ClueCharacter*) mrsWhite
{
    if(!MrsWhite)
        MrsWhite = [[ClueCharacter alloc] initWithNameAndColor:MrsWhiteID AndColor:[UIColor whiteColor]];
    
    return MrsWhite;
}

@end
