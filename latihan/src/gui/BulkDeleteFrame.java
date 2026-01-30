package gui;

import dao.BulkDeleteDAO;

import javax.swing.*;

public class BulkDeleteFrame extends JFrame {

    public BulkDeleteFrame() {

        setTitle("Hapus Data Kendaraan Sekaligus");
        setSize(400,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JButton btnHapusKeluar = new JButton("Hapus Kendaraan Sudah Keluar");
        JButton btnReset = new JButton("RESET SEMUA DATA");

        JPanel panel = new JPanel();
        panel.add(btnHapusKeluar);
        panel.add(btnReset);
        add(panel);

        btnHapusKeluar.addActionListener(e -> {
            int ok = JOptionPane.showConfirmDialog(this,
                    "Yakin hapus semua kendaraan yang sudah keluar?",
                    "Konfirmasi",
                    JOptionPane.YES_NO_OPTION);

            if (ok != JOptionPane.YES_OPTION) return;

            try {
                BulkDeleteDAO dao = new BulkDeleteDAO();
                dao.hapusKendaraanSudahKeluar();
                JOptionPane.showMessageDialog(this, "Data dihapus");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        btnReset.addActionListener(e -> {
            int ok = JOptionPane.showConfirmDialog(this,
                    "INI AKAN MENGHAPUS SEMUA DATA!",
                    "PERINGATAN",
                    JOptionPane.YES_NO_OPTION);

            if (ok != JOptionPane.YES_OPTION) return;

            try {
                BulkDeleteDAO dao = new BulkDeleteDAO();
                dao.resetSemuaData();
                JOptionPane.showMessageDialog(this, "SEMUA DATA DIRESET");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
    }
}
