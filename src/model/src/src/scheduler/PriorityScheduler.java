import java.util.*;

public class PriorityScheduler {
    public static void calculatePriority(List<Process> processes, List<String[]> log) {
        int currentTime = 0, completed = 0, n = processes.size();
        while (completed < n) {
            Process best = null;
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && p.remainingTime > 0) {
                    if (best == null || p.priority < best.priority) best = p;
                }
            }
            if (best == null) { currentTime++; continue; }

            if (best.firstTimeCPU == -1) {
                best.firstTimeCPU = currentTime;
                best.responseTime = best.firstTimeCPU - best.arrivalTime;
            }

            log.add(new String[]{best.id, String.valueOf(currentTime)});
            best.remainingTime--;
            currentTime++;
            if (best.remainingTime == 0) {
                best.completionTime = currentTime;
                best.turnaroundTime = best.completionTime - best.arrivalTime;
                best.waitingTime = best.turnaroundTime - best.burstTime;
                completed++;
            }
        }
    }
}
