OPERATION MANUAL

Requirements
JDK 17, Mysql 8.1, Postman

Preparation
1. Install JDK 17,Mysql 8.1 and Postman.
2. Create user with credentials
   username - cards
   password - password
3. Create a database called "cards".
4. Create the database tables by importing cards.sql script.
5. Give all the privileges on cards database to user cards.
6. Checkout the code from github repo: https://github.com/freydrey/cardslogicea
7. Open the code from an IDE such as intellij or on command line run the CardsApplication.java file on the root of the project folder.The API will run on port 8085. 
6. Open Postman application. 
7. Import the collection file called: CARDS SPRING RESTFUL API.postman_collection

POSTMAN API OPERATIONS
User Registration [Note: The users have already been added to the database]
1. On postman expand CARDS SPRING RESTFUL API collection and select "REGISTER USER" from the list.
2. Select POST method form the drop down.
3. Enter url: http://localhost:8085/api/v1/users 
4. Select "Body" tab.
5. Select raw option and change raw type to json instead of text.
6. Enter the payload for user registration as shown below.
{
	"email": "one@logicea.com",
	"password": "password",
	"role": "Admin"
}
7. Click on "SEND" button.
8. Current existing users credentials are as follows:
username: one@logicea.com  password: password
username: two@logicea.com password: password
username: admin@logicea.com password: password

User Login
1. On postman expand CARDS SPRING RESTFUL API collection and select "LOGIN USER" from the list.
2. Select POST method form the drop down.
3. Enter url: http://localhost:8085/api/v1/auth/login 
4. Select "Body" tab.
5. Select raw option and change raw type to json instead of text.
6. Enter the payload for user login as shown below.
{
	"username": "one@logicea.com",
	"password": "password"
}
7. Click on "SEND" button.
8. The JWT Token will be displayed on the output section. This will be used for subsequent requests.

Add Card
1. On postman expand CARDS SPRING RESTFUL API collection and select "ADD CARD" from the list.
2. Select POST method form the drop down.
3. Enter url: http://localhost:8085/api/v1/cards 
4. Select "Body" tab.
5. Select raw option and change raw type to json instead of text.
6. Enter the payload for adding a card as shown below.
{
	"name":"taskseven",
	"description":"do task seven like this",
	"color":"#66kl69"
}
7. Select "Header" tab. 
8. Enter key as "Authorization" and value as "Bearer " and the token that was generated on user login. e.g Bearer eyJhbGciOiJIUzUxMiJ9.eyJzY29wZXMiOlsiTWVtYmVyIl0sInN1YiI6InR3b0Bsb2dpY2VhLmNvbSIsImlzcyI6Imh0dHBzOi8vbG9naWNlYWNhcmRzLmNvbSIsImlhdCI6MTcxMzU3MjQ3OCwiZXhwIjoxNzE0MDA0NDc4fQ.nguhf3GSVNSPric3LUglENqgATqORKEkDFmd89-srlkY6Tm6BBEEgHZceM85wKiBQUIzlpzQWGhsvmYPGR49AA
9. Click on "SEND" button.

List All Cards
1. On postman expand CARDS SPRING RESTFUL API collection and select "LIST ALL CARDS" from the list.
2. Select GET method from the drop down.
3. Enter url: http://localhost:8085/api/v1/cards 
4. Select "Header" tab. 
5. Enter key as "Authorization" and value as "Bearer " and the token that was generated on user login. e.g Bearer eyJhbGciOiJIUzUxMiJ9.eyJzY29wZXMiOlsiTWVtYmVyIl0sInN1YiI6InR3b0Bsb2dpY2VhLmNvbSIsImlzcyI6Imh0dHBzOi8vbG9naWNlYWNhcmRzLmNvbSIsImlhdCI6MTcxMzU3MjQ3OCwiZXhwIjoxNzE0MDA0NDc4fQ.nguhf3GSVNSPric3LUglENqgATqORKEkDFmd89-srlkY6Tm6BBEEgHZceM85wKiBQUIzlpzQWGhsvmYPGR49AA
6. Click on "SEND" button.

