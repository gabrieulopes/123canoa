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
package esbam.canoa123.controll;

import esbam.canoa123.controll.dao.exceptions.ExceptionDAO;
import esbam.canoa123.model.Usuarios;
import esbam.canoa123.controll.bussines.LoginRepeat;
import esbam.canoa123.controll.dao.exceptions.ExceptionLogin;

/**
 *
 * @author ACER
 */
public class UsuariosController {  
    private java.sql.Connection con;
    
    /**
     * Metodo para validar os dados do usuario a ser cadastrado. E preciso ressaltar
     * que cada usuario cadastrado pelo adm tera o atributo de acesso como <strong>false</strong>.
     * Lembra-se tambem que na regra de negocio um usuario nao pode ter o mesmo login.
     * @param nome String do nome do usuario a ser cadastrado.
     * @param login String do login do usuario a ser cadastrado.
     * @param senha String da senha do usuario a ser cadastrado.
     * @param perfil String do perfil, com o padrao de <strong>User</strong>, do usuario a ser cadastrado.
     * @return True se os dados foram inserido com sucesso, caso ao contrario retorna false.
     */
    public boolean validar(String nome, String login, String senha, String perfil){
        if(verificaConexao() && (!verificaNullCampos(nome, login, senha, perfil)) && (!validaLogin(login))){
            try {
                Usuarios user = new Usuarios(nome, login, senha, false, perfil);
                user.cadastrar(user);
                return true;
            } catch (ExceptionDAO e) {
                return false;
            }finally{
                fechaConexao(con);
            }
        }else{
            fechaConexao(con);
            return false;
        }
    }
    
    public boolean validaLogin(String login){
        try {
            new LoginRepeat().UserLogin(login);
            return true;
        } catch (ExceptionLogin ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    /**
     * Retorna o usuario correspodente aos dados do login e da senha passado nos parâmetros.
     * @param login String em referência ao login do objeto.
     * @param senha String em referência a senha do objeto.
     * @return Um objeto usuario da classe Usuarios, do qual tem todo o registro da tupla buscada pelos dadcos do login e senha.
     */
    public Usuarios usuarioLoginSenha(String login, String senha){
        try {
            return new Usuarios().usuarioLoginSenha(login, senha);
        } catch (ExceptionDAO ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    /**
     * Método para alterar o acesso de um usuario, colocando como true (1) ou false (0) de
     * um objeto da classe Usuarios.
     * @param user Objeto da classe Usuarios (não pode ser nulo).
     * @param valor O valor booleano de acesso, true se o tem o acesso ou false se não tem o acesso.
     * @return True se o usuario passado como parametro teve o acesso alterado, caso ao contrario retorna false.
     */
    public boolean alteraAcessoUsuario(Usuarios user, boolean acesso){
        if(user != null){
            try{ 
                boolean sucesso = new Usuarios().alteraUsuario(user.getId(), acesso);
                return sucesso;
            }catch(ExceptionDAO ex){
                System.out.println(ex.getClass());
                return false;
            }
        }else{
            return false;
        }   
    }
    
    /**
     * Método para modificar o acesso do usuário que está longado. É verificado o usuario passado
     * no primeiro parâmetro e se o mesmo é ADM ou USER (dados do segundo parâmetro).
     * Depois é modificado/alterado o acesso do usuário se for validado.
     * @param user Objeto usuário da classe Usuarios.
     * @param userAcess String do usuário de acesso, ADM ou USER.
     * @return True se validado e altera o acesso do usuário, ao contrário retorna false.
     */
    public boolean usuarioLoginAcesso(Usuarios user, String userAcess){
        if(user != null && user.getPerfil().equalsIgnoreCase(userAcess)){
            this.alteraAcessoUsuario(user, true);
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Método para verificar quem está com o acesso, no caso quem tem o acesso o valor é igual a true, mas também pode haver false.
     * O ideal do método é verifica o user que tem o acesso igual true, podendo ser reutilizado para outros fins.
     * @param acesso Boolean em referência ao acesso do usuário, true o usuario com acesso ou false com usuario sem acesso.
     * @return Um objeto usuário da classe Usuarios, do qual tem em seu campo de acesso o valor passado no parâmetro.
     */
    public Usuarios verificaAcessoUsuario(boolean acesso){
        try {
            return new Usuarios().usuarioAcesso(acesso);
        } catch (ExceptionDAO ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    /**
     * Método para resetar os acessos de todos os usuários do banco. O método reseta para false, a fim de retirar o acesso.
     * Baseando-se no id 1 até o último id do banco.
     * @return True se foi resetado com sucesso, caso ao contrário retorna false.
     */
    public boolean resetaAcessos(){
        try {
            return new Usuarios().resetaAcesso();
        } catch (ExceptionDAO ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    /**
     * Método para verificar se há pelo menos um objeto nulo dos objetos fornecidos nos parâmetros.
     * @param nome String em referência ao nome do objeto.
     * @param login String em referência ao login do objeto.
     * @param senha String em referência a senha do objeto.
     * @param perfil String em referência ao perfil do objeto.
     * @return True se houver pelo menos um objeto vazio (""), caso ao contrário retorna false.
     */
    public boolean verificaNullCampos(String nome, String login, String senha, String perfil){
        if(nome.isEmpty() || login.isEmpty() || senha.isEmpty() || perfil.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Método para verificar se há pelo menos um objeto nulo dos objetos fornecidos nos parâmetros.
     * @param login String em referência ao login do objeto.
     * @param senha String em referência a senha do objeto.
     * @return True se houver pelo menos um objeto vazio (""), caso ao contrário retorna false.
     */
    public boolean verificaNullCampos(String login, String senha){
        if(login.equals("") || senha.equals("")){
            return true;
        }else{
            return false;
        }
    }

    // **********************************************************************************
    
    private void restabeleceConexao(){ con = new esbam.canoa123.controll.dao.conexao.ConnectionMVC().getConnetion(); }
    
    private void fechaConexao(java.sql.Connection conn){ new esbam.canoa123.controll.dao.conexao.ConnectionMVC().closeConnection(conn); }
    
    /**
     * 
     * @return 
     */
    private boolean verificaConexao(){
        restabeleceConexao();
        try{
            if(con == null || con.isValid(2) == false){ // VERIFICANDO A CONEXÃO
                return false;
            }
        }catch(java.sql.SQLException exSQL){
            System.out.println(exSQL.getMessage() + " - " + exSQL.getCause());
        }
        return true;
    }
}
