# Vega Architecture Overview
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