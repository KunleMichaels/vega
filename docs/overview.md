# SocialEdge Vega Overview

Vega is an electronic transit fare payment system that is intended to become a convenient, fast and reliable way to pay for transit that was inspired by Chicago’s Venta project. It allows customers to pay or utilize transit passes (mostly) in one tap.

* **Contactless in mind**

  Vega's main goal is to simplify transit payments and minimize customer actions, therefore it relies heavily on contactless smart cards and devices.

* **Reusing of existing Smart Card infrastructure**
 
  There is no need to pack customers’ wallets with new cards, use theirs existing contactless cards instead. This allows to get rid of overpriced vending machines and achieve a huge cost cut.
 
  The primary option for Vega is to use contactless **payment** cards but it's perfectly ok to allow customers to pin almost (ePassports, ISO 14443-4, MRTD, are not supported) every  nfc/rfid device or card. It comes in handy when you want to keep backward compatibility with old solutions, such as prepaid Mifare Classic cards that are widely used in Europe. Legacy vending machines that can read rfid/nfc tags also can be kept. The only thing needed is the Internet connection.

* **Different bussines models**
  
  Besides traditional pay for transit in place model, Vega supports transit passes - a ticket that allows a passenger of the service to take either a certain number of pre-purchased trips or unlimited trips within a fixed period of time. Customers can buy and assign them in their web account.

* **Multiple contactless devices for one account**

  Vega doesn't force a customer to create a separate account for each payment card or smart device. It's allowed to have multiple enabled passes that are assigned to different cards under the same account.  
  
* **Loggable**

  Every transaction and boarding are loggable and provides the information when, where and by whom they were fulfilled. This makes an accounting with subcontractors simple and allows to perform advanced analytics. 

Our goal is to provide **open** and extendable platform and tools to make our cities smarter with affordabple.