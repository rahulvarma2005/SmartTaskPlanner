Smart Task Planner
<p align="center">
<img src="https://placehold.co/600x300/f7f9fc/333333?text=Smart+Task+Planner+Demo" alt="Smart Task Planner Demo">
</p>

📋 Table of Contents
📖 Overview

✨ Features

🛠️ Technologies Used

🚀 Getting Started

Prerequisites

Installation

💻 Usage

📂 Project Structure

🔀 API Endpoints

⚙️ Configuration

🤝 Contributing

📄 License

📖 Overview
The Smart Task Planner is an intelligent web application designed to help users break down large goals into smaller, manageable tasks. By leveraging the power of the Gemini Pro AI model, it automatically generates a detailed action plan with deadlines and dependencies, making goal achievement more organized and attainable.

✨ Features
AI-Powered Task Generation: Simply input your goal, and the application will use the Gemini Pro to generate a comprehensive list of tasks.

Automatic Timelines: Each task is assigned a relative deadline (e.g., "in 2 days," "in 1 week") to keep you on track.

Dependency Management: The AI identifies and assigns dependencies between tasks, ensuring a logical workflow.

Interactive Task Management: Mark tasks as complete and track your progress visually.

Clean and Modern UI: A user-friendly interface built with React and Tailwind CSS for a seamless experience.

🛠️ Technologies Used
Backend
Java 21

Spring Boot 3.5.6

Spring Data JPA

PostgreSQL

Maven

Frontend
React

Vite

TypeScript

Tailwind CSS

Lucide React (for icons)

AI
Google Gemini Pro

🚀 Getting Started
Follow these instructions to get a copy of the project up and running on your local machine for development and testing purposes.

Prerequisites
Java 21 or later

Maven

Node.js and npm

PostgreSQL

A Gemini API Key

Installation
Clone the repository:

git clone [https://github.com/rahulvarma2005/smart-task-planner.git](https://github.com/rahulvarma2005/smart-task-planner.git)
cd smart-task-planner

Backend Setup:

Navigate to the root of the project.

Create a copy of the application.properties.template file and rename it to application.properties.

Update application.properties with your PostgreSQL database credentials and your Gemini API key.

Build the Spring Boot application:

./mvnw clean install

Run the application:

./mvnw spring-boot:run

The backend will be running on http://localhost:8081.

Frontend Setup:

Navigate to the Frontend UI directory:

cd "Frontend UI"

Install the necessary npm packages:

npm install

Start the frontend development server:

npm run dev

The frontend will be accessible at http://localhost:5173.

💻 Usage
Open your web browser and navigate to http://localhost:5173.

In the input field, type in your desired goal (e.g., "Learn to play the guitar," "Build a personal website").

The application will make a request to the backend, which in turn queries the Gemini Pro.

A list of actionable tasks will be generated and displayed, each with a description, deadline, and any dependencies.

You can interact with the tasks by marking them as complete.

📂 Project Structure
The project is organized into two main parts: the Spring Boot backend and the React frontend.

SmartTaskPlanner (Root Directory):

src/main/java/com/planner/SmartTaskPlanner/: Contains the Java source code for the Spring Boot application.

controller/: REST API controllers.

dto/: Data Transfer Objects for handling requests and responses.

model/: JPA entity classes.

repository/: Spring Data JPA repositories.

service/: Business logic and services.

src/main/resources/: Application configuration files.

pom.xml: Maven project configuration.

Frontend UI/:

src/: Contains the React application's source code.

components/: Reusable React components.

App.tsx: The main application component.

main.tsx: The entry point of the React application.

package.json: NPM dependencies and scripts.

vite.config.ts: Vite configuration.

tailwind.config.js: Tailwind CSS configuration.

🔀 API Endpoints
The backend exposes the following REST API endpoint:

POST /api/tasks/generate

Description: Generates a list of tasks based on a user-provided goal.

Request Body:

{
  "goal": "Your goal description here"
}

Response:

[
  {
    "id": 1,
    "taskDescription": "Generated task description",
    "deadline": "in X days/weeks",
    "status": "To Do",
    "dependencies": "[Depends on: Task #X]"
  }
]

⚙️ Configuration
The primary configuration file for the backend is application.properties. Here, you must set the following properties:

spring.datasource.url: The JDBC URL for your PostgreSQL database.

spring.datasource.username: The username for your PostgreSQL database.

spring.datasource.password: The password for your PostgreSQL database.

gemini.api.key: Your API key for the Gemini Pro.

gemini.api.url: The URL for the Gemini API.

You can also customize the prompt sent to the Gemini Pro by modifying the gemini.api.prompt.template in this file.

🤝 Contributing
Contributions are welcome! Please feel free to submit a pull request.

📄 License
This project is licensed under the MIT License.
