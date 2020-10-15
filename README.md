About Simple Trading Bot
=================

The Simple Trading Bot tracks the price of a certain product and executes a pre-defined trade on that product when it reaches a given price. After the product has been bought the Trading Bot keeps on tracking the prices and executes a
sell order when a certain price is hit.


## Libraries used
- Java 11
- Spring Boot 2
- H2 DB
- Commons IO
- SLF4J 


# Installation

## Prerequisites 
- JDK 11
- Maven 3.6

## Installation steps
Build the application with the following maven command:

 `mvn clean install`.

This command will run tests along with the build. To omit test use the following maven command:

`mvn clean install -DskipTests`

## Build artifact
Build artifact is to be found in the target directory of simple-trading-bot module with the following name pattern:

`simple-trading-bot-${project.version}.jar`

## Environment variables
The following environment variable should be set:
JAVA_HOME=path/to/jdk

## Running
At startup the Trading Bot expects the following arguments: 
- 'spring.application.productId' represents the product id
- 'spring.application.buyPrice' represent the buy price. If the stock price doesn't reach that price the position shouldn't be opened.
- 'spring.application.upperSellingPrice' represents the upper limit sell price. This is the price you are willing to close a position and make a profit. 
- 'spring.application.lowerSellingPrice' represents the lower limit sell price. This the price you want are willing to close a position at and make a loss.

Please note that for the trading logic to be valid, the relation between the buy price and the lower / upper limit should be: lower limit sell price < buy price < upper limit sell price.

To run the application, run the following command in a terminal window of the target directory of simple-trading-bot where values of cli arguments could be replaced with actual values:

`java -jar  media-search-api-${project.version}.jar --spring.application.productId=sb26496 --spring.application.buyPrice=8000 --spring.application.upperSellingPrice=9000 --spring.application.lowerSellingPrice=2500`

or by using the Maven command:
`./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.application.productId=sb26496 --spring.application.buyPrice=8000 --spring.application.upperSellingPrice=9000 --spring.application.lowerSellingPrice=2500"`


## TODOs
- add tests for testing handling the messages coming from the product channel

# Developers
@mlazic
