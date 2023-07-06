package esbam.canoa123.model;

import esbam.canoa123.controll.PassageirosController;
import esbam.canoa123.controll.RotasController;
import esbam.canoa123.controll.dao.PassagemDAO;
import esbam.canoa123.controll.dao.exceptions.ExceptionDAO;
import java.util.ArrayList;
import java.util.Date;
/**
 *
 * @author ACER
 */
public class Passagens{
    
    private int id;
    private Passageiros id_passageiro;
    private Rotas id_rota;
    private Date dataCompra;
    
    public Passagens(){}

    public Passagens(Passageiros id_passageiro, Rotas id_rota, Date dataCompra) {
        this.id_passageiro = id_passageiro;
        this.id_rota = id_rota;
        this.dataCompra = dataCompra;
    }

    public int getId() {
        return id;
    }

    public Passageiros getId_passageiro() {
        return id_passageiro;
    }

    public Rotas getId_rota() {
        return id_rota;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_passageiro(Passageiros id_passageiro) {
        this.id_passageiro = id_passageiro;
    }

    public void setId_rota(Rotas id_rota) {
        this.id_rota = id_rota;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    } 
    
    private ArrayList<Passagens> listagemPassagens(java.sql.ResultSet pResult) throws Exception{
        ArrayList<Passagens> passagens = new ArrayList<>();
            try {
                while(pResult.next()){                  
                    Rotas rota = new RotasController().consultaID(pResult.getInt(2));
                    Passageiros passageiro = new PassageirosController().buscaPassageiro(pResult.getInt(3));
                    Date data = pResult.getDate(4);
                    Passagens passagem = new Passagens(passageiro, rota, data);
                    passagem.setId(pResult.getInt(1));
                    passagens.add(passagem);
                }
            } catch (java.sql.SQLException e) {
                throw new RuntimeException("Erro ao atribuir os dados da passagem");
            }
        pResult.close();
        return passagens;
    }
    
    // ************************************************************
    
    /**
     * 
     * @param passagem
     * @throws ExceptionDAO 
     */
    public void cadastrar(Passagens passagem) throws ExceptionDAO{
        try {
            new PassagemDAO<>().inseri(passagem);
        } catch (ExceptionDAO e) {
            throw new ExceptionDAO("Erro ao executar o inseri() da passagem");
        }        
    }
    
    /**
     * 
     * @param passagem
     * @return
     * @throws ExceptionDAO 
     */
    public boolean excluir(Passagens passagem)  throws ExceptionDAO{
        try {
            new PassagemDAO<>().deleta(passagem);
            return true;
        } catch (ExceptionDAO e) {
            System.out.println(e.getCause());
            throw new ExceptionDAO("Erro em executar o deleta da passagem");
        }
    }
    
    /**
     * 
     * @return
     * @throws ExceptionDAO 
     */
    public ArrayList<Passagens> buscaLista() throws ExceptionDAO{
        try {
            ArrayList<Passagens> passagens = listagemPassagens(new PassagemDAO<>().lista());
            return passagens;
        } catch (ExceptionDAO e) {
            throw new ExceptionDAO("Erro ao executar o new PassagemDAO<>().lista()");
        } catch (Exception e) {
            throw new ExceptionDAO("Erro ao executar a listagemPassagens()");
        }
    }
    
    /**
     * 
     * @param id
     * @return
     * @throws ExceptionDAO 
     */
    public Passagens joinTabelasRotaCidadeCategoria(int id) throws ExceptionDAO{
        try {
            java.sql.ResultSet resultJoin = new PassagemDAO<>().joinTabelaRotaCidadeCategoria(id);
            int idPassagem = resultJoin.getInt(1);
            Cidades origem = new Cidades().buscaLocalidade(resultJoin.getString(2));
            Cidades destino = new Cidades().buscaLocalidade(resultJoin.getString(3));
            Categorias categoria = new Categorias().consultaNome(resultJoin.getString(4));
            float valor = resultJoin.getFloat(5);
            Date data = resultJoin.getDate(6);
            Rotas rota = new Rotas(valor, origem, destino, categoria);
            Passagens passagem = new Passagens();
            passagem.setId_rota(rota);
            passagem.setId(idPassagem);
            passagem.setDataCompra(data);
            resultJoin.close();
            return passagem;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao executar o new PassagemDAO<>().joinTabelaRotaCidadeCategoria()");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao executar o new PassagemDAO<>().joinTabelaRotaCidadeCategoria()");
        }
    }
}
