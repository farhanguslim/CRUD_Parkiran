package dao;

import config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataParkirDAO {

    // =================================================
    // 1️⃣ TAMPILKAN DATA PARKIR (UNTUK CONSOLE)
    // DIPAKAI OLEH MainMenu.java
    // =================================================
    public void tampilkanSemua() throws Exception {

        String sql =
                "SELECT NO_PLAT, JENIS, JAM_MASUK, JAM_KELUAR, BIAYA, STATUS " +
                        "FROM V_DATA_PARKIR " +
                        "ORDER BY JAM_MASUK DESC";

        Connection conn = DatabaseConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        System.out.println("NO_PLAT | JENIS | JAM_MASUK | JAM_KELUAR | BIAYA | STATUS");

        while (rs.next()) {
            System.out.println(
                    rs.getString("NO_PLAT") + " | " +
                            rs.getString("JENIS") + " | " +
                            rs.getString("JAM_MASUK") + " | " +
                            rs.getString("JAM_KELUAR") + " | " +
                            rs.getInt("BIAYA") + " | " +
                            rs.getString("STATUS")
            );
        }
    }

    // =================================================
    // 2️⃣ AMBIL SEMUA DATA PARKIR (UNTUK GUI TABLE)
    // =================================================
    public ResultSet getAllResultSet() throws Exception {

        String sql =
                "SELECT NO_PLAT, JENIS, JAM_MASUK, JAM_KELUAR, BIAYA, STATUS " +
                        "FROM V_DATA_PARKIR " +
                        "ORDER BY JAM_MASUK DESC";

        Connection conn = DatabaseConnection.getConnection();
        Statement st = conn.createStatement();

        return st.executeQuery(sql);
    }

    // =================================================
    // 3️⃣ AMBIL DATA BERDASARKAN PLAT (UNTUK STRUK PARKIR)
    // =================================================
    public ResultSet getByNoPlat(String noPlat) throws Exception {

        String sql =
                "SELECT NO_PLAT, JENIS, JAM_MASUK, JAM_KELUAR, BIAYA, STATUS " +
                        "FROM V_DATA_PARKIR " +
                        "WHERE NO_PLAT = ?";

        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, noPlat);

        return ps.executeQuery();
    }

    // =================================================
    // 4️⃣ HAPUS TOTAL DATA KENDARAAN (PER PLAT)
    // BISA MASIH PARKIR / SUDAH KELUAR
    // =================================================
    public void deleteTotalByNoPlat(String noPlat) throws Exception {

        Connection conn = DatabaseConnection.getConnection();

        // 1️⃣ HAPUS PARKIR KELUAR
        String sqlKeluar =
                "DELETE FROM PARKIR_KELUAR " +
                        "WHERE ID_MASUK IN (" +
                        "   SELECT ID_MASUK FROM PARKIR_MASUK WHERE NO_PLAT = ?" +
                        ")";
        PreparedStatement psKeluar = conn.prepareStatement(sqlKeluar);
        psKeluar.setString(1, noPlat);
        psKeluar.executeUpdate();

        // 2️⃣ HAPUS PARKIR MASUK
        String sqlMasuk =
                "DELETE FROM PARKIR_MASUK WHERE NO_PLAT = ?";
        PreparedStatement psMasuk = conn.prepareStatement(sqlMasuk);
        psMasuk.setString(1, noPlat);
        psMasuk.executeUpdate();

        // 3️⃣ HAPUS DATA KENDARAAN
        String sqlKendaraan =
                "DELETE FROM KENDARAAN WHERE NO_PLAT = ?";
        PreparedStatement psKendaraan = conn.prepareStatement(sqlKendaraan);
        psKendaraan.setString(1, noPlat);
        psKendaraan.executeUpdate();
    }
}
