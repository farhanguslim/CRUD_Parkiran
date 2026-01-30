package dao;

import config.DatabaseConnection;
import java.sql.*;

public class KendaraanDAO {

    // ===== CEK KENDARAAN =====
    public boolean exists(String noPlat) throws Exception {
        String sql = "SELECT COUNT(*) FROM KENDARAAN WHERE NO_PLAT = ?";
        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
        ps.setString(1, noPlat);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1) > 0;
    }

    // ===== CREATE =====
    public void insert(String noPlat, String jenis) throws Exception {
        String sql = "INSERT INTO KENDARAAN (NO_PLAT, JENIS) VALUES (?, ?)";
        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
        ps.setString(1, noPlat);
        ps.setString(2, jenis);
        ps.executeUpdate();
    }

    // ===== UPDATE JENIS =====
    public void updateJenis(String noPlat, String jenisBaru) throws Exception {
        String sql = "UPDATE KENDARAAN SET JENIS = ? WHERE NO_PLAT = ?";
        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
        ps.setString(1, jenisBaru);
        ps.setString(2, noPlat);

        if (ps.executeUpdate() == 0) {
            throw new Exception("Kendaraan tidak ditemukan");
        }
    }

    // ===== DELETE (AMAN) =====
    public void delete(String noPlat) throws Exception {

        String cek =
                "SELECT COUNT(*) FROM PARKIR_MASUK WHERE NO_PLAT = ?";
        PreparedStatement psCek =
                DatabaseConnection.getConnection().prepareStatement(cek);
        psCek.setString(1, noPlat);
        ResultSet rs = psCek.executeQuery();
        rs.next();

        if (rs.getInt(1) > 0) {
            throw new Exception("Kendaraan masih memiliki data parkir");
        }

        String sql = "DELETE FROM KENDARAAN WHERE NO_PLAT = ?";
        PreparedStatement ps =
                DatabaseConnection.getConnection().prepareStatement(sql);
        ps.setString(1, noPlat);
        ps.executeUpdate();
    }
}
