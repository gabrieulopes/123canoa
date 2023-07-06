package esbam.canoa123.controll;

import esbam.canoa123.controll.dao.exceptions.ExceptionDAO;
import esbam.canoa123.model.Categorias;
import java.util.ArrayList;

/**
 *
 * @author Jonny Nunes Viana
 */
public class CategoriasController {
    java.sql.Connection con;
    
    /**
     * 
     * @param nome
     * @return 
     */
    public boolean validarCategoria(String nome){
        if((!nome.isEmpty()) && !nome.startsWith(" ", nome.length() - 1) && verificaConexao()){
            try {
                Categorias categoria = new Categorias(nome);
                categoria.cadastra(categoria);
                return true;
            } catch (ExceptionDAO e) {
                e.printStackTrace();
                return false;
            }finally{
                fechaConexao(con);
            }
        }else{
            return false;
        }        
    }
    
    /**
     * 
     * @param nome
     * @return 
     */
    public Categorias buscaNome(String nome){
        try {
            return new Categorias().consultaNome(nome);
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 
     * @return 
     */
    public ArrayList<Categorias> lista(){
        try {
            return new Categorias().listarCategorias();
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new AbstractMethodError("Erro ao executar o método listaCategorias");
        }
    }
    
    // *****************************************************************
    
    private boolean verificaConexao(){
        restabeleceConexao();
        try{
            if(con == null || con.isValid(2) == false){
                return false;
            }
        }catch(java.sql.SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Erro ao se conectar ao banco");
        }
        return true;
    }
    
    private void restabeleceConexao(){
        con = new esbam.canoa123.controll.dao.conexao.ConnectionMVC().getConnetion();
    }
    
    private void fechaConexao(java.sql.Connection conn){
        new esbam.canoa123.controll.dao.conexao.ConnectionMVC().closeConnection(conn);
    }
}
