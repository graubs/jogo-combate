package br.edu.unijorge.socket;

import br.edu.unijorge.entidade.Peca;
import br.edu.unijorge.entidade.Posicao;
import br.edu.unijorge.entidade.Tabuleiro;

/**
 *
 * @author Glauber
 */

public class CombateProtocol {
    static final int IDX_OPERACAO = 0;
    static final int IDX_POSICAO = 1;
    static final int IDX_PECA = 0;

    public String processInput(String input) {
        String output = "";
        Peca pc = null;
        String [] params = input.split(";");
        Tabuleiro t = null;
        Posicao ps = t.getPosicao(params[IDX_POSICAO]);
        try{
            pc = (Peca)ps.getComponent(0);
            output = pc.toString();
        }catch(NullPointerException ex){
            output = ps.getNome();
        }
        return output;
    }
}
