# AcademyTask17
Task 17
Group members: Thomas Grefsrud, Helene Harmens, Line Saevold, Stian Heimset

Input should be in SQL-like syntax:
Print info:
    SELECT First, Last FROM Person WHERE address LIKE 'veien'

Add info:
    ADD  First Last // INSERT First into Person WHERE id=1
    Ask for phone numbers, email, date of birth and address or take everything on one line

Update info
    UPDATE phone Work 135245 in Person

Delete info
    DROP id     - add confirmation dialog.



__________DATABASE ContactInformationDB.db__________
CREATE TABLE [Contact] (
	[contactID] NVARCHAR(50)  NOT NULL PRIMARY KEY,
	[firstName] NVARCHAR(50)  NOT NULL,
	[lastName] NVARCHAR(50)  NOT NULL,
	[address] NVARCHAR(50)  NOT NULL,
	[dateOfBirth] DATE  NULL
);
CREATE TABLE [Relation] (
	[contactID] NVARCHAR(50) NOT NULL,
	[kind] NVARCHAR(50) NULL

);
CREATE TABLE [Family] (
	[contactID] NVARCHAR(50) NOT NULL,
	[relativeID] NVARCHAR(50) NULL,
	[relationshipID] NVARCHAR(50) NULL

);
CREATE TABLE [Email] (
	[contactID] NVARCHAR(50) NOT NULL,
	[personalEmail] NVARCHAR(50)  NULL,
	[workEmail] NVARCHAR(50)  NULL
);
CREATE TABLE [Phone] (
	[contactID] NVARCHAR(50) NOT NULL,
	[personalPhone] NVARCHAR(50)  NULL,
	[homePhone] NVARCHAR(50)  NULL,
	[workPhone] NVARCHAR(50)  NULL
);
____________________________________________________



# New implementation:

SQLite database to store info.
Person class to hold info on a person
In main build console interface that takes SQL commands and passes them to the SQLite DB.
Use SPRING to get web access


