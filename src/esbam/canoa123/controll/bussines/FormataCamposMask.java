package esbam.canoa123.controll.bussines;

import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

/**
 * Classe aos campos foramatado para definir uma formatação específica.
 * @author Jonny Nunes Viana
 */
public class FormataCamposMask {
    
    /**
     * Método para a formatação para CPF.
     * @param campo Um objeto da classe JFormattedTextField.
     * @throws java.text.ParseException
     */
    public void fieldCPF(JFormattedTextField campo) throws ParseException{
        try {
            MaskFormatter mask = new MaskFormatter("###.###.###-##");
            mask.install(campo);
        } catch (ParseException e) {
            throw new ParseException(e.getLocalizedMessage(), 0);
        }
    }
    
    /**
     * Método para a formatação para Data
     * @param campo
     * @throws ParseException 
     */
    public void fieldData(JFormattedTextField campo) throws ParseException {
        try {
            MaskFormatter mask = new MaskFormatter("##/##/####");
            mask.install(campo);
        } catch (ParseException e) {
            throw new ParseException(e.getLocalizedMessage(), 0);
        }
    }
}
