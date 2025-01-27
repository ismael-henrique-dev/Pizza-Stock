package Services;

public class AdminSession {

    private static int idAdmin;

    // Método para definir o idAdmin
    public static void setIdAdmin(int id) {
        idAdmin = id;
    }


    public static int getIdAdmin() {
        return idAdmin;
    }

    // verificar se o usuário está logado
    public static boolean isLoggedIn() {
        return idAdmin > 0;
    }


    public static void clearSession() {
        idAdmin = 0;
    }
}
