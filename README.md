# Payroll_system 
A simple payroll system for staff members deveoped in Spring boot using Java

## Techonologies used in this application
1. MySQL
2. Java
3. Java Spring Framework
4. Maven

### Install MySQL
Install MySQL [from here]( https://dev.mysql.com/downloads/mysql/). Create database with the name of your choice. Change the database configurations [here](/server/src/db/index.js)

###Run Application
Once all the dependencies are installed navigate inside client/server folders. Run ```npm start``` commands to run the individual applications.

## Functionalitites
The whole project has been divided into 2 modules

* Admin
* User

### Admin module functionalities
* Login (For the purpose of this assignment login for user/admin is hardcoded. Few of the users are created on database using MySQL commnd line for client console directly.)
* Add books 

### User module functionalities
* Login
* View books
* Borrow Books (Based on the use cases in the assignment)
* Return Book 

## API Routes
> POST : ```/api/login```
API routes for users and admin to login to the application

> POST : ```/api/books/addBook (name, author, publisher, quantity)```
An API route that allow admins to add new book:

> GET : ```/api/books/getAllBooks```
An API route that allow users and admins to get all books in the library

> POST : ```/api/books/borrowBook (email, book_id, quantity)```
An API route that allow users to borrow a book in the library

> GET : ```/api/books/getAllUserBooks?email=email_id```
An API route that allow users to get all the books that the user has borrowed but has not returned

> POST : ```/api/books/returnBook (email, book_id, quantity)```
An API route that allow user to return a book

###Approach
The project contains two tables on the database. One for the user management and another for maintaining books in the library.
The user table has ```isAdmin``` bit in order to classsify the user as admin or user. Based on this flag different home screens are loaded.
Admin page shows a form to add books and a table to view all books. In contrast, home screen for a user shows availbale books in the library. 
User has given option to view his borrowed books by navigating to the ```My List``` from the top right dropdown.

Books table has columns such as name of the book, author, publisher and quantity available in the library. This table is updated based on the operations performed by the user such as reducing the quantity of a borrowed book. Further, a new table ```borrowedbooks``` table is maintained in order to track borrowed books. When user borrows a book, the quantity is reduced from  ```books``` table and simultaneously inserted into ```borrowedbooks``` table. Similarly when user returns a book, the qunatity is increased from ```books``` table and removed from ```borrowedbooks``` table. Using the life cycle methods provided ny ReactJS framework the home screen interfaces are updated regularly whenever an event occurs. The ```borrowedbooks``` table can further be used to get all the borrowed books by the user by joining it with ```books``` table based on ```user_id```.

Rather than focussing on writing unit test cases, the project provides a comprehensive user interface designed using modular ReactJS components. 
The same use cases can be performed for multiple users also. 
