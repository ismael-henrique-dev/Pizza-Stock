package Services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.prefs.Preferences;
import DAO.Conexao;

public class AdminSession {

    private static final String PREF_KEY_ADMIN_ID = "admin_id";
    private static Preferences prefs = Preferences.userRoot().node(AdminSession.class.getName());

    // Salvar ID do Admin localmente
    public static void saveAdminId(int idAdmin) {
        prefs.putInt(PREF_KEY_ADMIN_ID, idAdmin);
        System.out.println("ID do Admin salvo localmente: " + idAdmin);
    }

    // Recuperar ID salvo localmente
    public static Integer getLocalAdminId() {
        int idAdmin = prefs.getInt(PREF_KEY_ADMIN_ID, -1);
        return (idAdmin == -1) ? null : idAdmin;
    }

    // Verificar se ID local é válido comparando com a sessão ativa no banco
    public static boolean isSessionValid() {
        Integer localAdminId = getLocalAdminId();

        if (localAdminId == null) {
            System.out.println("Nenhum ID de admin armazenado localmente.");
            return false;
        }

        String sql = "SELECT COUNT(*) AS total FROM tbSessions WHERE idAdmin = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, localAdminId);
            rs = ps.executeQuery();

            if (rs.next() && rs.getInt("total") > 0) {
                System.out.println("Sessão válida para o Admin ID: " + localAdminId);
                return true;
            } else {
                System.out.println("Sessão inválida para o Admin ID: " + localAdminId);
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao verificar sessão.");
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Retornar o ID do Admin validado
    public static Integer getValidatedAdminId() {
        if (isSessionValid()) {
            return getLocalAdminId();
        } else {
            return null; 
        }
    }

    public static void clearSession() {
        String sql = "DELETE FROM tbSessions WHERE idAdmin = ?";
        Integer localAdminId = getLocalAdminId();
    
        if (localAdminId == null) {
            System.out.println("Nenhum admin está logado.");
            return;
        }
    
        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setInt(1, localAdminId);
            int rowsAffected = ps.executeUpdate();
    
            if (rowsAffected > 0) {
                System.out.println("Sessão do Admin ID " + localAdminId + " removida com sucesso.");
            } else {
                System.out.println("Nenhuma sessão encontrada para o Admin ID " + localAdminId);
            }
    
            // Limpar ID local após a remoção da sessão
            prefs.remove(PREF_KEY_ADMIN_ID);
            System.out.println("ID do Admin removido localmente.");
    
        } catch (SQLException e) {
            System.out.println("Erro ao fazer logout: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}
