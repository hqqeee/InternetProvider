# Internet Provider

The Administrator registers the Subscriber in the system.
The Subscriber is provided with a list of Services (Telephone, Internet, Cable TV, IP-TV, etc.)
and a list of Tariffs for each service. Implement the ability to download tariff plans in one of
the formats (eg, txt, pdf, docx). For the list of tariffs to implement the possibility of sorting
by:
- alphabetically / in reverse order (a-z, z-a);
- by price.

The subscriber can choose one or more services at a certain rate. The subscriber has an
account that he can replenish. Funds are withdrawn from the account by the system
depending on the tariff plans selected by the subscriber. If the amount in the account is not
enough, the system blocks the user.
The user is automatically unlocked by making the required amount to the account.
The system administrator has the rights:
- add, delete or edit a tariff plan;
- user registration, blocking and unblocking.

# DB Schema

![alt text](https://i.ibb.co/W2n34LS/DBSchema.png)


# Used technologies/frameworks

- Java SE 8
- Servlet 4.0.1
- JSP 2.0
- JSTL 1.2
- JDBC 
- MariaDB 15.1
- JUnit Jupiter 5.9
- Mockito 4.8
- Log4J 2.8
- Java Mail 1.6.2
- iText PDF 5.5.13
- Maven 3.8.7

# Local deployment

- Start the MariaDB database server. Run the SQL scripts in the following order from the 'db' folder: create_database.sql, create_user.sql (you can use your own user credentials but make sure to update them in the context.xml file), init_data.sql, init_tariff.sql, and init_users.sql.
- Build the project by running 'mvn package' in the main directory. This will create a .war file in the target/ folder.
- Deploy the generated .war file on your server.