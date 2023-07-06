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
package esbam.canoa123.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe para tratar dos dados, criptografando e outros fins.
 * @author Jonny Nunes Viana
 * @since 1.0
 */
public class SecurityDada {
    
    /**
     * Método para criptografar dados de uma string, o qual o Hast do dado é gerado e passado para a função.
     * Sendo que o Hast também é convertido para hexadecimal.
     * @param str String do qual deseja criptografar, exemplo de senha ou mensagem.
     * @return String criptografada em formato hexadecimal.
     */
    public String criptString(String str){
        String ecoding = "UTF-8";
        try {
            MessageDigest alg = MessageDigest.getInstance("SHA-512");
            byte[] hastByte = alg.digest(str.getBytes(ecoding));      
            StringBuilder sd = new StringBuilder();
            for (byte b : hastByte){
                sd.append(String.format("%02X", 0xFF & b)); // CONVERTER PARA HEXADECIMAL
            }
            return str = sd.toString();
        } catch (NoSuchAlgorithmException ex) {
            System.err.println(ex);
        } catch (UnsupportedEncodingException ex) {
            System.err.println(ex);
        }
        return null;
    }
}
