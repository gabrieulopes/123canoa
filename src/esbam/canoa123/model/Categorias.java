package esbam.canoa123.model;

import esbam.canoa123.controll.dao.CategoriaDAO;
import esbam.canoa123.controll.dao.exceptions.ExceptionDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ACER
 */
public class Categorias{
    private int id;
    private String nome;
    
    public Categorias(){}

    public Categorias(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * 
     * @param catResult
     * @return
     * @throws Exception 
     */
    private ArrayList<Categorias> listagemCategorias(ResultSet catResult) throws Exception{
        ArrayList<Categorias> categorias = new ArrayList<>();
            try {
                while(catResult.next()){
                    Categorias categoria = new Categorias(catResult.getString(2));
                    categoria.setId(catResult.getInt(1));
                    categorias.add(categoria);
                }
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao atribuir os dados na Categorias");
            }
        catResult.close();
        return categorias;
    }
    
    // ************************************************************************
    
    /**
     * 
     * @param categoria
     * @throws ExceptionDAO 
     */
    public void cadastra(Categorias categoria) throws ExceptionDAO{
        try {
            new CategoriaDAO<>().inseri(categoria);
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao executar o método inseri");
        }
    }
    
    /**
     * 
     * @param nome
     * @return
     * @throws ExceptionDAO 
     */
    public Categorias consultaNome(String nome) throws ExceptionDAO{
        try {
            ResultSet catResult = new CategoriaDAO<>().consultaNome(nome);
            Categorias categoria = new Categorias(catResult.getString(2));
            categoria.setId(catResult.getInt(1));
            catResult.close();
            return categoria;
        } catch (ExceptionDAO | SQLException e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao executar a buscar pelo nome");
        }
    }
    
    /**
     * 
     * @return
     * @throws ExceptionDAO 
     */
    public ArrayList<Categorias> listarCategorias() throws ExceptionDAO{
        try {
            ArrayList<Categorias> categorias = listagemCategorias(new CategoriaDAO<>().lista());
            return categorias;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao lista as categorias");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em atribuir os dados das categorias");
        }
    }
    
    /**
     * 
     * @param categoria
     * @return
     * @throws ExceptionDAO 
     */
    public boolean deleta(Categorias categoria) throws ExceptionDAO{
        try {
            new CategoriaDAO<>().deleta(categoria);
            return true;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao deletaer a categoria");
        }
    }
    
    /**
     * 
     * @param id
     * @return
     * @throws ExceptionDAO 
     */
    public Categorias busca(int id) throws ExceptionDAO{
        try {
            ResultSet catResult = new CategoriaDAO<>().consulta(id);
            Categorias categoria = new Categorias(catResult.getString(2));
            categoria.setId(catResult.getInt(1));
            catResult.close();
            return categoria;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao consultar a categoria");
        }
    }
}
