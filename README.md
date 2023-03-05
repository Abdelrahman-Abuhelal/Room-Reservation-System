# room-reservation-system

How to Start a Spring Boot Project:

This guide will walk you through the steps to start a new Spring Boot project using the command line.

Prerequisites:

Before you begin, you will need to have the following installed on your computer:

Java Development Kit (JDK) version 17 

Maven build tool

An IDE (such as IntelliJ or Eclipse)

Cloning the Repository:

To get started, you'll need to clone the repository to your local machine. Here are the steps:

Open a terminal window and navigate to the directory where you want to clone the repository.

Type the following command to clone the repository:

git clone https://github.com/Abdelrahman-Abuhelal/room-reservation-system.git



Deploy the application:

Environment:
Use the 192.168.200.150 Computer In Remote Desktop.
Get the credentials from IT Administrator.

Maven Configuration: JAR packaging
You should add spring-boot-maven-plugin artifact to your pom.xml with the configuration and executable tags like below:


![Screenshot (279)](https://user-images.githubusercontent.com/77440941/222962195-ef824c98-5347-4465-8f94-4bd11819f8e1.png)

● build the project using View → Tool Window → Maven projects → {Project Name} -> LifeCycle → package

● You can find the jar file in the target folder.

● You can use this GitHub documentation https://github.com/winsw/winsw to download the WinSW.NET4.exe and sample-minimal.xml.

● Put the JAR file, WinSW executable and config in the same folder.

● Edit the sample-minimal.xml name to WinSW.NET4.xml.

● WinSW-xxx.xml file needs to be configured correctly.

● Open the cmd Run as Administrator.

● Go to the Folder directory

● Enter the command “WinSW.NET4.exe install”

● Open the local Services, and run the service.


● YouTube Step By Step for installing the application as a windows service: https://www.youtube.com/watch?v=OOVE_g6F8mQ
