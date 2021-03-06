# Filter-Coding-Test

This project requirements:
1. Java 11
2. Apache Commons
3. JUnit 5

This project contains:
1. Source code
2. pom.xml file
3. code-coverage.JPG
4. cyclomatic-complexity.JPG

## Implementation Details:
NB This test will require you to be running Java 11 on your machine.

As a developer
I need to be able to filter log extracts
 - by country
 - by country where the response time was above a certain limit
 - where the response time is above average
so that I can debug issues effectively.

A log extract file contains a header line, followed by zero or more data lines, in comma-separated value format. The
first column is the Unix timestamp of the time the request was made, the second column is the country from which the
request originated, and the third column is the time in milliseconds which the request took to complete. The data lines
are not sorted.

An example file is:

    REQUEST_TIMESTAMP,COUNTRY_CODE,RESPONSE_TIME
    1433190845,US,539
    1432917066,GB,37

We have included a sample log extract and the interface you are required to implement for the solution.

We expect your submission to implement the above functionality with evidence that the features you have implemented work
correctly. We suggest spending 1-1.5 hours on the test. Your solution will be judged on a number of criteria
pertinent to good software development practice. Please feel free to add a README with any further comments you have.

## Test Implementation
Unit Test classes provided at src/test/java/uk/sky

## Running the Test
Use any IDE that supports Maven to import the project, and then assuming the IDE using Java 11 and Java 11 JDK, run the test as usual.

