# Vega Architecture Overview
A running Vega system contains backend platform, frontend application and boarding terminal device. This diagram shows very hight level components, though we're still working on a few things.

<p align="center">
  <img width="600px" src="https://cloud.githubusercontent.com/assets/5632544/20363924/718aae34-ac41-11e6-83f4-9e38f8154420.png"/>
</p>

The Vega’s heart is backend platform that is responsible for fare and customer management, payments and boarding process. Communication between backend components is fully asynchronically and message-driven.

API Gateway provides a centralized proxy for external calls to backend component from fronted and terminal. Frontend is a standalone JS application, while Boarding Terminal is operated by its firmware. Both, Frontend and Boarding Terminal consume REST Gateway's API. 

## Contents
* [Use Cases](usecases.md)