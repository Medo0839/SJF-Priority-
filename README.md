# 🚀 CPU Scheduling Simulator (SJF & Priority)

## 📝 Project Description

This project is a Java-based simulator designed to visualize and compare CPU scheduling algorithms. It provides a graphical user interface (GUI) to display calculation metrics and a real-time Gantt Chart for:
- **Shortest Job First (SJF):** Preemptive (SRTF) and Non-Preemptive.
- **Priority Scheduling:** Preemptive algorithm (Lower value = Higher priority).

## 📊 Detailed Comparison & Analysis
*Based on the simulation results and the comparison study.*

### 1. Algorithms Logic & Assumptions
- **Tie-Breaking Rule:** If two processes have the same burst time or priority, **FCFS** is used based on Arrival Time.
- **Preemption:**
  - **Priority:** Interrupted if a higher priority process arrives.
  - **SJF (SRTF):** Interrupted if a process with a shorter remaining time arrives.
- **Input Validation:** The system rejects negative values, non-numeric data, and duplicate IDs to ensure stability.

### 2. Test Scenarios & Results

#### 🔹 Scenario A: Basic Mixed Workload
*Confirms the fundamental logic where SJF Preemptive interrupts for shorter jobs while Priority focuses on status.*
![Scenario A](screenshots/Scenario A.png)

#### 🔹 Scenario B: Conflict between Burst Time and Priority
*Reveals the trade-off: Priority favors urgent long jobs, while SJF favors short non-urgent jobs to minimize wait time.*
![Scenario B](screenshots/Scenario B.png)

#### 🔹 Scenario C: Fairness & Starvation Case
*Demonstrates starvation risks: Long jobs in SJF and low-priority jobs in Priority scheduling may suffer significant delays.*
![Scenario C](screenshots/Scenario C.png)

#### 🔹 Scenario D: Input Validation
*Shows the simulator's ability to handle invalid data (negative numbers, empty fields, etc.) effectively.*
![Scenario D](screenshots/Scenario D.png)

### 3. Required Analysis Summary
- **Lower Average Waiting/Turnaround Time:** **SJF Preemptive (SRTF)** performed best overall in minimizing system-wide delays.
- **Urgency vs. Efficiency:** **SJF** maximizes efficiency (throughput), while **Priority Scheduling** respects task urgency but may lower overall system efficiency.
- **Starvation Observation:** Starvation was observed for "long jobs" in SJF and "low-status jobs" in Priority scheduling.
- **Fairness:** **SJF Non-Preemptive** appeared slightly fairer in practice as it prevents repeated interruptions once a long job starts.

## 🛠️ Build and Run Steps
### Prerequisites
- **JDK:** Version 17 or higher.
- **Library:** Standard Java Swing & AWT.

### Execution
1. **Using IDE:** Open the project root and run `src/gui/MainDashboard.java`.
2. **Using Command Line:**
   ```bash
   javac -d out src/model/*.java src/scheduler/*.java src/gui/*.java
   java -cp out gui.MainDashboard
