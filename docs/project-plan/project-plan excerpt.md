09/27/2012

# Project Plan #

Excerpt for the Clue-less system project plan

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

## Quality Assurance ##

The team will focus its code quality efforts on maintainability, loose coupling, and testability. Testability is important since the same characteristics that make code amenable to unit testing are the characteristics of maintainable, loosely coupled code that follows the single responsibiltiy principle. In order to encourage quality code, "The Butler Did It" will follow Test Driven Development practices when reasonable. The automated tests will help the team verify that the application meets its requirements.

Despite our automated testing efforts, software bugs will emerge in development. When a bug surfaces, the team will use Github.com's Issues feature to document the bug, explain how to reproduce it, and associate the source code commit with the fix that closes the issue. 
