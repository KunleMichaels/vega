# Vega Use Cases
The following use case diagrams introduce the actors and the high-level use cases they can perform.

## Passenger
<p align="center">
  <img width="650px" src="https://cloud.githubusercontent.com/assets/5632544/20366994/da476474-ac4d-11e6-9f42-c65c62b7e4f9.png"/>
</p>
**Passenger** is a mass transit operator's customer that has a registered account.

### Terminal

#### UC1: Board with contactless smart card
The Passenger uses his contactless smart(/payment) card as an identifier to his registered account. When Passenger touch Boarding Terminal (BT) with a card, BT extracts card details and sends them to the backend for processing. If backend found a valid transit pass, it will send the signal to pass Passenger immediately (extension point 1) otherwise, BT will offer to pay for a transit using any payment card available in account's wallet.

#### UC2: Board with contactless ticket
Passenger can also use contactless tickets. Each ticket's RFID/NFC tag id is tired with "headless" transit pass that isn't assigned to any user.

### Account Management System

#### UC4: Manage cards
Each account has its own wallet, where all Passenger's cards live. These cards are used to buy a transit pass, to pay for a transit and to identify Passenger. Each card that handles identifying function, can by connected with a transit pass. It's allowed to use even non-contactless, but in this case, such card cannot be used as an identifier, therefore it's allowed to assign passes to such cards. 

#### UC5: Buy and assign pass
Pass (Subscription, Travel Card etc) are is a ticket that allows a passenger of the service to take either a certain number of pre-purchased trips or unlimited trips within a fixed period of time. Passenger can buy a pass with any card buy it can be assigned to exactly one **contactless** card and **only once**. Each contactless card can have 0 or more passes assigned.

#### UC6: View transaction
All Passenger's transaction are recorded into Vega's database. Passenger can trace each transaction ever made with any card that was associated with his account's wallet anytime (even if a card was deleted from wallet, transaction history for this cards must be present).
