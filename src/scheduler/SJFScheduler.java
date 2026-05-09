import java.util.*;

public class SJFScheduler {

    public static void calculateSJF(List<Process> processes, List<String[]> log) {
        int currentTime = 0, completed = 0, n = processes.size();
        while (completed < n) {
            Process shortest = null;
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && p.remainingTime > 0) {
                    if (shortest == null || p.remainingTime < shortest.remainingTime) shortest = p;
                }
            }
            if (shortest == null) { currentTime++; continue; }
            
            if (shortest.firstTimeCPU == -1) {
                shortest.firstTimeCPU = currentTime;
                shortest.responseTime = shortest.firstTimeCPU - shortest.arrivalTime;
            }
            
            log.add(new String[]{shortest.id, String.valueOf(currentTime)});
            shortest.remainingTime--;
            currentTime++;
            if (shortest.remainingTime == 0) {
                shortest.completionTime = currentTime;
                shortest.turnaroundTime = shortest.completionTime - shortest.arrivalTime;
                shortest.waitingTime = shortest.turnaroundTime - shortest.burstTime;
                completed++;
            }
        }
    }


    public static void calculateSJFNonPreemptive(List<Process> processes, List<String[]> log) {
        int currentTime = 0, completed = 0, n = processes.size();
        while (completed < n) {
            Process shortest = null;
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && p.remainingTime > 0) {

                    if (shortest == null || p.burstTime < shortest.burstTime) shortest = p;
                }
            }
            
            if (shortest == null) { 
                currentTime++; 
                continue; 
            }


            shortest.firstTimeCPU = currentTime;
            shortest.responseTime = shortest.firstTimeCPU - shortest.arrivalTime;


            for (int i = 0; i < shortest.burstTime; i++) {
                log.add(new String[]{shortest.id, String.valueOf(currentTime + i)});
            }

            currentTime += shortest.burstTime;
            shortest.remainingTime = 0;
            shortest.completionTime = currentTime;
            shortest.turnaroundTime = shortest.completionTime - shortest.arrivalTime;
            shortest.waitingTime = shortest.turnaroundTime - shortest.burstTime;
            completed++;
        }
    }
}
