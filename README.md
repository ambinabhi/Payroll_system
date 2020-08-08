# Payroll_system 
A simple payroll system for staff members deveoped in Spring boot using Java

## Techonologies used in this application
1. MySQL
2. Java
3. Java Spring Framework
4. Maven

### Install MySQL
Install MySQL [from here]( https://dev.mysql.com/downloads/mysql/). Create database with the name of your choice. Change the database configurations [here](/src/main/resources/application.properties)

###Run Application
Clone the repository on to your system. Import the project into your IDE. Make sure all the dependencies are added and compiled succesfully. 

## Functionalitites
* Staff
* Worklog

### Staff functionalities
* Staff members can be added, removed, deleted and updated. Every time a new staff is added, if the type of salary is not provided, isHourly flag is made 0 meaning that the employee has fixed salary. Along with the type the wage for the employee is also maintained in the same table. 

### Worklog functionalities
* Worklog table is maintained in order to track the working hours or days of a particular employee. Worklog table stores working hours in a day for a particular employee. This table needs to updated on a daily basis. 

## API Routes
> POST : ```/staff/new```
API route for adding a new staff

> GET : ```/staff/list```
An API route that allows to retrieve all existing users.

> GET : ```staff/view/{id}```
An API route that allow get a particular staff data based on ID

> DELETE : ```staff/delete/{id}```
An API route that allows to delete a particular staff from database.

> DELETE : ```staff/deleteAll```
An API route that allow delete all staff members from DB

> PUT : ```staff/edit/{id}```
An API route that allows to edit an existing staff's data

> PATCH : ```staff/edit/{id}```
An API route that allows to update any parameter of user. Making partial changes.

> POST : ```worklog/new```
An API route that allows to worklog of an employee

> GET : ```worklog/view/{staffId}```
An API route that allows to retreive payroll information of a particular staff member.

###Assumptions
The APIs retreiving the payroll information for a particular employee are implemented based on the real-life scenario in companies. The employees need to log their working hours regularly on a daily basis or the working hours are tracked based on their log in and log out time from their respective work stations.

Similary, the worklog table needs to be updated daily. When we need to retreive the payroll infromation, > GET : ```worklog/view/{staffId}``` does following calculations.




