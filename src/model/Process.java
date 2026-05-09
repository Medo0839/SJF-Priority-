public class Process {
    String id;
    int arrivalTime, burstTime, remainingTime, priority;
    int completionTime, waitingTime, turnaroundTime, responseTime;
    int firstTimeCPU = -1; 

    public Process(String id, int arrivalTime, int burstTime, int priority) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.priority = priority;
    }
}
