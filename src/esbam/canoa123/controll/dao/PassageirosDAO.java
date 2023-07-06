package esbam.canoa123.controll.dao;

// CLASSES (ESBAM)
import esbam.canoa123.controll.dao.classeabstract.BaseDAO;
import esbam.canoa123.controll.dao.exceptions.ExceptionDAO;
import esbam.canoa123.model.Passageiros;

// CLASSES (JAVA)
import java.sql.SQLException;
/**
 *
 * @author Jonny Nunes Viana
 * @param <T>
 */
public class PassageirosDAO<T extends Passageiros> extends BaseDAO<T>{
    String sql;
    java.sql.Connection con;
    java.sql.PreparedStatement st;
    java.sql.ResultSet rSet;
    
    private void restabeleceConexao(){ con = new esbam.canoa123.controll.dao.conexao.ConnectionMVC().getConnetion(); }
    
    private void fechaConexao(java.sql.Connection conn, java.sql.PreparedStatement psp){ new esbam.canoa123.controll.dao.conexao.ConnectionMVC().closeConnection(conn, psp); }
    
    private void fechaConexao(java.sql.Connection conn, java.sql.PreparedStatement stt, java.sql.ResultSet rset){ new esbam.canoa123.controll.dao.conexao.ConnectionMVC().closeConnection(conn, stt, rset); }
    
    // **********************************************************************

    @Override
    public void inseri(T obj) throws ExceptionDAO {
        restabeleceConexao();
        String nome = obj.getNome();
        String cpf = obj.getCpf();
        String telefone = obj.getTelefone();
        String sexo = obj.getSexo();
        sql = "insert into passageiro(nome, cpf, telefone, sexo) values(?, ?, ?, ?)";
        try{
            st = con.prepareStatement(sql);
            st.setString(1, nome);
            st.setString(2, cpf);
            st.setString(3, telefone);
            st.setString(4, sexo);
            st.execute(); 
        }catch(java.sql.SQLException ex){
            fechaConexao(con, st, rSet);
            throw new ExceptionDAO("Erro ao inserir os dados");
        }finally{
            fechaConexao(con, st);
        }
    }
    
    @Override
    public void altera(T obj) throws ExceptionDAO {
        restabeleceConexao();
        int id = obj.getId();
        String nome = obj.getNome();
        String cpf = obj.getCpf();
        String sexo = obj.getSexo();
        String tell = obj.getTelefone();
        sql = "update passageiro set nome = ?, cpf = ?, sexo = ?, telefone = ? where id = ?";
        try {
            st = con.prepareStatement(sql);
            st.setString(1, nome);
            st.setString(2, cpf);
            st.setString(3, sexo);
            st.setString(4, tell);
            st.setInt(5, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionDAO("Erro em alterar os dados do passageiro");
        }finally{
            fechaConexao(con, st);
        }
    }
    
    @Override
    public void deleta(T obj) throws ExceptionDAO {
        restabeleceConexao();
        sql = "delete from passageiro where id = ?";
        try {
            int id = obj.getId();
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            st.execute();
        } catch (java.sql.SQLException ex) {
            fechaConexao(con, st);
            throw new ExceptionDAO("Erro ao deletar o passageiro do id especificado");
        }finally{
            fechaConexao(con, st);
        }
    }

    @Override
    public java.sql.ResultSet consulta(int id) throws ExceptionDAO {
        try {
            restabeleceConexao();
            sql = "select * from passageiro where id = ?";
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            rSet = st.executeQuery();
            if(rSet.next()){
                return rSet;
            }
        } catch (java.sql.SQLException e) {
            fechaConexao(con, st, rSet);
            throw new ExceptionDAO("Erro ao consultar o passageiros");
        }
        fechaConexao(con, st, rSet);
        return null;
    }
    
    @Override
    public java.sql.ResultSet lista() throws ExceptionDAO {
        restabeleceConexao();
        sql = "select * from passageiro";
        try {
            st = con.prepareStatement(sql);
            rSet = st.executeQuery();
        } catch (java.sql.SQLException e) {
            fechaConexao(con, st, rSet);
            throw new ExceptionDAO("Erro ao tentar buscar a lista de passageiros");
        }
        return rSet;
    }
  
    // **********************************************************************
    
    /**
     * 
     * @param cpf
     * @return
     * @throws ExceptionDAO 
     */
    public java.sql.ResultSet consultaCPF(String cpf) throws ExceptionDAO{
        restabeleceConexao();
        sql = "SELECT * FROM passageiro WHERE cpf = ?";
        try {
            st = con.prepareStatement(sql);
            st.setString(1, cpf);
            rSet = st.executeQuery(); 
            if(rSet.next()){ 
                return rSet;
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            fechaConexao(con, st, rSet);
            throw new ExceptionDAO("Erro em consultar o cpf");
        }
        fechaConexao(con, st, rSet);
        return null;
    }
    
    /**
     * 
     * @return 
     */
    public java.sql.ResultSet consultaUltimoID() throws ExceptionDAO{
        restabeleceConexao();
        sql = "Select max(id) as id from passageiro";
        try{
            st = con.prepareStatement(sql);
            rSet = st.executeQuery();
            if(rSet.next()){
                return rSet;
            }
        }catch(java.sql.SQLException e){
            e.printStackTrace();
            fechaConexao(con, st, rSet);
            throw new ExceptionDAO("Erro em consultar o cpf");
        }
        fechaConexao(con, st, rSet);
        return null;
    }
    
    /**
     * 
     * @param nome
     * @return
     * @throws ExceptionDAO 
     */
    public java.sql.ResultSet listaNome(String nome) throws ExceptionDAO{
        restabeleceConexao();
        sql = "select * from passageiro where nome like ?";
        try {
            st = con.prepareStatement(sql);
            st.setString(1, nome + "%");
            rSet = st.executeQuery();
        } catch (java.sql.SQLException e) {
            fechaConexao(con, st, rSet);
            throw new ExceptionDAO("Erro em consultar a lista de nomes");
        }
        return rSet;
    }
    
    /**
     * 
     * @param sexo
     * @return
     * @throws ExceptionDAO 
     */
    public java.sql.ResultSet listaSexo(String sexo) throws ExceptionDAO{
        restabeleceConexao();
        sql = "select * from passageiro where sexo = ?";
        try {
            st = con.prepareStatement(sql);
            st.setString(1, sexo);
            rSet = st.executeQuery();
        } catch (SQLException e) {
            fechaConexao(con, st, rSet);
            throw new ExceptionDAO("Erro ao lista o sexo dos passageiros");
        }
        return rSet;
    }
    
    /**
     * 
     * @param tell
     * @return
     * @throws ExceptionDAO 
     */
    public java.sql.ResultSet listaTelefone(String tell)  throws ExceptionDAO{
        restabeleceConexao();
        sql = "select * from passageiro where telefone = ?";
        try {
            st = con.prepareStatement(sql);
            st.setString(1, tell);
            rSet = st.executeQuery();
        } catch (SQLException e) {
            fechaConexao(con, st, rSet);
            throw new ExceptionDAO("Erro ao lista o telefone dos passageiros");
        }
        return rSet;
    }
}
