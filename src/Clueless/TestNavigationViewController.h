//
//  TestNavigationViewController.h
//  Clueless
//
//  Created by John Gilday on 11/29/12.
//  Copyright (c) 2012 Johns Hopkins University. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GameCenterMatchHelper.h"

@interface TestNavigationViewController : UIViewController

@property (weak, nonatomic) IBOutlet UITextView *playerLocationText;
@property (weak, nonatomic) IBOutlet UITextField *navigationField;
- (IBAction)navigationButton:(UIButton *)sender;

@end
