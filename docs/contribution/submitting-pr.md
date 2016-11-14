# Submitting Pull Requests
**Before creating a pull request**, please ensure that an issue exists for the corresponding change in the pull request that you intend to make. **If an issue does not exist, please create one per the guidelines above**. The goal is to discuss the design and necessity of the proposed change with Polymer authors and community before diving into a pull request.

When submitting pull requests, please provide:

* **A reference to the corresponding issue** or issues that will be closed by the pull request. Please refer to these issues in the pull request description using the following syntax:

 ```markdown
 (For a single issue)
 Fixes #20

 (For multiple issues)
 Fixes #32, implementes #40
 ```

* **A succinct description of the design** used to fix any related issues. For example:

 ```markdown
 This fixes #20 by removing styles that leaked which would cause the page to turn pink whenever `paper-foo` is clicked.
 ```

* **At least one test for each bug fixed or feature added** as part of the pull request. Pull requests that fix bugs or add features without accompanying tests will not be considered.

* If a proposed change contains multiple commits, please **squash them into one**.

* Make sure that the commit you propose to pull meet all requirements, described in [commit messages guide](https://github.com/socialedge/edgepay/wiki/Commit-messages).

*This contribution guide is based on [Polymer Elements Guide for Contributors](https://github.com/PolymerElements/ContributionGuide/blob/master/CONTRIBUTING.md).*