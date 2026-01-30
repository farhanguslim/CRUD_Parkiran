package gui;

import dao.KendaraanDAO;

import javax.swing.*;
import java.awt.*;

public class UpdateJenisFrame extends JFrame {

    public UpdateJenisFrame() {

        setTitle("Update Jenis Kendaraan");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JTextField txtPlat = new JTextField();
        JRadioButton rbMotor = new JRadioButton("Motor");
        JRadioButton rbMobil = new JRadioButton("Mobil");

        ButtonGroup bg = new ButtonGroup();
        bg.add(rbMotor);
        bg.add(rbMobil);

        JButton btnUpdate = new JButton("Update");

        setLayout(new GridLayout(5,1,10,10));
        add(new JLabel("No Plat"));
        add(txtPlat);
        add(rbMotor);
        add(rbMobil);
        add(btnUpdate);

        btnUpdate.addActionListener(e -> {
            String plat = txtPlat.getText().trim().toUpperCase();
            String jenis = rbMotor.isSelected() ? "Motor" :
                    rbMobil.isSelected() ? "Mobil" : null;

            if (plat.isEmpty() || jenis == null) {
                JOptionPane.showMessageDialog(this, "Lengkapi data");
                return;
            }

            try {
                KendaraanDAO dao = new KendaraanDAO();
                dao.updateJenis(plat, jenis);

                JOptionPane.showMessageDialog(this, "Jenis berhasil diupdate");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
    }
}