Find One Card
1. On postman expand CARDS SPRING RESTFUL API collection and select "FIND ONE CARD" from the list.
2. Select GET method form the drop down.
3. Enter url: http://localhost:8085/api/v1/cards/1 
4. Select "Header" tab. 
5. Enter key as "Authorization" and value as "Bearer " and the token that was generated on user login. e.g Bearer eyJhbGciOiJIUzUxMiJ9.eyJzY29wZXMiOlsiTWVtYmVyIl0sInN1YiI6InR3b0Bsb2dpY2VhLmNvbSIsImlzcyI6Imh0dHBzOi8vbG9naWNlYWNhcmRzLmNvbSIsImlhdCI6MTcxMzU3MjQ3OCwiZXhwIjoxNzE0MDA0NDc4fQ.nguhf3GSVNSPric3LUglENqgATqORKEkDFmd89-srlkY6Tm6BBEEgHZceM85wKiBQUIzlpzQWGhsvmYPGR49AA
6. Click on "SEND" button.

Filter by Value and Sortby
1. On postman expand CARDS SPRING RESTFUL API collection and select "SEARCH BY VALUE AND SORTBY" from the list.
2. Select GET method from the drop down.
3. Enter url: http://localhost:8085/api/v1/cards/task/name
   task is the search value
   name is the sort by field. (Sort fields allowed:name,color,status,createdAt)
4. Select "Header" tab. 
5. Enter key as "Authorization" and value as "Bearer " and the token that was generated on user login. e.g Bearer eyJhbGciOiJIUzUxMiJ9.eyJzY29wZXMiOlsiTWVtYmVyIl0sInN1YiI6InR3b0Bsb2dpY2VhLmNvbSIsImlzcyI6Imh0dHBzOi8vbG9naWNlYWNhcmRzLmNvbSIsImlhdCI6MTcxMzU3MjQ3OCwiZXhwIjoxNzE0MDA0NDc4fQ.nguhf3GSVNSPric3LUglENqgATqORKEkDFmd89-srlkY6Tm6BBEEgHZceM85wKiBQUIzlpzQWGhsvmYPGR49AA
6. Click on "SEND" button.

Update Card
1. On postman expand CARDS SPRING RESTFUL API collection and select "UPDATE CARD" from the list.
2. Select PUT method from the drop down.
3. Enter url: http://localhost:8085/api/v1/cards/3
   Value 3 is the id of one of the cards present.
4. Select "Body" tab.
5. Select raw option and change raw type to json instead of text.
6. Enter the payload for adding a card as shown below.
{
	"name": "taskthree",
	"description": "do task three like this",
	"color": "#33rty6",
	"status": "In Progress"
}
5. Select "Header" tab. 
6. Enter key as "Authorization" and value as "Bearer " and the token that was generated on user login. e.g Bearer eyJhbGciOiJIUzUxMiJ9.eyJzY29wZXMiOlsiTWVtYmVyIl0sInN1YiI6InR3b0Bsb2dpY2VhLmNvbSIsImlzcyI6Imh0dHBzOi8vbG9naWNlYWNhcmRzLmNvbSIsImlhdCI6MTcxMzU3MjQ3OCwiZXhwIjoxNzE0MDA0NDc4fQ.nguhf3GSVNSPric3LUglENqgATqORKEkDFmd89-srlkY6Tm6BBEEgHZceM85wKiBQUIzlpzQWGhsvmYPGR49AA
7. Click on "SEND" button.

Delete Card
1. On postman expand CARDS SPRING RESTFUL API collection and select "DELETE CARD" from the list.
2. Select DELETE method from the drop down.
3. Enter url: http://localhost:8085/api/v1/cards/12
   Value 12 is the id of one of the cards present.
4. Select "Header" tab. 
5. Enter key as "Authorization" and value as "Bearer " and the token that was generated on user login. e.g Bearer eyJhbGciOiJIUzUxMiJ9.eyJzY29wZXMiOlsiTWVtYmVyIl0sInN1YiI6InR3b0Bsb2dpY2VhLmNvbSIsImlzcyI6Imh0dHBzOi8vbG9naWNlYWNhcmRzLmNvbSIsImlhdCI6MTcxMzU3MjQ3OCwiZXhwIjoxNzE0MDA0NDc4fQ.nguhf3GSVNSPric3LUglENqgATqORKEkDFmd89-srlkY6Tm6BBEEgHZceM85wKiBQUIzlpzQWGhsvmYPGR49AA
6. Click on "SEND" button.


TODO
1. Add offset and limit for the data returned.
2. Put in place CI/CD to automate deployment to Cloud such as AWS.
3. Add a constants or properties file to hold the string values in the application for ease of maintenance.
4. Use DTO(Data Transfer Object) and mappers to ensure only required entity details are sent to the client.
5. Documenting the API using a platform such as swagger.