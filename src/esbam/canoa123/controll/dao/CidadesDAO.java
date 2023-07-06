package esbam.canoa123.controll.dao;

import esbam.canoa123.controll.dao.classeabstract.BaseDAO;
import esbam.canoa123.controll.dao.exceptions.ExceptionDAO;
import esbam.canoa123.model.Cidades;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Jonny Nunes Viana
 * @param <T>
 */
public class CidadesDAO<T extends Cidades> extends BaseDAO<T>{
    String sql;
    java.sql.Connection con;
    java.sql.PreparedStatement st;
    java.sql.ResultSet rSet; // RECEBER O QUE VEM NO BANCO DE DADOS
    
    private void restabeleceConexao(){
        con = new esbam.canoa123.controll.dao.conexao.ConnectionMVC().getConnetion();
    }
    
    private void fechaConexao(java.sql.Connection conn){
        new esbam.canoa123.controll.dao.conexao.ConnectionMVC().closeConnection(conn);
    }
    
    private void fechaConexao(java.sql.Connection conn, java.sql.PreparedStatement st){
        new esbam.canoa123.controll.dao.conexao.ConnectionMVC().closeConnection(conn, st);
    }
    
    private void fechaConexao(java.sql.Connection conn, java.sql.PreparedStatement st, java.sql.ResultSet rset){
        new esbam.canoa123.controll.dao.conexao.ConnectionMVC().closeConnection(conn, st, rset);
    }
    
    //*******************************************************************************************

    @Override
    public void inseri(T obj) throws ExceptionDAO {
        restabeleceConexao();
        String estado = obj.getEstado();
        String localidade = obj.getLocalidade();
        sql = "insert into cidade (localidade, estado) values (?,?)";
        try{    
            st = con.prepareStatement(sql);
            st.setString(1, localidade);
            st.setString(2, estado);
            st.execute();
        }catch(SQLException e){
            e.printStackTrace();
            fechaConexao(con, st);
            throw new ExceptionDAO("Erro ao inserir os dados da Cidade");
        }finally{
            fechaConexao(con, st);
        }
    }

    @Override
    public ResultSet consulta(int id) throws ExceptionDAO {
        restabeleceConexao();
        sql = "SELECT * FROM cidade WHERE id = ?";
        try {
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            rSet = st.executeQuery();
            if(rSet.next()){
                return rSet;
            }
        } catch (SQLException e) {
            fechaConexao(con, st, rSet);
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao consultar uma Cidade pelo id especificado");
        }
        fechaConexao(con, st, rSet);
        return null;
    }
    
    @Override
    public void altera(T obj) throws ExceptionDAO {
        restabeleceConexao();
        sql = "update cidade set localidade = ?, estado = ? where id = ?";
        int id = obj.getId();
        String localidade = obj.getLocalidade();
        String estado = obj.getEstado();
        try {
            st = con.prepareStatement(sql);
            st.setString(1, localidade);
            st.setString(2, estado);
            st.setInt(3, id);
            st.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            fechaConexao(con, st);
            throw new ExceptionDAO("Erro em alterar a cidade");
        }finally{
            fechaConexao(con, st);
        }
        
    }
    
    @Override
    public void deleta(T obj) throws ExceptionDAO {
        restabeleceConexao();
        sql = "delete from cidade where id = ?";
        int id = obj.getId();
        try {
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            st.execute();
        } catch (java.sql.SQLException e) {
            fechaConexao(con, st);
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao excluir a cidade");
        }finally{
            fechaConexao(con, st);
        }
    }

    @Override
    public ResultSet lista() throws ExceptionDAO {
        restabeleceConexao();
        sql = "SELECT * FROM cidade";
        try {     
            st = con.prepareStatement(sql);
            rSet = st.executeQuery();    
        } catch (SQLException e) {
            fechaConexao(con, st, rSet);
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao lista as cidades");
        }
        return rSet;
    }
    
    //*******************************************************************************************

    /**
     * 
     * @param localidade
     * @return 
     */
    public ResultSet consultaLocalidade(String localidade) throws ExceptionDAO{
        restabeleceConexao(); 
        sql = "SELECT * FROM cidade WHERE localidade = ?";
        try {
            st = con.prepareStatement(sql);
            st.setString(1, localidade);
            rSet = st.executeQuery();
            if(rSet.next()){ 
                return rSet;
            }
        } catch (SQLException e) {
            fechaConexao(con, st, rSet);
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao colsutar a cidade pela localidade");
        }
        fechaConexao(con, st, rSet);
        return null;
    }
    
    /**
     * 
     * @param localidade
     * @param estado
     * @return
     * @throws ExceptionDAO 
     */
    public ResultSet listaLocalidadeEstado(String localidade, String estado) throws ExceptionDAO{
        restabeleceConexao();
        sql = "select *from cidade where localidade like '%"+localidade+"%' and estado like '%"+estado+"%'";
        try {
            st = con.prepareStatement(sql);
            rSet = st.executeQuery();
        } catch (java.sql.SQLException e) {
            fechaConexao(con, st, rSet);
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao listar as cidades pela localidade e estado");
        }
        return rSet;
    }

    /**
     * 
     * @param localidade
     * @return
     * @throws ExceptionDAO 
     */
    public ResultSet listaLocalidade(String localidade) throws ExceptionDAO{
        restabeleceConexao();
        sql = "select * from cidade where localidade like '%"+localidade+"%'";
        try {
            st = con.prepareStatement(sql);
            rSet = st.executeQuery();
        } catch (java.sql.SQLException e) {
            fechaConexao(con, st, rSet);
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao consulta a lista de cidades pela localidade");
        }
        return rSet;
    }
    
    /**
     * 
     * @param estado
     * @return
     * @throws ExceptionDAO 
     */
    public ResultSet listaEstado(String estado) throws ExceptionDAO{
        restabeleceConexao();
        sql = "select * from cidade where estado like '%"+estado+"%'";
        try {
            st = con.prepareStatement(sql);
            rSet = st.executeQuery();
        } catch (java.sql.SQLException e) {
            fechaConexao(con, st, rSet);
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao listar as cidades do estado especificado");
        }
        return rSet;
    } 
}
