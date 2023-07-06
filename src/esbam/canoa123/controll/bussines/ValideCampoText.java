package esbam.canoa123.controll.bussines;

/**
 * Classe para validar campos de entradas de dados específicos e aceitáveis. Bloquando algumas entradas de dados ou parando o processo.
 * Essa classe é abragente, baseando-se na regra de negócio e definindo o processo ou retornando resultado.
 * @author Jonny Nunes Viana
 */
public class ValideCampoText {
    
    /**
     * Valida campo para somente números.
     * @param evt Um objeto da classe de evento KeyEvent.
     */
    public void validaCamposComNumero(java.awt.event.KeyEvent evt) {
        // SOMENTE OS DADOS ACEITADOS
        String caracts = "0123456789";
        // SE OS DADOS FOR UM NUMERO, QUERO QUE ELE PROCESSA, CASO AO OCNTRARIO NÃO PROCESSA
        if (!caracts.contains(evt.getKeyChar() + "")) { // ANALISAR OS CARACTERES
            evt.consume(); // NÃO PROCESSA
        }
    }
    
    /**
     * Validar campo em tamanho padrão do telefone, definindo a capacidade total de dados.
     * Neste caso, o tamanho máximo é 10.
     * @param evt Um objeto da classe de evento KeyEvent.
     * @param txtTel Um objeto da classe JTextField
     */
    public void validaCampoTelefone(java.awt.event.KeyEvent evt, javax.swing.JTextField txtTel){
        if(txtTel.getText().length() == 10){
            evt.consume();
        }
    }
    
    /**
     * Método para validar campo com um estado de um país. No caso é de forma abreviada, possuindo só dois digitos.
     * @param evt Um objeto da classe de evento KeyEvent.
     * @param txtEstado Um objeto da classe JTextField.
     */
    public void validaCampoEstado(java.awt.event.KeyEvent evt, javax.swing.JTextField txtEstado){
        if(txtEstado.getText().length() == 2){
            evt.consume();
        }   
    }
    
    /**
     * Metodo para validar se o campo de login possui no minino 8 digitos.
     * @param login
     * @return True se nao possui no minino 8 digitos, caso ao contrario retorna false.
     */
    public boolean validaCampoLogin(String login){
        if(login.length() < 8){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Metodo para validar se o campo da senha possui no minino 8 digitos.
     * @param senha
     * @return True se nao possui no minino 8 digitos, caso ao contrario retorna false.
     */
    public boolean validaCampoSenha(String senha){
        if(senha.length() < 8){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Método para validar se o campo de cpf tem no máximo 14 dgitos de entrada. Além
     * de verificar se não é um campo vazio e se entre seus digitos não há espaço em branco.
     * @param cpf String em refência ao cpf de um passageiro.
     * @return 
     */
    public boolean validaCampoCpf(String cpf){
        if((!cpf.startsWith(" ")) && (!cpf.isEmpty()) && (!(cpf.length() < 14))){   
            return true;
        }else{
            return false;
        }
    }
}
