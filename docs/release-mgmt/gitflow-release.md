# GitFlow Release

Once `develop` has acquired enough features for a release (or a predetermined release date is approaching), a release branch will be forked off of `develop`. Creating this branch starts the next release cycle, so no new features can be added after this point—only bug fixes, documentation generation, and other release-oriented tasks should go in this branch. Once it's ready to ship, the release gets merged into `master` and tagged with a version number. In addition, it should be merged back into `develop`, which may have progressed since the release was initiated.
![](https://www.atlassian.com/git/images/tutorials/collaborating/comparing-workflows/gitflow-workflow/04.svg)

**Conventions:**
* branch off: `develop`
* merge into: *master*
* naming: release/*

*This guide is based on [Atlassian Git Tutorial](https://www.atlassian.com/git/tutorials/comparing-workflows).*