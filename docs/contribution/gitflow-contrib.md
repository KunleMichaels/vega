# GitFlow Overview

We are using GitFlow in EdgePay project. The Gitflow Workflow section below is derived from Vincent Driessen at [nvie](http://nvie.com/posts/a-successful-git-branching-model/) and [Atlassian Git Tutorial](https://www.atlassian.com/git/tutorials/comparing-workflows/feature-branch-workflow). **Please pay attention to conventions**, described after each branch type.

The Gitflow Workflow defines a strict branching model designed around the project release. This workflow doesn’t add any new concepts or commands beyond what’s required for the Feature Branch Workflow. Instead, it assigns very specific roles to different branches and defines how and when they should interact. In addition to feature branches, it uses individual branches for preparing, maintaining, and recording releases. Of course, you also get to leverage all the benefits of the Feature Branch Workflow: pull requests, isolated experiments, and more efficient collaboration.

## How It Works

The Gitflow Workflow still uses a central repository as the communication hub for all developers. And, as in the other workflows, developers work locally and push branches to the central repo. The only difference is the branch structure of the project.

The only branch where developers can contribute their regular changes is `develop` branch. It's done with Feature Branches **via Pull Requests only**.

### Feature Branches
Each new feature should reside in its own branch, which can be pushed to the central repository for backup/collaboration. But, instead of branching off of `master`, feature branches use `develop` as their parent branch. When a feature is complete, it gets merged back into `develop`. Features should never interact directly with `master`.
![](https://www.atlassian.com/git/images/tutorials/collaborating/comparing-workflows/gitflow-workflow/03.svg)

**Conventions:**
* branch off: `develop`
* merge into: *develop* **via Pull Requests**
* naming: feature/*

### Hotfix Branches
Hotfix branches are used to quickly patch production releases. This is the only branch that should fork directly off of `master`. As soon as the fix is complete, it should be merged into both `master` and `develop` (or the current release branch), and `master` should be tagged with an updated version number.

Having a dedicated line of development for bug fixes lets EdgePay team address issues without interrupting the rest of the workflow or waiting for the next release cycle. You can think of maintenance branches as ad-hoc release branches that work directly with master.
![](https://www.atlassian.com/git/images/tutorials/collaborating/comparing-workflows/gitflow-workflow/05.svg)

**Conventions:**
* branch off: `master`
* merge into: *master* & *develop*
* naming: hotfix/*

Example, that demonstrates how this workflow can be used to manage a single release cycle is available [here](https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow).

*This guide is based on [Atlassian Git Tutorial](https://www.atlassian.com/git/tutorials/comparing-workflows).*