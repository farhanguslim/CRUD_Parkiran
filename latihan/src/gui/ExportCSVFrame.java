package gui;

import dao.ExportCSVDAO;

import javax.swing.*;

public class ExportCSVFrame extends JFrame {

    public ExportCSVFrame() {

        setTitle("Export CSV");
        setSize(300,150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JButton btnExport = new JButton("Export Data Parkir");

        add(btnExport);

        btnExport.addActionListener(e -> {
            try {
                ExportCSVDAO dao = new ExportCSVDAO();
                dao.exportDataParkir();
                dao.exportRekapPendapatan();

                JOptionPane.showMessageDialog(this,
                        "Export CSV berhasil");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
    }
}
