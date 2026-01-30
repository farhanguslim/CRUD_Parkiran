package dao;

import config.DatabaseConnection;
import java.sql.*;

public class ParkirMasukDAO {

    public void insert(String noPlat) throws Exception {
        String sql =
                "INSERT INTO PARKIR_MASUK (ID_MASUK, NO_PLAT, JAM_MASUK) " +
                        "VALUES (SEQ_PARKIR_MASUK.NEXTVAL, ?, SYSDATE)";

        PreparedStatement ps =
                DatabaseConnection.getConnection().prepareStatement(sql);
        ps.setString(1, noPlat);
        ps.executeUpdate();
    }
}
