//
//  GameBoard.m
//  Clueless
//
//  Created by John Gilday on 11/23/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import "GameBoard.h"
#import "GameBoardSpace.h"
#import "Room.h"
#import "HallwaySpace.h"
#import "ClueCharacter.h"

@implementation GameBoard

@synthesize spaces;
@synthesize players;

-(id) initWithPlayers: (NSArray*) pplayers
{
    self = [super init];
    
    [self createSpaces];
    self.players = pplayers;
    
    return self;
}

-(NSArray*) getPossibleMoves:(Player *)player
{
    GameBoardSpace *currentSpace = [spaces objectForKey:[player location]];
    NSMutableArray *possibleSpaces = [[NSMutableArray alloc] init];
    for(GameBoardSpace *space in currentSpace.navigationTargets)
    {
        if([space isMemberOfClass:[HallwaySpace class]])
        {
            BOOL hallwaySpaceIsOccupied = NO;
            for (Player *otherPlayer in players)
            {
                GameBoardSpace *otherPlayerPosition = [spaces objectForKey:[otherPlayer location]];
                hallwaySpaceIsOccupied = [otherPlayerPosition.spaceId isEqualToString:space.spaceId];
                if(hallwaySpaceIsOccupied) break;
            }
            if(!hallwaySpaceIsOccupied) [possibleSpaces addObject:space];
        } else
        {
            [possibleSpaces addObject:space];
        }
    }
    return possibleSpaces;
}

-(BOOL) isPlayerAbleToMove:(Player *)player toSpace: (NSString *) spaceId
{
    NSArray* possibleSpaces = [self getPossibleMoves:player];
    
    for(GameBoardSpace *space in possibleSpaces)
    {
        NSLog(@"Can player at %@ move to %@?", player.location, space.spaceId);
        if([space.spaceId isEqualToString:spaceId]) return YES;
    }
    return NO;
}

-(void) createSpaces
{
    spaces = [[NSMutableDictionary alloc] init];
    
    Room *study = [[Room alloc] initWithId: Study];
    Room *hall = [[Room alloc] initWithId: Hall];
    HallwaySpace *hallway = [[HallwaySpace alloc] initWithRoomsToConnect:study ConnectsTo:hall];
    [self addGameBoardSpace:study];
    [self addGameBoardSpace:hall];
    [self addGameBoardSpace:hallway];
    
    Room *lounge = [[Room alloc] initWithId: Lounge];
    hallway = [[HallwaySpace alloc] initWithRoomsToConnect:hall ConnectsTo:lounge];
    [self addGameBoardSpace:lounge];
    [self addGameBoardSpace:hallway];
    
    Room *diningRoom = [[Room alloc] initWithId: DiningRoom];
    hallway = [[HallwaySpace alloc] initWithRoomsToConnect:lounge ConnectsTo:diningRoom];
    [self addGameBoardSpace:diningRoom];
    [self addGameBoardSpace:hallway];

    Room *kitchen = [[Room alloc] initWithId: Kitchen];
    hallway = [[HallwaySpace alloc] initWithRoomsToConnect:kitchen ConnectsTo:diningRoom];
    [self addGameBoardSpace:kitchen];
    [self addGameBoardSpace:hallway];
    
    Room *ballRoom = [[Room alloc] initWithId:BallRoom];
    hallway = [[HallwaySpace alloc] initWithRoomsToConnect:ballRoom ConnectsTo:kitchen];
    [self addGameBoardSpace:ballRoom];
    [self addGameBoardSpace:hallway];

    Room *conservatory = [[Room alloc] initWithId:Conservatory];
    hallway = [[HallwaySpace alloc] initWithRoomsToConnect:conservatory ConnectsTo:ballRoom];
    [self addGameBoardSpace:conservatory];
    [self addGameBoardSpace:hallway];

    Room *library = [[Room alloc] initWithId:Library];
    hallway = [[HallwaySpace alloc] initWithRoomsToConnect:library ConnectsTo:conservatory];
    [self addGameBoardSpace:library];
    [self addGameBoardSpace:hallway];
    hallway = [[HallwaySpace alloc] initWithRoomsToConnect:library ConnectsTo:study];
    [self addGameBoardSpace:hallway];
    
    // Add billiard room in the middle
    Room *billiardRoom = [[Room alloc] initWithId: BilliardRoom];
    [self addGameBoardSpace:billiardRoom];
    hallway = [[HallwaySpace alloc] initWithRoomsToConnect:hall ConnectsTo:billiardRoom];
    [self addGameBoardSpace:hallway];
    hallway = [[HallwaySpace alloc] initWithRoomsToConnect:billiardRoom ConnectsTo:library];
    [self addGameBoardSpace:hallway];
    hallway = [[HallwaySpace alloc] initWithRoomsToConnect:ballRoom ConnectsTo:billiardRoom];
    [self addGameBoardSpace:hallway];
    hallway = [[HallwaySpace alloc] initWithRoomsToConnect:diningRoom ConnectsTo:billiardRoom];
    [self addGameBoardSpace:hallway];
    
    // Create secret passages
    [kitchen connectSpace:study];
    [conservatory connectSpace:lounge];
    
    // Create start spots
    GameBoardSpace *msScarlet = [[GameBoardSpace alloc] initWithId:MsScarletID];
    [[msScarlet navigationTargets] addObject:[spaces valueForKey:@"Hall-Lounge"]];
    [self addGameBoardSpace:msScarlet];
    
    GameBoardSpace *colMustard = [[GameBoardSpace alloc] initWithId:ColMustardID];
    [[colMustard navigationTargets] addObject:[spaces valueForKey:@"Lounge-DiningRoom"]];
    [self addGameBoardSpace:colMustard];
    
    GameBoardSpace *mrsWhite = [[GameBoardSpace alloc] initWithId:MrsWhiteID];
    [[mrsWhite navigationTargets] addObject:[spaces valueForKey:@"BallRoom-Kitchen"]];
    [self addGameBoardSpace:mrsWhite];
    
    GameBoardSpace *mrGreen = [[GameBoardSpace alloc] initWithId:MrGreenID];
    [[mrGreen navigationTargets] addObject:[spaces valueForKey:@"Conservatory-BallRoom"]];
    [self addGameBoardSpace:mrGreen];
    
    GameBoardSpace *mrsPeacock = [[GameBoardSpace alloc] initWithId:MrsPeacockID];
    [[mrsPeacock navigationTargets] addObject:[spaces valueForKey:@"Library-Conservatory"]];
    [self addGameBoardSpace:mrsPeacock];
    
    GameBoardSpace *profPlum = [[GameBoardSpace alloc] initWithId:ProfPlumID];
    [[profPlum navigationTargets] addObject:[spaces valueForKey:@"Library-Study"]];
    [self addGameBoardSpace:profPlum];
}

-(void) addGameBoardSpace: (GameBoardSpace*) space
{
    [self.spaces setValue:space forKey:space.spaceId];
}

@end
