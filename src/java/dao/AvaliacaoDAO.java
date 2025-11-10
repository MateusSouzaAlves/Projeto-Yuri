package dao;

import model.Avaliacao;
import utils.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO {

    public void inserir(Avaliacao avaliacao) {
        final String sql =
                "INSERT INTO avaliacao (idProduto, nomeUsuario, comentario, nota) VALUES (?, ?, ?, ?)";

        try (Connection connection = ConnectionFactory.getConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, avaliacao.getIdProduto());
            preparedStatement.setString(2, avaliacao.getNomeUsuario());
            preparedStatement.setString(3, avaliacao.getComentario());
            preparedStatement.setInt(4, avaliacao.getNota());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Avaliacao avaliacao) {
        final String sql =
                "UPDATE avaliacao SET idProduto = ?, nomeUsuario = ?, comentario = ?, nota = ? WHERE idAvaliacao = ?";

        try (Connection connection = ConnectionFactory.getConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, avaliacao.getIdProduto());
            preparedStatement.setString(2, avaliacao.getNomeUsuario());
            preparedStatement.setString(3, avaliacao.getComentario());
            preparedStatement.setInt(4, avaliacao.getNota());
            preparedStatement.setInt(5, avaliacao.getIdAvaliacao());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluir(int idAvaliacao) {
        final String sql = "DELETE FROM avaliacao WHERE idAvaliacao = ?";

        try (Connection connection = ConnectionFactory.getConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idAvaliacao);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Avaliacao buscarPorId(int idAvaliacao) {
        final String sql =
                "SELECT a.*, p.nomeProduto FROM avaliacao a " +
                "LEFT JOIN Produto p ON a.idProduto = p.idProduto " +
                "WHERE a.idAvaliacao = ?";

        try (Connection connection = ConnectionFactory.getConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idAvaliacao);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Avaliacao avaliacao = new Avaliacao();
                    avaliacao.setIdAvaliacao(resultSet.getInt("idAvaliacao"));
                    avaliacao.setIdProduto(resultSet.getInt("idProduto"));
                    avaliacao.setNomeUsuario(resultSet.getString("nomeUsuario"));
                    avaliacao.setComentario(resultSet.getString("comentario"));
                    avaliacao.setNota(resultSet.getInt("nota"));
                    avaliacao.setNomeProduto(resultSet.getString("nomeProduto"));
                    return avaliacao;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Avaliacao> listarPorProduto(int idProduto) {
        List<Avaliacao> lista = new ArrayList<>();
        final String sql =
                "SELECT a.*, p.nomeProduto FROM avaliacao a " +
                "LEFT JOIN Produto p ON a.idProduto = p.idProduto " +
                "WHERE a.idProduto = ? " +
                "ORDER BY a.idAvaliacao DESC";

        try (Connection connection = ConnectionFactory.getConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idProduto);

            try (ResultSet rs = preparedStatement.executeQuery()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Avaliacao> listarTodas() {
        List<Avaliacao> lista = new ArrayList<>();
        final String sql =
                "SELECT a.*, p.nomeProduto FROM avaliacao a " +
                "LEFT JOIN Produto p ON a.idProduto = p.idProduto " +
                "ORDER BY a.idAvaliacao DESC";

        try (Connection connection = ConnectionFactory.getConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

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
