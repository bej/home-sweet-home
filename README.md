# home-sweet-home
Do you know where your rental is going? 
This is a small pet project to sketch a Java based full stack web application in a scenario around accounting and cashflow.

## Why does it exist?
I need a playground to play around with 
- Spring Boot and several other great Spring components like Spring Security, Spring Data Rest, Spring RestDocs
- Angular
- Swagger and Swagger-Codegen

in order to
- have some showcase to explain _things_ in spontaneous discussions
- have a setup to try out _things_ outside of real projects with deadlines, restrictions, and _reasons_.

## Scenario
Every month you pay the rent for your flat. But where is this money going? Yeah, sure the landlord get's the biggest piece, 
but there is also heating, water, cleaning, repairs, and many other little things being paid.
So the idea is, to build up a network of accounts to visualize the cashflow of all the incomes and expenses
(e.g. as a [Sankey diagram](https://bost.ocks.org/mike/sankey/))

## Run it

### Backend

1. run ```mvn spring-boot:run``` in the project _root_ folder.
2. from the log output copy the generated password
3. paste the password to the config object ```./home_sweet_home/webapp/home-sweet-home/src/app/app.module.ts```
(yes, this is a definitely a _todo_)

You should be able to access the REST API at ```http://locahost:8080``` with user ```user``` and the previous password.

### Frontend

1. in ```./home_sweet_home/webapp/home-sweet-home``` run ```ng serve```

You should be able to access a minimal angular app listing a single account.

## Simulate payment

By _POSTing_ a request like the following (note: adapt the authorization header) you can simulate a payment that
will be split to several transactions according to the expenses related to the given account.

    curl --request POST \
      --url http://localhost:8080/incomes \
      --header 'authorization: Basic Base64EncodedUserAndPasswordHere' \
      --header 'content-type: application/json' \
      --data '{
        "account": "/accounts/1",
        "amount": 2000,
        "title": "Miete Oktober + November"
    }'