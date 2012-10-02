## Configuration Management ##

"The Butler Did It" will employ Github.com heavily to assist in configuration management. Distributed version control will help the team share source code commits to a common development thread. Git's simple branching feature allows the team to develop features in parallel efforts and merge feature branches to integration branches for testing. The team will employ a branching strategy inspired by ["A successful Git branching model"](http://nvie.com/posts/a-successful-git-branching-model/). Our take on this branching strategy employs the following code branches:

1. Master Branch
	This branch holds the project releases. Merging on to this branch means a deliverable is ready.
2. Development Branch
	This is the main development thread and integration branch
3. feature-- branches
	Feature branches are split from the `development` branch. These branches hold progress towards some feature until it is ready to be merged on to the `development` branch.
4. hotfix-- branches
	Hotfix branches are split from the `development` branch. These branches hold commits towards bugfixes. These bugfixes are integrated back into `development` and possibly `master` and `feature-- branches`.