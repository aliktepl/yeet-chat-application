# Yeet! - A web chat application
  
## Description
Welcome to the Yeet! project! This Git repository contains the source code for a web chat application built using HTML, CSS, Bootstrap, JavaScript, and React     running on Node.js. The web chat application provides a seamless and interactive chatting experience.  
Users can log in, add contacts, and engage in real-time conversations. The application utilizes modern web technologies to create a responsive and user-friendly interface.

[![My Skills](https://skills.thijs.gg/icons?i=js,html,css,react,nodejs,mongodb)](https://skills.thijs.gg)

## Getting Started
To get started with the web chat application, follow these steps:  
1. Clone the Git repository to your local machine: `git clone https://github.com/aliktepl/AP2_EX2.git`  
2. Navigate to the cloned repository in the terminal and run the following command to start the client: `npm install` this will install all the required dependencies, once done run `npm start` to start the app (will open automatically) and then proceed to running the server.    
3. To start the server navigate the the `/server` directory in the project directory and run `npm start`. Navigating to `localhost:5000` will also load the server.  
4. The application will automatically open in your default browser, and you will be directed to the login screen.  

## Application Overview
The web application is divided into different parts to ensure a smooth user experience:  
* Login Screen: Users enter their login credentials to gain access to the web application. If a user doesn't have an account, they can refer to the sign-up screen to create a new account.  
* Sign-up Screen: Users can create a new account by providing their necessary information. Once the account is created, they can proceed to the login screen.  
* Chat Screen: After successful login, users are transferred to the chat screen. Here, they can send text messages to other users in their contact list in real-time. Users can also add new contacts to their contact list.  

## Server Implementation
The server-side implementation of the web chat application follows the Model-View-Controller (MVC) architectural pattern and integrates with MongoDB for data storage. The server is responsible for handling client requests, interacting with the database, and enabling real-time communication.  

#### Part 1: Client-Server Interaction
In this part, the static React code is modified to interact with the server. The client-side code is updated to send requests to the server and handle responses.  
#### Part 2: Creating a Node.js Server using the MVC Architecture and MongoDB  
The server is implemented using the Model-View-Controller (MVC) architectural pattern, providing a structured approach to development. It includes the following components:
* Models - 
The models in the server architecture define the structure and behavior of the data, serving as the interface between the server and the database.
* Views - 
The views define the templates and UI components that are rendered on the client-side. React is used for the client-side views, ensuring a responsive and interactive user interface.
* Controllers - 
The controllers handle the logic and processing of client requests. They interact with the models to retrieve or update data and communicate with the views to render the appropriate response.  
* MongoDB - 
used as the database to store user data. The server establishes a connection with MongoDB and performs CRUD operations (Create, Read, Update, Delete) on the user model, ensuring efficient data management.
#### Part 3: Real-time Communication
Real-time communication is enabled using techniques such as WebSockets or libraries like Socket.IO. This allows users to send messages to each other, and the server relays these messages in real-time, ensuring instant communication and a seamless chat experience.

## Chat
The chat functionality allows users to communicate with their contacts in real-time. Here's a step-by-step guide to using the chat feature:  
* Adding Contacts: Click the add contact icon and enter the contact's credentials. Make sure to provide a name as it is a mandatory field.  
* Navigating Between Contacts: Users can navigate between contacts and click on the desired contact they want to chat with. The active chat will be highlighted in blue in the chat box screen.  
* Chat Box: After selecting a contact to chat with, a chat box will appear on the right-hand side of the chat screen, displaying the contact's credentials. Users can type messages into the chat bar below and press Enter or click the send icon to send the message.  
* Timestamps: Each message in the chat will have a timestamp indicating when it was sent.  
* Contact Information: The last message sent to a contact will appear alongside the timestamp in the contact's information in the contact list.  
* Logging Out: When you are done using the application, click the logout button to be transferred back to the login screen. Note that the profile will be saved for future logins, but all contacts and messages will be deleted.

## Contributors
Roy Amit  
Alik Teplitsky  
Roi Nir

