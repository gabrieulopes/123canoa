/*
 * The MIT License
 *
 * Copyright 2023 ACER.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package esbam.canoa123.controll.dao;

import esbam.canoa123.controll.dao.classeabstract.BaseDAO;
import esbam.canoa123.controll.dao.exceptions.ExceptionDAO;
import esbam.canoa123.model.Usuarios;

/**
 *
 * @author ACER
 * @param <T>
 */
public class UsuariosDAO<T extends Usuarios> extends BaseDAO<T>{
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
        String login = obj.getLogin();
        String senha = obj.getSenha();
        boolean acesso = obj.isAcesso();
        String perfil = obj.getPerfil();
        sql = "insert into usuarios(nome, login, senha, acesso, perfil) values(?, ?, ?, ?, ?)";
        try {
            st = con.prepareStatement(sql);
            st.setString(1, nome);
            st.setString(2, login);
            st.setString(3, senha);
            st.setBoolean(4, acesso);
            st.setString(5, perfil);
            st.execute();
        } catch (java.sql.SQLException ex) {
            fechaConexao(con, st, rSet);
            throw new ExceptionDAO("Erro ao executar a inserção dos dados");
        }finally{
            fechaConexao(con, st);
        }
    }
    
    @Override
    public void altera(T obj) throws ExceptionDAO {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleta(T obj) throws ExceptionDAO {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public java.sql.ResultSet consulta(int id) throws ExceptionDAO {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public java.sql.ResultSet lista() throws ExceptionDAO {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    // ********************************************************************************************
    
    /**
     * 
     * @param login
     * @param senha
     * @return 
     * @throws esbam.canoa123.controll.dao.exceptions.ExceptionDAO 
     */
    public java.sql.ResultSet consultaLoginSenha(String login, String senha) throws ExceptionDAO{
        restabeleceConexao();
        sql = "select * from usuarios where login = ? and senha = ?";
        try {
            st = con.prepareStatement(sql);
            st.setString(1, login);
            st.setString(2, senha);
            rSet = st.executeQuery();
            if(rSet.next()){
                return rSet;
            }
        } catch (java.sql.SQLException e) {
            fechaConexao(con, st, rSet);
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao consultar pelo login e senha");
        }
        fechaConexao(con, st, rSet);
        return null;
    }
    
    /**
     * 
     * @param login
     * @return 
     * @throws esbam.canoa123.controll.dao.exceptions.ExceptionDAO 
     */
    public java.sql.ResultSet consultaLogin(String login) throws ExceptionDAO{
        restabeleceConexao();
        sql = "select * from usuarios where login = ?";
        try {
            st = con.prepareStatement(sql);
            st.setString(1, login);
            rSet = st.executeQuery();
            if(rSet.next()){
                return rSet;
            }
        } catch (java.sql.SQLException e) {
            fechaConexao(con, st, rSet);
            e.printStackTrace();
            throw new ExceptionDAO("Erro em consultar pelo login");
        }
        fechaConexao(con, st, rSet);
        return null;
    }
    
    /**
     * 
     * @param acesso
     * @return 
     * @throws esbam.canoa123.controll.dao.exceptions.ExceptionDAO 
     */
    public java.sql.ResultSet consultaAcesso(boolean acesso) throws ExceptionDAO{  
        restabeleceConexao();
        sql = "select * from usuarios where acesso = ?";
        try {
            st = con.prepareStatement(sql);
            st.setBoolean(1, acesso);
            rSet = st.executeQuery();
            if(rSet.next()){
                return rSet;
            }
        } catch (java.sql.SQLException e) {
            fechaConexao(con, st, rSet);
            e.printStackTrace();
            throw new ExceptionDAO("Erro em consultar o acesso");
        }
        fechaConexao(con, st, rSet);
        return null;
    }
    
    /**
     * 
     * @param id
     * @param acesso
     * @return 
     */
    public boolean alteraAcesso(int id, boolean acesso) throws ExceptionDAO{
        restabeleceConexao();
        sql = "update usuarios set acesso = ? where id = ?";
        try {    
            st = con.prepareStatement(sql);
            st.setBoolean(1, acesso);
            st.setInt(2, id);
            st.executeUpdate();
            fechaConexao(con, st);
            return true;
        } catch (java.sql.SQLException ex) {
            fechaConexao(con, st);
            throw new ExceptionDAO("Erro tentar alterar os dados da coluna especificada");
        }
    }
    
   
    /**
     * Metódo para resetar o acesso de todos os usuarios, o reseta equivale a false para o campo acesso.
     * @return 
     * @throws esbam.canoa123.controll.dao.exceptions.ExceptionDAO 
     */
    public boolean resetAcesso() throws ExceptionDAO{
        restabeleceConexao();
        sql = "update usuarios set acesso = false where id between 1 and last_insert_id(id)";
        try {
            st = con.prepareStatement(sql);
            st.executeUpdate();
            return true;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            fechaConexao(con, st);
            throw new ExceptionDAO("Erro ao resetar os acessos");
        }finally{
            fechaConexao(con, st);
        }
    }  
}
