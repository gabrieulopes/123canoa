package esbam.canoa123.controll;

import esbam.canoa123.controll.dao.exceptions.ExceptionDAO;
import esbam.canoa123.model.Cidades;
import java.util.ArrayList;

/**
 *
 * @author ACER
 */
public class CidadesController {
    java.sql.Connection con;
    
    /**
     * 
     * @param estado
     * @param localidade
     * @return 
     */
    public boolean validarCidade(String estado, String localidade){
        if(verificaConexao() && (!estado.isEmpty() && !estado.contains(" ") && !estado.startsWith(" ", estado.length() - 1)) && (!localidade.isEmpty() && !localidade.startsWith(" ", localidade.length() - 1))){
            try {
                Cidades cidade = new Cidades(localidade, estado);
                cidade.cadastrar(cidade);
                fechaConexao(con);
                return true;
            } catch (ExceptionDAO ex) {
                fechaConexao(con);
                ex.printStackTrace();
                return false;
            }
        }else{
            fechaConexao(con);
            return false;
        }
    }
    
    /**
     * 
     * @param localidade
     * @return 
     */
    public Cidades buscaLocalidade(String localidade){
        if((!localidade.isEmpty() && !localidade.startsWith(" ", localidade.length() - 1))){
            try {
                return new Cidades().buscaLocalidade(localidade);
            } catch (ExceptionDAO e) {
                e.printStackTrace();
                return null;
            }
        }else{
            return null;
        }
    }
    
    /**[
     * 
     * @param localidade
     * @param estado
     * @return 
     */
    public ArrayList<Cidades> listaLocalidadeEstado(String localidade, String estado){
        try {
            return new Cidades().listaLocalidadeEstado(localidade, estado);
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new AbstractMethodError("Erro ao executar o método listaLocalidadeEstado()");
        }
    }
    
    /**
     * 
     * @param localidade
     * @return 
     */
    public ArrayList<Cidades> listaLocalidade(String localidade){
        try {
            return new Cidades().listaLocalidade(localidade);
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao executar o método listaLocalidade()");
        }
    }
    
    /**
     * 
     * @param estado
     * @return 
     */
    public ArrayList<Cidades> listaEstado(String estado){
        try {
            return new Cidades().listaEstado(estado);
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new Error("Erro ao executar o método listaEstado()");
        }
    }
    
    /**
     * 
     * @param id
     * @return 
     */
    public Cidades busca(int id){
        try {
            return new Cidades().buscar(id);
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 
     * @return 
     */
    public ArrayList<Cidades> listaCidades(){
        try {
            return new Cidades().listaCidades();
        } catch (ExceptionDAO ex) {
            ex.printStackTrace();
            throw new AbstractMethodError("Erro ao executar o método listaCidades()");
        }
    }
    
    /**
     * 
     * @param id
     * @param estado
     * @param localidade
     * @return 
     */
    public boolean altera(int id, String estado, String localidade){
        try {
            Cidades cidade = new Cidades(localidade, estado);
            cidade.setId(id);
            cidade.atualiza(cidade);
            return true;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 
     * @param id
     * @return 
     */
    public boolean exluirCidade(int id){
        if(id > 0){
            try {
                Cidades cidade = new Cidades();
                cidade.setId(id);
                new Cidades().exluir(cidade);
                return true;
            } catch (ExceptionDAO ex) {
                ex.printStackTrace();
                return false;
            }
        }else{
            return false;
        }
    }
    
    
    public boolean verificaCamposNull(String localidade, String estado){
        if(localidade.equals("") || estado.equals("")){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean verificaAllCamposNull(String localidade, String estado){
        if(localidade.equals("") && estado.equals("")){
            return true;
        }else{
            return false;
        }
    }
    
    // REGRAS DA CLASSE
    // *********************************************************************************
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
