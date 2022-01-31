1.PROBLEM:
Please write a microservice that gets the current Covid-19 statistics
from the URL https://api.covid19api.com/summary and prepares them, so they can be accessed per country.
* Use Spring Boot
* Download data from https://api.covid19api.com/summary and process it
* Store in a local database
* Create a rest endpoint /country/{COUNTRYCODE} where the country code is that two letter string,
like BG for Bulgaria and DE for Germany (capital letters only allowed)
* Add documentation to README.md to explain how to build, start and access the service
* Put all into a GitHub repository

2.SOLUTION:

//todo: When downloading code, please type password in yml file. Password sent via email:

    username: root
    password: ....
    
SOURCE URL: https://api.covid19api.com/summary

EXAMPLES: (COUNTRYCODE: capital letters only allowed.)

url format: https://covid19countries.herokuapp.com/country/{COUNTRYCODE}

http://localhost:8000/country/BG

http://localhost:8000/country/UA

http://localhost:8000/country/US

I also deployed the app on herokuapp.com. Here you can see some examples:

https://covid19countries.herokuapp.com/country/BG

https://covid19countries.herokuapp.com/country/UA

https://covid19countries.herokuapp.com/country/US
