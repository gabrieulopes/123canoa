package esbam.canoa123.controll.dao;

import esbam.canoa123.controll.dao.classeabstract.BaseDAO;
import esbam.canoa123.controll.dao.exceptions.ExceptionDAO;
import esbam.canoa123.model.Cidades;
import esbam.canoa123.model.Rotas;
import java.sql.ResultSet;

/**
 *
 * @author Jonny Nunes Viana
 * @param <T>
 */
public class RotaDAO<T extends Rotas> extends BaseDAO<T>{
    String sql;
    java.sql.Connection con;
    java.sql.PreparedStatement st;
    java.sql.ResultSet rSet;
    
    private void restabeleceConexao(){
        con = new esbam.canoa123.controll.dao.conexao.ConnectionMVC().getConnetion();
    }
    
    private void fechaConexao(java.sql.Connection conn, java.sql.PreparedStatement psp){
        new esbam.canoa123.controll.dao.conexao.ConnectionMVC().closeConnection(conn, psp);
    }
    
    private void fechaConexao(java.sql.Connection conn, java.sql.PreparedStatement stt, java.sql.ResultSet rset){
        new esbam.canoa123.controll.dao.conexao.ConnectionMVC().closeConnection(conn, stt, rset);
    }
    
    // *********************************************************************************

    @Override
    public void inseri(T obj) throws ExceptionDAO {
        restabeleceConexao();
        float valor = obj.getValor();
        int idOrigem = obj.getOrigem().getId();
        int idDestino = obj.getDestino().getId();
        int idCategoria = obj.getCategoria().getId();
        sql = "INSERT INTO rota (valor, id_origem, id_destino, id_categoria) VALUES (?, ?, ?, ?)";
        try{      
            st = con.prepareStatement(sql);
            st.setFloat(1, valor);
            st.setInt(2, idOrigem);
            st.setInt(3, idDestino);
            st.setInt(4, idCategoria);
            st.execute();   
        }catch(java.sql.SQLException ex){
            ex.printStackTrace();
            fechaConexao(con, st);
            throw new ExceptionDAO("Erro ao inserir os dados da Rota");
        }finally{
            fechaConexao(con, st);
        }
    }

    @Override
    public void deleta(T obj) throws ExceptionDAO {
        restabeleceConexao();
        int id = obj.getId();
        sql = "delete from rota where id = ?";
        try {
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            st.execute();
        } catch (java.sql.SQLException e) {
            fechaConexao(con, st);
            throw new ExceptionDAO("Erro ao deletar a Rota");
        }finally{
            fechaConexao(con, st);
        }
    }
    
    @Override
    public ResultSet consulta(int id) throws ExceptionDAO {
        restabeleceConexao();
        sql = "select * from rota where id = ?";
        try {
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            rSet = st.executeQuery();
            if(rSet.next()){
                return rSet;
            }
        } catch (java.sql.SQLException e) {
            fechaConexao(con, st, rSet);
            throw new ExceptionDAO("Erro ao consultar a Rota");
        }
        fechaConexao(con, st, rSet);
        return null;
    }
    
    @Override
    public void altera(T obj) throws ExceptionDAO {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ResultSet lista() throws ExceptionDAO {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }    
    // *********************************************************************************

    /**
     * 
     * @param origem
     * @param destino
     * @return 
     * @throws esbam.canoa123.controll.dao.exceptions.ExceptionDAO 
     */
    public java.sql.ResultSet consultaRotaOrigemDestino(Cidades origem, Cidades destino) throws ExceptionDAO{
        restabeleceConexao();
        int idOrigem = origem.getId();
        int idDestino = destino.getId();
        sql = "select * from rota where id_origem = ? and id_destino = ?";
        try { 
            st = con.prepareStatement(sql);
            st.setInt(1, idOrigem);
            st.setInt(2, idDestino);
            rSet = st.executeQuery();
            if(rSet.next()){
                return rSet;
            }  
        } catch (java.sql.SQLException e) {
            fechaConexao(con, st, rSet);
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao busca a Rota das cidades especificadas");
        }
        fechaConexao(con, st, rSet);
        return null;
    }
}
