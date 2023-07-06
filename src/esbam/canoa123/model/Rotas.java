package esbam.canoa123.model;

import esbam.canoa123.controll.dao.RotaDAO;
import esbam.canoa123.controll.dao.exceptions.ExceptionDAO;

/**
 *
 * @author ACER
 */
public class Rotas{
    private int id;
    private float valor;
    private Cidades origem;
    private Cidades destino;
    private Categorias categoria;
    
    public Rotas(){}

    public Rotas(float valor, Cidades origem, Cidades destino, Categorias categoria) {
        this.valor = valor;
        this.origem = origem;
        this.destino = destino;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public float getValor() {
        return valor;
    }

    public Cidades getOrigem() {
        return origem;
    }

    public Cidades getDestino() {
        return destino;
    }

    public Categorias getCategoria() {
        return categoria;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public void setOrigem(Cidades origem) {
        this.origem = origem;
    }

    public void setDestino(Cidades destino) {
        this.destino = destino;
    }

    public void setCategoria(Categorias categoria) {
        this.categoria = categoria;
    }
    
    // ****************************************************
    /**
     * 
     * @param rota 
     */
    public void cadastrar(Rotas rota) throws ExceptionDAO{
        try {
            new RotaDAO<>().inseri(rota);
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao inserir a rota");
        }  
    }
    
    /**
     * Método para consulta uma Rota atráves do id especificado.
     * @param id
     * @return 
     * @throws esbam.canoa123.controll.dao.exceptions.ExceptionDAO 
     */
    public Rotas consulta(int id) throws ExceptionDAO{
        try {
            java.sql.ResultSet pResult = new RotaDAO<>().consulta(id);
            float valor = pResult.getFloat(2);
            Cidades origem = new Cidades().buscar(pResult.getInt(3));
            Cidades destino = new Cidades().buscar(pResult.getInt(4));
            Categorias categoria = new Categorias().busca(pResult.getInt(5));
            Rotas rota = new Rotas(valor, origem,destino, categoria);
            rota.setId(pResult.getInt(1));
            pResult.close();
            return rota;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao buscar a Rota do id especificado");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em atribuir os dados na rota");
        }
    }
    
    /**
     * Método para consulta uma Rota pelo objetos da cidade de origem e cidade de destino.
     * @param origem
     * @param destino
     * @return 
     * @throws esbam.canoa123.controll.dao.exceptions.ExceptionDAO 
     */
    public Rotas consultaOrigemDestino(Cidades origem, Cidades destino) throws ExceptionDAO{
        try {
            java.sql.ResultSet pResult = new RotaDAO<>().consultaRotaOrigemDestino(origem, destino);
            int id = pResult.getInt("id");
            float valor = pResult.getFloat("valor");                
            Cidades cidadeOrigem = new Cidades().buscar(pResult.getInt("id_origem"));
            Cidades cidadeDestino = new Cidades().buscar(pResult.getInt("id_destino"));
            Categorias categoria = new Categorias().busca(pResult.getInt("id_categoria"));
            Rotas rota = new Rotas(valor,cidadeOrigem,cidadeDestino,categoria);
            rota.setId(id);
            pResult.close();
            return rota;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao buscar a Rota");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em atribuir os dados na Rota");
        }
    }
}
