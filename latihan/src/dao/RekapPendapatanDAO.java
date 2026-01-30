package dao;

import config.DatabaseConnection;
import java.sql.*;

public class RekapPendapatanDAO {

    // ================= UNTUK GUI (CHART) =================
    public int getTotalPendapatanHariIni() throws Exception {

        String sql =
                "SELECT NVL(SUM(TOTAL_BIAYA),0) AS TOTAL " +
                        "FROM PARKIR_KELUAR " +
                        "WHERE TRUNC(JAM_KELUAR) = TRUNC(SYSDATE)";

        Statement st = DatabaseConnection.getConnection().createStatement();
        ResultSet rs = st.executeQuery(sql);

        if (rs.next()) {
            return rs.getInt("TOTAL");
        }
        return 0;
    }

    // ================= UNTUK CONSOLE (LAMA) =================
    public void rekapHariIni() throws Exception {

        int total = getTotalPendapatanHariIni();

        System.out.println("\n===== REKAP PENDAPATAN HARI INI =====");
        System.out.println("Total Pendapatan : Rp " + total);
    }
}
