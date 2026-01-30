package gui;

import dao.KendaraanDAO;
import dao.ParkirMasukDAO;

import javax.swing.*;
import java.awt.*;

public class ParkirMasukFrame extends JFrame {

    private JTextField txtPlat;
    private JRadioButton rbMotor, rbMobil;
    private JButton btnSimpan, btnBatal;

    public ParkirMasukFrame() {

        setTitle("Parkir Masuk");
        setSize(350, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ===== PANEL =====
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("No Plat"));
        txtPlat = new JTextField();
        panel.add(txtPlat);

        panel.add(new JLabel("Jenis Kendaraan"));
        JPanel panelRadio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rbMotor = new JRadioButton("Motor");
        rbMobil = new JRadioButton("Mobil");

        ButtonGroup group = new ButtonGroup();
        group.add(rbMotor);
        group.add(rbMobil);

        panelRadio.add(rbMotor);
        panelRadio.add(rbMobil);

        panel.add(panelRadio);

        btnSimpan = new JButton("Simpan");
        btnBatal = new JButton("Batal");

        panel.add(btnSimpan);
        panel.add(btnBatal);

        add(panel);

        // ===== ACTION =====
        btnSimpan.addActionListener(e -> simpan());
        btnBatal.addActionListener(e -> dispose());
    }

    private void simpan() {
        String noPlat = txtPlat.getText()
                .trim()
                .toUpperCase()
                .replaceAll("\\s+", " ");

        String jenis = null;
        if (rbMotor.isSelected()) jenis = "Motor";
        if (rbMobil.isSelected()) jenis = "Mobil";

        if (noPlat.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No Plat wajib diisi");
            return;
        }

        if (jenis == null) {
            JOptionPane.showMessageDialog(this,
                    "Pilih jenis kendaraan");
            return;
        }

        try {
            KendaraanDAO kendaraanDAO = new KendaraanDAO();
            ParkirMasukDAO masukDAO = new ParkirMasukDAO();

            // kalau kendaraan sudah ada → update jenis
            if (kendaraanDAO.exists(noPlat)) {
                kendaraanDAO.updateJenis(noPlat, jenis);
            } else {
                kendaraanDAO.insert(noPlat, jenis);
            }

            masukDAO.insert(noPlat);

            JOptionPane.showMessageDialog(this,
                    "Parkir masuk berhasil");

            dispose(); // tutup form

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
