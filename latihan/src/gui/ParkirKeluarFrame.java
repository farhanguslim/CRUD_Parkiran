package gui;

import dao.ParkirKeluarDAO;
import dao.DataParkirDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class ParkirKeluarFrame extends JFrame {

    private JTextField txtPlat;
    private JLabel lblBiaya;
    private JButton btnCek, btnKeluar, btnBatal;

    public ParkirKeluarFrame() {

        setTitle("Parkir Keluar");
        setSize(350, 230);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("No Plat"));
        txtPlat = new JTextField();
        panel.add(txtPlat);

        panel.add(new JLabel("Biaya"));
        lblBiaya = new JLabel("-");
        panel.add(lblBiaya);

        btnCek = new JButton("Cek Biaya");
        btnKeluar = new JButton("Parkir Keluar");
        btnKeluar.setEnabled(false);

        panel.add(btnCek);
        panel.add(btnKeluar);

        btnBatal = new JButton("Batal");
        panel.add(new JLabel());
        panel.add(btnBatal);

        add(panel);

        btnCek.addActionListener(e -> cekBiaya());
        btnKeluar.addActionListener(e -> parkirKeluar());
        btnBatal.addActionListener(e -> dispose());
    }

    private void cekBiaya() {
        String plat = txtPlat.getText().trim().toUpperCase();

        try {
            ParkirKeluarDAO dao = new ParkirKeluarDAO();
            int biaya = dao.getBiayaByNoPlat(plat);

            lblBiaya.setText("Rp " + biaya);
            btnKeluar.setEnabled(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void parkirKeluar() {
        int ok = JOptionPane.showConfirmDialog(this,
                "Sudah dibayar?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION);

        if (ok != JOptionPane.YES_OPTION) return;

        try {
            String plat = txtPlat.getText().trim().toUpperCase();
            ParkirKeluarDAO dao = new ParkirKeluarDAO();
            dao.insertByNoPlat(plat);

            // === AMBIL DATA UNTUK STRUK ===
            DataParkirDAO dataDAO = new DataParkirDAO();
            ResultSet rs = dataDAO.getByNoPlat(plat);

            if (rs.next()) {
                new StrukParkirFrame(
                        rs.getString("NO_PLAT"),
                        rs.getString("JENIS"),
                        rs.getString("JAM_MASUK"),
                        rs.getString("JAM_KELUAR"),
                        rs.getInt("BIAYA")
                ).setVisible(true);
            }

            JOptionPane.showMessageDialog(this,
                    "Parkir keluar berhasil");

            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}
