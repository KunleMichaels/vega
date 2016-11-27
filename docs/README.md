# SocialEdge Vega Documentation
Welcome to Vega documentation! Here we explain the Vega platform, core concepts, solution architecture and provide guidelines for contributors and developers.

## Help to improve this documentation
The Vega documentation is hosted on GitHub, and we welcome your feedback.
Click the Edit this page on GitHub link at the top of a documentation page to open the file in our GitHub repository, where you are invited to suggest changes by creating pull requests or open a discussion by creating an issue.

## Contact us
Feel free to contact the documentation team directly at <a href="mailto:vega.docs@socialedge.eu">vega.docs@socialedge.eu</a>

# Contents
* [Vision Statement](#vision-statement)
* [Definitions](#definitions)
* [Software Requirements Specification](#software-requirements-specification)
  * [Overview](#overview)
  * [Goals and Objectives](#goals-and-objectives)
  * [Scope](#scope)
  * [General Design Constraints](#general-design-constraints)
  * [Nonfunctional Requirements](#nonfunctional-requirements)
  * [System Features](#system-features)
* [Architecture Design](#architecture-design)
  * [Logical View](#logical-view)
    * [High-Level Design](#high-level-design)
    * [Mid-Level Design](#mid-level-design)
  * Data View
  * User Interface View
  * [Deployment View](#deployment-view)
* [Coding Conventions](#coding-conventions)
* [Contribution](#contribution)
* [Release Management](#release-management)

# Vision Statement
Vega is a Chicago Venta-inspired electronic transit fare system designed for passengers of mass transit operators, that is intended to become a convenient, fast and reliable way to pay for transit. Unlike most fare systems in Europe, Vega is a contactless in mind system, that focuses on reusing of existing customer’s contactless devices such as contactless credit cards and mobile NFC-enabled devices but with keeping compatibility with old card infrastructure (e.g. Mirfare City Cards) and traditional ticketing by RFID technology utilization.

Our goal is to provide open extendable platform and tools to make cities smarter with affordable fare payment system.

# Definitions
The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT", "SHOULD", "SHOULD NOT", "RECOMMENDED", "MAY", and "OPTIONAL" in this documentation are to be interpreted as described in RFC 2119.

- **Use case** – describes a goal-oriented interaction between the system and an actor. A use case may define several variants called scenarios that result in different paths through the use case and usually different outcomes.
- **Product** or **Application** or **System** – what is being described in this documentation; the software system specified in this documentation.
- **Operator** – an operator of mass transit.
- **Payment credit card record** or **Payment credit card**  – a record in the system that contains all information about a credit card that allows charging a passenger bank account.
- **Transit value** - money “stored” on a deattached contactless device that’s used to pay for regular fares.
- **Contactless device** – any item that contains RFID or NFC tag which is used to identify a passenger.
- **Transit account** – a virtual account in the system that has a passenger’s payment credit card record and a set of contactless devices attached to it.
- **Shadow transit account** – a transit account of unregistered passenger. Such account has no payment credit card record, transit value for payment operations is used instead.
- **Attached contactless device** – any contactless devices attached to transit account.
- **Deattached contactless device** – any contactless devices, that are not attached to a transit account (contactless tickets mostly) or attached to a shadow transit account
- **Check-in** – a passenger boarding process that includes transit pass purchasing or utilization
- **Check-out** – time when passenger registers his leaving a public transport vehicle.
- **Frontend** – the user interface application.
- **Backend** – the business and computing layer of the Vega system.
- **Registered passenger** – a passenger, which uses attached contactless device for check-in and check-out.
- **Unregistered passenger** – a passenger, which uses deattached contactless device for check-in and check-out.
- **Transit pass**, **pass** – a ticket that allows a passenger of the service to take either a certain number of pre-purchased trips or unlimited trips within a fixed period.

# Software Requirements Specification
## Overview
Vega is a system designed to be an electronic transit fare system that simplifies transit payments and minimize customer actions, therefore it relies heavily on contactless smart cards and devices. Vega handles passenger check-in and check-out, logging, payment transaction processing and transit pass utilization. Once passenger registered in Vega system, he can attach multiple contactless devices, buy and assign transit passes to them, view transaction via web-interface and use them to pay for transit.

The purpose of the software requirement specification section is to list the application’s requirements in a manner that can be easily understood and verified by the operators, yet provide enough details so that the SocialEdge team and contributors can build the application using the details contained herein. It does not address any project issues such as schedule, development methods, development phases, deliverables or testing procedures.

## Goals and Objectives
The main goals of this application are to develop open and extendable transit fare platform and tools that will provide the following:

1. Simple transit payments with fewer customer actions by utilization contactless technologies;
2. Reusing of existing contactless devices such as contactless credit cards, RFID-enabled tickets, and mobile NFC-enabled devices;
3. Paperless accounting;
4. Full logging of payments and transit pass utilization;
5. Tools for settlement with operators based on logging statistics.

## Scope
The Vega platform covers the entire fare handling flow: from transit pass handling and fare inspection tools to means of a settlement with operators. Nevertheless, it takes part in neither transit tariffs calculations nor payment processing which is delegated to third-party payment gateways.

## General Design Constraints
### Product Environment
The product may operate in the environment, where many different operators are present and each of them has their own set of defined tariffs. Therefore, Vega should provide fluent migration process. During the migration, Vega should support old contactless devices such as Mirfare City Cards.

The Vega system consists of three main components: backend, frontend and boarding terminal. Each of them has their own environment boundaries.

<p align="center">
  <img width="600px" src="https://cloud.githubusercontent.com/assets/5632544/20643938/58cb76a2-b426-11e6-903b-20deed0bd2da.png"/>
</p>

The backend resides on IaaS/CaaS/SaaS cloud and utilizes such its components as a load balancer, virtualization tools, network tools and firewalls. Moreover, backend requires external RDBMS that resides on the same cloud.

The frontend application shares the same environment with the backend, except RDBMS. In addition, it requires a web server, where it’s deployed.

The boarding terminal resides in public transport vehicles and requires stable connection to the backend application via the Internet. It may also require connection to a vehicle’s cockpit to receive data about the vehicle position and generate terminal installation details, required for transaction processing.

### User Characteristics
The following categories of users can be distinguished for this application:

1. **Passengers**, primary, secondary students and adults with varying computer technology proficiencies, who are the primary users of the user interface and boarding terminals.
2. **Administrative assistants**, who manage mass transit operators’ accounts and mediate settlements with mass transit operators.  The administrative assistants are probably the least technically trained of all the users.
3. Mass transit operators’ **management staff**, who maintains any operator related data such as available transit passes, tariffs etc. Management staff is probably the most technically trained users.

### Mandated Constraints
1. The application must not use any proprietary software: libraries, database management system, programming language, operating system etc.

## Nonfunctional Requirements
### Usability Requirements
1. Boarding terminal’s interface elements (e.g. menus) should be big enough to be easy to hit for people with poor vision: min font size - 0.26º (visual arc), padding = font size.
2. The interface actions and elements should be consistent.
3. Error messages should explain how to recover from the error.
4. Actions which cannot be undone should ask for confirmation.
5. The system may be customizable to meet specific operators’ needs.

### Operational Requirements
1. The users’ environment is noisy so the system shouldn’t depend on the user hearing the audible output.
2. A boarding terminal shall be used by a passenger, standing up in a place with limited room for movements.
3. The frontend application must be able to interface with any modern HTML browser.

### Performance Requirements
1. The operations carried out in the system on a passenger request using a boarding terminal must respond within 4 seconds.
2. The system must support up to the half area’s population operatable by system concurrent users.
3. The system must meet or exceed 99% uptime.
4. The system must not be unavailable more than 1 hour per 1000 hours of operation.

### Security Requirements
1. The application shall identify all its client applications before allowing them to use its capabilities.
2. Classified data (confidential or strictly confidential such as credit card details) must be encrypted while data transmission or storing and provide end-to-end encryption.

### Legal Requirements
1. Each payment operation carried out in the system must be followed with a printable receipt.
2. The passenger’s credit card details must be visible to this passenger only.
3. For shared transit pass definitions, a settlement with operators must consider the pass usage statistics.

### External: User Interface
The boarding terminal’s home screen may offer nothing but a hint to use the passenger’s contactless device (registered on his account) to board the vehicle. The buy screen (which appears in the case of lack of eligible transit pass) should offer a very short list of clickable buttons with transit passes to purchase.

The frontend application’s interface should follow the Google Material Design Style Guide. It must be clear for passengers and not overloaded with text or web elements.

## System Features
### Feature: Board the public transit vehicle (registered passenger)
<p align="center">
  <img width="600px" src="https://cloud.githubusercontent.com/assets/5632544/20643968/1fad9412-b427-11e6-8b71-ca53e5630ea7.png"/>
</p>
A registered passenger may use his attached contactless device to check-in a public transit vehicle. During the check-in, the passenger utilizes available transit pass or purchases new one.
 
In the case of purchasing new transit pass, the registered passenger will be charged using primary payment credit card record.

Check-out is an optional feature which may be forced by an operator.

- **Cost:** high
- **Risk:** high
- **Value:** high

### Feature: Board the public transit vehicle (unregistered user)
<p align="center">
  <img width="600px" src="https://cloud.githubusercontent.com/assets/5632544/20643998/8bfbefe2-b427-11e6-9e79-36c28bb8cfec.png"/>
</p>

When an unregistered passenger’s contactless device interacts with the system to purchase a pass for the first time, the system creates a shadow transit account and attaches used contactless device to it. The system skips creating a payment credit card record and creates transit value record instead. The passenger can top-up the transit value then and buy a pass.

It comes in handy for migration and backward compatibility with the old fare systems, because it allows the passengers to use their old cards in the same way they used them before the Vega system even without registration.

This feature also covers deattached contactless tickets processing and any other contactless device that a passenger may not want to register.

The check-in and check-out processes are conducted in the same way as described in the previous feature, except purchasing a pass case, where transit value is used instead of charging account using a payment credit card record.

- **Cost:** high
- **Risk:** high
- **Value:** medium

### Feature: Account Management System (registered user)
<p align="center">
  <img width="600px" src="https://cloud.githubusercontent.com/assets/5632544/20644010/d35f6710-b427-11e6-86a1-1efcd48f8c99.png"/>
</p>
A registered passenger has an access to Account Management System via the frontend application’s user interface, where he can manage contactless devices used to identify his account, manage payment credit cards records, buy a transit pass, assign passes to contactless devices and view payment transaction.

### Feature: Account Management System (unregistered user)
<p align="center">
  <img width="600px" src="https://cloud.githubusercontent.com/assets/5632544/20644019/ec92d154-b427-11e6-9931-914f214cd3c2.png"/>
</p>

In the context of account management system use case, an unregistered passenger can sign-in and sign-up to the system.

If the unregistered passenger has no any deattached contactless devices with transit value, he registers new, greenfield account.
 
An unregistered passenger may also want to register a regular transit account and attach deattached contactless device to it. In this case, transit value will be used as a primary payment method. Once transit value becomes empty, the system will destroy it and a payment credit card record is used instead.

# Architecture Design

This section gives you a high level overview of Vega's core concepts and its architecture.

## Architecture Overview
The purpose of this document is to describe the architecture and design of the Vega application in a way that addresses the interests and concerns of developers, DevOps and UX/UI developers.

This document provides a comprehensive architectural overview of the system, using a number of different architectural views to depict different aspects of the system:

1.	**Logical View** includes major components, their attributes and operations. This view also includes relationships between components and their interactions;
2.	**Data View** describes how and where application’s data is persisted; 
3.	**User Interface View** includes UI prototypes; 
4.	**Deployment View** describes how system components will be physically deployed.

It is intended to capture and convey the significant architectural decisions which have been made on the system.

### Logical View
The Vega application is divided into layers based on the 3-tier architecture:

<p align="center">
  <img width="300px" src="https://cloud.githubusercontent.com/assets/5632544/20664567/0fd07d26-b55c-11e6-98ee-1f77aec4322b.png"/>
</p>

The layering model of the online catering application is based on a responsibility layering strategy that associates each layer with a particular responsibility. 

This strategy has been chosen because it isolates various system responsibilities from one another, so that it improves both system development and maintenance.

#### High-Level Design
The high-level view or architecture consists of three major components:

<p align="center">
  <img width="400px" src="https://cloud.githubusercontent.com/assets/5632544/20665258/30a5ab5e-b55f-11e6-8cc2-3a5416da471d.png"/>
</p>

* The **frontend application** provides UI for managing a passenger’s account (passenger’s wallet, transit passes, transit value) and provides a dashboard for administrative assistants and management staff.
* The **boarding terminal** is a device that resides in public transport vehicles that a passenger uses to check-in and check-out.
* The **backend application** contains business logic and serves the frontend application and the boarding terminal.

#### Mid-Level Design
Transit fare systems like Vega must be robust, scalable, fault-tolerant and highly available. That's why we've chosen a **stateless**, **event-driven** **microservice** architecture. 

A running Vega system contains backend platform, frontend application and boarding terminal device. This diagram shows very hight level components, though we're still working on a few things.

<p align="center">
  <img width="600px" src="https://cloud.githubusercontent.com/assets/5632544/20664656/6543303c-b55c-11e6-8e93-0f4395bae085.png"/>
</p>

* The **Customer Microservice** is responsible for handling all customer-related activities: passenger and operator registration, passenger wallet managing.
* The **Fare Microservice** manages fare tariffs and transit passes.
* The **Boarding Microservice** is responsible for passengers’ boarding: check-in and check-out. Makes a decision whether the passenger is eligible to board or not.
* The **Payment Microservice** handles all payment-related activities such as settlement with operators, and transit purchasing.
* The **Boarding Terminal Firmware** provides high-level API for a boarding terminal device.
* The **API Gateway** manages all microservices’ API and proxy request from the outside world (a boarding terminal or the frontend application) directly to particular microservice.

##### Boarding
Boarding process starts when a passenger touches the boarding terminal with his contactless device. The interaction between different components in boarding scenario is described in the following diagrams:

<p align="center">
  <img width="600px" src="https://cloud.githubusercontent.com/assets/5632544/20664976/f44c1d88-b55d-11e6-9719-e14255657c31.png"/>
</p>
<p align="center">
  <img width="600px" src="https://cloud.githubusercontent.com/assets/5632544/20664994/06ab2aaa-b55e-11e6-9a91-cd1ee8662d47.png"/>
</p>
<p align="center">
  <img width="600px" src="https://cloud.githubusercontent.com/assets/5632544/20665005/14498e72-b55e-11e6-9e20-ab25844161ae.png"/>
</p>

### Deployment View
The Frontend application and backend’s components are deployed inside Docker container on a Kubernetes-enabled cloud provider that provides infrastructure, Service Registry, Service Discovery and Load Balancing features.

<p align="center">
  <img width="600px" src="https://cloud.githubusercontent.com/assets/5632544/20665031/352857e0-b55e-11e6-928e-64f8747ba792.png"/>
</p>

* **Docker** allows backend’s microservices to be isolated into containers with instructions for exactly what they need to survive that can be easily ported from machine to machine.
* A K8s **Pod** (as in a pod of whales or pea pod) is a group of one or more containers (such as Docker containers), the shared storage for those containers, and options about how to run the containers. Pods are always co-located and co-scheduled, and run in a shared context.
* K8s **Services** provide load balancing capabilities for K8S pods (containers) where Docker images reside.
* A K8s **Ingress** is a collection of rules that allow inbound connections to reach the cluster services. It makes the frontend application and an API Gateway to be available outside the cluster.

# Coding Conventions

This section contains standard conventions that we at SocialEdge follow and recommend that others follow. It covers filenames, file organization, indentation, comments, declarations, statements, white space, naming conventions, programming practices and includes some code examples.

## Code Style Guide

We are adhering to Google's [Style Guide](https://google.github.io/styleguide/) to maintain readability and general code prettiness. Please follow these guides if you want to contribute some code to the project. 

 * [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) ([Eclipse Code Style Scheme](https://raw.githubusercontent.com/google/styleguide/gh-pages/eclipse-java-google-style.xml) | 
   [IntelliJ Code Style Scheme](https://raw.githubusercontent.com/google/styleguide/gh-pages/intellij-java-google-style.xml))
 * [Google HTML/CSS Style Guide](https://google.github.io/styleguide/htmlcssguide.xml)
 * [Google JavaScript Style Guide](https://google.github.io/styleguide/javascriptguide.xml)
 * [Google AngularJS Style Guide](https://google.github.io/styleguide/angularjs-google-style.html)

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
<p align="center">
  <img width="600px" src="https://www.atlassian.com/git/images/tutorials/collaborating/comparing-workflows/gitflow-workflow/03.svg"/>
</p>

**Conventions:**
* branch off: `develop`
* merge into: *develop* **via Pull Requests**
* naming: feature/*

#### Hotfix Branches
Hotfix branches are used to quickly patch production releases. This is the only branch that should fork directly off of `master`. As soon as the fix is complete, it should be merged into both `master` and `develop` (or the current release branch), and `master` should be tagged with an updated version number.

Having a dedicated line of development for bug fixes lets EdgePay team address issues without interrupting the rest of the workflow or waiting for the next release cycle. You can think of maintenance branches as ad-hoc release branches that work directly with master.
<p align="center">
  <img width="600px" src="https://www.atlassian.com/git/images/tutorials/collaborating/comparing-workflows/gitflow-workflow/05.svg"/>
</p>

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
<p align="center">
  <img width="600px" src="https://www.atlassian.com/git/images/tutorials/collaborating/comparing-workflows/gitflow-workflow/04.svg"/>
</p>

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