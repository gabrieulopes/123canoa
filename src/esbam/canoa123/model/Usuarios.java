/*
 * The MIT License
 *
 * Copyright 2023 ACER.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package esbam.canoa123.model;

import esbam.canoa123.controll.dao.UsuariosDAO;
import esbam.canoa123.controll.dao.exceptions.ExceptionDAO;

/**
 *
 * @author ACER
 */
public class Usuarios {
    
    private int id;
    private String nome;
    private String login;
    private String senha;
    private boolean acesso;
    private String perfil;
    
    public Usuarios(){}

    public Usuarios(String nome, String login, String senha, boolean acesso, String perfil) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.acesso = acesso;
        this.perfil = perfil;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setAcesso(boolean acesso) {
        this.acesso = acesso;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public boolean isAcesso() {
        return acesso;
    }

    public String getPerfil() {
        return perfil;
    }
    // **************************************************************************
    
    /**
     * Metodo para cadastrar/registrar um novo usuario.
     * @param user Um objeto usuario com seus dados.
     * @throws esbam.canoa123.controll.dao.exceptions.ExceptionDAO
     */
    public void cadastrar(Usuarios user) throws ExceptionDAO{
        try {
            new UsuariosDAO<>().inseri(user);
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro ao inserir os dados do usuario");
        }
        
    }
    
    /**
     * Metodo para retorna um usuario pelo login e senha passado.
     * @param login String do login do usuario
     * @param senha String da senha do usuario
     * @return Um objeto Usuarios se for encontrado pela login e senha passado, 
     * caso ao contrario retorna null
     * @throws esbam.canoa123.controll.dao.exceptions.ExceptionDAO
     */
    public Usuarios usuarioLoginSenha(String login, String senha) throws ExceptionDAO{
        try {
            java.sql.ResultSet userResult = new UsuariosDAO<>().consultaLoginSenha(login, senha);
            Usuarios user = new Usuarios(userResult.getString(2), userResult.getString(3), userResult.getString(4), userResult.getBoolean(5), userResult.getString(6));
            user.setId(userResult.getInt(1));
            userResult.close();
            return user;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em buscar o usuario pelo login e senha");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em atribuir os dados ao usuario");
        }
    }
    
    /**
     * 
     * @param login
     * @return 
     * @throws esbam.canoa123.controll.dao.exceptions.ExceptionDAO 
     */
    public Usuarios usuarioLogin(String login) throws ExceptionDAO{
        try {
            java.sql.ResultSet userResult = new UsuariosDAO<>().consultaLogin(login);
            Usuarios user = new Usuarios(userResult.getString(2), userResult.getString(3), userResult.getString(4), userResult.getBoolean(5), userResult.getString(6));
            user.setId(userResult.getInt(1));
            userResult.close();
            return user;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em buscar o usuario pelo login");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em atribuir os dadoss do usuario");
        }
    }
    
    /**
     * 
     * @param id
     * @param campo
     * @param valor
     * @return 
     * @throws esbam.canoa123.controll.dao.exceptions.ExceptionDAO 
     */
    public boolean alteraUsuario(int id, boolean acesso) throws ExceptionDAO{
        boolean sucesso = new UsuariosDAO<>().alteraAcesso(id, acesso);
        return sucesso;
    }
    
    /**
     * 
     * @return 
     * @throws esbam.canoa123.controll.dao.exceptions.ExceptionDAO 
     */
    public boolean resetaAcesso() throws ExceptionDAO{
        boolean sucesso = new UsuariosDAO<>().resetAcesso();
        return sucesso;
    }
    
    /**
     * 
     * @param acesso
     * @return 
     * @throws esbam.canoa123.controll.dao.exceptions.ExceptionDAO 
     */
    public Usuarios usuarioAcesso(boolean acesso) throws ExceptionDAO{
        try {
            java.sql.ResultSet userResult = new UsuariosDAO<>().consultaAcesso(acesso);
            Usuarios user = new Usuarios(userResult.getString(2), userResult.getString(3), userResult.getString(4), userResult.getBoolean(5), userResult.getString(6));
            user.setId(userResult.getInt(1));
            userResult.close();
            return user;
        } catch (ExceptionDAO e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em buscar pelo acesso espeficidado do usuario");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionDAO("Erro em atribuir os do usuario");
        }
    }
}
