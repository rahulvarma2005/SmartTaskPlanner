# SmartTaskPlanner 🎯

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18.3.1-blue.svg)](https://reactjs.org/)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.5.3-blue.svg)](https://www.typescriptlang.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-12%2B-blue.svg)](https://www.postgresql.org/)
[![Gemini AI](https://img.shields.io/badge/Gemini-2.5%20Flash-4285F4.svg)](https://ai.google.dev/)

**Break user goals into actionable tasks with timelines using AI reasoning.**

SmartTaskPlanner is a full-stack application that leverages Google Gemini AI to intelligently decompose high-level goals into structured, time-bound, and dependency-aware tasks. Perfect for project planning, personal goal tracking, and task management.

🎥 **[Demo Video](https://youtu.be/mIs-oWbpg04)** | 🌐 **[Live Demo](https://smart-task-planner-backend-wwha.onrender.com)**

---

## 🌟 Features

- ✨ **AI-Powered Task Breakdown**: Automatically generates actionable tasks using Google Gemini 2.5 Flash
- ⏱️ **Smart Timeline Management**: Ensures tasks fit within specified deadlines with relative time durations
- 🔗 **Dependency Tracking**: Identifies and manages task dependencies intelligently
- 🎨 **Modern UI**: Beautiful, responsive interface built with React, TypeScript, and Tailwind CSS
- 🚀 **RESTful API**: Robust Spring Boot backend with comprehensive error handling
- 💾 **Persistent Storage**: PostgreSQL database for reliable task storage
- ✅ **Task Completion Tracking**: Interactive UI to mark tasks as complete
- 🐳 **Docker Support**: Containerized deployment for both frontend and backend
- 📱 **Responsive Design**: Works seamlessly on desktop and mobile devices
- 🔐 **Environment-based Configuration**: Secure configuration management for different environments

---

## 🏗️ Architecture

```mermaid
graph TB
    subgraph "Client Layer"
        A[React Frontend<br/>TypeScript + Vite]
        A1[Task Card Component]
        A2[Goal Input Form]
    end
    
    subgraph "API Layer"
        B[Spring Boot REST API<br/>Port: 8081]
        B1[Task Controller]
    end
    
    subgraph "Business Logic Layer"
        C[Task Service]
        C1[AI Integration Logic]
        C2[Task Parsing & Validation]
    end
    
    subgraph "External Services"
        D[Google Gemini 2.5 Flash<br/>AI Task Generation]
    end
    
    subgraph "Data Access Layer"
        E[Task Repository<br/>Spring Data JPA]
    end
    
    subgraph "Database"
        F[(PostgreSQL<br/>Task Storage)]
    end
    
    A -->|HTTP Requests<br/>VITE_API_BASE_URL| B
    A1 -.->|Renders Tasks| A
    A2 -.->|Submits Goal| A
    B -->|Routes to| B1
    B1 -->|Calls| C
    C -->|Requests AI<br/>Task Breakdown| D
    D -->|Returns<br/>Structured Task List| C
    C -->|Parses & Saves| E
    E -->|SQL Queries| F
    C1 -.->|WebClient| D
    C2 -.->|Regex Parsing| C
    
    style A fill:#61dafb,stroke:#333,stroke-width:2px,color:#000
    style B fill:#6db33f,stroke:#333,stroke-width:2px,color:#fff
    style C fill:#f89820,stroke:#333,stroke-width:2px,color:#000
    style D fill:#4285f4,stroke:#333,stroke-width:2px,color:#fff
    style E fill:#ff6b6b,stroke:#333,stroke-width:2px,color:#fff
    style F fill:#336791,stroke:#333,stroke-width:2px,color:#fff
```

### System Architecture Overview

The SmartTaskPlanner follows a **layered architecture** pattern with clear separation of concerns:

1. **Client Layer (React Frontend)**
   - User interface built with React 18.3.1 and TypeScript
   - Styled with Tailwind CSS for responsive design
   - Communicates with backend via REST API
   - Environment-based API URL configuration
   - Runs on Vite dev server (Port 5173 for development)

2. **API Layer (Spring Boot)**
   - RESTful endpoints for task operations
   - CORS enabled for cross-origin requests
   - Handles HTTP requests and responses
   - Runs on Port 8081 (configurable via PORT environment variable)

3. **Business Logic Layer**
   - Task orchestration and processing
   - Integration with Google Gemini AI via WebClient
   - Regex-based parsing of AI-generated task lists
   - Business rules and validation
   - Error handling and logging

4. **Data Access Layer**
   - Spring Data JPA repositories
   - ORM mapping with Hibernate
   - Database abstraction layer
   - Connection pooling with HikariCP

5. **Database Layer**
   - PostgreSQL for persistent storage
   - Stores tasks with descriptions, deadlines, and dependencies
   - Automatic schema updates via Hibernate

---

## 🤖 AI Prompt Template

The application uses a carefully crafted prompt template to ensure consistent and accurate task generation from Google Gemini AI:

```
You are a project manager AI. Your task is to break down a user's goal into a 
sequence of actionable tasks. The total duration of all tasks combined MUST NOT 
exceed the overall timeline specified in the goal. For each task, provide a 
relative deadline which MUST be a time duration (e.g., 'in 3 hours', 'in 5 days', 
'in 2 weeks'). **DO NOT** put anything else inside the parentheses. Strictly 
follow this format: [Task Number]. [Task Description] (deadline) [Depends on: Task #X]. 

Example for a 'one week' goal:
1. Initial Planning (in 1 day)
2. Design and Mockups (in 2 days) [Depends on: Task #1]

Now, apply this logic to the following goal: {goal}
```

### Key Prompt Features:
- **Structured Output**: Enforces specific format for easy parsing
- **Time Constraints**: Ensures tasks fit within the specified timeline
- **Dependency Management**: Automatically identifies task dependencies
- **Relative Deadlines**: Uses relative time expressions (in X days/weeks)
- **Clear Instructions**: Minimizes ambiguity in AI responses

### Example AI Response:
**Input Goal**: "Create a mobile app in one month"

**AI Output**:
```
1. Define app requirements and target audience (in 3 days)
2. Create wireframes and UI/UX design (in 5 days) [Depends on: Task #1]
3. Set up development environment (in 2 days) [Depends on: Task #1]
4. Develop core features (in 12 days) [Depends on: Task #2, Task #3]
5. Testing and bug fixes (in 5 days) [Depends on: Task #4]
6. Deploy to app stores (in 3 days) [Depends on: Task #5]
```

---

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- **Java 21** or higher ([Download](https://www.oracle.com/java/technologies/downloads/))
- **Maven 3.6+** (included via Maven wrapper)
- **Node.js 18+** and npm ([Download](https://nodejs.org/))
- **PostgreSQL 12+** ([Download](https://www.postgresql.org/download/))
- **Google Gemini API Key** ([Get one here](https://makersuite.google.com/app/apikey))
- **Git** for version control

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/rahulvarma2005/SmartTaskPlanner.git
cd SmartTaskPlanner
```

### 2. Database Setup

Create a PostgreSQL database:

```sql
CREATE DATABASE smarttaskplanner;
```

### 3. Backend Configuration

1. Navigate to `Smart Task Planner Backend/src/main/resources/`
2. Copy `application.properties.template` to `application.properties`:

```bash
cd "Smart Task Planner Backend"
copy src\main\resources\application.properties.template src\main\resources\application.properties
```

3. Edit `application.properties` with your configuration:

```properties
spring.application.name=SmartTaskPlanner

# Server Configuration
server.port=${PORT:8081}

# PostgreSQL Database Configuration
spring.datasource.url=${DATABASE_URL:jdbc:postgresql://localhost:5432/smarttaskplanner}
spring.datasource.username=${DB_USERNAME:your_username}
spring.datasource.password=${DB_PASSWORD:your_password}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Connection Pool Settings
spring.datasource.hikari.maximum-pool-size=5
spring.datasource.hikari.minimum-idle=2

# Gemini API Configuration
gemini.api.key=${GEMINI_API_KEY:your_gemini_api_key_here}
gemini.api.url=${GEMINI_API_URL:https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent}

# Gemini Prompt Template (configured in application.properties)
gemini.api.prompt.template=You are a project manager AI...
```

### 4. Frontend Configuration

1. Navigate to `Smart Task Planner Frontend/`
2. Create `.env` file:

```bash
cd "Smart Task Planner Frontend"
copy .env.example .env
```

3. Edit `.env` with your backend URL:

```env
# For local development
VITE_API_BASE_URL=http://localhost:8081

# For production (after deploying backend)
# VITE_API_BASE_URL=https://your-backend-url.onrender.com
```

> **Note:** All Vite environment variables must be prefixed with `VITE_`

### 5. Start the Backend

Navigate to the backend directory and run:

```bash
cd "Smart Task Planner Backend"
mvnw.cmd spring-boot:run
```

Or if you have Maven installed globally:
```bash
mvn spring-boot:run
```

The backend will start on `http://localhost:8081`

### 6. Start the Frontend

Open a new terminal and navigate to the frontend directory:

```bash
cd "Smart Task Planner Frontend"
npm install
npm run dev
```

The frontend will start on `http://localhost:5173`

---

## 🧪 Running Tests

### Backend Tests

```bash
cd "Smart Task Planner Backend"
mvnw.cmd test
```

Or if you have Maven installed globally:
```bash
mvn test
```

### Frontend Tests

```bash
cd "Smart Task Planner Frontend"
npm run typecheck
npm run lint
```

---

## 📡 API Endpoints

### POST `/api/tasks/generate`

Generate tasks from a goal using AI.

**Request:**
```bash
POST http://localhost:8081/api/tasks/generate
Content-Type: application/json

{
  "goal": "Create a mobile app in one month"
}
```

**Request Body:**
```json
{
  "goal": "Create a mobile app in one month"
}
```

**Response:**
```json
[
  {
    "id": 1,
    "taskDescription": "Define app requirements and target audience",
    "deadline": "in 3 days",
    "status": "To Do",
    "dependencies": null
  },
  {
    "id": 2,
    "taskDescription": "Create wireframes and UI/UX design",
    "deadline": "in 5 days",
    "status": "To Do",
    "dependencies": "Depends on: Task #1"
  }
]
```

**cURL Example:**
```bash
curl -X POST http://localhost:8081/api/tasks/generate \
  -H "Content-Type: application/json" \
  -d "{\"goal\":\"Learn Docker in one week\"}"
```

---

## 📁 Project Structure

```
SmartTaskPlanner/
├── Smart Task Planner Backend/           # Spring Boot backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/planner/SmartTaskPlanner/
│   │   │   │       ├── SmartTaskPlannerApplication.java
│   │   │   │       ├── controller/
│   │   │   │       │   └── TaskController.java
│   │   │   │       ├── dto/
│   │   │   │       │   ├── GoalRequest.java
│   │   │   │       │   └── TaskResponse.java
│   │   │   │       ├── model/
│   │   │   │       │   └── Task.java
│   │   │   │       ├── repository/
│   │   │   │       │   └── TaskRepository.java
│   │   │   │       └── service/
│   │   │   │           └── TaskService.java
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       └── application.properties.template
│   │   └── test/
│   │       └── java/
│   │           └── com/planner/SmartTaskPlanner/
│   │               └── controller/
│   │                   └── TaskControllerTest.java
│   ├── .dockerignore
│   ├── Dockerfile                        # Multi-stage Docker build
│   ├── pom.xml                           # Maven dependencies
│   ├── mvnw & mvnw.cmd                   # Maven wrapper
│   └── RENDER_DEPLOYMENT.md              # Render deployment guide
│
├── Smart Task Planner Frontend/          # React + Vite frontend
│   ├── src/
│   │   ├── components/
│   │   │   └── TaskCard.tsx              # Task card component
│   │   ├── App.tsx                       # Main app component
│   │   ├── main.tsx                      # Entry point
│   │   └── index.css                     # Global styles
│   ├── .env                              # Environment variables (gitignored)
│   ├── .dockerignore
│   ├── Dockerfile                        # Multi-stage Docker build
│   ├── package.json                      # npm dependencies
│   ├── vite.config.ts                    # Vite configuration
│   ├── tailwind.config.js                # Tailwind CSS config
│   ├── tsconfig.json                     # TypeScript config
│   ├── ENV_VARIABLES.md                  # Environment variable docs
│   └── QUICK_START.md                    # Quick start guide
│
├── .idea/                                # IntelliJ IDEA project files
├── target/                               # Build output (gitignored)
├── Smart Task Planner Demo Video.mp4     # Demo video
└── README.md                             # This file
```

---

## 🛠️ Technologies Used

### Backend Stack
| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 21 | Programming language |
| Spring Boot | 3.5.6 | Application framework |
| Spring Data JPA | 3.5.6 | Data persistence |
| Spring Web | 3.5.6 | REST API |
| Spring WebFlux | 3.5.6 | Reactive HTTP client for Gemini API |
| Spring Boot Actuator | 3.5.6 | Health checks & monitoring |
| PostgreSQL | 12+ | Database |
| Hibernate | 6.4.x | ORM framework |
| Lombok | Latest | Reduce boilerplate code |
| Maven | 3.6+ | Build tool |

### Frontend Stack
| Technology | Version | Purpose |
|------------|---------|---------|
| React | 18.3.1 | UI framework |
| TypeScript | 5.5.3 | Type-safe JavaScript |
| Vite | 5.4.2 | Build tool & dev server |
| Tailwind CSS | 3.4.1 | Utility-first CSS framework |
| Lucide React | 0.344.0 | Icon library |
| PostCSS | 8.4.35 | CSS processing |
| Autoprefixer | 10.4.18 | CSS vendor prefixing |
| ESLint | 9.9.1 | Code linting |

### AI Integration
| Service | Model | Purpose |
|---------|-------|---------|
| Google Gemini | 2.5 Flash | Task generation & decomposition |

### DevOps & Deployment
| Tool | Purpose |
|------|---------|
| Docker | Containerization |
| Render | Cloud hosting (backend) |
| PostgreSQL on Render | Managed database |
| Git | Version control |

---

## 🐳 Docker Deployment

### Backend Docker

The backend uses a multi-stage Docker build for optimized image size:

```dockerfile
# Build stage - Uses JDK 21
FROM eclipse-temurin:21-jdk-alpine AS build
# ... build process ...

# Runtime stage - Uses JRE 21 (smaller)
FROM eclipse-temurin:21-jre-alpine
# ... runtime setup ...
```

**Build and run:**
```bash
cd "Smart Task Planner Backend"
docker build -t smarttaskplanner-backend .
docker run -p 8081:8081 \
  -e DATABASE_URL=your_db_url \
  -e GEMINI_API_KEY=your_api_key \
  smarttaskplanner-backend
```

### Frontend Docker

The frontend uses Nginx for production serving:

```dockerfile
# Build stage - Node.js build
FROM node:20-alpine AS builder
# ... build process ...

# Runtime stage - Nginx serving
FROM nginx:alpine
# ... serve static files ...
```

**Build and run:**
```bash
cd "Smart Task Planner Frontend"
docker build -t smarttaskplanner-frontend .
docker run -p 80:80 smarttaskplanner-frontend
```

---

## 🚀 Deployment

### Deploy to Render

#### Backend Deployment

1. **Push code to GitHub**
2. **Create Web Service on Render:**
   - Connect your GitHub repository
   - Select "Docker" as runtime
   - Set root directory: `Smart Task Planner Backend`
   - Add environment variables:
     - `GEMINI_API_KEY`
     - `DATABASE_URL` (from Render PostgreSQL)
     - `PORT` (automatically set by Render)

3. **Create PostgreSQL Database:**
   - Create PostgreSQL instance on Render
   - Connect to web service

For detailed instructions, see [`Smart Task Planner Backend/RENDER_DEPLOYMENT.md`](./Smart%20Task%20Planner%20Backend/RENDER_DEPLOYMENT.md)

#### Frontend Deployment

1. **Build the frontend:**
```bash
npm run build
```

2. **Deploy to Render/Vercel/Netlify**
   - Set environment variable: `VITE_API_BASE_URL` to your backend URL

---

## 🔐 Environment Variables

### Backend Environment Variables

| Variable | Description | Required | Default |
|----------|-------------|----------|---------|
| `PORT` | Server port | No | 8081 |
| `DATABASE_URL` | PostgreSQL connection string | Yes | localhost:5432 |
| `DB_USERNAME` | Database username | Yes | postgres |
| `DB_PASSWORD` | Database password | Yes | - |
| `GEMINI_API_KEY` | Google Gemini API key | Yes | - |
| `GEMINI_API_URL` | Gemini API endpoint | Yes | Set in properties |

### Frontend Environment Variables

| Variable | Description | Required | Default |
|----------|-------------|----------|---------|
| `VITE_API_BASE_URL` | Backend API URL | Yes | http://localhost:8081 |

> **Security Note:** Never commit `.env` files or expose API keys in public repositories

---

## 💡 Usage Examples

### Example 1: Project Planning
**Input:** "Launch a SaaS product in 3 months"

**Generated Tasks:**
1. Market research and competitor analysis (in 1 week)
2. Define product features and MVP scope (in 1 week) [Depends on: Task #1]
3. Design system and UI/UX (in 2 weeks) [Depends on: Task #2]
4. Backend development (in 4 weeks) [Depends on: Task #3]
5. Frontend development (in 4 weeks) [Depends on: Task #3]
6. Testing and QA (in 2 weeks) [Depends on: Task #4, Task #5]
7. Beta launch and user feedback (in 2 weeks) [Depends on: Task #6]
8. Official launch and marketing (in 1 week) [Depends on: Task #7]

### Example 2: Learning Goal
**Input:** "Learn React in 4 weeks"

**Generated Tasks:**
1. Learn JavaScript fundamentals (in 5 days)
2. Study React basics and components (in 5 days) [Depends on: Task #1]
3. Learn React Hooks and state management (in 5 days) [Depends on: Task #2]
4. Build practice projects (in 1 week) [Depends on: Task #3]
5. Learn React Router and advanced concepts (in 1 week) [Depends on: Task #3]

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. **Fork the repository**
2. **Create a feature branch:**
   ```bash
   git checkout -b feature/AmazingFeature
   ```
3. **Commit your changes:**
   ```bash
   git commit -m 'Add some AmazingFeature'
   ```
4. **Push to the branch:**
   ```bash
   git push origin feature/AmazingFeature
   ```
5. **Open a Pull Request**

### Development Guidelines
- Follow existing code style
- Write meaningful commit messages
- Add tests for new features
- Update documentation as needed
- Ensure all tests pass before submitting PR

---

## 🐛 Troubleshooting

### Common Issues

#### Backend Issues

**Problem:** Application fails to start
- **Solution:** Check if PostgreSQL is running and credentials are correct
- Verify `application.properties` configuration
- Check Java version: `java -version`

**Problem:** Gemini API errors
- **Solution:** Verify API key is valid
- Check API endpoint URL
- Review rate limits on Gemini API

**Problem:** Database connection errors
- **Solution:** Ensure PostgreSQL is running
- Verify database exists: `CREATE DATABASE smarttaskplanner;`
- Check firewall rules

#### Frontend Issues

**Problem:** API calls failing with CORS errors
- **Solution:** Verify `@CrossOrigin` annotation in backend controller
- Check if backend is running on correct port
- Ensure `VITE_API_BASE_URL` is correctly set

**Problem:** Environment variables not loading
- **Solution:** Ensure `.env` file exists in frontend root
- Restart dev server after changing `.env`
- Variables must be prefixed with `VITE_`

**Problem:** Build errors
- **Solution:** Delete `node_modules` and reinstall: `npm install`
- Clear cache: `npm cache clean --force`
- Check Node.js version: `node -v`

---

## 📝 License

This project is open source and available under the [MIT License](LICENSE).

---

## 👨‍💻 Author

**Rahul Varma**
- GitHub: [@rahulvarma2005](https://github.com/rahulvarma2005)
- Repository: [SmartTaskPlanner](https://github.com/rahulvarma2005/SmartTaskPlanner)

---

## 🙏 Acknowledgments

- **Google Gemini AI** for powerful language model capabilities
- **Spring Boot Community** for excellent framework and documentation
- **React Team** for the amazing UI library
- **Vite** for blazing fast development experience
- **Tailwind CSS** for utility-first styling
- **Render** for easy cloud deployment
- All contributors and supporters of this project

---

## 📞 Support

If you encounter any issues or have questions:

1. **Check the documentation** in this README
2. **Review the troubleshooting section** above
3. **Open an issue** on [GitHub Issues](https://github.com/rahulvarma2005/SmartTaskPlanner/issues)
4. **Watch the demo video** for usage examples

---

## 🗺️ Roadmap

Future enhancements planned:

- [ ] User authentication and authorization
- [ ] Task editing and deletion features
- [ ] Multiple project management
- [ ] Task progress tracking with percentage
- [ ] Calendar integration
- [ ] Email notifications for deadlines
- [ ] Export tasks to PDF/CSV
- [ ] Mobile app version
- [ ] Real-time collaboration features
- [ ] Integration with other project management tools

---

## 📊 Project Stats

- **Lines of Code:** ~2000+
- **Components:** 1 main component + TaskCard
- **API Endpoints:** 1 (extensible)
- **Database Tables:** 1 (Task entity)
- **Docker Images:** 2 (Backend + Frontend)

---

**Made with ❤️ by Rahul Varma**

**Happy Planning! 🚀**
