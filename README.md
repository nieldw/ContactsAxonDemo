# Example: Pragmatic design of an Axon system

This repository is meant to accompany [this blog post](https://medium.com/@nieldw/a-pragmatic-design-for-an-axon-system-b29cc2054d05).
It attempts to lay out a pragmatic approach for creating a system based on the [Axon Framework](http://www.axoniq.io/)
that avoids common pitfalls and supports maintainability.

## Running
The application has three profiles: `rest`, `command` and `query`.

To run the application with all profiles active, simply do: `$ ./gradlew bootRun`

To run the application with a specific profile, do: `$ SPRING_PROFILES_ACTIVE={profile} ./gradlew bootRun`

For example: `$ SPRING_PROFILES_ACTIVE=command ./gradlew bootRun`

## Tests
This app has thorough automated tests, including unit tests using Axon's test fixtures for testing aggregates and sagas. Other tests include a spring boot application context test and `@DataJpaTest`.

To start the tests, do `$ ./gradlew test`.