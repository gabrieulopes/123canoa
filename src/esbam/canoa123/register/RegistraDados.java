package esbam.canoa123.register;

// CLASSES (ESBAM)
import esbam.canoa123.controll.BarcosController;
import esbam.canoa123.controll.CategoriasController;
import esbam.canoa123.controll.CidadesController;
import esbam.canoa123.controll.PassageirosController;
import esbam.canoa123.controll.PassagensController;
import esbam.canoa123.controll.RotasController;
import esbam.canoa123.model.Categorias;
import esbam.canoa123.model.Cidades;
import esbam.canoa123.model.Passageiros;
import esbam.canoa123.model.Rotas;
import java.text.ParseException;

// CLASSES (JAVA)
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe para registrar as entidades do banco. Atribuindo entradas específicas em referência a entidade a ser registrada.
 * @author Jonny Nunes Viana
 */
public class RegistraDados{
    
    /**
     * Método destinado ao registro do barco. As entradas são objetos com a String em seu valor getText() facilitando as validações e evitando exeções.
     * @param nomeBarco Um objeto da classe JTextField em referênica ao nome do barco.
     * @param capacidadeBarco Um objeto da classe JTextField em referência a capacidade do barco.
     * @return True se foi registrado com sucesso, caso ao contrário retorna false.
     * @since 1.1
     */
    // BARCO
    public boolean registraBarco(JTextField nomeBarco, JTextField capacidadeBarco) throws Exception{       
        try {
            boolean validadoBarco = false;       
            if(new BarcosController().verificaCampoNullBarco(nomeBarco.getText(), capacidadeBarco.getText())){
                JOptionPane.showMessageDialog(null, "Nao pode haver campos vazios!", "Atencao", JOptionPane.WARNING_MESSAGE);
                return false;
            }    
            String nome = nomeBarco.getText();
            int capacidade = Integer.parseInt(capacidadeBarco.getText()); // INT NÃO RECEBE NULO
            validadoBarco = new esbam.canoa123.controll.BarcosController().validarBarco(nome, capacidade);
            if (validadoBarco) {
                javax.swing.JOptionPane.showMessageDialog(null, "Cadastrado com sucesso", "Sucesso no cadastro", javax.swing.JOptionPane.YES_NO_CANCEL_OPTION);
                return validadoBarco;
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Campos não preenchidos ou problema de conexão", "Não cadastrado", javax.swing.JOptionPane.OK_OPTION);
                nomeBarco.requestFocus(); // FOCAR NO CAMPO
                return validadoBarco;
            }
        } catch (Exception ex) {
            nomeBarco.requestFocus(); // FOCAR NO CAMPO
            javax.swing.JOptionPane.showMessageDialog(null, "Erro: " + ex, "Não cadastrado", javax.swing.JOptionPane.OK_OPTION);
            throw new Error("Erro ao tentar registrar o Barco");
        }
    }
    
