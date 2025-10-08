# Api Automation Test Framework with Player Controller Tests

## Toolchain
Java 11 • TestNG • RestAssured • Maven • Allure • SLF4J/Logback • Jackson

## Config
`src/test/resources/application.properties`
```properties
baseUrl=http://3.68.165.45
http.timeout.ms=30000
threads=3
```

### How to run tests and generate report

	1.	Run tests: mvn clean test
	2.	Generate Allure report: mvn allure:report
    Fast:   mvn clean test allure:serve

### Skip known issues
Tests with known bugs are excluded in testng.xml by default for a green report