/*
 * The MIT License
 *
 * Copyright 2023 Jonny Nunes Viana.
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
import esbam.canoa123.model.Categorias;
import java.sql.ResultSet;

/**
 *
 * @author ACER
 * @param <T>
 */
public class CategoriaDAO<T extends Categorias> extends BaseDAO<T>{
    String sql;
    java.sql.Connection con;
    java.sql.PreparedStatement st;
    ResultSet rSet;

    private void restabeleceConexao() {
        con = new esbam.canoa123.controll.dao.conexao.ConnectionMVC().getConnetion();
    }

    private void fechaConexao(java.sql.Connection conn, java.sql.PreparedStatement psp) {
        new esbam.canoa123.controll.dao.conexao.ConnectionMVC().closeConnection(conn, psp);
    }

    private void fechaConexao(java.sql.Connection conn, java.sql.PreparedStatement st, java.sql.ResultSet rset) {
        new esbam.canoa123.controll.dao.conexao.ConnectionMVC().closeConnection(conn, st, rset);
    }
    
    //*******************************************************************************************
    
    @Override
    public void inseri(T obj) throws ExceptionDAO {
        restabeleceConexao();
        String nome = obj.getNome();
        sql = "INSERT INTO categoria (nome) VALUES (?)";
        try {    
            st = con.prepareStatement(sql);
            st.setString(1, nome);
            st.execute();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            fechaConexao(con, st);
            throw new ExceptionDAO("Erro ao método ao executar a inserção da categoria");
        } finally {
            fechaConexao(con, st);
        }    
    }
    
    @Override
    public ResultSet consulta(int id) throws ExceptionDAO {
        restabeleceConexao();
        sql = "SELECT * FROM categoria WHERE id = ?";
        try {
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            rSet = st.executeQuery();     
            if(rSet.next()){
                return rSet;
            }
        } catch (java.sql.SQLException e) {
            fechaConexao(con, st, rSet);
            e.printStackTrace();
            throw new ExceptionDAO("Erro em consultar a categoria");
        }
        fechaConexao(con, st, rSet);
        return null;
    }
    
    @Override
    public void deleta(T obj) throws ExceptionDAO {
        restabeleceConexao();
        sql = "delete from categoria where id = ?";
        int id = obj.getId();
        try {
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            fechaConexao(con, st);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            fechaConexao(con, st);
            throw new ExceptionDAO("Erro em deletar a categoria do id especificado");
        }finally{
            fechaConexao(con, st);
        }
    }
    
    @Override
    public void altera(T obj) throws ExceptionDAO {
        restabeleceConexao();
        sql = "update categoria set nome = ? where id = ?";
        int id = obj.getId();
        String nome = obj.getNome();
        try {
            st = con.prepareStatement(sql);
            st.setString(1, nome);
            st.setInt(2, id);
            st.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            fechaConexao(con, st);
            throw new ExceptionDAO("Erro em alterar a categoria");
        }finally{
            fechaConexao(con, st);
        }
    }

    @Override
    public ResultSet lista() throws ExceptionDAO {
        restabeleceConexao();
        sql = "SELECT * FROM categoria";
        try {
            st = con.prepareStatement(sql);
            rSet = st.executeQuery();            
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            fechaConexao(con, st, rSet);
            throw new ExceptionDAO("Erro em listar as categoria");
        }
        return rSet;
    }
    
    //*******************************************************************************************
    
    /**
     * 
     * @param nome
     * @return
     * @throws ExceptionDAO 
     */
    public ResultSet consultaNome(String nome) throws ExceptionDAO {
        restabeleceConexao();
        sql = "SELECT * FROM categoria WHERE nome = ?";
        try {
            st = con.prepareStatement(sql);
            st.setString(1, nome);
            rSet = st.executeQuery();
            if(rSet.next()){
                return rSet;
            }
        } catch (java.sql.SQLException e) {
            fechaConexao(con, st, rSet);
            throw new ExceptionDAO("Erro em consulta a categoria pelo nome");
        }
        fechaConexao(con, st, rSet);
        return null;
    }
}
