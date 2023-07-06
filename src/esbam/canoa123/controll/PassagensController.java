package esbam.canoa123.controll;

// CLASSES (ESBAM)
import esbam.canoa123.controll.dao.exceptions.ExceptionDAO;
import esbam.canoa123.model.Cidades;
import esbam.canoa123.model.Passageiros;
import esbam.canoa123.model.Passagens;
import esbam.canoa123.model.Rotas;

// CLASSES (JAVA)
import java.util.ArrayList;

import java.util.Date;

/**
 *
 * @author Jonny Nunes Viana
 */
public class PassagensController {
    java.sql.Connection con;
    
    /**
     * 
     * @param valor
     * @param rota
     * @param passageiro
     * @param dataCompra
     * @return 
     */
    public boolean validaPassagem(float valor, Rotas rota, Passageiros passageiro, Date dataCompra){
        restabeleceConexao();
        if(verificaConexao() && (valor != 0f && valor > 0f) && (rota != null) && (passageiro != null) && dataCompra != null){
            try {
                Passagens passagem = new Passagens(passageiro, rota, dataCompra);
                passagem.cadastrar(passagem);
                fechaConexao(con);
                return true;
            } catch (ExceptionDAO ex) {
                fechaConexao(con);
                return false;
            }
        }else{
            fechaConexao(con);
            return false;
        }
    }
    
    public boolean excluirPassagem(int id){
        if(id > 0){
            try {
                Passagens passagem = new Passagens();
                passagem.setId(id);
                passagem.excluir(passagem);
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
     * @param id
     * @return 
     */
    public Passagens joinTableRCC(int id){
        try {
            return new Passagens().joinTabelasRotaCidadeCategoria(id);
        } catch (ExceptionDAO e) {
            return null;
        }
    }
    
    /**
     * 
     * @return 
     */
    public ArrayList<Passagens> listaPassagens(){
        try {
            return new Passagens().buscaLista();
        } catch (ExceptionDAO e) {
            throw new RuntimeException("Erro ao excutar o metodo buscaLista() da passagem");
        }
    }
    
    /**
     * Método para verificar se há campos nulos quando estiver registrando um passageiro.
     * @param valor
     * @param objOrigem
     * @param objDestino
     * @param objDate
     * @return True se pelo menos um campo e nulo, caso ao contrario retorna false
     */
    public boolean verificarCamposNull(String valor, Cidades objOrigem, Cidades objDestino, Date objDate){
        if(objOrigem.getLocalidade().equals("") || objDestino.getLocalidade().equals("") || valor.equals("") || objDate.toString().equals("")){
            return true;
        }else{
            return false;
        }
    }
    
    // *************************************************************************************
    private void restabeleceConexao(){ con = esbam.canoa123.controll.dao.conexao.ConnectionMVC.getConnetion();}
    
    private void fechaConexao(java.sql.Connection conn){
        new esbam.canoa123.controll.dao.conexao.ConnectionMVC().closeConnection(conn);   
    }
    
    private boolean verificaConexao(){
        if(con != null){
            restabeleceConexao();
        }
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
}
