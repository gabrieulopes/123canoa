package esbam.canoa123.controll;

import esbam.canoa123.controll.dao.exceptions.ExceptionDAO;
import esbam.canoa123.model.Categorias;
import esbam.canoa123.model.Cidades;
import esbam.canoa123.model.Rotas;

/**
 *
 * @author ACER
 */
public class RotasController {
    java.sql.Connection con;
    
    /**
     * 
     * @param valor
     * @param origem
     * @param destino
     * @param categoria
     * @return 
     */
    public boolean validarRota(float valor, Cidades origem, Cidades destino, Categorias categoria){
        if(varificaConexao() && (valor != 0f && valor > 0f) && (origem != null) && (destino != null) && (categoria != null)){
            try {
                Rotas rota = new Rotas(valor, origem, destino, categoria);
                rota.cadastrar(rota);
                return true;
            } catch (ExceptionDAO e) {
                e.printStackTrace();
                return false;
            }finally{
                fechaConexao(con);
            }
        }else{    
            fechaConexao(con);
            return false;
        }
    }
    
    /**
     * 
     * @param id
     * @return 
     */
    public Rotas consultaID(int id){
        if(id > 0){
            try {
                return new Rotas().consulta(id);
            } catch (ExceptionDAO e) {
                e.printStackTrace();
                return null;
            }
        }else{
            return null;
        }
    }
    
    /**
     * 
     * @param origem
     * @param destino
     * @return 
     */
    public Rotas buscaOrigemDestino(Cidades origem, Cidades destino){
        if((origem != null) && (destino != null)){
            try {
                return new Rotas().consultaOrigemDestino(origem, destino);
            } catch (ExceptionDAO e) {
                e.printStackTrace();
                return null;
            }
        }else{
            return null;
        }
    }
    
    /**
     * 
     * @param valor
     * @param localidadeOrigem
     * @param localidadeDestino
     * @param categoria
     * @return 
     */
    public boolean verificaCamposNullRota(String valor, String localidadeOrigem, String localidadeDestino, String categoria){
        if((valor.equals("") || localidadeOrigem.equals("") || localidadeDestino.equals("")) || categoria.equals("")){
            return true;
        }else{
           return false;
        }
    }

    // ***********************************************************************
    private boolean varificaConexao(){
        restabeleceConexao();
        try{
            if(con == null || con.isValid(2) == false){
                return false;
            }
        }catch(java.sql.SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Erro em se conectar ao banco");
        }  
        
        return true;
    }
    
    private void restabeleceConexao(){
        con = esbam.canoa123.controll.dao.conexao.ConnectionMVC.getConnetion();
    }
    
    private void fechaConexao(java.sql.Connection conn){
        new esbam.canoa123.controll.dao.conexao.ConnectionMVC().closeConnection(conn);
    }
}
