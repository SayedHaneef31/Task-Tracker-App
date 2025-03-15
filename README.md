# 📌 Task Tracker Application  

## Overview  
The **Task Tracker** is a web-based application designed to help users efficiently manage and track their daily tasks. It offers a seamless experience with features like user authentication, task categorization, and real-time updates.

## ✨ Features  

- 🔑 **User Authentication** – JWT-based login and signup  
- ✅ **Task Management** – Create, update, and delete tasks  
- 📌 **Task Categorization** – Organize tasks as **Completed, Pending, or In Progress**  
- 🔒 **Secure REST API** – Ensures data security and authentication  
- 📱 **Responsive UI** – Works across devices for an optimal user experience  
- ⚡ **Real-time Updates** (Optional if WebSockets or polling is used)  
- 🛠 **Robust Backend** – Powered by **Spring Boot** and **PostgreSQL**  

## 🛠️ Tech Stack  

### Frontend  
- ⚛ **React.js** – For building a dynamic user interface  
- 🔄 **React Router** – Handles client-side navigation  
- 🎨 **Tailwind CSS** – Provides a sleek and responsive design  

### Backend  
- 🏗 **Spring Boot** – Manages the server-side logic  
- 🔐 **Spring Security** – Implements JWT-based authentication  
- 🗄 **PostgreSQL** – Relational database for storing tasks  
- ⚙ **JPA/Hibernate** – Simplifies database interactions  

## 🚀 Installation  

### Prerequisites  
- Node.js and npm (for frontend)  
- Java 17+ (for backend)  
- PostgreSQL database  

### Backend Setup  

1. Clone the repository:  
   ```sh
   git clone https://github.com/your-username/task-tracker.git
   cd task-tracker/backend
   ```
2. Configure the database in `application.properties`:  
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/task_tracker
   spring.datasource.username=your_db_username
   spring.datasource.password=your_db_password
   ```
3. Build and run the application:  
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```

### Frontend Setup  

1. Navigate to the frontend directory:  
   ```sh
   cd ../frontend
   ```
2. Install dependencies:  
   ```sh
   npm install
   ```
3. Start the development server:  
   ```sh
   npm start
