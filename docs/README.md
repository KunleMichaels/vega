# SocialEdge Vega Documentation
Welcome to Vega documentation! Here we explain the Vega platform, core concepts, solution architecture and provide guidelines for contributors and developers.

# Contents
* [Overview](#overview)
* [Architecture](#architecture)
* [Coding Conventions](#coding-conventions)
* [Contribution](#contribution)
* [Release Management](#release-management)

# Overview
Vega is an electronic transit fare payment system that is intended to become a convenient, fast and reliable way to pay for transit that was inspired by Chicago’s [Venta](https://www.youtube.com/watch?v=Onlt0WEems4) project. It allows customers to pay or utilize transit passes (mostly) in one tap.

Our goal is to provide **open** extendable platform and tools to make our cities smarter with affordable fare payment system.

## Features
* **Contactless in mind**

  Vega's main goal is to simplify transit payments and minimize customer actions, therefore it relies heavily on contactless smart cards and devices.

* **Reusing of existing Smart Card infrastructure**
 
  There is no need to pack customers’ wallets with new cards, use theirs existing contactless cards instead. This allows to get rid of overpriced vending machines and achieve a huge cost cut.
 
  The primary option for Vega is to use contactless **payment** cards but it's perfectly ok to allow customers to pin almost (ePassports, ISO 14443-4, MRTD, are not supported) every  NFC/RFID device or card. It comes in handy when you want to keep backward compatibility with old solutions, such as prepaid Mifare Classic cards that are widely used in Europe. Legacy vending machines that can read NFC/RFID tags also can be kept. The only thing needed is the Internet connection.

* **Different business models**
  
  Besides traditional pay for transit in place model, Vega supports transit passes - a ticket that allows a passenger of the service to take either a certain number of pre-purchased trips or unlimited trips within a fixed period of time. Customers can buy and assign them in their web account.

* **Multiple contactless devices for one account**

  Vega doesn't force a customer to create a separate account for each payment card or smart device. It's allowed to have multiple enabled passes that are assigned to different cards under the same account.  
  
* **Loggable**

  Every transaction and boarding are loggable and provides the information when, where and by whom they were fulfilled. This makes an accounting with subcontractors simple and allows to perform advanced analytics. 
  
* **Paperless**

  You don't require to print any receipts. All this data is available online and each passenger can retrieve them via the website.

## How does it work?
<p align="center">
  <img width="512px" src="https://cloud.githubusercontent.com/assets/5632544/20359397/ed059f0e-ac2f-11e6-8ce2-81595598bcab.png"/>
</p>
Vega utilizes RFID and NFC contactless technologies for passenger identification and payments. The solution consists of 3 main components: boarding terminal (a RFID/NFC scanner), backend and frontend applications. 

Boarding terminals are directly responsible for identifying passenger and payment process. When a regular passenger wants to board with Vega, he taps the terminal with his payment card (PayPass or VisaWave) and purchases a transit. 
<p align="center">
  <img width="580px" src="https://cloud.githubusercontent.com/assets/5632544/20359144/d72cf73c-ac2e-11e6-9baa-9dd346c36ac4.png"/>
</p>

A regular passenger can register an account online in Vega via the website to access more features. The Contactless Payment Card of a registered passenger becomes not only his payment option but his account's identifier too. That allows buying different transit passes (city cards), review the transaction history, add new Payment Cards or any other Contactless Smart Card and tie them with different passes.

## Use Cases
The following use case diagrams introduce the actors and the high-level use cases they can perform.

### Passenger
<p align="center">
  <img width="650px" src="https://cloud.githubusercontent.com/assets/5632544/20366994/da476474-ac4d-11e6-9f42-c65c62b7e4f9.png"/>
</p>
**Passenger** is a mass transit operator's customer that has a registered account.

#### Terminal

##### UC1: Board with contactless smart card
Passenger uses his contactless smart(/payment) card as an identifier to his registered account. When Passenger touch Boarding Terminal (BT) with a card, BT extracts card details and sends them to the backend for processing. If backend found a valid transit pass, it will send the signal to pass Passenger immediately (extension point 1) otherwise, BT will offer to pay for a transit using any payment card available in account's wallet.

##### UC2: Board with contactless ticket
Passenger can also use contactless tickets. Each ticket's RFID/NFC tag id is tired with "headless" transit pass that isn't assigned to any user.

#### Account Management System

##### UC4: Manage cards
Each account has its own wallet, where all Passenger's cards live. These cards are used to buy a transit pass, to pay for a transit and to identify Passenger. Each card that handles identifying function, can by connected with a transit pass. It's allowed to use even non-contactless, but in this case, such card cannot be used as an identifier, therefore it's allowed to assign passes to such cards. 

##### UC5: Buy and assign pass
Pass (Subscription, Travel Card etc) are is a ticket that allows a passenger of the service to take either a certain number of pre-purchased trips or unlimited trips within a fixed period of time. Passenger can buy a pass with any card buy it can be assigned to exactly one **contactless** card and **only once**. Each contactless card can have 0 or more passes assigned.

##### UC6: View transaction
All Passenger's transaction are recorded into Vega's database. Passenger can trace each transaction ever made with any card that was associated with his account's wallet anytime (even if a card was deleted from wallet, transaction history for this cards must be present).

### Guest
<p align="center">
  <img width="650px" src="https://cloud.githubusercontent.com/assets/5632544/20388015/322e90de-acc3-11e6-9cc4-4c120e847570.png"/>
</p>
**Guest** is a mass transit operator's customer that has **not** registered an account.

##### UC7: Board with contactless ticket
Guest can use contactless tickets to board. Each ticket's RFID/NFC tag id is tired with "headless" transit pass that isn't assigned to any account.

##### UC8: Pay with contactless payment card
Since Guest has no account, contactless payment card isn't used as an identifier (see UC1). In that case, Guest's Contactless Payment Card is used the same way you pay in store: touch, pay & board.

# Architecture

This section gives you a high level overview of Vega's core concepts and its architecture.

## Architecture Overview
Transit fare systems like Vega must be robust, scalable, fault-tolerant and highly available. That's why we've chosen a **stateless**, **event-driven** **microservice** architecture. 

A running Vega system contains backend platform, frontend application and boarding terminal device. This diagram shows very hight level components, though we're still working on a few things.

<p align="center">
  <img width="600px" src="https://cloud.githubusercontent.com/assets/5632544/20445226/7a3f05d6-add4-11e6-935b-c97d19d6b4c6.png"/>
</p>

The Vega’s heart is backend platform that is responsible for fare and customer management, payments and boarding process. Communication between backend components is fully asynchronically and message-driven.

API Gateway provides a centralized proxy for external calls to backend component from fronted and terminal. Frontend is a standalone JS application, while Boarding Terminal is operated by its firmware. Both, Frontend and Boarding Terminal consume REST Gateway's API. 

<p align="center">
  <img width="600px" src="https://cloud.githubusercontent.com/assets/5632544/20384102/c7d20816-acb2-11e6-8a10-e888adfcc4d8.png"/>
</p>

Each service, gateway and frontend application are deployed in theirs own Docker container on Google Kubernets CaaS that provides infrastructure, Service Registry, Service Discovery and Load Balancing features (to simplify the diagram, Docker containers and many K8s internal thighs, like Service Registry, were omitted).

## Vega Boarding
Boarding process starts when Passenger touches the Boarding Terminal with his Contactless Smart Card or Ticket. The interaction between different components in boarding scenario is described in the following diagrams:  

<p align="center">
  <img width="600px" src="https://cloud.githubusercontent.com/assets/5632544/20408129/76a54ffe-ad14-11e6-8c97-46168c186360.png"/>
</p>

<p align="center">
  <img width="600px" src="https://cloud.githubusercontent.com/assets/5632544/20387647/64cee4a0-acc1-11e6-9642-092c3d6bdc89.png"/>
</p>

<p align="center">
  <img width="600px" src="https://cloud.githubusercontent.com/assets/5632544/20408176/96e5e7a6-ad14-11e6-983a-103483762c26.png"/>
</p>

<p align="center">
  <img width="600px" src="https://cloud.githubusercontent.com/assets/5632544/20387662/7a9adde8-acc1-11e6-90f8-1a032e32c1b1.png"/>
</p>

# Coding Conventions

This section contains standard conventions that we at SocialEdge follow and recommend that others follow. It covers filenames, file organization, indentation, comments, declarations, statements, white space, naming conventions, programming practices and includes some code examples.

## Code Style Guide

We are adhering to Google's [Style Guide](https://google.github.io/styleguide/) to maintain readability and general code prettiness. Please follow these guides if you want to contribute some code to the project. 

 * [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) ([Eclipse Code Style Scheme](https://raw.githubusercontent.com/google/styleguide/gh-pages/eclipse-java-google-style.xml) | 
   [IntelliJ Code Style Scheme](https://raw.githubusercontent.com/google/styleguide/gh-pages/intellij-java-google-style.xml))
 * [Google HTML/CSS Style Guide](https://google.github.io/styleguide/htmlcssguide.xml)
 * [Google JavaScript Style Guide](https://google.github.io/styleguide/javascriptguide.xml)
 * [Google AngularJS Style Guide](https://google.github.io/styleguide/angularjs-google-style.html)

###Some reasons for following conventions
* 80% of the lifetime cost of a piece of software goes to maintenance.
* Hardly any software is maintained for its whole life by the original author.
* Code conventions improve the readability of the software, allowing engineers to understand new code more quickly and thoroughly.

# Contribution

This section provides detailed instructions on the contribution workflow. Please follow these guidelines to keep the  project management quality consistent.

## Filing Issues
Each issue/feature/bugfix must be described as a GitHub issue to maintained GitHub Project Board and to ensure that everything is documented. Before you begin, perform a web / GitHub search to avoid creating a duplicate ticket. 

**If you are filing an issue to request a feature**, please provide a clear description of the feature. It can be helpful to describe answers to the following questions:
* **Who will use the feature?**
* **When will they use the feature?**
* **What is the user’s goal?**

**If you are filing an issue to report a bug**, please provide:
* **A clear description of the bug and related expectations.** Consider using the following example template for reporting a bug:

 ```markdown
 The `paper-foo` element causes the page to turn pink when clicked.

 ## Expected outcome

 The page stays the same color.

 ## Actual outcome

 The page turns pink.

 ## Steps to reproduce

 1. Put a `paper-foo` element in the page.
 2. Open the page in a web browser.
 3. Click the `paper-foo` element.
 ```

* **A reduced test case that demonstrates the problem.**

*This contribution guide is based on [Polymer Elements Guide for Contributors](https://github.com/PolymerElements/ContributionGuide/blob/master/CONTRIBUTING.md).*

## GitFlow Overview

We are using GitFlow in EdgePay project. The Gitflow Workflow section below is derived from Vincent Driessen at [nvie](http://nvie.com/posts/a-successful-git-branching-model/) and [Atlassian Git Tutorial](https://www.atlassian.com/git/tutorials/comparing-workflows/feature-branch-workflow). **Please pay attention to conventions**, described after each branch type.

The Gitflow Workflow defines a strict branching model designed around the project release. This workflow doesn’t add any new concepts or commands beyond what’s required for the Feature Branch Workflow. Instead, it assigns very specific roles to different branches and defines how and when they should interact. In addition to feature branches, it uses individual branches for preparing, maintaining, and recording releases. Of course, you also get to leverage all the benefits of the Feature Branch Workflow: pull requests, isolated experiments, and more efficient collaboration.

### How It Works

The Gitflow Workflow still uses a central repository as the communication hub for all developers. And, as in the other workflows, developers work locally and push branches to the central repo. The only difference is the branch structure of the project.

The only branch where developers can contribute their regular changes is `develop` branch. It's done with Feature Branches **via Pull Requests only**.

#### Feature Branches
Each new feature should reside in its own branch, which can be pushed to the central repository for backup/collaboration. But, instead of branching off of `master`, feature branches use `develop` as their parent branch. When a feature is complete, it gets merged back into `develop`. Features should never interact directly with `master`.
![](https://www.atlassian.com/git/images/tutorials/collaborating/comparing-workflows/gitflow-workflow/03.svg)

**Conventions:**
* branch off: `develop`
* merge into: *develop* **via Pull Requests**
* naming: feature/*

#### Hotfix Branches
Hotfix branches are used to quickly patch production releases. This is the only branch that should fork directly off of `master`. As soon as the fix is complete, it should be merged into both `master` and `develop` (or the current release branch), and `master` should be tagged with an updated version number.

Having a dedicated line of development for bug fixes lets EdgePay team address issues without interrupting the rest of the workflow or waiting for the next release cycle. You can think of maintenance branches as ad-hoc release branches that work directly with master.
![](https://www.atlassian.com/git/images/tutorials/collaborating/comparing-workflows/gitflow-workflow/05.svg)

**Conventions:**
* branch off: `master`
* merge into: *master* & *develop*
* naming: hotfix/*

Example, that demonstrates how this workflow can be used to manage a single release cycle is available [here](https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow).

*This guide is based on [Atlassian Git Tutorial](https://www.atlassian.com/git/tutorials/comparing-workflows).*

## Commit messages

Please follow this guide to write commit messages consistently and correctly.

### Commit structure
```
Capitalized, short (50 chars or less) summary

More detailed explanatory text, if necessary.  Wrap it to about 72
characters or so.  In some contexts, the first line is treated as the
subject of an email and the rest of the text as the body.  The blank
line separating the summary from the body is critical (unless you omit
the body entirely); tools like rebase can get confused if you run the
two together.

Write your commit message in the present tense: "Fix bug" and not "Fixed
bug."  This convention matches up with commit messages generated by
commands like git merge and git revert.

Further paragraphs come after blank lines.

- Bullet points are okay, too

- Typically a hyphen or asterisk is used for the bullet, preceded by a
  single space, with blank lines in between, but conventions vary here

- Use a hanging indent
```

**Before you commit, make sure that:**
* First line is short, <= 50 chars
* **You live the second line blank**
* Each line in commit body is less then 72
* **Commit message is meaningful. It says what the change includes and why.**
* **The summary starts with a verb in the present tense** (e.g. Add, change, fix etc.).

Why good commit messages matter? See [this article](http://chris.beams.io/posts/git-commit/).

### Examples

**Good commit:**
```
git commit -m "Add instructions to help setup gmail 2FA \
\
For those who use two-factor authentication with gmail, git-send-email \
will not work unless it is setup with an app-specific password. \
The example for setting up git-send-email for use with gmail will now \
include information on generating and storing the app-specific password."
```
Short title < 50 ?, empty line after title ?, clear statement ?, the summary starts with a verb in the present tense ?, each line in commit body is less then 72 ?

**Bad commit:**
```
git commit -m "Initial API implementation \
Rename repository to spica-deployments \
Cleanup project after project renaming"
```
Short title < 50 ?, empty line after title ?, clear statement ? (no statement at all)

*This contribution guide is based on [A Note About Git Commit Messages](http://tbaggery.com/2008/04/19/a-note-about-git-commit-messages.html) article.*

## Submitting Pull Requests
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

## GitFlow Release

Once `develop` has acquired enough features for a release (or a predetermined release date is approaching), a release branch will be forked off of `develop`. Creating this branch starts the next release cycle, so no new features can be added after this point—only bug fixes, documentation generation, and other release-oriented tasks should go in this branch. Once it's ready to ship, the release gets merged into `master` and tagged with a version number. In addition, it should be merged back into `develop`, which may have progressed since the release was initiated.
![](https://www.atlassian.com/git/images/tutorials/collaborating/comparing-workflows/gitflow-workflow/04.svg)

**Conventions:**
* branch off: `develop`
* merge into: *master*
* naming: release/*

*This guide is based on [Atlassian Git Tutorial](https://www.atlassian.com/git/tutorials/comparing-workflows).*

## Versioning

Our versioning strategy follows [Semantic Versioning Specification v2.0.0](http://semver.org/spec/v2.0.0.html).

Given a version number MAJOR.MINOR.PATCH, increment the:
* MAJOR version when you make incompatible API changes,
* MINOR version when you add functionality in a backwards-compatible manner, and
* PATCH version when you make backwards-compatible bug fixes.

Additional labels for pre-release and build metadata are available as extensions to the MAJOR.MINOR.PATCH format.

More info [http://semver.org/spec/v2.0.0.html](on semver.org).

# Help to improve this documentation
The Vega documentation is hosted on GitHub, and we welcome your feedback.

Click the Edit this page on GitHub link at the top of a documentation page to open the file in our GitHub repository, where you are invited to suggest changes by creating pull requests or open a discussion by creating an issue.

## Contact us
Feel free to contact the documentation team directly at <a href="mailto:vega-docs@socialedge.eu">vega-docs@socialedge.eu</a>