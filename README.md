# Room Reservation System 
It is an internal project developed by me, manages meeting room reservations across multiple branches within the company. 

## Key Features:

1- Multi-branch Support: Seamlessly handles reservations across various company branches.

2- User Authentication: Securely manages user access with authentication and authorization functionalities.

3- Real-time Availability: Provides live updates on room availability and schedules.

4- Reservation Management: Allows users to create, update, and cancel reservations.

5- Exception Handling: Includes robust exception handling mechanisms to manage and gracefully handle errors and exceptions throughout the application.

6- Unit Testing: Incorporates unit tests for critical components ensuring reliability and correctness.


# How to Start the Project:
 
This guide will walk you through the steps to start the project using the command line.

## Prerequisites:

Before you begin, you will need to have the following installed on your computer:

** Java Development Kit (JDK) version 17 

** Maven build tool

** An IDE (such as IntelliJ or Eclipse)

## Cloning the Repository:

To get started, you'll need to clone the repository to your local machine. Here are the steps:

Open a terminal window and navigate to the directory where you want to clone the repository.

Type the following command to clone the repository:

```git clone https://github.com/Abdelrahman-Abuhelal/room-reservation-system.git```

## Importing the Project

After cloning the repository, you can import the project into your IDE. Here's how to do it in IntelliJ:

1. Open IntelliJ and select "Import Project" from the welcome screen.

2. Navigate to the directory where you cloned the repository and select the pom.xml file.

3. Click "Open" and select "Import Maven Projects".

4. Select the checkbox next to the project and click "Next".

5. Leave the default options and click "Next" again.

6. Click "Finish" to import the project.


# Deploy the application:

● Environment:
Use the 192.168.200.150 Computer In Remote Desktop.
Get the credentials from IT Administrator.

● Maven Configuration: JAR packaging
You should add spring-boot-maven-plugin artifact to your pom.xml with the configuration and executable tags like below:


![Screenshot (279)](https://user-images.githubusercontent.com/77440941/222962195-ef824c98-5347-4465-8f94-4bd11819f8e1.png)

● build the project using View → Tool Window → Maven projects → {Project Name} -> LifeCycle → package

● You can find the jar file in the target folder.

● You can use this GitHub documentation https://github.com/winsw/winsw to download the WinSW.NET4.exe and sample-minimal.xml.

- YouTube Step By Step for installing the application as a windows service: https://www.youtube.com/watch?v=OOVE_g6F8mQ

● Put the JAR file, WinSW executable and config in the same folder.

● Edit the sample-minimal.xml name to WinSW.NET4.xml.

● WinSW-xxx.xml file needs to be configured correctly.

● Open the cmd Run as Administrator.

● Go to the Folder directory

● Enter the command “WinSW.NET4.exe install”

● Open the local Services, and run the service.


