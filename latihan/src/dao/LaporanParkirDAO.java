package dao;

import config.DatabaseConnection;
import java.sql.*;

public class LaporanParkirDAO {

    public void tampilkanLaporan() throws Exception {
        String sql = "SELECT * FROM V_LAPORAN_PARKIR ORDER BY ID_KELUAR";
        Statement st = DatabaseConnection.getConnection().createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            System.out.println(
                    rs.getInt("ID_KELUAR") + " | " +
                            rs.getString("NO_PLAT") + " | " +
                            rs.getString("JAM_MASUK") + " | " +
                            rs.getString("JAM_KELUAR") + " | " +
                            rs.getInt("TOTAL_BIAYA")
            );
        }
    }
}
