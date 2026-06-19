# 🛡️ SentinelSync – Distributed Hostel Mess E-Governance Portal

![CI](https://github.com/komal2267g/Shared-Memory-Mess-Feedback-Live-Counter/actions/workflows/ci.yml/badge.svg)
![Docker](https://img.shields.io/badge/Docker-Containerized-blue?logo=docker)
![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Status](https://img.shields.io/badge/Status-Live-green)

🌐 **Live Demo:** https://shared-memory-mess-feedback-live-counter.onrender.com/

SentinelSync is a distributed hostel mess feedback and auditing platform developed for the Distributed Systems Lab. The system enables students to submit real-time mess feedback while providing wardens and administrators with live monitoring, analytics, and reporting capabilities.

## 📌 Project Overview

SentinelSync is a distributed hostel mess feedback and auditing platform developed for the Distributed Systems Lab.

The system enables students to submit real-time mess feedback while providing wardens and administrators with live monitoring, analytics, and reporting capabilities.

---

## ✨ Features

### 👨‍🎓 Student Portal

- Submit mess quality feedback
- Rating options: Good, Average, Poor
- Add textual comments
- Session persistence using localStorage
- Personal feedback highlighting with "YOU" badge

### 👨‍💼 Warden Dashboard

- Live feedback monitoring
- Satisfaction summary cards
- Audit trail visibility
- Trend analysis
- Printable reports

---

## 🛠️ Tech Stack

| Layer | Technology |
|---------|------------|
| Backend | Java 17 |
| Frontend | HTML5, JavaScript |
| Styling | Tailwind CSS |
| Containerization | Docker |
| CI/CD | GitHub Actions |
| Deployment | Render |

---

## 📂 Project Structure

```text
SentinelSync/
│
├── .github/
│   └── workflows/
│       └── ci.yml
│
├── data/
│   └── campus_data.bin
│
├── src/
│   ├── WebServer.java
│   ├── SharedMemoryManager.java
│   ├── FeedbackHandler.java
│   └── ...
│
├── web/
│   ├── index.html
│   ├── dashboard.html
│   ├── analytics.html
│   └── assets/
│
├── Dockerfile
├── docker-compose.yml
├── .gitignore
└── README.md
```

## ⚙️ Local Setup

### Run with Docker

```bash
docker-compose up --build

## 📜 Academic Context

Developed as part of the **Distributed Systems Laboratory** coursework, focusing on shared-memory communication, synchronization, and concurrent system design.

**Team:** Group E  
**Project Lead & DevOps Engineer:** Komal Chaurasiya
