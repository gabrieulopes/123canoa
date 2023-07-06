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
package esbam.canoa123.controll.bussines;

import esbam.canoa123.controll.dao.exceptions.ExceptionCpf;
import esbam.canoa123.controll.dao.exceptions.ExceptionDAO;
import esbam.canoa123.model.Passageiros;

/**
 *
 * @author ACER
 */
public class CpfRepeat {
   
    /**
     * Método para verificar se existe um passageiro do cpf especificado no parâmetro.
     * @param cpf String em referência do cpf de um passageiro.
     * @return True se um passageiro foi achado pelos dados do cpf passado, caso ao contrário retorna false.
     * @throws esbam.canoa123.controll.dao.exceptions.ExceptionCpf
     */
    public void passageiroCPF(String cpf) throws ExceptionCpf{   
        try {
            new Passageiros().buscarCPF(cpf);
        } catch (ExceptionDAO ex) {
            ex.printStackTrace();
            throw new ExceptionCpf("Erro: Cpf nao existe");
        } 
    }
}
