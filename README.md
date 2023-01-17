# ServletCommunicationApp
Servlet communication

1. Created a Registration form with name, age, email and mobile fields
2. Created a servlet to get the details from the form and then 
3. If age is less than 18 or greater than 30 (Not eligible) otherwise eligible. (using response.SendError(int,String) method we can send the error message to the client)
4. Write the JDBC connection logic in init() method. As if there are more than one client then no need to create new connection object, ServerConfig will create only one object, It will use the same connection object for all the clients.