    /**
     * Método destinado ao registro da rota.A rota se baseia em objetos de origem e destino (âmbos da classe Cidades). As entradas são objetos com a String em seu valor getText() facilitando as validações e evitando exeções.
     * @param categoriaRota
     * @param valorRota Um objeto da classe JTextField em referência ao valor da rota.
     * @param origemRota Um objeto da classe JTextField em referência a origem da rota.
     * @param destinoRota Um objeto da classe JTextField em referência ao destino da rota.
     * @return True se o registro foi feito com sucesso, caso ao contrário retorna false.
     * @since 1.1
     */
    // ROTA
    public boolean registraRota(JTextField valorRota, JTextField origemRota, JTextField destinoRota, JTextField categoriaRota) throws Exception{       
        try {           
            boolean validadoRota = false;   
            if(new RotasController().verificaCamposNullRota(valorRota.getText(), origemRota.getText(), destinoRota.getText(), categoriaRota.getText())){  // VALIDAR SE OS CAMPOS NÃO ESTÃO VAZIOS
                JOptionPane.showMessageDialog(null, "Não pode haver campos vazios!", "Atenção", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            float valor = Float.parseFloat(valorRota.getText());
            Cidades origem = new CidadesController().buscaLocalidade(origemRota.getText());
            Cidades destino = new CidadesController().buscaLocalidade(destinoRota.getText());
            Categorias categoria = new CategoriasController().buscaNome(categoriaRota.getText());
            if(new RotasController().buscaOrigemDestino(origem, destino) != null){ // VERIFICANDO SE A ROTA JÁ ESTÁ REGISTRADA (VERIFICANDO AS CIDADES DE ORIGEM E DESTINO)
                JOptionPane.showMessageDialog(null, "Essa rota está registrada!", "Atenção", JOptionPane.WARNING_MESSAGE);
                return false;
            }       
            validadoRota = new esbam.canoa123.controll.RotasController().validarRota(valor, origem, destino, categoria); // CRIO O OBJETO DE CONTROLE E VALIDO AS ENTRADAS DOS CAMPOS
            if (validadoRota) {
                javax.swing.JOptionPane.showMessageDialog(null, "Cadastrado com sucesso", "Sucesso no cadastro", javax.swing.JOptionPane.YES_NO_CANCEL_OPTION);
                return validadoRota;
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Campos incorretos ou problema de conexão", "Não cadastrado", javax.swing.JOptionPane.OK_OPTION);
                valorRota.requestFocus(); // FOCAR NO CAMPO
                return validadoRota;
            }
        } catch (Exception ex) {   
            valorRota.requestFocus(); // FOCAR NO CAMPO
            javax.swing.JOptionPane.showMessageDialog(null, "Erro: " + ex, "Não cadastrado", javax.swing.JOptionPane.OK_OPTION);
            throw new Error("Erro ao tentar registrar a Rota");
        }
    }
    
    /**
     * 
     * @param nomePassageiro
     * @param sexoPassageiro
     * @param cpfPassageiro
     * @param telPassageiro
     * @param nomeOrigem
     * @param nomeDestino
     * @param valorPassagem
     * @return 
     */
    // PASSAGEIRO
    public boolean registraPassageiro(JTextField nomePassageiro, String sexoPassageiro,JFormattedTextField cpfPassageiro, JFormattedTextField telPassageiro, JTextField nomeOrigem,JTextField nomeDestino, JTextField valorPassagem, JTextField dataPassagem) throws Exception{
        try{
            boolean validadoPassageiro = false;
            boolean validadoPassagem = false;
            String nome = nomePassageiro.getText();
            String cpf = cpfPassageiro.getText();
            String telefone = telPassageiro.getText();
            String sexo = sexoPassageiro;        
            String valor = valorPassagem.getText();
            Date dataCompra = new SimpleDateFormat("dd/MM/yyyy").parse(dataPassagem.getText());
            if(new PassageirosController().verficarCamposNull(nome, cpf, telefone, sexo)){ // VERIFICANDO SE A UM CAMPO E NULO NO PASSAGEIRO
                JOptionPane.showMessageDialog(null, "campos do passageiro vazios!", "Atenção", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            if(new PassageirosController().validarCpf(cpf)){ // VERIFICAR SE O CPF DE ENTRADA JÁ EXISTE
                JOptionPane.showMessageDialog(null, "O cpf está cadastrado!", "Cpf cadastrado", JOptionPane.WARNING_MESSAGE);
                return false;
            }   
            Cidades origem = new CidadesController().buscaLocalidade(nomeOrigem.getText());
            Cidades destino = new CidadesController().buscaLocalidade(nomeDestino.getText());
            Rotas rota = new RotasController().buscaOrigemDestino(origem, destino); // PEGANDO A ROTA PELO NOME DAS CIADADES DE ORIGEM E DESTINO
            PassageirosController pController = new PassageirosController();
            if(new PassagensController().verificarCamposNull(valor, origem, destino, dataCompra)){ // VERIFICANDO SE A UM CAMPO E NULO NA PASSAGEM
                JOptionPane.showMessageDialog(null, "campos da passagem vazios!", "Atenção", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            if(origem == null || destino == null){ // VERIIFACAR SE AS CIDADES EXISTEM
                JOptionPane.showMessageDialog(null, "Cidades não encontrada!", "Atencao", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            validadoPassageiro = new esbam.canoa123.controll.PassageirosController().validarPassageiros(nome, cpf, telefone, sexo); // VALIDANDO OS CAMPOS E CADASTRANDO O PASSAGEIRO
            if(validadoPassageiro){
                Passageiros passageiro = pController.buscaPassageiro(pController.ultimoID().getId()); // OBTENDO O ULTIMO ID DO PASSAGEIRO REGISTRADO PARA ATRIBUIR A PASSAGEM
                validadoPassagem = new esbam.canoa123.controll.PassagensController().validaPassagem(rota.getValor(), rota, passageiro, dataCompra); // VALIDANDO OS CAMPOS E CADASTRANDO A PASSAGEM
                if(validadoPassagem){
                    nomePassageiro.requestFocus(); // FOCAR NO CAMPO NOME
                    JOptionPane.showMessageDialog(null, "Cadastrado com sucesso", "Sucesso no cadastro", javax.swing.JOptionPane.YES_NO_CANCEL_OPTION); // VAI MOSTRAR NA TELA UMA MESSAGEM DE SUCESSO (SE OS CAMPOS FOREM PREENCHIDOS)
                    return validadoPassageiro;
                }else{
                    nomePassageiro.requestFocus(); // FOCAR NO CAMPO NOME
                    JOptionPane.showMessageDialog(null, "Não foi possível registrar a passagem ao novo passageiro " + passageiro.getNome(), "Não cadastrado", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    return validadoPassageiro;
                }
            }else{
                nomePassageiro.requestFocus(); // FOCAR NO CAMPO NOME
                JOptionPane.showMessageDialog(null, "Não foi possível cadastrar o passageiro", "Não cadastrado", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                return validadoPassageiro;
            }
        }catch(Exception ex){
            nomePassageiro.requestFocus(); // FOCAR O CURSO NO CAMPO NOME
            JOptionPane.showMessageDialog(null, "Erro nos dados! verifique todos os campos do passageiro e passagem ou se os dados estão inseridos corretamente", "Não cadastrado", JOptionPane.OK_OPTION);
            throw new Error("Erro ao tentar registrar passageiro e sua passagem");
        }
    }
    
    /**
     * Método destinado ao registro da cidade. As entradas são objetos com a String em seu valor getText() facilitando as validações e evitando exeções.
     * @param estadoCidade Um objeto da classe JTextField em referência ao estado (AM, BH, SP...) da cidade
     * @param localidadeCidade Um objeto da classe JTextField em referência a localidade da cidade
     * @return True se foi registrado com sucesso, caso ao contrário retorna false
     */
    // CIDADE
    public boolean registraCidade(JTextField estadoCidade, JTextField localidadeCidade) throws Exception{
        try{
            boolean validadoCidade = false;
            String estado = estadoCidade.getText();
            String localidade = localidadeCidade.getText();
            if(new CidadesController().buscaLocalidade(localidade) != null){
                JOptionPane.showMessageDialog(null, "Essa cidade está registrada!", "Atenção", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            validadoCidade = new esbam.canoa123.controll.CidadesController().validarCidade(estado, localidade);
            if (validadoCidade) {
                // VAI MOSTRAR NA TELA UMA MESSAGEM DE SUCESSO (SE OS CAMPOS FOREM PREENCHIDOS)
                javax.swing.JOptionPane.showMessageDialog(null, "Cadastrado com sucesso", "Sucesso no cadastro", javax.swing.JOptionPane.YES_NO_CANCEL_OPTION);
                return validadoCidade;
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Campos não preenchidos ou problema de conexão", "Não cadastrado", javax.swing.JOptionPane.OK_OPTION);
                estadoCidade.requestFocus(); // FOCAR NO CAMPO
                return validadoCidade;
            }
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "Erro: " + ex, "Não cadastrado", javax.swing.JOptionPane.OK_OPTION);
            estadoCidade.requestFocus(); // FOCAR NO CAMPO
            throw new Error("Erro ao registrar a Cidade");   
        }
    }
    
    /**
     * 
     * @param nomeCategoria
     * @return 
     */
    // CATEGORIA
    public boolean registrarCategoria(JTextField nomeCategoria) throws Exception{
        try {    
            boolean validadoCategoria = false;       
            String nome = nomeCategoria.getText();   
            validadoCategoria = new CategoriasController().validarCategoria(nome);
            if (validadoCategoria) {               
                javax.swing.JOptionPane.showMessageDialog(null, "Cadastrado com sucesso", "Sucesso no cadastro", javax.swing.JOptionPane.YES_NO_CANCEL_OPTION);
                return validadoCategoria;
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Campos não preenchidos ou problema de conexão", "Não cadastrado", javax.swing.JOptionPane.OK_OPTION);
                nomeCategoria.requestFocus();
                return validadoCategoria;
            }
        } catch (Exception ex) {
            nomeCategoria.requestFocus();    
            javax.swing.JOptionPane.showMessageDialog(null, "Erro: " + ex, "Não cadastrado", javax.swing.JOptionPane.OK_OPTION);
            throw new Error("Erro ao registrar a Categoria");
        }
    }
}
