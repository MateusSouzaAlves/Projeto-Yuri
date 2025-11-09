package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;

import model.Produto;
import utils.ConnectionFactory;

public class ProdutoDAO {
    
    public boolean inserir (Produto objProduto) {
            boolean retorno = false;
            
            String sql = "INSERT INTO Produto (nomeProduto, qtdProduto, valorProduto) VALUES (?,?,?)";
            
            try (
                    Connection conexao = ConnectionFactory.getConexao();
                    PreparedStatement comando = conexao.prepareStatement(sql);
            ){
                    comando.setString (1, objProduto.getNomeProduto());
                    comando.setInt(2,objProduto.getQtdProduto());
                    comando.setFloat (3, objProduto.getValorProduto());
                    
                    int linhaAfetadas = comando.executeUpdate () ;
                    
                    if(linhaAfetadas >0) {
                        retorno = true;
                    }
            
            }catch(SQLException ex) {
                System.out.println(ex);
                ex.printStackTrace();
            }
            
            return retorno;
    }
    
        public ArrayList<Produto> listar(){
            
            ArrayList<Produto> listaRetorno = new ArrayList<>();
            String sql = "SELECT * FROM Produto";
            
            try(
                    Connection conexao = ConnectionFactory.getConexao();
                    PreparedStatement comando = conexao.prepareStatement(sql);
                    ResultSet rs = comando.executeQuery();
            ){
                
                    while (rs.next()) {
                        
                        int idProduto = rs.getInt("idProduto");
                        String nomeProduto = rs.getString("nomeProduto");
                        int qtdProduto = rs.getInt("qtdProduto");
                        float valorProduto = rs.getFloat("valorProduto");
                        
                        Produto objLista = new Produto(idProduto, nomeProduto, qtdProduto, valorProduto);
                        
                        listaRetorno.add(objLista);
                        
                    }
                    
            }catch(SQLException e) {
                e.printStackTrace();
                
            }
            
            return listaRetorno;           
    }
        
    public Produto buscarPorId(int id){
            
            Produto objRetorno = null;
            String sql = "SELECT * FROM Produto WHERE idProduto = ?";
            
            try(
                    Connection conexao = ConnectionFactory.getConexao();
                    PreparedStatement comando = conexao.prepareStatement(sql);
            ){
                    comando.setInt(1, id);
                    ResultSet rs = comando.executeQuery();
                            
                    while (rs.next()) {
                        
                        int idProduto = rs.getInt("idProduto");
                        String nomeProduto = rs.getString("nomeProduto");
                        int qtdProduto = rs.getInt("qtdProduto");
                        float valorProduto = rs.getFloat("valorProduto");
                        
                        objRetorno = new Produto(idProduto, nomeProduto, qtdProduto, valorProduto);
                    }
                    
            }catch(SQLException e) {
                e.printStackTrace();
                
            }
            
            return objRetorno;           
    }
    
    public boolean atualizar (Produto objAtualizar) {
            
        boolean retorno = false;
        
        String sql = "UPDATE Produto SET nomeProduto =?, qtdProduto=?, valorProduto=? WHERE idProduto = ?";
            
        try(
                Connection conexao = ConnectionFactory.getConexao();
                PreparedStatement comando = conexao.prepareStatement(sql);
        ){    
            comando.setString(1, objAtualizar.getNomeProduto());
            comando.setInt(2, objAtualizar.getQtdProduto());
            comando.setFloat(3, objAtualizar.getValorProduto());
            comando.setInt(4, objAtualizar.getIdProduto());
            
            int linhasAfetadas = comando.executeUpdate();
            
            if(linhasAfetadas >0) {retorno = true;}
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return retorno;
    }
    
    public boolean excluir (int id) {
            
        boolean retorno = false;
        
        String sql = "DELETE FROM Produto WHERE idProduto = ?";
            
        try(
                Connection conexao = ConnectionFactory.getConexao();
                PreparedStatement comando = conexao.prepareStatement(sql);
        ){    
            comando. setInt(1, id);
            
            int linhasAfetadas = comando.executeUpdate();
            
            if(linhasAfetadas >0) {retorno = true;}
            
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return retorno;
    }
    
    
}

