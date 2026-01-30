package dao;

import config.DatabaseConnection;
import model.User;
import java.sql.*;

public class UserDAO {

    public User login(String username, String password) throws Exception {

        String sql =
                "SELECT USERNAME, ROLE FROM USERS " +
                        "WHERE USERNAME = ? AND PASSWORD = ?";

        PreparedStatement ps =
                DatabaseConnection.getConnection().prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new User(
                    rs.getString("USERNAME"),
                    rs.getString("ROLE")
            );
        }


        return null;
    }
}
