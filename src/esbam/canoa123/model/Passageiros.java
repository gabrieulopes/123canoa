package esbam.canoa123.model;

import esbam.canoa123.controll.dao.PassageirosDAO;
import esbam.canoa123.controll.dao.exceptions.ExceptionDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ACER
 */
public class Passageiros{
    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String sexo;
    
    public Passageiros(){
    }

    public Passageiros(String nome, String cpf, String telefone, String sexo) {
        this.sexo = sexo;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getSexo() {
        return sexo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    private ArrayList<Passageiros> listagemPassageiros(java.sql.ResultSet cResult) throws Exception{
        ArrayList<Passageiros> passageiros = new ArrayList<>();
            try {
                while(cResult.next()){
                    Passageiros passageiro = new Passageiros(cResult.getString(2), cResult.getString(3), cResult.getString(4), cResult.getString(5));
                    passageiro.setId(cResult.getInt(1));
                    passageiros.add(passageiro);
                }
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao atribuir os dados na Cidade");
            }
        cResult.close();
        return passageiros;
    }
    
    // ********************************************************
    
    /**
     * 
     * @param passageiro 
     * @throws esbam.canoa123.controll.dao.exceptions.ExceptionDAO 
     */
    public void cadastrar(Passageiros passageiro) throws ExceptionDAO {
        try {
            new PassageirosDAO<>().inseri(passageiro);
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao inserir os dados do passageiro");
        }
        
    }
    
    /**
     * 
     * @param passageiro
     * @return 
     * @throws esbam.canoa123.controll.dao.exceptions.ExceptionDAO 
     */
    public boolean excluir(Passageiros passageiro) throws ExceptionDAO {
        try {
            new PassageirosDAO<>().deleta(passageiro);
            return true;
        } catch (ExceptionDAO e) {
            throw new ExceptionDAO("Erro em excutar o metodo deleta()");
        }
    }
    
    /**
     * 
     * @param id
     * @return 
     * @throws esbam.canoa123.controll.dao.exceptions.ExceptionDAO 
     */
    public Passageiros consulta(int id) throws ExceptionDAO {
        try {
            java.sql.ResultSet pResult = new PassageirosDAO<>().consulta(id);
            Passageiros passageiro = new Passageiros(pResult.getString(2), pResult.getString(3), pResult.getString(4), pResult.getString(5));
            passageiro.setId(pResult.getInt(1));
            return passageiro;
        }catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao buscar o passageiro do id especificado");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao buscar o passageiro do id especificado");
        }
    }
    
    /**
     * 
     * @param passageiro
     * @throws ExceptionDAO 
     */
    public void atualiza(Passageiros passageiro) throws ExceptionDAO{
        try {
            new PassageirosDAO<>().altera(passageiro);
        } catch (ExceptionDAO e) {
            throw new ExceptionDAO("Erro em executar o altera()");
        }  
    }
    
    /**
     * 
     * @param cpf
     * @return 
     * @throws esbam.canoa123.controll.dao.exceptions.ExceptionDAO 
     */
    public Passageiros buscarCPF(String cpf) throws ExceptionDAO {
        try {
            java.sql.ResultSet pResult = new PassageirosDAO<>().consultaCPF(cpf);
            Passageiros pssageiro = new Passageiros(pResult.getString(2), pResult.getString(3), pResult.getString(4), pResult.getString(5));
            pssageiro.setId(pResult.getInt(1));
            pResult.close();
            return pssageiro;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em buscar o pasageiro pelo cpf");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em atribuir os dados do passageiro");
        }
    }
    
    /**
     * 
     * @return 
     * @throws esbam.canoa123.controll.dao.exceptions.ExceptionDAO 
     */
    public Passageiros buscarUltimoID() throws ExceptionDAO {
        try {
            java.sql.ResultSet pResult = new PassageirosDAO<>().consultaUltimoID();
            Passageiros passageiro = new Passageiros();
            passageiro.setId(pResult.getInt(1));
            pResult.close();
            return passageiro;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em buscar o ultimo passageiro");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em atribuir os dados do passageiro");
        }
    }
    
    /**
     * 
     * @return 
     * @throws esbam.canoa123.controll.dao.exceptions.ExceptionDAO 
     */
    public ArrayList<Passageiros> lista() throws ExceptionDAO {
        try {
            ArrayList<Passageiros> passageiros = listagemPassageiros(new PassageirosDAO<>().lista());
            return passageiros;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao listar os passageiros");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em atribuir os dados dos passageiros");
        }
    }
    
    /**
     * 
     * @param nome
     * @return
     * @throws ExceptionDAO 
     */
    public ArrayList<Passageiros> listaNome(String nome) throws ExceptionDAO{
        try {
            ArrayList<Passageiros> passageiros = listagemPassageiros(new PassageirosDAO<>().listaNome(nome));
            return passageiros;
        } catch (ExceptionDAO e) {
            throw new ExceptionDAO("Erro em listar os nomes no model");
        } catch (Exception ex) {
            throw new ExceptionDAO("Erro em atribuir os dados dos nomes dos passageiros");
        }
    }
    
    /**
     * 
     * @param sexo
     * @return
     * @throws ExceptionDAO 
     */
    public ArrayList<Passageiros> listaSexo(String sexo) throws ExceptionDAO{
        try {
            ArrayList<Passageiros> passageiro = listagemPassageiros(new PassageirosDAO<>().listaSexo(sexo));
            return passageiro;
        } catch (ExceptionDAO e) {
            throw new ExceptionDAO("Erro em listar os sexos dos passageiros no model");
        } catch (Exception ex) {
            throw new ExceptionDAO("Erro em atribuir os dados dos sexos dos passageiro no model");
        }
    }
    
    /**
     * 
     * @param tell
     * @return
     * @throws ExceptionDAO 
     */
    public ArrayList<Passageiros> listaTelefone(String tell) throws ExceptionDAO{
        try {
            ArrayList<Passageiros> passageiro = listagemPassageiros(new PassageirosDAO<>().listaTelefone(tell));
            return passageiro;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em listar os telefones dos passageiros");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ExceptionDAO("Erro em atribuir os dados dos telefones dos passageiros");
        }
    }
}