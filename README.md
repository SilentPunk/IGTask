# Introduction

Welcom to simple application that is created for interview purposes. Backend and fronted are separate projects:

- front - is simple project builded with react, sage-carbon ready components and flux for dispatching events,
- back - is build with mvn, springboot, springdata, H2 database

Each of sub-project has separate readme file with details how to run it.

## Ideas

There are couple things that can be do better way:

### Testing

- We can create integration test for springboot application,
- We can create some E2E test - for example using tools like cucumber - to test frontend with backend
- Add UI tests for components

### Front

- Find some more eficiency way to do some polling from REST service - for now is just simple setInterval - but after
learning domain of this product we can propose some solution like websocket protocol or maybe create some listener
that we will be refresh elements after they change on backend side
- Adding functionality to create new user - right now is hardcoded default value

### Back

- Some additional logging - using for example AOP with log4j - with information about request and response,
- Creating separate Patch endpoint - for now we have one Post endpoint that is working like Post when we want add
some new bookmark and like Patch when we just update existing one bookmark - so this should be propably moved to some
fron end logic.
