# AcademyTask17
Task 17
Group members: Thomas Grefsrud, Helene Harmens, Line Saevold, Stian Heimset


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




