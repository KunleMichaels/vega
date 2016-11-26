<p align="center">
  <img width="140px" src="https://cloud.githubusercontent.com/assets/5632544/20643870/57550d26-b424-11e6-9d95-022f6ae8cbf3.png"/>
  <br/>
  <img width="560px" src="https://cloud.githubusercontent.com/assets/5632544/20643875/78cba032-b424-11e6-94f2-c7904dbb5567.png"/>
</p>

# SocialEdge Vega <img src="https://api.travis-ci.org/socialedge/vega.svg?branch=develop">
Vega is an electronic transit fare payment system that is intended to become a convenient, fast and reliable way to pay for transit.

Our goal is to provide **open** extendable platform and tools to make our cities smarter with affordable fare payment system.

## Features
* **Contactless in mind**

  Vega's main goal is to simplify transit payments and minimize customer actions, therefore it relies heavily on contactless devices.

* **Reusing of existing Smart Card infrastructure**
 
  There is no need to pack customers’ wallets with new cards. Vega uses theirs existing contactless cards and devices instead. This allows to get rid of overpriced vending machines and achieve a huge cost cut.
 
  The primary option for Vega is to use contactless **payment** cards but it's perfectly OK to allow customers to pin almost (ePassports, ISO 14443-4, MRTD, are not supported) every  NFC/RFID-enabled device or ticket. It comes in handy when you want to keep backward compatibility with old solutions, such as prepaid Mifare Classic cards that are widely used in Europe. Legacy vending machines that can read NFC/RFID tags also can be reused.


* **Multiple contactless devices for one account**

  Vega doesn't force a customer to create a separate account for each pass ID. It's allowed to have multiple enabled passes that are assigned to different contactless devices under the same account.  
  
* **Loggable**

  Every transaction and boarding are loggable and provides information when, where and by whom they were fulfilled. This makes a settlement with subcontractors simple and allows to perform advanced analytics. 
  
* **Paperless**

  You don't require to print any receipts. All this data is available online and each passenger can retrieve them via the website.

## Documentation
Documentation for this project is available [here](docs/).

## Bugs and Feedback
For bugs, questions and discussions please use the [Github Issues](https://github.com/socialedge/vega/issues).

## License
Except as otherwise noted this software is licensed under the [GNU General Public License, v3](http://www.gnu.org/licenses/gpl-3.0.txt)

Licensed under the GNU General Public License, v3 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

[http://www.gnu.org/licenses/gpl-3.0.txt](http://www.gnu.org/licenses/gpl-3.0.txt)
