package dao;

import java.sql.*;
import model.Usuario;
import utils.ConnectionFactory;

public class UsuarioDAO {
    public Usuario autenticar(String email, String senha) {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuario WHERE email=? AND senha=?";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }
}
