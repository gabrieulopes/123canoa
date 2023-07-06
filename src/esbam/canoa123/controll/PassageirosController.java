package esbam.canoa123.controll;

import esbam.canoa123.controll.dao.exceptions.ExceptionDAO;
import esbam.canoa123.model.Passageiros;
import esbam.canoa123.controll.bussines.CpfRepeat;
import esbam.canoa123.controll.dao.exceptions.ExceptionCpf;
import java.util.ArrayList;

/**
 *
 * @author ACER
 */
public class PassageirosController {
    private static java.sql.Connection con;
    
    /**
     * 
     * @param nome
     * @param cpf
     * @param tel
     * @param sexo
     * @return 
     */
    public boolean validarPassageiros(String nome, String cpf, String tel, String sexo){
        if(verificaConexao() && !nome.isEmpty() && (!cpf.startsWith(" ")) && !cpf.isEmpty() && !tel.isEmpty() && (tel.length() == 10) && !tel.startsWith(" ") && !sexo.isEmpty()){   
            try {
                Passageiros passageiro = new Passageiros(nome, cpf, tel, sexo);
                passageiro.cadastrar(passageiro);               
                return true;
            } catch (ExceptionDAO ex) {
                ex.printStackTrace();
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
     * Método para verificar um cpf fornecido de um passageiro. O processo consiste em
     * verificar se um cpf já existe. Baseando-se da regra de negócio sobre repetição de cpf.
     * @param cpf String em referência ao cpf de um passageiro.
     * @return True se um passageiro foi identificado pelos dados do cpf, caso ao contrário retorna false.
     */
    public boolean validarCpf(String cpf){
        try {
            new CpfRepeat().passageiroCPF(cpf);
            return true;
        } catch (ExceptionCpf ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public Passageiros buscaCpf(String cpf){
        if((!cpf.startsWith(" "))){
            try {
                return new Passageiros().buscarCPF(cpf);
            } catch (ExceptionDAO e) {
                return null;
            }
        }else{
            return null;
        }
    }
    
    /**
     * 
     * @return 
     */
    public Passageiros ultimoID(){
        try {
            return new Passageiros().buscarUltimoID();
        } catch (ExceptionDAO ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    /**
     * 
     * @param id
     * @return 
     */
    public Passageiros buscaPassageiro(int id){
        if(id > 0){
            try {
                return new Passageiros().consulta(id);
            } catch (ExceptionDAO ex) {
                ex.printStackTrace();
                return null;
            }
        }else{
            return null;
        }
    }
    
    /**
     * 
     * @param id
     * @return 
     */
    public boolean excluirPassageiro(int id){
        if(id > 0){
            try {
                Passageiros passageiro = new Passageiros();
                passageiro.setId(id);
                return passageiro.excluir(passageiro);
            } catch (ExceptionDAO ex) {
                ex.printStackTrace();
                return false;
            }
        }else{
            return false;
        }
    }
    
    /**
     * 
     * @param id
     * @param nome
     * @param cpf
     * @param tel
     * @param sexo
     * @return 
     */
    public boolean altera(int id, String nome, String cpf, String tel, String sexo){
        if((id > 0) && !nome.isEmpty() && (!cpf.startsWith(" ")) && !cpf.isEmpty() && !tel.isEmpty() && (tel.length() == 10) && !tel.startsWith(" ") && !sexo.isEmpty()){
            try {
                Passageiros passageiro = new Passageiros(nome, cpf, tel, sexo);
                passageiro.setId(id);
                passageiro.atualiza(passageiro);
                return true;
            } catch (ExceptionDAO e) {
                return false;
            }
        }else{
            return false;
        }
    }
    
    /**
     * 
     * @return 
     */
    public ArrayList<Passageiros> listaPassageiros(){
        try {
            return new Passageiros().lista();
        } catch (ExceptionDAO ex) {
            ex.printStackTrace();
            throw new AbstractMethodError("Erro ao excutar o metodo buscaLista()");
        }
    }
    
    /**
     * 
     * @param nome
     * @return 
     */
    public ArrayList<Passageiros> listNomePassageiros(String nome){
        if(!nome.isEmpty()){
            try {
                return new Passageiros().listaNome(nome);
            } catch (ExceptionDAO e) {
                throw new AbstractMethodError("Erro ao excutar o metodo listaNomeTable()");
            }
        }else{
            throw new NullPointerException("Erro, o nome e null");
        } 
    }
    
    /**
     * 
     * @param sexo
     * @return 
     */
    public ArrayList<Passageiros> listaSexoPassageiro(String sexo){
        try {
            return new Passageiros().listaSexo(sexo);
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new RuntimeException("Erro em executar o listaSexo()");
        }
    }
    
    /**
     * 
     * @param tell
     * @return 
     */
    public ArrayList<Passageiros> listaTelefone(String tell){
        if(!tell.isEmpty() && (tell.length() == 10) && !tell.startsWith(" ")){
            try {
                return new Passageiros().listaTelefone(tell);
            } catch (ExceptionDAO e) {
                throw new AbstractMethodError("Erro ao excutar o metodo listaTelefone()");
            }
        }else{
            throw new NullPointerException("Erro, o telefone e null");
        }
    }
    
    
    /**
     * 
     * @param nome
     * @param cpf
     * @param telefone
     * @param sexo
     * @return 
     */
    public boolean verficarCamposNull(String nome, String cpf, String telefone, String sexo){
        if(nome.equals("") || cpf.equals("") || telefone.equals("") || sexo.equals("")){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 
     * @param nome
     * @param cpf
     * @param telefone
     * @param sexo
     * @return 
     */
    public boolean verificaAllCampoNull(String nome, String cpf, String telefone, String sexo){
        if(nome.equals("") && cpf.startsWith(" ") && telefone.equals("") && sexo.equals("")){
            return true;
        }else{
           return false;
        }
    }
    
    // **********************************************************************************
    
    private void restabeleceConexao(){ con = new esbam.canoa123.controll.dao.conexao.ConnectionMVC().getConnetion(); }
    
    private void fechaConexao(java.sql.Connection conn){ new esbam.canoa123.controll.dao.conexao.ConnectionMVC().closeConnection(conn); }
    
    private boolean verificaConexao(){
        try{
            restabeleceConexao();
            if(con == null || con.isValid(2) == false){
                return false;
            }
        }catch(java.sql.SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Erro ao se conectar ao banco");
        }
        return true;
    }
}
