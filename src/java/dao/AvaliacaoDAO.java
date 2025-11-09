package dao;

import model.Avaliacao;
import utils.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO {

    public void inserir(Avaliacao a) {
        final String sql = "INSERT INTO avaliacao (idProduto, nomeUsuario, comentario, nota) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, a.getIdProduto());
            stmt.setString(2, a.getNomeUsuario());
            stmt.setString(3, a.getComentario());
            stmt.setInt(4, a.getNota());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Avaliacao> listarPorProduto(int idProduto) {
        List<Avaliacao> lista = new ArrayList<>();
        final String sql = "SELECT * FROM avaliacao WHERE idProduto=? ORDER BY idAvaliacao DESC";

        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProduto);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Avaliacao a = new Avaliacao();
                    a.setIdAvaliacao(rs.getInt("idAvaliacao"));
                    a.setIdProduto(rs.getInt("idProduto"));
                    a.setNomeUsuario(rs.getString("nomeUsuario"));
                    a.setComentario(rs.getString("comentario"));
                    a.setNota(rs.getInt("nota"));
                    lista.add(a);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Avaliacao> listarTodas() {
        List<Avaliacao> lista = new ArrayList<>();
        final String sql = "SELECT a.*, p.nomeProduto FROM avaliacao a " +
                           "LEFT JOIN Produto p ON a.idProduto = p.idProduto " +
                           "ORDER BY a.idAvaliacao DESC";

        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Avaliacao a = new Avaliacao();
                a.setIdAvaliacao(rs.getInt("idAvaliacao"));
                a.setIdProduto(rs.getInt("idProduto"));
                a.setNomeUsuario(rs.getString("nomeUsuario"));
                a.setComentario(rs.getString("comentario"));
                a.setNota(rs.getInt("nota"));
                a.setNomeProduto(rs.getString("nomeProduto"));
                lista.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
