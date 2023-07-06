package esbam.canoa123.controll.dao.conexao;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 * Classe para conectar ao banco de dados Mysql. O driver utilizado é
 * mysql-connector-j-8.0.33.
 *
 * @author Jonny Nunes Viana
 * @version 1.1
 */
public class ConnectionMVC {

    // PASSOS:
    /*
        1 - O DRIVER DO BANCO (TIPO)
        2 - O CAMINHO DO BANCO (LOCAL OU SERVIDOR)
        3 - O USUARIO (RESPONSÁVEL PELO ACESSOS)
        4 - A SENHA DO USER (AUTENTICAR)
     */
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String BANCO = "canoa123";
    private static final String USER = "root";
    private static final String PASS = "142134#$gG66";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * Método para estabelcer a conexão com o banco de dados.
     *
     * @return a conexão caso for conectado, caso ao contrário retorna null
     */
    public static Connection getConnetion() {
        Connection conexao = null;
        // TRATAMENTO DE EXECÕES, CASO A INFRAESTRUTURA DER PANE, CONEÇÃO CAIU E MUITO OUTROS IMPREVISTO
        try {
            // ENCONTRANDO O DRIVER (A PONTE ENTRE O BANCO E JAVA)
            Class.forName(DRIVER);
        } catch (ClassNotFoundException ex) {
            // ERRO SE O DRIVER NAO FOR ENCONTRADO
            System.err.println("Driver not found!");
            // Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            // VERIFICANDO SE OS DADOS ESTAO CORRETOS
            // CONECTANDO O BANCO DE DADOS
            conexao = DriverManager.getConnection(URL.concat(BANCO), USER, PASS);
            return conexao;
        } catch (SQLException ex) {
            // ERRO DE CONECXÃO COM O BANCO
            System.err.println(ex.getCause() + " - " + ex.getMessage());
            return null;
        }
    }

    /**
     * Método para fechar a conexão do objeto da classe Connection
     * @param con Objeto de conexão da classe Connection para ser fechado
     */
    // PODE SER UMA FUNCAO VAZIA (VOID)
    // FECHAR O MEU BANCO (DESCONECTAR)
    public static void closeConnection(Connection con) {
        try {
            // SE FOR DIFERENTE QUE NULL, E PQ TEM CONEXAO
            if (con != null) {
                // FECHAR A CONEXAO
                con.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro: " + e.getSQLState());
        }
    }

    /**
     * Método para fechar as conexões do objeto da classe Connection e PreparedStatement.
     * @param con Objeto de conexão da classe Connection
     * @param psp Objeto de pré compilção de SQL da Interface PreparedStatement 
     */
    public static void closeConnection(Connection con, PreparedStatement psp) {
        closeConnection(con); // FAZENDO O FECHAMENTO (REAPROVEITANDO)
        try {
            if (psp != null) {
                psp.close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro: " + ex.getMessage());
        }
    }

    /**
     * Método para fechar as conexões do objeto da classe Connection, PreparedStatement e ResultSet. 
     * @param con Objeto de conexão da classe Connection
     * @param psp Objeto de pré compilção de SQL da Interface PreparedStatement
     * @param rSet Objeto de consulta do banco de dados da classe ResultSet
     */
    public static void closeConnection(Connection con, PreparedStatement psp, ResultSet rSet) {
        closeConnection(con, psp); // FAZENDO O FECHAMENTO (REAPROVEITANDO)
        try {
            if (rSet != null) {
                rSet.close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro: " + ex.getMessage());
        }
    }
}
