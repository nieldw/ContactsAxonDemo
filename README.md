# Example: Pragmatic design of an Axon system

This repository is meant to accompany [this blog post](https://medium.com/@nieldw/a-pragmatic-design-for-an-axon-system-b29cc2054d05).
It attempts to lay out a pragmatic approach for creating a system based on the [Axon Framework](http://www.axonframework.org/)
that avoids common pitfalls and supports maintainability.

## Running
Although this project was created with Spring Boot, it has no public endpoints. All the behaviour of the different domain
components have been verified with unit tests, mostly using Axon's test fixtures.

To start the tests, do `$ ./gradlew test`.