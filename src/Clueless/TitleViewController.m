//
//  ViewController.m
//  Clueless
//
//  Created by Johnathan Gilday on 10/27/12.
//  Copyright (c) 2012 Johnathan Gilday. All rights reserved.
//

#import "TitleViewController.h"
#import "GameCenterMatchHelper.h"

@interface TitleViewController ()

@end

@implementation TitleViewController

@synthesize gameCenterButton;

GameCenterMatchHelper *gcHelper;

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    gcHelper = [GameCenterMatchHelper singleton];
	
    [gcHelper authenticateLocalUser:self];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)openGameCenterClick:(id)sender {
    [gcHelper findMatchWithMinPlayers:3 maxPlayers:6 viewController:self];
}

@end
