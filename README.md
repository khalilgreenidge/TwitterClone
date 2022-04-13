# Jabber 
The following project contains a Twitter 🐤 clone desktop application built using Java, and PostgreSQL. 

### Server
- Main class is called "JabberServer.java"

### Client
- Main class is called "Main.java"
- The primary controller is called "MainController.java"
- There are two other controllers called "TimelineController.java" and "UserNotFollowingController.java" but running the program from the main controller takes care of their usage and they do not need to be actively executed.


## How it works:
This application is split into two folders: 1) JabberGUI and 2) JabberServer. It was best to close the connection after each request rather than keeping it open.

The User class is used to connect to the JabberServer via the *connect()* method. 
Whenever the client wants to send a request to the JabberServer, the Client Message is passed as a string
to the *connect()* method which then encapsulates this request in a JabberMessage before passing it to the
server. When the server replies with a response, it is returned and encapsulated as a JabberMessage. 

	
## Getting Started
In order to run the application, you must first run "JabberServer.java" then "Main.java".
