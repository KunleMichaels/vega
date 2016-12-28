<p align="center">
  <img width="140px" src="https://cloud.githubusercontent.com/assets/5632544/20643870/57550d26-b424-11e6-9d95-022f6ae8cbf3.png"/>
  <br/>
  <img width="560px" src="https://cloud.githubusercontent.com/assets/5632544/20643875/78cba032-b424-11e6-94f2-c7904dbb5567.png"/>
</p>

# SocialEdge Vega - frontend application
To read more about SocialEdge Vega project please [read this description](https://github.com/socialedge/vega/blob/develop/README.md).

We use:
* [Angular 2](https://angular.io/) with [TypeScript](http://www.typescriptlang.org/),
* [Webpack](http://webpack.github.io/) for building our files and assisting with boilerplate. We're also using Protractor for our end-to-end story and Karma for our unit tests.
* Angular 2 code is being tested with [Jasmine](http://jasmine.github.io/) and [Karma](http://karma-runner.github.io/).
* coverage with [Istanbul](https://github.com/gotwarlost/istanbul)
* End-to-end Angular 2 code using [Protractor](https://angular.github.io/protractor/).
* [Polymer](https://www.polymer-project.org/1.0/) based [Web Components](http://webcomponents.org/),
* Stylesheets with [SASS](http://sass-lang.com/) (not required, it supports regular css too).
* Error reported with [TSLint](http://palantir.github.io/tslint/) and [Codelyzer](https://github.com/mgechev/codelyzer).
* Documentation with [TypeDoc](http://typedoc.org/)

>Warning: Make sure you're using the latest version of Node.js and NPM

We follow the Angular's [Style Guide](http://angular.io/styleguide).

### Quick start

> Clone/Download the repo

```bash
# clone repo
$ git clone https://github.com/socialedge/vega.git vega

# change directory to
$ cd vega/frontend

# install the dependencies
$ npm install && bower install

# start the server
$ npm start
```
go to [http://localhost:8080](http://localhost:8080) in your browser.

# Table of Contents

* [Getting Started](#getting-started)
    * [Dependencies](#dependencies)
    * [Installing](#installing)
    * [Developing](#developing)
    * [Testing](#testing)
    * [Production](#production)
    * [Documentation](#documentation)
* [License](#license)

# Getting Started

## Dependencies

What you need to run this app:
* `node` and `npm` (Use [NVM](https://github.com/creationix/nvm))
* Ensure you're running Node (`v5.x.x`+) and NPM (`3.x.x`+)

## Developing

After you have installed all dependencies you can now start developing with:

* `npm start`

It will start a local server using `webpack-dev-server` which will watch, build (in-memory), and reload for you. The application can be checked at `http://localhost:8080`.

As an alternative, you can work using Hot Module Replacement (HMR):

* `npm run start:hmr`

And you are all set! You can now modify your components on the fly without having to reload the entire page.

## Testing

#### 1. Unit Tests

* single run: `npm test`
* live mode (TDD style): `npm run test-watch`

#### 2. End-to-End Tests (aka. e2e, integration)

* single run:
  * in a tab, *if not already running!*: `npm start`
  * in a new tab: `npm run webdriver-start`
  * in another new tab: `npm run e2e`
* interactive mode:
  * instead of the last command above, you can run: `npm run e2e-live`
  * when debugging or first writing test suites, you may find it helpful to try out Protractor commands without starting up the entire test suite. You can do this with the element explorer.
  * you can learn more about [Protractor Interactive Mode here](https://github.com/angular/protractor/blob/master/docs/debugging.md#testing-out-protractor-interactively)

## Production

To build your application, run:

* `npm run build`

You can now go to `/dist` and deploy that to your server!

## Documentation

You can generate api docs (using [TypeDoc](http://typedoc.org/)) for your code with the following:

* `npm run docs`

# License

Except as otherwise noted this software is licensed under the [GNU General Public License, v3](http://www.gnu.org/licenses/gpl-3.0.txt)

Licensed under the GNU General Public License, v3 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

[http://www.gnu.org/licenses/gpl-3.0.txt](http://www.gnu.org/licenses/gpl-3.0.txt)
