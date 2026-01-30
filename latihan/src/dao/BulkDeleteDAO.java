package dao;

import config.DatabaseConnection;
import java.sql.Statement;

public class BulkDeleteDAO {

    // ================= HAPUS KENDARAAN YANG SUDAH KELUAR =================
    public void hapusKendaraanSudahKeluar() throws Exception {

        Statement st = DatabaseConnection.getConnection().createStatement();

        // 1. Hapus parkir keluar
        st.executeUpdate("DELETE FROM PARKIR_KELUAR");

        // 2. Hapus parkir masuk yang tidak aktif
        st.executeUpdate(
                "DELETE FROM PARKIR_MASUK " +
                        "WHERE ID_MASUK NOT IN (" +
                        "   SELECT DISTINCT ID_MASUK FROM PARKIR_KELUAR" +
                        ")"
        );

        // 3. Hapus kendaraan yang tidak punya parkir aktif
        st.executeUpdate(
                "DELETE FROM KENDARAAN " +
                        "WHERE NO_PLAT NOT IN (" +
                        "   SELECT DISTINCT NO_PLAT FROM PARKIR_MASUK" +
                        ")"
        );
    }

    // ================= RESET SEMUA DATA =================
    public void resetSemuaData() throws Exception {

        Statement st = DatabaseConnection.getConnection().createStatement();

        st.executeUpdate("DELETE FROM PARKIR_KELUAR");
        st.executeUpdate("DELETE FROM PARKIR_MASUK");
        st.executeUpdate("DELETE FROM KENDARAAN");
    }
}
