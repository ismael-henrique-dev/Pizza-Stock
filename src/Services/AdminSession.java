package Services;

import java.util.prefs.Preferences;

public class AdminSession {
    private static final String ADMIN_ID_KEY = "admin_id";
    private static Preferences prefs = Preferences.userNodeForPackage(AdminSession.class);

    public static void setIdAdmin(int id) {
        prefs.putInt(ADMIN_ID_KEY, id);
    }

    public static int getIdAdmin() {
        return prefs.getInt(ADMIN_ID_KEY, -1); // Retorna -1 se não houver usuário logado
    }

    public static boolean isLoggedIn() {
        return getIdAdmin() > 0;
    }

    public static void clearSession() {
        prefs.remove(ADMIN_ID_KEY);
    }
}
