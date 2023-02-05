/***********************************************************
* Create user in MariaDB for working with DB
************************************************************/

CREATE USER internet_provide@localhost IDENTIFIED BY 'eJVr';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP
ON internet_provider.*
TO internet_provide@localhost;