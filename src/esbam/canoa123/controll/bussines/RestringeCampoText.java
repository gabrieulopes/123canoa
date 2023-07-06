package esbam.canoa123.controll.bussines;

// DESTINADA AO CAMPO DE TESTO, PELO EVENTO DE ENTRADA DE DADOS DO TECLADO
// REGRAS PARA OS DADOS AO CAMPO *********************************************

/**
 * Classe para restrigir/bloquear dados específicos. De modo que não processe os dados.
 * Baseando-se da regra de negócios.
 * @author Jonny Nunes Viana
 */
public class RestringeCampoText {
// *********************************************************************************************************
    
    /**
     * Método para restringir um campo dos números e caracteres especiais.
     * @param evt Um objeto da classe de evento KeyEvent.
     */
    public void restringirCampoDeNumeroEspecial(java.awt.event.KeyEvent evt) {    
        String caracts = "0123456789";
        String caractsEspecial = "!@#$%¨/?°\\|&*();.´`,~_][}{=+-><:^";
        if(caracts.contains(evt.getKeyChar() + "") || caractsEspecial.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }
    
// *********************************************************************************************************
}
