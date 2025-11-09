package utils;

import java.sql.Connection;
import java.sql.DriverManager; 
import java.sql.SQLException;

public class ConnectionFactory {
    
    public static Connection getConexao () {

        Connection conexao = null;

        try {
        Class. forName ("com.mysql.cj.jdbc.Driver");
        
        String url = "jdbc:mysql://localhost/exemplomvcyoutube";
        

        conexao = DriverManager. getConnection (url, "root","root");
        

    } catch (SQLException ex) {
     
        System.out.print ("Erro ao abrir a conexao");
        throw new RuntimeException ("Erro ao abrir conexão", ex);
        
    }catch (ClassNotFoundException ex) {
        System.out.print ("Erro ao abrir a conexao");
        throw new RuntimeException ("Erro ao registrar driver do JDBC", ex);
    }
    return conexao;
}
}