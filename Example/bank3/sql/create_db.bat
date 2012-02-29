@ECHO off
:: Murach's Java Servlets and JSP (2nd Edition)
:: company: Mike Murach & Associates, Inc.
:: date:    Jan 1, 2008
:: 
:: Uses the MySQL monitor to run the SQL scripts that create
:: and populate the tables in the two sample databases used
:: by this book.

cd \Program Files\MySQL\MySQL Server 5.0\bin
mysql -u root -p < c:\murach\servlet_jsp\db\create_db.sql

ECHO.
ECHO If no error message is shown, the databases named murach and music were created successfully.
ECHO.

:: Display 'press any key to continue' message
PAUSE