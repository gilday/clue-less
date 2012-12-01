//
//  CharacterTest.m
//  Clueless
//
//  Created by John Gilday on 11/21/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import "CharacterTest.h"
#import "ClueCharacterFactory.h"

@implementation CharacterTest

/*
 * This is a really stupid test. Still getting used to Objective C and I need this 
 * for sanity
 */
- (void) testSingletonInit
{
    msScarlet = [ClueCharacterFactory msScarlet];
    STAssertNotNil(msScarlet, @"ClueCharacter MsScarlet should have been created in testSetup");
    STAssertEquals([msScarlet color], [UIColor redColor], @"MsScarlet is red");
    STAssertTrue([[msScarlet name] isEqualToString:@"MsScarlet"], @"Wrong name");
}

@end
