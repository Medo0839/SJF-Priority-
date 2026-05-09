import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GanttChartPanel extends JPanel {
    private List<String[]> rawLog;
    private String title;

    public GanttChartPanel(List<String[]> log, String title) {
        this.rawLog = log;
        this.title = title;

        setPreferredSize(new Dimension(550, 200)); 
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.setColor(Color.BLACK);
        g2.drawString(title, 20, 25);

        if (rawLog == null || rawLog.isEmpty()) return;


        List<GanttBlock> blocks = new ArrayList<>();
        String currentId = rawLog.get(0)[0];
        int startTime = Integer.parseInt(rawLog.get(0)[1]);
        int duration = 0;

        for (String[] entry : rawLog) {
            if (entry[0].equals(currentId)) {
                duration++;
            } else {
                blocks.add(new GanttBlock(currentId, startTime, duration));
                currentId = entry[0];
                startTime = Integer.parseInt(entry[1]);
                duration = 1;
            }
        }
        blocks.add(new GanttBlock(currentId, startTime, duration));

        int xOffset = 30;
        int yOffset = 50;
        int height = 50;
        int scale = 20; 

        for (int i = 0; i < blocks.size(); i++) {
            GanttBlock b = blocks.get(i);
            int width = b.duration * scale;

            g2.setColor(new Color(235, 235, 235));
            g2.fillRect(xOffset, yOffset, width, height);
            
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(1.2f));
            g2.drawRect(xOffset, yOffset, width, height);


            g2.setFont(new Font("Arial", Font.BOLD, 12));
            FontMetrics metrics = g2.getFontMetrics();
            int stringX = xOffset + (width - metrics.stringWidth(b.id)) / 2;
            int stringY = yOffset + (height - metrics.getHeight()) / 2 + metrics.getAscent();
            g2.drawString(b.id, stringX, stringY);


            g2.setFont(new Font("Arial", Font.PLAIN, 10));
            g2.drawString(String.valueOf(b.startTime), xOffset - 5, yOffset + height + 15);

            if (i == blocks.size() - 1) {
                g2.drawString(String.valueOf(b.startTime + b.duration), xOffset + width - 5, yOffset + height + 15);
            }

            xOffset += width;
        }
    }

    private static class GanttBlock {
        String id;
        int startTime;
        int duration;

        GanttBlock(String id, int startTime, int duration) {
            this.id = id;
            this.startTime = startTime;
            this.duration = duration;
        }
    }
}
