# Weld Homework

* Download the WildFly application server (10.0.0.Final)
* Create a public github repository under your github account
* Commit and push solutions of the following tasks

## Task 1 - Simple CDI bean
* Create a CDI bean shared accross the application and implementing `Factorial` interface
* Verify with `FactorialTest` Arquillian test

## Task 2 - View layer integration
* Finish `FactorialModel` component used in factorial.xhtml JSF template
* Verify manually, i.e. deploy the application to WildFly and test the UI with a web browser

## Task 3 - Events
* Fire `FactorialComputationFinished` event when a computation finishes
* Verify with `FactorialTest.testEvent()`
* Tips: `Event<FactorialComputationFinished>`, `@Observes`

## Task 4 - Parallelization
* Create a new CDI bean which also implements `Factorial` interface but is capable of computing factorial in two threads by splitting the range into two parts
* Enhance `MathOperations` EJB to support asynchronous computation
* Add a new test method to `FactorialTest` to test the new implementation
* Create a `@Parallel` qualifier to distinguish which implementation is required by the client
* Tips: `@Asynchronous`, `Future<BigInteger>`, `AsyncResult`