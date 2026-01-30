package dao;

import config.DatabaseConnection;
import java.io.FileWriter;
import java.sql.*;

public class ExportCSVDAO {

    // ================= EXPORT DATA PARKIR =================
    public void exportDataParkir() throws Exception {

        String sql = "SELECT * FROM V_DATA_PARKIR ORDER BY JAM_MASUK DESC";
        Statement st = DatabaseConnection.getConnection().createStatement();
        ResultSet rs = st.executeQuery(sql);

        FileWriter fw = new FileWriter("data_parkir.csv");

        // header
        fw.append("NO_PLAT,JENIS,JAM_MASUK,JAM_KELUAR,BIAYA,STATUS\n");

        while (rs.next()) {
            fw.append(rs.getString("NO_PLAT")).append(",");
            fw.append(rs.getString("JENIS")).append(",");
            fw.append(rs.getString("JAM_MASUK")).append(",");
            fw.append(rs.getString("JAM_KELUAR")).append(",");
            fw.append(String.valueOf(rs.getInt("TOTAL_BIAYA"))).append(",");
            fw.append(rs.getString("STATUS")).append("\n");
        }

        fw.flush();
        fw.close();

        System.out.println("✅ data_parkir.csv berhasil dibuat");
    }

    // ================= EXPORT REKAP PENDAPATAN =================
    public void exportRekapPendapatan() throws Exception {

        String sql =
                "SELECT TRUNC(JAM_KELUAR) AS TANGGAL, " +
                        "COUNT(*) AS JUMLAH_KENDARAAN, " +
                        "SUM(TOTAL_BIAYA) AS TOTAL_PENDAPATAN " +
                        "FROM PARKIR_KELUAR " +
                        "GROUP BY TRUNC(JAM_KELUAR) " +
                        "ORDER BY TANGGAL";

        Statement st = DatabaseConnection.getConnection().createStatement();
        ResultSet rs = st.executeQuery(sql);

        FileWriter fw = new FileWriter("rekap_pendapatan.csv");

        // header
        fw.append("TANGGAL,JUMLAH_KENDARAAN,TOTAL_PENDAPATAN\n");

        while (rs.next()) {
            fw.append(rs.getDate("TANGGAL").toString()).append(",");
            fw.append(String.valueOf(rs.getInt("JUMLAH_KENDARAAN"))).append(",");
            fw.append(String.valueOf(rs.getInt("TOTAL_PENDAPATAN"))).append("\n");
        }

        fw.flush();
        fw.close();

        System.out.println("✅ rekap_pendapatan.csv berhasil dibuat");
    }
}
