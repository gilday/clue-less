//
//  MatchState.m
//  Clueless
//
//  Created by Johnathan Gilday on 11/12/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import "MatchState.h"

@implementation MatchState

@synthesize players;
@synthesize currentPlayer;
@synthesize gameBoard;

-(void) endTurn
{
    int currentIndex = [players indexOfObject:currentPlayer];
    if(currentIndex == (players.count - 1))
        currentIndex = 0;
    else
        currentIndex++;
    
    currentPlayer = [players objectAtIndex:currentIndex];
}

@end
