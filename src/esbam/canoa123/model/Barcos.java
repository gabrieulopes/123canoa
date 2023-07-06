package esbam.canoa123.model;

import esbam.canoa123.controll.dao.BarcosDAO;
import esbam.canoa123.controll.dao.exceptions.ExceptionDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Barcos{

    private int id;
    private String nome;
    private int capacidade;
    
    public Barcos(){}

    public Barcos(String nome, int capacidade) {
        this.nome = nome;
        this.capacidade = capacidade;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }
    
    /**
     * 
     * @param catResult
     * @return
     * @throws Exception 
     */
    private ArrayList<Barcos> listagemBarcos(ResultSet bResult) throws Exception{
        ArrayList<Barcos> barcos = new ArrayList<>();
        try {
            while(bResult.next()){
                Barcos barco = new Barcos(bResult.getString(2), bResult.getInt(3));
                barco.setId(bResult.getInt(1));
                barcos.add(barco);
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atribuir os dados na Categorias");
        }
        bResult.close();
        return barcos;
    }
    
    // *****************************************************************
    
    /**
     * 
     * @param barco 
     */
    public void cadastra(Barcos barco) throws ExceptionDAO{
        try {
            new BarcosDAO<>().inseri(barco);
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro na inserção do barco");
        }
    }
    
     /**
     * 
     * @param id
     * @return 
     */
    public Barcos busca(int id) throws ExceptionDAO{
        try {
            ResultSet bResult = new BarcosDAO<>().consulta(id);
            Barcos barco = new Barcos(bResult.getString("nome"), bResult.getInt("capacidade"));
            barco.setId(bResult.getInt("id"));
            bResult.close();
            return barco;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em buscar o barco");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em atribuir os dados ao barco");
        }
    }
    
    /**
     * 
     * @param bacos 
     */
    public boolean excluir(Barcos barco) throws ExceptionDAO{
        try {
            new BarcosDAO<>().deleta(barco);
            return true;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em excluir o barco");
        }
    }
    
    /**
     * 
     * @param barco
     * @return 
     */
    public boolean atualiza(Barcos barco) throws ExceptionDAO{
        try {
            new BarcosDAO<>().altera(barco);
            return true;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em alterar o barco");
        }
    }
    
    /**
     * 
     * @return 
     */
    public ArrayList<Barcos> lista() throws ExceptionDAO{
        try {
            ArrayList<Barcos> barcos = listagemBarcos(new BarcosDAO<>().lista());
            return barcos;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em executar o método listagemBarcos()");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em atribuir os dados ao barco");
        }
    }
    
    /**
     * 
     * @param capacidade
     * @return 
     */
    public ArrayList<Barcos> listaCapacidade(int capacidade) throws ExceptionDAO{
        try {
            ArrayList<Barcos> barcos = listagemBarcos(new BarcosDAO<>().listarCapacidade(capacidade));
            return barcos;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em buscar os barcos pela capacidade do método");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em buscar os barcos pela capacidade");
        }
    }
    
    /**
     * 
     * @param nome
     * @param capacidade
     * @return 
     */
    public ArrayList<Barcos> listaNomeCapacidade(String nome, int capacidade) throws ExceptionDAO{
        try {
            ArrayList<Barcos> barcos = listagemBarcos(new BarcosDAO<>().listaNomeCapacidade(nome, capacidade));
            return barcos;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em buscar os barcos pela nome e capacidade do método");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em buscar os barcos pela nome e capacidade");
        }
    }
    
    /**
     * 
     * @param barco
     * @return 
     */
    public ArrayList<Barcos> listaNome(String nome) throws ExceptionDAO{
        try {
            ArrayList<Barcos> barcos = listagemBarcos(new BarcosDAO<>().listaNome(nome));
            return barcos;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em buscar os barcos pela nome do método");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em buscar os barcos pela nome");
        }
    }
}
