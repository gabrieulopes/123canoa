package esbam.canoa123.controll;

import esbam.canoa123.controll.dao.exceptions.ExceptionDAO;
import esbam.canoa123.model.Barcos;
import java.util.ArrayList;

/**
 *
 * @author ACER
 */
public class BarcosController {
    
    java.sql.Connection con;
    
    /**
     * Método para validar os dados de entrada em referência ao cadastro do barco no sistema.
     * E realizar o cadastro caso a validação for aceito.
     * @param nome Nome do barco para validação
     * @param capacidade Capacidade total do barco para validação
     * @return Retorna true se for validado, caso ao contrário retorna false
     */
    public boolean validarBarco(String nome, int capacidade){
        if(verificaConexao() && (!nome.isEmpty() && !nome.startsWith(" ", nome.length() - 1)) && (capacidade != 0 && capacidade > 0)){          
            try {
                Barcos barco = new Barcos(nome, capacidade);
                barco.cadastra(barco);
                fechaConexao(con);
                return true;
            } catch (ExceptionDAO e) {
                fechaConexao(con);
                e.printStackTrace();
                return false;
            }
        }else{    
            fechaConexao(con);
            return false;
        }
    }
    
    /**
     * 
     * @param id
     * @param nome
     * @param capacidade
     * @return 
     */
    public boolean altera(int id, String nome, int capacidade){
        if((id > 0) && (!nome.isEmpty() && !nome.startsWith(" ", nome.length() - 1)) && (capacidade != 0 && capacidade > 0)){
            try {
                Barcos barco = new Barcos(nome, capacidade);
                barco.setId(id);
                barco.atualiza(barco);
                return true;
            } catch (ExceptionDAO e) {
                e.printStackTrace();
                return false;
            }
        }else{
            return false;
        }
    }
    
    public boolean excluir(int id){
        try {
            Barcos barco = new Barcos();
            barco.setId(id);
            return barco.excluir(barco);
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
    public Barcos busca(int id){
        try {
            return new Barcos().busca(id);
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 
     * @return 
     */
    public ArrayList<Barcos> listaBarcos(){
        try {
            return new Barcos().lista();
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao executar o método lista()");
        }
    }
    
    /**
     * 
     * @param nome
     * @param capacidade
     * @return 
     */
    public ArrayList<Barcos> listaBarcosNomeCapacidade(String nome, int capacidade){
        try {
            return new Barcos().listaNomeCapacidade(nome, capacidade);
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao executar o método listaNomeCapacidade()");
        }
    }
    
    /**
     * 
     * @param capacidade
     * @return 
     */
    public ArrayList<Barcos> listaBarcosCapacidade(int capacidade){
        try {
            return new Barcos().listaCapacidade(capacidade);
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao executar o método listaCapacidade()");
        }
    }
    
    /**
     * 
     * @param nome
     * @return 
     */
    public ArrayList<Barcos> listaBarcosNome(String nome){
        try {
            return new Barcos().listaNome(nome);
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao executar o método listaNome()");
        }
    }

    /**
     * Método para verificar se todos os campos correspondente ao barco são nulos ou não.
     * Todos serão verificados como String. Se um compo especifico não existe, então digite " "
     * no argumento desse campo.
     * @param id O campo id do barco
     * @param nome O campo nome do barco
     * @param capacidade O campo capacidade do barco
     * @return true se todos os campos passados no parâmetro são nulos (id, nome, capacidade), caso contrário retorna false.
     */
    public boolean verificaAllCampoNull(String nome, String capacidade){
        if((nome.equals("") && capacidade.equals(""))){
            return true;
        }else{
           return false;
        }
    }
    
    /**
     * Método para verificar se pelo menos um campo correspondente ao barco é nulos ou não.
     * Todos serão verificados como String.
     * @param id O campo id do barco
     * @param nome O campo nome do barco
     * @param capacidade O campo capacidade do barco
     * @return true se os campos são nulos (id, nome, capacidade), caso contrário retorna false.
     */
    public boolean verificaCampoNullBarco(String nome, String capacidade){
        if((nome.equals("") || capacidade.equals(""))){
            return true;
        }else{
           return false;
        }
    }
    
    //***************************************************************************************
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
        con = esbam.canoa123.controll.dao.conexao.ConnectionMVC.getConnetion();
    }
    
    private void fechaConexao(java.sql.Connection conn){
        new esbam.canoa123.controll.dao.conexao.ConnectionMVC().closeConnection(conn);
    }
}
