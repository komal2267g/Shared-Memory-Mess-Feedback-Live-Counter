🛡️ SentinelSync: Distributed Hostel Mess E-Governance Portal
![alt text](https://github.com/komal2267g/Shared-Memory-Mess-Feedback-Live-Counter/actions/workflows/ci.yml/badge.svg)

![alt text](https://img.shields.io/badge/Docker-Containerized-blue?logo=docker)

![alt text](https://img.shields.io/badge/Java-17-orange?logo=openjdk)

![alt text](https://img.shields.io/badge/Status-Live-green)
SentinelSync is a professional, high-performance auditing system built to manage hostel mess feedback in real-time. Developed for the Distributed Systems Lab (Group E), this project replaces traditional slow-performing databases with a Low-Latency Shared Memory IPC Engine.
🔗 Live Deployment: Hostel Management Portal
🚀 The Core Innovation: Shared Memory IPC
In high-concurrency environments like a college mess, traditional SQL databases introduce significant disk I/O overhead and connection pooling latency. SentinelSync leverages Inter-Process Communication (IPC) via Shared Memory to achieve nanosecond-level data throughput.
🧠 Engineering Architecture
Memory-Mapped I/O (mmap): Uses Java NIO MappedByteBuffer to map a 1MB binary segment (campus_data.bin) directly into the system's virtual address space.
Atomic Operations: By bypassing the standard filesystem stack, data is updated directly in RAM, making it visible to all processes/containers instantly.
Distributed Mutex (Race Condition Handling): Implements system-wide synchronization using FileChannel.lock(). This prevents the "Lost Update" problem when hundreds of students submit feedback at the exact same millisecond.
Binary Circular Buffer: Audit logs are stored in a fixed-size sequential binary format, ensuring O(1) write performance and efficient memory utilization.
✨ Key Features
👤 User-Centric Experience
Session Persistence: Integrated localStorage roll-number identification to maintain user sessions across browser refreshes.
Contextual Feedback: Students can submit ratings (Good/Average/Poor) along with qualitative text comments.
The "YOU" Badge: Smart UI logic highlights personal entries in the global audit log for better accountability.
Professional UI: A clean, corporate aesthetic using Plus Jakarta Sans and Tailwind CSS.
📊 Warden & Management Analytics
Live Audit Logs: Instant visibility of every feedback entry without the need for manual page refreshes (Short-polling architecture).
Executive Dashboard: High-level summary cards showing "Satisfied," "Average," and "Critical" quality metrics.
Business Intelligence (BI) Reports: A filter-driven analytics view with Chart.js integration to track quality trends over daily/monthly ranges.
Official Statements: Print-ready CSS logic for downloading "Official Mess Quality Statements" directly as PDFs.
🏗️ DevOps & Tech Stack
This project is built with a production-first mindset, following industry-standard DevOps practices:
Containerization: Architected with a multi-stage Docker build to encapsulate the OpenJDK environment and WebServer, optimized for a slim Alpine-JRE footprint.
CI/CD Pipeline: Automated GitHub Actions workflow that triggers on every push to verify code compilation and Docker image build integrity.
Cloud Infrastructure: Deployed on Render via Docker runtime, demonstrating cloud-native state management of shared memory segments.
Version Control: Managed via Git with a clean .gitignore to prevent binary bloat and repository pollution.
🛠️ Tech Stack Details
Layer	Technology
Backend	Java 17 (NIO, HttpServer API)
Frontend	HTML5, JavaScript (ES6+), Tailwind CSS
Data Engine	Shared Memory (Binary Memory-Mapped Files)
Visuals	Chart.js, FontAwesome 6
Infrastructure	Docker, Docker Compose, GitHub Actions
Deployment	Render (PaaS)
⚙️ How to Run Locally
Prerequisites
Docker Installed
OR Java JDK 17+ installed
Option 1: Docker (Recommended)
code
Bash
docker-compose up --build
The application will be live at http://localhost:8080.
Option 2: Manual Run
code
Bash
# Compile classes
javac src/*.java -d .

# Start the Web Server
java src.WebServer
📜 Lab Requirements Compliance

Distributed Communication: Shared Memory Segment Implementation.

Synchronization: Distributed Mutex for Race Condition Handling.

State Management: Binary record addressing with 365-day calendar mapping.

In-Memory Storage: Zero SQL/NoSQL database usage (fully compliant).
Developed by: Group E - Distributed Systems Lab
Project Lead & DevOps Engineer: Komal
Research Focus: High-Speed IPC in Containerized Environments.
