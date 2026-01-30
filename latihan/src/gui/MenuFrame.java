package gui;

import dao.DataParkirDAO;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class MenuFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private User user;

    public MenuFrame(User user) {
        this.user = user;

        setTitle("Sistem Parkir Gedung - Login sebagai " + user.getRole());
        setSize(1150, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ================= TABLE =================
        String[] kolom = {
                "NO_PLAT",
                "JENIS",
                "JAM_MASUK",
                "JAM_KELUAR",
                "BIAYA",
                "STATUS"
        };

        model = new DefaultTableModel(kolom, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // ================= BUTTON UMUM =================
        JButton btnRefresh = new JButton("Refresh");
        JButton btnMasuk = new JButton("Parkir Masuk");
        JButton btnKeluar = new JButton("Parkir Keluar");
        JButton btnLogout = new JButton("Logout");

        // ================= BUTTON ADMIN =================
        JButton btnHapusTotal = new JButton("Hapus Data Kendaraan");
        JButton btnUpdateJenis = new JButton("Update Jenis Kendaraan");
        JButton btnChart = new JButton("Rekap Pendapatan");
        JButton btnExport = new JButton("Export CSV");
        JButton btnBulkDelete = new JButton("Bulk Delete");

        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelButton.add(btnRefresh);
        panelButton.add(btnMasuk);
        panelButton.add(btnKeluar);

        // ================= ROLE CONTROL =================
        if (user.getRole().equalsIgnoreCase("ADMIN")) {
            panelButton.add(btnHapusTotal);
            panelButton.add(btnUpdateJenis);
            panelButton.add(btnChart);
            panelButton.add(btnExport);
            panelButton.add(btnBulkDelete);
        }

        panelButton.add(btnLogout);

        add(scrollPane, BorderLayout.CENTER);
        add(panelButton, BorderLayout.SOUTH);

        // ================= ACTION =================
        btnRefresh.addActionListener(e -> loadData());

        btnMasuk.addActionListener(e ->
                new ParkirMasukFrame().setVisible(true)
        );

        btnKeluar.addActionListener(e ->
                new ParkirKeluarFrame().setVisible(true)
        );

        // ===== ADMIN ACTION =====
        btnHapusTotal.addActionListener(e ->
                new HapusParkirFrame().setVisible(true)
        );

        btnUpdateJenis.addActionListener(e ->
                new UpdateJenisFrame().setVisible(true)
        );

        btnChart.addActionListener(e ->
                new ChartPendapatanFrame().setVisible(true)
        );

        btnExport.addActionListener(e ->
                new ExportCSVFrame().setVisible(true)
        );

        btnBulkDelete.addActionListener(e ->
                new BulkDeleteFrame().setVisible(true)
        );

        btnLogout.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        // LOAD DATA PERTAMA KALI
        loadData();
    }

    // ================= LOAD DATA PARKIR =================
    private void loadData() {
        model.setRowCount(0);

        DataParkirDAO dao = new DataParkirDAO();

        try {
            ResultSet rs = dao.getAllResultSet();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("NO_PLAT"),
                        rs.getString("JENIS"),
                        rs.getString("JAM_MASUK"),
                        rs.getString("JAM_KELUAR"),
                        rs.getInt("BIAYA"),
                        rs.getString("STATUS")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
