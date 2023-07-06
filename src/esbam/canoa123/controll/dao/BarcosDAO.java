package esbam.canoa123.controll.dao;

import esbam.canoa123.controll.dao.classeabstract.BaseDAO;
import esbam.canoa123.controll.dao.exceptions.ExceptionDAO;
import esbam.canoa123.model.Barcos;
import java.sql.ResultSet;

/**
 * Classe para efetuar comandos SQL da entidade Barcos do banco de dados.Nessa classe foi refatorado no 6º mês para acrescentar alguns métodos de consultas.
 * Os métodos são protegidos, para o acessos aos métodos da classe deve-se acessar apartir da classe Barcos do pacote Model.
 * @author Jonny Nunes Viana
 * @version 1.1
 * @param <T>
 */

public class BarcosDAO<T extends Barcos> extends BaseDAO<T>{    
    String sql;
    java.sql.Connection con;
    java.sql.PreparedStatement st;
    java.sql.ResultSet rSet;

    // ********************************************************************************************
    private void restabeleceConexao(){
        con = new esbam.canoa123.controll.dao.conexao.ConnectionMVC().getConnetion();
    }
    
    private void fechaConexao(java.sql.Connection conn, java.sql.PreparedStatement psp){
        new esbam.canoa123.controll.dao.conexao.ConnectionMVC().closeConnection(conn, psp);
    }
    
    private void fechaConexao(java.sql.Connection conn, java.sql.PreparedStatement stt, java.sql.ResultSet rset){
        new esbam.canoa123.controll.dao.conexao.ConnectionMVC().closeConnection(conn, stt, rset);
    }
    
    // ********************************************************************************************
    
    @Override
    public void inseri(T obj) throws ExceptionDAO {
        restabeleceConexao();
        String nome = obj.getNome();
        int capacidade = obj.getCapacidade();
        sql = "INSERT INTO barco (nome, capacidade) VALUES (?,?)";
        try{            
            st = con.prepareStatement(sql); //
            st.setString(1, nome);
            st.setInt(2, capacidade);
            st.execute();
        }catch(java.sql.SQLException e){
            e.printStackTrace();
            fechaConexao(con, st);
            throw new ExceptionDAO("Erro em fazer a inserção do barco");
        }finally{
            fechaConexao(con, st);
        }
    }
    
    @Override
    public void altera(T obj) throws ExceptionDAO {
        restabeleceConexao();
        int id = obj.getId();
        String nome = obj.getNome();
        int capacidade = obj.getCapacidade();
        sql = "update barco set nome = ?, capacidade = ? where id = ?";
        try {
            st = con.prepareStatement(sql);
            st.setString(1, nome);
            st.setInt(2, capacidade);
            st.setInt(3, id);
            st.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            fechaConexao(con, st);
            throw new ExceptionDAO("Erro em alterar o barco");
        }finally{
            fechaConexao(con, st);
        }
    }
    
    @Override
    public void deleta(T obj) throws ExceptionDAO {
        restabeleceConexao();
        sql = "delete from barco where id = ?";
        int id = obj.getId(); 
        try {
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            st.execute();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            fechaConexao(con, st);
            throw new ExceptionDAO("Erro em deletar o barco");
        }finally{
            fechaConexao(con, st);
        }
    }

    @Override
    public ResultSet consulta(int id) throws ExceptionDAO {
        restabeleceConexao();
        sql = "select * from barco where id = ?";
        try {
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            rSet = st.executeQuery();
            if(rSet.next()){
                return rSet;
            }
        } catch (java.sql.SQLException e) {
            fechaConexao(con, st);
            e.printStackTrace();
            throw new ExceptionDAO("Erro em consultar o barco pelo id especificado");
        }
        fechaConexao(con, st, rSet);
        return null;
    }

    @Override
    public ResultSet lista() throws ExceptionDAO { 
        restabeleceConexao();
        sql = "SELECT * FROM barco";
        try {
            st = con.prepareStatement(sql);
            rSet = st.executeQuery();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            fechaConexao(con, st, rSet);
            throw new ExceptionDAO("Erro em listar os barcos");
        }
        return rSet;
    }

    // *********************************************************************
    
    /**
     * 
     * @param capacidade
     * @return
     * @throws ExceptionDAO 
     */
    public ResultSet listarCapacidade(int capacidade) throws ExceptionDAO{
        restabeleceConexao();
        sql = "SELECT * FROM barco WHERE capacidade = ?";
        try {
            st = con.prepareStatement(sql);
            st.setInt(1, capacidade);
            rSet = st.executeQuery();
        } catch (java.sql.SQLException e) {
            fechaConexao(con, st, rSet);
            e.printStackTrace();
            throw new ExceptionDAO("Erro em listar barcos pela capacidade");
        }
        return rSet;
    }
    
    /**
     * 
     * @param nome
     * @param capacidade
     * @return
     * @throws ExceptionDAO 
     */
    public ResultSet listaNomeCapacidade(String nome, int capacidade) throws ExceptionDAO{
        restabeleceConexao();
        sql = "select * from barco where nome like ? and capacidade = ?";       
        try {
            st = con.prepareStatement(sql);
            st.setString(1, "%"+nome+"%");
            st.setInt(2, capacidade);
            rSet = st.executeQuery();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            fechaConexao(con, st, rSet);
            throw new ExceptionDAO("Erro em lista os barcos pelo nome e capacidade");
        }
        return rSet;
    }
    
    /**
     * 
     * @param nome
     * @return
     * @throws ExceptionDAO 
     */
    public ResultSet listaNome(String nome) throws ExceptionDAO{
        restabeleceConexao();
        sql = "select * from barco where nome like ?";
        try {
            st = con.prepareStatement(sql);
            st.setString(1, "%"+nome+"%");
            rSet = st.executeQuery();            
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            fechaConexao(con, st, rSet);
            throw new ExceptionDAO("Erro em listar os barcos pelo nome");
        }
        return rSet;
    }
}
