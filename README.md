# 🚀 CPU Scheduling Simulator (SJF & Priority)

## 📝 Project Description
This project is a comprehensive **Java-based simulator** designed to visualize and compare CPU scheduling algorithms. It provides a graphical user interface (GUI) that allows users to input process data and monitor execution through real-time visualization.

### Supported Algorithms:
- **Shortest Job First (SJF):** Includes both **Preemptive (SRTF)** and **Non-Preemptive** modes.
- **Priority Scheduling:** **Preemptive** algorithm where the execution order is determined by assigned priority levels.
- **Gantt Chart:** A dynamic, color-coded chart that visualizes the timeline of process execution.

---

## 👥 Team Members | فريق العمل
| No. | Name | Student ID |
| :--- | :--- | :--- |
| 1 | **محمد صلاح محمدي محمد** | 20240839 |
| 2 | **محمد احمد محمد محمد** | 20240792 |
| 3 | **مصطفي علي مصطفي علي** | 20240969 |
| 4 | **عمار عربي سيد رمضان** | 20230358 |
| 5 | **احمد خالد عبد الصبور أحمد** | 20240034 |
| 6 | **بلال جمال عبد العزيز علي** | 20230126 |

---

## 📊 Detailed Comparison & Analysis
*Derived from our simulation results and comparative study.*

### 1. Algorithms Logic & Assumptions
- **Priority Definition:** We follow the **Lower Value = Higher Priority** rule (e.g., Priority 1 > Priority 5).
- **Tie-Breaking Rule:** If two processes have the same burst time or priority, the **FCFS** (First-Come-First-Served) rule is applied based on Arrival Time.
- **Preemption Logic:**
  - **Priority (Preemptive):** The current process is interrupted if a new process with higher priority arrives.
  - **SJF (SRTF):** The current process is interrupted if a new process arrives with a shorter remaining burst time.
- **Input Validation:** The system ensures stability by rejecting negative values, non-numeric data, and duplicate Process IDs.

### 2. Test Scenarios & Results
| Scenario | Description & Objective | Visualization |
| :--- | :--- | :--- |
| **Scenario A** | **Basic Mixed Workload:** Confirms the logic where SJF Preemptive interrupts for shorter jobs while Priority focuses on status. | ![Scenario A](screenshots/Scenario A.png) |
| **Scenario B** | **Conflict (Burst vs Priority):** Reveals the trade-off between urgent long jobs (Priority) and short non-urgent jobs (SJF). | ![Scenario B](screenshots/Scenario B.png) |
| **Scenario C** | **Fairness & Starvation:** Demonstrates how long jobs in SJF and low-priority jobs in Priority scheduling suffer delays. | ![Scenario C](screenshots/Scenario C.png) |
| **Scenario D** | **Input Validation:** Shows the simulator's ability to handle invalid data (negative numbers, empty fields) effectively. | ![Scenario D](screenshots/Scenario D.png) |

### 3. Analysis Summary
- **Efficiency:** **SJF Preemptive (SRTF)** performed best in minimizing average Waiting and Turnaround times across most test cases.
- **Starvation Risk:** Observed in "long jobs" under SJF and "low-priority jobs" under Priority scheduling.
- **Fairness:** **SJF Non-Preemptive** appeared slightly fairer as it prevents repeated interruptions once a job secures the CPU.
- **Recommendation:** Use **SJF** for general-purpose efficiency and **Priority** for mission-critical or real-time systems.

---

## 🛠️ Build and Run Steps

### Prerequisites
- **JDK:** Version 17 or higher.
- **Library:** Standard Java Swing & AWT (Integrated in JDK).

### Execution Steps
1. **Using an IDE (e.g., IntelliJ / VS Code):**
   - Open the project root folder.
   - Run the main class located at: `src/gui/MainDashboard.java`.

2. **Using Command Line:**
   ```bash
   # Compile the project
   javac -d out src/model/*.java src/scheduler/*.java src/gui/*.java
   
   # Run the application
   java -cp out gui.MainDashboard
