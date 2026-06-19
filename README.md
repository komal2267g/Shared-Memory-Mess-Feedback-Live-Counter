# 🛡️ SentinelSync – Distributed Hostel Mess E-Governance Portal

![CI](https://github.com/komal2267g/Shared-Memory-Mess-Feedback-Live-Counter/actions/workflows/ci.yml/badge.svg)
![Docker](https://img.shields.io/badge/Docker-Containerized-blue?logo=docker)
![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Status](https://img.shields.io/badge/Status-Live-green)

🌐 **Live Demo:** https://shared-memory-mess-feedback-live-counter.onrender.com/

SentinelSync is a distributed hostel mess feedback and auditing platform developed for the Distributed Systems Lab. The system enables students to submit real-time mess feedback while providing wardens and administrators with live monitoring, analytics, and reporting capabilities.

📌 Project Overview

SentinelSync is a distributed hostel mess feedback and auditing platform developed for the Distributed Systems Lab. The system enables students to submit real-time mess feedback while providing wardens and administrators with live monitoring, analytics, and reporting capabilities.

The project demonstrates practical implementation of Shared Memory IPC, Synchronization, Containerization, and CI/CD automation in a real-world campus management system.

🏗️ System Architecture
Shared Memory Data Engine

The application uses Java NIO's MappedByteBuffer to create a memory-mapped file (campus_data.bin) that acts as a shared storage segment between processes.

Synchronization

To prevent concurrent write conflicts, file-level locking is implemented using:

FileChannel.lock()

This ensures safe updates when multiple users submit feedback simultaneously.

Audit Log Storage

Feedback entries are stored using a fixed-size binary structure, enabling:

Predictable memory usage
Fast sequential writes
Efficient retrieval of records
✨ Features
👨‍🎓 Student Portal
Submit mess quality feedback
Rating options: Good, Average, Poor
Add textual comments
Session persistence using localStorage
Personal feedback highlighting with "YOU" badge
Responsive and modern UI
👨‍💼 Warden Dashboard
Live feedback monitoring
Satisfaction summary cards
Audit trail visibility
Daily and monthly trend analysis
Printable quality reports
Interactive charts and analytics
📊 Analytics Dashboard

The dashboard provides:

Satisfaction metrics
Critical issue tracking
Historical quality trends
Interactive visualizations powered by Chart.js
🛠️ Tech Stack
Layer	Technology
Backend	Java 17
Networking	Java HttpServer API
Shared Memory	Java NIO (MappedByteBuffer)
Frontend	HTML5, JavaScript (ES6+)
Styling	Tailwind CSS
Visualization	Chart.js
Icons	Font Awesome 6
Containerization	Docker
CI/CD	GitHub Actions
Deployment	Render
⚙️ DevOps Practices
Docker
Multi-stage Docker builds
Lightweight runtime image
Reproducible deployment environment
CI/CD Pipeline

GitHub Actions automatically:

Validate project builds
Verify Docker image generation
Run automated checks on every push
Cloud Deployment
Containerized deployment on Render
Automated build and deployment pipeline
Cloud-hosted distributed architecture
📂 Project Structure
SentinelSync/
│
├── src/
│   ├── WebServer.java
│   ├── SharedMemoryManager.java
│   ├── FeedbackHandler.java
│   └── ...
│
├── public/
│   ├── index.html
│   ├── dashboard.html
│   └── assets/
│
├── Dockerfile
├── docker-compose.yml
├── .github/workflows/
├── campus_data.bin
└── README.md
⚙️ Local Setup
Prerequisites
Docker
OR Java JDK 17+
Option 1: Run with Docker
docker-compose up --build

Open:

http://localhost:8080
Option 2: Run Manually

Compile:

javac src/*.java -d .

Run:

java src.WebServer

Open:

http://localhost:8080

🎯 Distributed Systems Concepts Demonstrated
Shared Memory Communication (IPC)
Inter-Process Coordination
Synchronization using File Locks
Concurrent Access Handling
Memory-Mapped File Storage
State Management without SQL Databases

📜 Academic Context
Developed as part of the Distributed Systems Laboratory project work.

Team: Group E
Project Lead: Komal Chaurasiya
