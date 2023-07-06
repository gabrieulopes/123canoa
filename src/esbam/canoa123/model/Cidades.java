package esbam.canoa123.model;

import esbam.canoa123.controll.dao.CidadesDAO;
import esbam.canoa123.controll.dao.exceptions.ExceptionDAO;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ACER
 */
public class Cidades{
    
    /// ATRIBUTOS DA CLASSE
    private int id;
    private String localidade;
    private String estado;
    
    public Cidades(){}
    
    public Cidades(String localidade, String estado) {
        this.localidade = localidade;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public String getLocalidade() {
        return localidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    /**
     * Método para retornar a listagem de todos as cidades consultadas pelo parâmetro. Um método
     * comum rodando a Array e atribuindo os dados da cidades consultadas ao novo Array de retorno.
     * @param cResult
     * @return
     * @throws Exception 
     */
    private ArrayList<Cidades> listagemCidades(java.sql.ResultSet cResult) throws Exception{
        ArrayList<Cidades> cidades = new ArrayList<>();
            try {
                while(cResult.next()){
                    Cidades cidade = new Cidades(cResult.getString(2), cResult.getString(3));
                    cidade.setId(cResult.getInt(1));
                    cidades.add(cidade);
                }
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao atribuir os dados na Cidade");
            }
        cResult.close();
        return cidades;
    }
    
    // *********************************************************************
    
    /**
     * 
     * @param cidade 
     * @throws esbam.canoa123.controll.dao.exceptions.ExceptionDAO 
     */
    public void cadastrar(Cidades cidade) throws ExceptionDAO{        
        try {
            new CidadesDAO().inseri(cidade);
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao executar o metodo inseri()");
        }
    }
    
    /**
     * 
     * @param cidade
     * @return 
     */
    public boolean exluir(Cidades cidade) throws ExceptionDAO{
        try {
            new CidadesDAO<>().deleta(cidade);
            return true;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao executar o método deleta()");
        }        
    }
    
    /**
     * 
     * @param cidade
     * @return 
     */
    public boolean atualiza(Cidades cidade) throws ExceptionDAO{
        try {
            new CidadesDAO<>().altera(cidade);
            return true;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao executar método altera()");
        }
    }
    
    /**
     * 
     * @param id
     * @return 
     */
    public Cidades buscar(int id) throws ExceptionDAO{
        try {
            java.sql.ResultSet cResult = new CidadesDAO().consulta(id);
            Cidades cidade = new Cidades(cResult.getString(2), cResult.getString(3));
            cidade.setId(cResult.getInt(1));
            cResult.close();
            return cidade;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao executar o método consulta()");
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ExceptionDAO("Erro ao atribuir os dados na Cidade");
        }
    }
    
    /**
     * 
     * @param cidade
     * @return 
     */
    public Cidades buscaLocalidade(String localidade) throws ExceptionDAO{
        try {
            java.sql.ResultSet cResult = new CidadesDAO().consultaLocalidade(localidade);
            Cidades cidade = new Cidades(cResult.getString("localidade"), cResult.getString("estado"));       
            cidade.setId(cResult.getInt("id"));
            cResult.close();
            return cidade;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao executar o método consultaLocalidade()");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao atribuir os dados na cidade");
        }
    }
    
    /**
     * 
     * @return 
     */
    public ArrayList<Cidades> listaCidades() throws ExceptionDAO{  
        try {
            ArrayList<Cidades> cidades = listagemCidades(new CidadesDAO<>().lista());
            return cidades;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao executar o método lista()");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Erro ao executar o método listagemCidades()");
        }   
    }
    
    /**
     * 
     * @param localidade
     * @param estado
     * @return 
     */
    public ArrayList<Cidades> listaLocalidadeEstado(String localidade, String estado) throws ExceptionDAO{
        try {
            ArrayList<Cidades> cidades = listagemCidades(new CidadesDAO<>().listaLocalidadeEstado(localidade, estado));
            return cidades;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao executar o método listaLocalidadeEstado()");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Erro ao executar o método listagemCidades()");
        }
    }
    
    /**
     * 
     * @param nomeLocalidade
     * @return 
     * @throws esbam.canoa123.controll.dao.exceptions.ExceptionDAO 
     */
    public ArrayList<Cidades> listaLocalidade(String localidade) throws ExceptionDAO{
        try {
            ArrayList<Cidades> cidades = listagemCidades(new CidadesDAO<>().listaLocalidade(localidade));
            return cidades;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao executar o método listarLocalidade()");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Erro ao executar o método listagemCidades()");
        }
    }
    
    /**
     * 
     * @param cidade
     * @return 
     */
    public ArrayList<Cidades> listaEstado(String estado) throws ExceptionDAO{
        try {
            ArrayList<Cidades> cidades = listagemCidades(new CidadesDAO<>().listaEstado(estado));
            return cidades;
        } catch (ExceptionDAO e) {
            throw new ExceptionDAO("Erro ao executar o método listaEstado()");
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao executar o método listagemCidades()");
        }
    }
     
}
