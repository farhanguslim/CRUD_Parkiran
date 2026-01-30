package gui;

import dao.DataParkirDAO;

import javax.swing.*;
import java.awt.*;

public class HapusParkirFrame extends JFrame {

    public HapusParkirFrame() {

        setTitle("Hapus Data Kendaraan (Total)");
        setSize(400, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JTextField txtPlat = new JTextField();
        JButton btnHapus = new JButton("HAPUS TOTAL");

        setLayout(new GridLayout(4,1,10,10));
        add(new JLabel("No Plat Kendaraan"));
        add(txtPlat);
        add(new JLabel("⚠️ Data akan dihapus PERMANEN"));
        add(btnHapus);

        btnHapus.addActionListener(e -> {

            String plat = txtPlat.getText().trim().toUpperCase();

            if (plat.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No plat wajib diisi");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "HAPUS TOTAL data kendaraan " + plat + "?\n" +
                            "Data parkir & kendaraan akan hilang!",
                    "PERINGATAN",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) return;

            try {
                DataParkirDAO dao = new DataParkirDAO();
                dao.deleteTotalByNoPlat(plat);

                JOptionPane.showMessageDialog(this,
                        "✅ Data kendaraan " + plat + " berhasil dihapus TOTAL");

                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
