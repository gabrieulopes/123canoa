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
import esbam.canoa123.model.Passagens;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PassagemDAO<T extends Passagens> extends BaseDAO<T>{
    String sql;
    Connection con;
    PreparedStatement st;
    ResultSet rSet;
    
    private void restabeleceConexao(){ con = new esbam.canoa123.controll.dao.conexao.ConnectionMVC().getConnetion(); }
    
    private void fechaConexao(Connection conn, PreparedStatement psp){ new esbam.canoa123.controll.dao.conexao.ConnectionMVC().closeConnection(conn, psp); }
    
    private void fechaConexao(Connection conn, PreparedStatement stt, ResultSet rset){ new esbam.canoa123.controll.dao.conexao.ConnectionMVC().closeConnection(conn, stt, rset); }
    // **********************************************************************

    @Override
    public void inseri(T obj) throws ExceptionDAO {
        restabeleceConexao();
        int idPAssageiro = obj.getId_passageiro().getId();
        int idRota = obj.getId_rota().getId();
        Date dataCompra = obj.getDataCompra();
        String dataCompraFormatada = new SimpleDateFormat("dd/MM/yyyy").format(dataCompra); // OBTENDO OS DADOS DA DATA FORMATADA DA COMPRA
        DateTimeFormatter dataLocalFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataLocal = LocalDate.parse(dataCompraFormatada,dataLocalFormatada); // CONVERTENDO OS DADOS DA DATA PARA UM FORMATO AMERICANO
        sql = "insert into passagem (id_rota, id_passageiro, dat_compra) values (?,?,?)";   
        try {
            st = con.prepareStatement(sql);
            st.setInt(1, idRota);
            st.setInt(2, idPAssageiro);
            st.setDate(3, java.sql.Date.valueOf(dataLocal)); // CONVERTE O OBJETO "LOCALDATE" PARA DATE DO SQL
            st.execute();
        } catch (java.sql.SQLException ex) {
            fechaConexao(con, st);
            throw new ExceptionDAO("Erro ao cadastrar a passagem");
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
        restabeleceConexao();
        int id = obj.getId();
        sql = "delete from passagem where id = ?";
        try {
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            st.execute();
        } catch (Exception e) {
            e.printStackTrace();
            fechaConexao(con, st);
            throw new ExceptionDAO("Erro em deletar a passagem");
        }finally{
            fechaConexao(con, st);
        }
    }

    @Override
    public ResultSet consulta(int id) throws ExceptionDAO {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ResultSet lista() throws ExceptionDAO {
        restabeleceConexao();
        sql = "select * from passagem";
        try {
            st = con.prepareStatement(sql);
            rSet = st.executeQuery();
        } catch (SQLException e) {
            fechaConexao(con, st, rSet);
            throw new ExceptionDAO("Erro em listar as passagens");
        }
        return rSet;
    }
    
    // **********************************************************************
    
    public ResultSet joinTabelaRotaCidadeCategoria(int id) throws ExceptionDAO{
        restabeleceConexao();
        sql = "select pg.id, cO.localidade as origem, cD.localidade as destino, cG.nome as categoria, rt.valor, pg.dat_compra" +
              " from passagem pg " +
              " inner join rota rt on pg.id_rota = rt.id " +
              " inner join cidade cO on rt.id_origem = cO.id " +
              " inner join cidade cD on rt.id_destino = cD.id " +
              " inner join categoria cG on rt.id_categoria = cG.id " +
              " where pg.id = ?";
        try {
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            rSet = st.executeQuery();
            if(rSet.next()){
                return rSet;
            }
        } catch (SQLException e) {
            fechaConexao(con, st, rSet);
            throw new ExceptionDAO("Erro em juntas as tabelas da passagem");
        }
        return null;        
    }
    
}
