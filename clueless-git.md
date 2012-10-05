# Using Git to version control Clueless #
Git is a modern, distributed version control system. Distributed is the key word in its description. If you are coming from an older system like CVS or SVN, it may be harder at first to learn the distributed model. In the distributed model, everyone working on the project has a copy of the entire code repository. You make changes and commits to your local repository and may share your commits with remote repositories. Typically, teams will establish a master repository which everyone pushes their local changes to in order to collaborate; Clueless will use Github as its master repository.

## Install Git ##
While there exist various GUI clients for using Git, this is not the optimal way to learn Git. Download the Git client for Windows from the official [Git site](http://git-scm.com/). Once you have Git installed, open up Git Bash. If you've used Cygwin or a Bash terminal, this will look familiar. Git was made by Linus Tourvalds so naturally it's very *nixy and this help replicate that in Windows.

*All commands from here on will be in Git bash*

## Set up SSH ##
Git uses SSH to talk to remote repositories and it comes with the Windows Git installation. Luckily, Github makes managing your SSH key identities easy. Assuming you don't have SSH set-up on your machine, run the following command to generate your private, public key pair

`ssh-keygen`

Accept the defaults, give a password to access your private key if your feel like it, and you'll now have your keys in a hidden `.ssh` folder in your home directory. In Windows Explorer, this is `%USERPROFILE\.ssh`

`cat ~/.ssh/id_rsa.pub`

Should print out your public key. You'll link this public key to your Github account so it can identify you when you SSH in. Open browser to Github now and head to your account settings. In the left navigation, you'll see "SSH Keys". Go ahead and add your public key. You'll have to do this for every computer you intend to use with Github (unless you share your .ssh folder in all of them).

## Clone the Repository ##
Now that Github knows who you are, and your Github user has read/write access to the Clueless repository, you can clone the Github repository to your local machine. Change directory to the folder you keep your code projects in and 

`git clone git@github.com:gilday/clue-less.git`

Now you have your very own copy of the source code repository. Check it out

`cd clue-less`

`ls`

If you don't have a clue-less folder, you may not have been able to connect and something was wrong with your SSH key. Since you cloned the repository from Github, Git will know that Github is this repository's `origin`. Check out your remotes and you'll see.

`git remote -v`

## Branching ##

By default, Git has checked out the `master` branch. See the README.md for a note on our branching convention. Verify this

`git status`

Let's list the other branches. 

`git branch`

Only `master` for now - but there are more on the server. List the remote branches

`git branch -r`

In order to switch to one of these branches, use the checkout command.

`git checkout development`

## Making Commits ##
Let's go through a complete example of adding a new "feature". While on the development branch, start a new feature branch for your changes. This command will make a new branch and switch to it.

`git checkout -b feature--gildayChangeExample`

Now use your favorite text editor to add your name to the top of this file and save. Check

`git status`

This command will tell you that there are changes to this file that are not staged for commit. Let's commit the changes to this feature branch. First, stage all the changes for commit. This adds them to the list of things that will be committed.

`git add .`

The period means "add all the modified and new files to the commit". Now make the commit and provide a commit message

`git commit -m "Johnathan Gilday adding my name to the clueless-git.md file"`

Cool. Let's call this feature done and merge it back into the `development` branch. Since we're merging this branch *into* `development`, we need to be in `development`.

`git checkout development`

See that your change is no longer in the file bc you've switch branches

`top clueless-git.md`

Before we merge, it's a good idea to make sure `development` is up to date (in this case it probably is). Pull the latest changes from Github

`git pull origin development`

This branch is up to date now. Merge the feature branch.

`git merge --no-ff feature--gildayChangeExample`

Now all the commits you made on the feature branch are merged into development. See:

`top clueless-git.md`

We're done with that feature and it's merged into the `development` branch so it's safe to delete it now

`git branch -d feature--gildayChangeExample`

## Sending Changes to Github ##

This commit is only on your machine in your repository. Your branch is technically ahead of the Github copy and Git will tell you this.

`git status`

You can keep making changes and commits to your copy until you want to share your commits with Github. Let's sync up the `development` branch's progress with Github. 

`git push origin development`

Now your change should be visible [here](https://github.com/gilday/clue-less/blob/master/README.md)!

## Help ##

Distributed version control is essential for agile development and Git is the best one out there. It's not easy to learn at first but it's sooooooo worth it and you'll never look back. Git is crazy stupid powerful and there are a lot more tricks for working with your code. You can find all the commands

`git help`

And for a specific command 

`git help checkout`