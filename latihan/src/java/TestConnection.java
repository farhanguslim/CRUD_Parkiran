import config.DatabaseConnection;

public class TestConnection {
    public static void main(String[] args) {
        if (DatabaseConnection.getConnection() != null) {
            System.out.println("Koneksi BERHASIL");
        } else {
            System.out.println("Koneksi GAGAL");
        }
    }
}
