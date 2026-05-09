import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class MainDashboard extends JFrame {
    private JTextField idF, arrF, burF, priF;
    private List<Process> inputProcesses = new ArrayList<>();
    private JTextArea sjfPreArea = new JTextArea(), sjfNonArea = new JTextArea(), priArea = new JTextArea();
    private DefaultTableModel tableModel;


    private JPanel sjfPreChartCont = new JPanel(new BorderLayout());
    private JPanel sjfNonChartCont = new JPanel(new BorderLayout());
    private JPanel priChartCont = new JPanel(new BorderLayout());

    public MainDashboard() {
        setTitle("Project C4: SJF vs Priority Dashboard (Full Pro Version)");
        setSize(1200, 950);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        controlPanel.add(new JLabel("ID:")); idF = new JTextField(5); controlPanel.add(idF);
        controlPanel.add(new JLabel("Arrival:")); arrF = new JTextField(5); controlPanel.add(arrF);
        controlPanel.add(new JLabel("Burst:")); burF = new JTextField(5); controlPanel.add(burF);
        controlPanel.add(new JLabel("Priority:")); priF = new JTextField(5); controlPanel.add(priF);
        
        JButton addB = new JButton("Add Process");
        addB.setBackground(new Color(173, 216, 230));
        controlPanel.add(addB);

        JButton resetB = new JButton("Reset All");
        resetB.setBackground(new Color(255, 99, 71)); 
        resetB.setForeground(Color.WHITE);
        controlPanel.add(resetB);
        

        String[] columns = {"ID", "Arrival Time", "Burst Time", "Priority"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable inputTable = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(inputTable);
        tableScroll.setPreferredSize(new Dimension(1000, 180));

        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(controlPanel, BorderLayout.NORTH);
        topContainer.add(tableScroll, BorderLayout.CENTER);
        add(topContainer, BorderLayout.NORTH);

        JButton runB = new JButton("RUN SIMULATION");
        runB.setBackground(new Color(50, 205, 50));
        runB.setFont(new Font("Arial", Font.BOLD, 20));
        add(runB, BorderLayout.SOUTH);


        JPanel resultsPanel = new JPanel(new GridLayout(1, 2, 15, 0));

        JPanel sjfSidePanel = new JPanel(new GridLayout(2, 1, 0, 10));
        

        JPanel preUnit = createResultUnit(sjfPreArea, sjfPreChartCont, "SJF PREEMPTIVE (SRTF)");

        JPanel nonUnit = createResultUnit(sjfNonArea, sjfNonChartCont, "SJF NON-PREEMPTIVE");
        
        sjfSidePanel.add(preUnit);
        sjfSidePanel.add(nonUnit);


        JPanel priUnit = createResultUnit(priArea, priChartCont, "PRIORITY SCHEDULING RESULTS");

        resultsPanel.add(sjfSidePanel);
        resultsPanel.add(priUnit);
        add(new JScrollPane(resultsPanel), BorderLayout.CENTER);

        addB.addActionListener(e -> validateAndAdd());
        resetB.addActionListener(e -> resetEverything());
        runB.addActionListener(e -> runSim());
    }


    private JPanel createResultUnit(JTextArea area, JPanel chartCont, String title) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE); 
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        area.setEditable(false);
        p.add(new JScrollPane(area), BorderLayout.CENTER);
        
        chartCont.setPreferredSize(new Dimension(0, 180)); 
        chartCont.setBackground(Color.WHITE);

        chartCont.setBorder(BorderFactory.createEmptyBorder(20, 5, 40, 5)); 
        p.add(chartCont, BorderLayout.SOUTH);
        return p;
    }

    private void validateAndAdd() {
        if (idF.getText().trim().isEmpty() || arrF.getText().trim().isEmpty() || 
            burF.getText().trim().isEmpty() || priF.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error: All fields must be filled!", "Missing Values", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = idF.getText().trim();
        int arr, bur, pri;

        try {
            arr = Integer.parseInt(arrF.getText().trim());
            bur = Integer.parseInt(burF.getText().trim());
            pri = Integer.parseInt(priF.getText().trim());

            if (arr < 0) {
                JOptionPane.showMessageDialog(this, "Arrival Time cannot be negative!", "Invalid Logic", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (bur <= 0) {
                JOptionPane.showMessageDialog(this, "Burst Time must be greater than zero!", "Invalid Logic", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (pri < 0) {
                JOptionPane.showMessageDialog(this, "Priority value cannot be negative!", "Invalid Logic", JOptionPane.WARNING_MESSAGE);
                return;
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: Please enter valid numbers only!", "Numeric Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (Process p : inputProcesses) {
            if (p.id.equalsIgnoreCase(id)) {
                JOptionPane.showMessageDialog(this, "Error: Process ID '" + id + "' already exists!", "Duplicate ID", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        inputProcesses.add(new Process(id, arr, bur, pri));
        tableModel.addRow(new Object[]{id, arr, bur, pri});
        
        idF.setText(""); arrF.setText(""); burF.setText(""); priF.setText("");
        idF.requestFocus();
    }

    private void resetEverything() {
        inputProcesses.clear();
        tableModel.setRowCount(0);
        sjfPreArea.setText(""); sjfNonArea.setText(""); priArea.setText("");
        sjfPreChartCont.removeAll(); sjfNonChartCont.removeAll(); priChartCont.removeAll();
        this.revalidate();
        this.repaint();
        JOptionPane.showMessageDialog(this, "System cleared successfully!");
    }

    private void runSim() {
        if(inputProcesses.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add at least one process to simulate!", "Empty List", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        List<String[]> sLogPre = new ArrayList<>(), sLogNon = new ArrayList<>(), pLog = new ArrayList<>();
        List<Process> sListPre = copy(inputProcesses), sListNon = copy(inputProcesses), pList = copy(inputProcesses);
        
        SJFScheduler.calculateSJF(sListPre, sLogPre); 
        SJFScheduler.calculateSJFNonPreemptive(sListNon, sLogNon); 
        PriorityScheduler.calculatePriority(pList, pLog); 
        
        double avgWTPre = getAvgWT(sListPre);
        double avgWTNon = getAvgWT(sListNon);
        double avgWTPri = getAvgWT(pList);

        double avgRTPre = getAvgRT(sListPre);
        double avgRTNon = getAvgRT(sListNon);
        double avgRTPri = getAvgRT(pList);

        double avgTATPre = getAvgTAT(sListPre);
        double avgTATNon = getAvgTAT(sListNon);
        double avgTATPri = getAvgTAT(pList);
        
        display(sjfPreArea, "SJF PREEMPTIVE RESULTS", sListPre, avgWTPre);
        display(sjfNonArea, "SJF NON-PREEMPTIVE RESULTS", sListNon, avgWTNon);
        display(priArea, "PRIORITY RESULTS", pList, avgWTPri);

        updateChart(sjfPreChartCont, sLogPre, "SJF Preemptive Timeline");
        updateChart(sjfNonChartCont, sLogNon, "SJF Non-Preemptive Timeline");
        updateChart(priChartCont, pLog, "Priority Timeline");

        // بناء تقرير التحليل والشرح
        StringBuilder analysis = new StringBuilder();
        analysis.append("📋 ALGORITHM ANALYSIS & COMPARISON 📋\n");
        analysis.append("------------------------------------------------------------\n\n");
        
        analysis.append("1. SJF Preemptive (SRTF):\n");
        analysis.append("   - Best for: Minimizing Average Waiting Time (Optimal).\n");
        analysis.append("   - Usage: Real-time systems where short tasks must finish ASAP.\n\n");

        analysis.append("2. SJF Non-Preemptive:\n");
        analysis.append("   - Best for: Reducing overhead (no context switching mid-burst).\n");
        analysis.append("   - Usage: Batch processing where task lengths are known.\n\n");

        analysis.append("3. Priority Scheduling:\n");
        analysis.append("   - Best for: Importance-based execution (VIP tasks first).\n");
        analysis.append("   - Usage: OS kernel tasks or systems with critical deadlines.\n\n");
        
        analysis.append(String.format("Final Stats (WT | RT | TAT):\n"));
        analysis.append(String.format("- SJF Pre:  %.1f | %.1f | %.1f\n", avgWTPre, avgRTPre, avgTATPre));
        analysis.append(String.format("- SJF Non:  %.1f | %.1f | %.1f\n", avgWTNon, avgRTNon, avgTATNon));
        analysis.append(String.format("- Priority: %.1f | %.1f | %.1f", avgWTPri, avgRTPri, avgTATPri));

        JOptionPane.showMessageDialog(this, new JScrollPane(new JTextArea(analysis.toString())), "Scheduling Analysis Report", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateChart(JPanel container, List<String[]> log, String title) {
        container.removeAll();
        GanttChartPanel chart = new GanttChartPanel(log, title); 
        container.add(chart);
        container.revalidate();
        container.repaint();
    }

    private double getAvgWT(List<Process> list) {
        double total = 0;
        for (Process p : list) total += p.waitingTime;
        return total / list.size();
    }

    private void display(JTextArea a, String title, List<Process> list, double avgWT) {
        String sep = "------------------------------------------\n";
        a.setText(title + "\n" + sep + "ID\tWT\tTAT\tRT\n");
        double tTAT = 0, tRT = 0;
        for (Process p : list) {
            a.append(p.id + "\t" + p.waitingTime + "\t" + p.turnaroundTime + "\t" + p.responseTime + "\n");
            tTAT += p.turnaroundTime; tRT += p.responseTime;
        }
        a.append(sep + "Average WT:  " + String.format("%.2f", avgWT) + "\n");
        a.append("Average TAT: " + String.format("%.2f", tTAT/list.size()) + "\n");
        a.append("Average RT:  " + String.format("%.2f", tRT/list.size()) + "\n");
    }

    private List<Process> copy(List<Process> l) {
        List<Process> c = new ArrayList<>();
        for (Process p : l) c.add(new Process(p.id, p.arrivalTime, p.burstTime, p.priority)); //[cite: 5]
        return c;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainDashboard().setVisible(true));
    }
    private double getAvgRT(List<Process> list) {
        double total = 0;
        for (Process p : list) total += p.responseTime;
        return total / list.size();
    }

    private double getAvgTAT(List<Process> list) {
        double total = 0;
        for (Process p : list) total += p.turnaroundTime;
        return total / list.size();
    }
}
