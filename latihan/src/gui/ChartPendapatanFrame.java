package gui;

import dao.RekapPendapatanDAO;

import javax.swing.*;
import java.awt.*;

public class ChartPendapatanFrame extends JFrame {

    private int total;

    public ChartPendapatanFrame() {

        setTitle("Chart Pendapatan Hari Ini");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        try {
            RekapPendapatanDAO dao = new RekapPendapatanDAO();
            total = dao.getTotalPendapatanHariIni();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            total = 0;
        }

        add(new ChartPanel());
    }

    class ChartPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.drawString("Pendapatan Hari Ini", 120, 30);

            int barHeight = Math.min(total / 1000, 150);

            g.setColor(Color.BLUE);
            g.fillRect(150, 200 - barHeight, 100, barHeight);

            g.setColor(Color.BLACK);
            g.drawRect(150, 200 - barHeight, 100, barHeight);

            g.drawString("Rp " + total, 155, 220);
        }
    }
}
