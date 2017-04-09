# jvm-instrumentation
Goal: measure methods execution time using instrumentation.
### Usage
```
mvn integration-test
```
### Results
In the test phase, tests run wihtout the agent, so there are no messages printed to stdout.
The agent is added in the integration-test phase and during that phase, it measures the execution times of methods decorated with `@Measure`, which results in below message.
```
[MEASUREMENT] Method calculateSum from class service.App execution time = 2000ms (actual arguments: 2, 3)
[MEASUREMENT] Method calculateSum from class service.App execution time = 3000ms (actual arguments: 3, 3)
[MEASUREMENT] Method calculateSum from class service.App execution time = 4000ms (actual arguments: 4, 3)
```
