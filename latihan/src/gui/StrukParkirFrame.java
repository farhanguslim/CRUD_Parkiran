package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PrinterException;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StrukParkirFrame extends JFrame {

    private JTextArea area;

    public StrukParkirFrame(
            String noPlat,
            String jenis,
            String jamMasuk,
            String jamKeluar,
            int biaya
    ) {

        setTitle("Struk Parkir");
        setSize(350, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));

        area.setText(generateStruk(
                noPlat, jenis, jamMasuk, jamKeluar, biaya
        ));

        JScrollPane scroll = new JScrollPane(area);

        JButton btnPrint = new JButton("Print");
        JButton btnSave = new JButton("Simpan TXT");
        JButton btnClose = new JButton("Tutup");

        JPanel panel = new JPanel();
        panel.add(btnPrint);
        panel.add(btnSave);
        panel.add(btnClose);

        add(scroll, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        // ===== ACTION =====
        btnPrint.addActionListener(e -> printStruk());
        btnSave.addActionListener(e -> saveStruk());
        btnClose.addActionListener(e -> dispose());
    }

    private String generateStruk(
            String noPlat,
            String jenis,
            String jamMasuk,
            String jamKeluar,
            int biaya
    ) {
        return
                "===== STRUK PARKIR GEDUNG =====\n" +
                        "Tanggal   : " + now() + "\n" +
                        "-------------------------------\n" +
                        "No Plat   : " + noPlat + "\n" +
                        "Jenis     : " + jenis + "\n" +
                        "Masuk     : " + jamMasuk + "\n" +
                        "Keluar    : " + jamKeluar + "\n" +
                        "-------------------------------\n" +
                        "Total Bayar: Rp " + biaya + "\n" +
                        "-------------------------------\n" +
                        "Terima kasih\n" +
                        "Hati-hati di jalan\n";
    }

    private String now() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }

    private void printStruk() {
        try {
            area.print();
        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(this,
                    "Gagal print: " + e.getMessage());
        }
    }

    private void saveStruk() {
        try {
            FileWriter fw = new FileWriter(
                    "struk_" + System.currentTimeMillis() + ".txt"
            );
            fw.write(area.getText());
            fw.close();

            JOptionPane.showMessageDialog(this,
                    "Struk berhasil disimpan");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Gagal simpan: " + e.getMessage());
        }
    }
}
