import dao.*;
import model.User;
import java.util.Scanner;

public class MainMenu {

    // ================= HELPER =================

    static int inputMenu(Scanner sc, String label) {
        while (true) {
            System.out.print(label);
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("❌ Masukkan angka yang valid");
            }
        }
    }

    static String inputText(Scanner sc, String label) {
        System.out.print(label);
        return sc.nextLine().trim();
    }

    static String inputPlat(Scanner sc, String label) {
        while (true) {
            System.out.print(label);
            String plat = sc.nextLine().trim().toUpperCase().replaceAll("\\s+", " ");
            if (plat.length() >= 3) return plat;
            System.out.println("❌ Plat tidak valid");
        }
    }

    static String inputJenis(Scanner sc, String label) {
        while (true) {
            System.out.print(label);
            String jenis = sc.nextLine().trim();
            if (jenis.equalsIgnoreCase("motor") || jenis.equalsIgnoreCase("mobil"))
                return jenis;
            System.out.println("❌ Jenis harus Motor atau Mobil");
        }
    }

    static boolean konfirmasi(Scanner sc, String pesan) {
        System.out.print(pesan + " (ya/tidak): ");
        return sc.nextLine().equalsIgnoreCase("ya");
    }

    static boolean isAdmin(User u) {
        return u.getRole().equals("ADMIN");
    }

    // ================= MAIN =================

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        UserDAO userDAO = new UserDAO();
        KendaraanDAO kendaraanDAO = new KendaraanDAO();
        ParkirMasukDAO masukDAO = new ParkirMasukDAO();
        ParkirKeluarDAO keluarDAO = new ParkirKeluarDAO();
        DataParkirDAO dataDAO = new DataParkirDAO();
        RekapPendapatanDAO rekapDAO = new RekapPendapatanDAO();
        ExportCSVDAO exportDAO = new ExportCSVDAO();
        BulkDeleteDAO bulkDAO = new BulkDeleteDAO();

        // ===== LOOP LOGIN =====
        while (true) {

            User user = null;
            System.out.println("\n===== LOGIN SISTEM PARKIR =====");

            while (user == null) {
                String u = inputText(sc, "Username: ");
                String p = inputText(sc, "Password: ");

                try {
                    user = userDAO.login(u, p);
                    if (user == null) {
                        System.out.println("❌ Login gagal");
                    }
                } catch (Exception e) {
                    System.out.println("⚠️ " + e.getMessage());
                }
            }

            System.out.println("✅ Login sebagai " + user.getRole());

            int pilih;
            do {
                System.out.println("\n===== MENU UTAMA =====");
                System.out.println("1. Data Parkir");
                System.out.println("2. Parkir Masuk");
                System.out.println("3. Parkir Keluar");

                if (isAdmin(user)) {
                    System.out.println("4. Hapus Data Parkir (per plat)");
                    System.out.println("5. Update Jenis Kendaraan");
                    System.out.println("6. Rekap Pendapatan");
                    System.out.println("7. Export CSV");
                    System.out.println("8. Hapus Data Kendaraan Sekaligus");
                }

                System.out.println("0. Logout");
                pilih = inputMenu(sc, "Pilih: ");

                try {
                    switch (pilih) {

                        case 1:
                            dataDAO.tampilkanSemua();
                            break;

                        case 2:
                            System.out.println("\n--- PARKIR MASUK ---");
                            String platMasuk = inputPlat(sc, "No Plat: ");
                            String jenis = inputJenis(sc, "Jenis (Motor/Mobil): ");

                            if (kendaraanDAO.exists(platMasuk))
                                kendaraanDAO.updateJenis(platMasuk, jenis);
                            else
                                kendaraanDAO.insert(platMasuk, jenis);

                            masukDAO.insert(platMasuk);
                            System.out.println("✅ Parkir masuk berhasil");
                            break;

                        case 3:
                            System.out.println("\n--- PARKIR KELUAR ---");
                            String platKeluar = inputPlat(sc, "No Plat: ");
                            int biaya = keluarDAO.getBiayaByNoPlat(platKeluar);
                            System.out.println("💰 Biaya: Rp " + biaya);

                            if (konfirmasi(sc, "Sudah dibayar")) {
                                keluarDAO.insertByNoPlat(platKeluar);
                                System.out.println("✅ Parkir keluar berhasil");
                            }
                            break;

                        case 4:
                            if (isAdmin(user)) {
                                String hapus = inputPlat(sc, "No Plat: ");
                                if (konfirmasi(sc, "Yakin hapus data parkir")) {
                                    keluarDAO.deleteLaporanByNoPlat(hapus);
                                    System.out.println("🗑️ Data parkir dihapus");
                                }
                            }
                            break;

                        case 5:
                            if (isAdmin(user)) {
                                kendaraanDAO.updateJenis(
                                        inputPlat(sc, "No Plat: "),
                                        inputJenis(sc, "Jenis baru: ")
                                );
                                System.out.println("✅ Jenis diupdate");
                            }
                            break;

                        case 6:
                            if (isAdmin(user)) {
                                rekapDAO.rekapHariIni();
                            }
                            break;

                        case 7:
                            if (isAdmin(user)) {
                                exportDAO.exportDataParkir();
                                exportDAO.exportRekapPendapatan();
                            }
                            break;

                        case 8:
                            if (isAdmin(user)) {
                                System.out.println("\n--- HAPUS DATA SEKALIGUS ---");
                                System.out.println("1. Hapus kendaraan yang sudah keluar");
                                System.out.println("2. RESET semua data parkir");
                                System.out.println("0. Batal");

                                int h = inputMenu(sc, "Pilih: ");

                                if (h == 1 && konfirmasi(sc, "Yakin hapus kendaraan yang sudah keluar")) {
                                    bulkDAO.hapusKendaraanSudahKeluar();
                                    System.out.println("🧹 Data kendaraan sudah keluar dihapus");
                                }
                                else if (h == 2 && konfirmasi(sc, "YAKIN reset SEMUA data")) {
                                    bulkDAO.resetSemuaData();
                                    System.out.println("🔥 Semua data parkir direset");
                                }
                            }
                            break;

                        case 0:
                            System.out.println("👋 Logout");
                            break;
                    }

                } catch (Exception e) {
                    System.out.println("⚠️ " + e.getMessage());
                }

            } while (pilih != 0);
        }
    }
}
