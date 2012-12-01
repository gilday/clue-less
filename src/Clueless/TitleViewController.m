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

- (void)viewDidLoad
{
    [super viewDidLoad];
	
    if(![GameCenterMatchHelper singleton].isAuthenticated) {
        gameCenterButton.titleLabel.text = @"Find Game";
    }
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)openGameCenterClick:(id)sender {
    GameCenterMatchHelper *gcHelper = [GameCenterMatchHelper singleton];
    if(gcHelper.isAuthenticated) {
        // TODO Find match
    } else {
        [self authenticateLocalUser];
    }
}

- (void) authenticateLocalUser {
    NSLog(@"Authenticating local user...");
    GKLocalPlayer *localPlayer = [GKLocalPlayer localPlayer];
    if (localPlayer.authenticated == NO) {
        localPlayer.authenticateHandler = ^(UIViewController *viewController, NSError *error){
            if(viewController != nil) {
                [self presentViewController:viewController animated:YES completion:nil];
            }
        };
    } else {
        NSLog(@"Already authenticated!");
    }
}
@end
