/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unijorge.entidade;

import br.edu.unijorge.constante.Constante;
import br.edu.unijorge.constante.EntidadesImoveis;
import br.edu.unijorge.constante.EntidadesMoveis;
import br.edu.unijorge.constante.PosicoesInvalidas;
import br.edu.unijorge.exception.PecaException;
import br.edu.unijorge.util.UtilX;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

/**
 *
 * @author Glauber
 */
public class Tabuleiro extends JLayeredPane {

    public static final int 
            LARGURA = 650,
            ALTURA = 650,
            LAYER_BACKGROUND = 0,
            LAYER_POSICAO = 400,
            LAYER_PECA = 800;
    
    private static Tabuleiro tabuleiro;
    private Posicao posSelec;
    private List<Peca> pecasTimeAzul;
    private List<Peca> pecasTimeVerm;
    private Exercito exercitoAtual;
    private Exercito exercitoAnt;
    private JLabel bg;

    private Tabuleiro() {
        setBounds(0, 0, ALTURA, LARGURA);
        bg = new JLabel();
        bg.setBounds(0, 0, LARGURA, ALTURA);
        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource(Constante.IMAGEM_FUNDO_TABULEIRO)));
        add(bg);
        setLayer(bg, LAYER_BACKGROUND);
    }

    public static Tabuleiro getInstance() {
        if (null == tabuleiro) {
            tabuleiro = new Tabuleiro();
        }
        return tabuleiro;
    }

    private void construirPecas() {
        PecaMovel pecaMovel;
        //Constroi as pecas moveis
        for (EntidadesMoveis c : EntidadesMoveis.values()) {
            for (int i = 0; i < c.getMaxPorExercito(); i++) {
                //Exercito azul
                pecaMovel = new PecaMovel(
                        new Exercito(Exercito.EXERCITO_AZUL),
                        c.getNome(),
                        c.getValor());
                pecasTimeAzul.add(pecaMovel);
                //Exercito vermelho
                pecaMovel = new PecaMovel(
                        new Exercito(Exercito.EXERCITO_VERMELHO),
                        c.getNome(),
                        c.getValor());
                pecasTimeVerm.add(pecaMovel);
            }
        }

        PecaImovel pecaImovel;
        //Constroi as pecas imoveis
        for (EntidadesImoveis c : EntidadesImoveis.values()) {
            for (int i = 0; i < c.getMaxPorExercito(); i++) {
                //Exercito azul
                pecaImovel = new PecaImovel(
                        new Exercito(Exercito.EXERCITO_AZUL),
                        c.getNome(),
                        c.getValor());
                pecasTimeAzul.add(pecaImovel);
                //Exercito vermelho
                pecaImovel = new PecaImovel(
                        new Exercito(Exercito.EXERCITO_VERMELHO),
                        c.getNome(),
                        c.getValor());
                pecasTimeVerm.add(pecaImovel);
            }
        }
    }

    private void construirPosicoes() {
        //Limpa o tabuleiro
        removeFromLayer(LAYER_POSICAO);
        
        Posicao pos;
        int idx = 0;
        //Preenche o tabuleiro com as posicoes (casas). Por definicao, e uma matriz de 10x10.
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                pos = new Posicao(i * Posicao.LARGURA_PADRAO, j * Posicao.ALTURA_PADRAO);

                pos.setPosicaoValida(isPosicaoInvalida(idx++) ? false : true);
                pos.setOpaque(false);
                add(pos);
                ((JLayeredPane) this).setLayer(pos, LAYER_POSICAO);
            }
        }
    }

    public Posicao getPosicao(String nome) {
        Component[] posicoesTab = getComponentsInLayer(LAYER_POSICAO);

        for (int i = 0; i < posicoesTab.length; i++) {
            if (posicoesTab[i] instanceof Posicao) {
                if (((Posicao) posicoesTab[i]).getNome().equals(nome)) {
                    return (Posicao) posicoesTab[i];
                }
            }
        }

        return null;
    }

    public void moverPeca(PecaMovel peca, Posicao posDestino) throws PecaException {
        boolean isConfronto = false;
        boolean isFimJogo = false;

        if (validarPosicoesDeslocadas(peca, posDestino)) {
            if (isPosicaoInvalida(Integer.parseInt(posDestino.getNome())) || !isFaixaValida(peca, posDestino)) {
                throw new PecaException("A peça não pode se mover para a posição selecionada.");
            }

            if (posDestino.getComponents().length > 0) {
                if (((Peca) posDestino.getComponent(0)).getExercito().equals(peca.getExercito())) {
                    throw new PecaException("Duas peças do mesmo Exército não podem ocupar a mesma posição.");
                } else if (peca.getTitulo().equals(EntidadesMoveis.SOLDADO.getNome())
                        && getQtdCasasDeslocadas(peca, posDestino) > 1) {
                    throw new PecaException("Um soldado não pode se mover e atacar na mesma jogada.");
                } else {
                    isFimJogo = confrontar(peca, posDestino);
                    isConfronto = true;
                }
            }

            if (!isFimJogo) {
                if (!isConfronto) {
                    if (null != getPosicaoPeca(peca)) {
                        getPosicaoPeca(peca).removeAll();
                    }
                    posDestino.removeAll();
                    posDestino.add(peca);
                }
                alternarExercito(false);
                tabuleiro.repaint();
            } else {
                finalizarJogo();
            }
        } else {
            throw new PecaException("Quantidade de casas deslocadas excede a máxima permitida.");
        }
    }

    public void autoPosicionarPecas() {
        for (int i = 0; i < pecasTimeAzul.size(); i++) {
            Peca pecaAzul = pecasTimeAzul.get(i);
            pecaAzul.setBounds(0, 0, Peca.LARGURA, Peca.ALTURA);
            ((Posicao) getComponent(i)).add(pecaAzul);
            repaint();
        }
        int x = 99;
        for (int i = pecasTimeVerm.size(); i > 0; i--) {
            ((Posicao) getComponent(x)).add(pecasTimeVerm.get(i - 1));
            x--;
            repaint();
        }
        alternarExercito(true);
    }

    public List getPecasTimeAzul() {
        return pecasTimeAzul;
    }

    public List getPecasTimeVerm() {
        return pecasTimeVerm;
    }

    public Posicao getPosSelec() {
        return posSelec;
    }

    public void setPosSelec(Posicao posSelec) {
        this.posSelec = posSelec;
    }

    public Exercito getExercitoAnt() {
        return exercitoAnt;
    }

    public void setExercitoAnt(Exercito exercitoAnt) {
        this.exercitoAnt = exercitoAnt;
    }

    public Exercito getExercitoAtual() {
        return exercitoAtual;
    }

    public void setExercitoAtual(Exercito exercitoAtual) {
        this.exercitoAtual = exercitoAtual;
    }

    private void alternarExercito(boolean firstRun) {
        Exercito atual = this.exercitoAnt;
        Exercito anterior = this.exercitoAtual;
        this.exercitoAtual = atual;
        this.exercitoAnt = anterior;
        desabilitarExercito(this.exercitoAnt);
        if (!firstRun) {
            JOptionPane.showMessageDialog(
                    this,
                    "Sua jogada terminou. Agora é a vez do seu adversário.",
                    "Jogada encerrada",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        habilitarExercito(this.exercitoAtual);
        repaint();
    }

    private boolean isPosicaoInvalida(int i) {
        for (PosicoesInvalidas p : PosicoesInvalidas.values()) {
            if (i == p.getValor()) {
                return true;
            }
        }
        return false;
    }

    private boolean validarPosicoesDeslocadas(PecaMovel peca, Posicao posDestino) {
        int qtdCasas = getQtdCasasDeslocadas(peca, posDestino);
        return (qtdCasas <= EntidadesMoveis.valueOf(UtilX.getTextoSemAcento(peca.getTitulo()).toUpperCase()).getMaxCasasPorRodada());
    }

    private Posicao getPosicaoPeca(Peca peca) {
        return (Posicao) ((Component) peca).getParent();
    }

    private boolean confrontar(PecaMovel peca, Posicao posDestino) {
        Peca pecaAtacada = (Peca) posDestino.getComponent(0);
        int valorPeca = peca.getValor();
        int valorPecaAtacada = pecaAtacada.getValor();

        if (valorPecaAtacada == EntidadesImoveis.BANDEIRA.getValor()) {
            JOptionPane.showMessageDialog(this, "Parabéns! O exército " + peca.getExercito().getNome() + " venceu!", "Fim de Jogo.", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else if (valorPecaAtacada == EntidadesImoveis.BOMBA.getValor()) {
            if (valorPeca == EntidadesMoveis.CABO_ARMEIRO.getValor()) {
                posDestino.removeAll();
                ((Posicao) peca.getParent()).removeAll();
                posDestino.add(peca);
            } else {
                peca.getParent().removeAll();
            }
        } else if ((valorPecaAtacada == EntidadesMoveis.ESPIAO.getValor() && valorPeca == EntidadesMoveis.MARECHAL.getValor())
                || (valorPeca == EntidadesMoveis.ESPIAO.getValor() && valorPecaAtacada == EntidadesMoveis.MARECHAL.getValor())) {
            posDestino.removeAll();
            ((Posicao) peca.getParent()).removeAll();
            posDestino.add(peca);
        } else if (valorPeca == valorPecaAtacada || valorPeca > valorPecaAtacada) {
            posDestino.removeAll();
            ((Posicao) peca.getParent()).removeAll();
            posDestino.add(peca);
        } else if (valorPeca < valorPecaAtacada) {
            peca.getParent().removeAll();
        }
        return false;
    }

    private int getQtdCasasDeslocadas(PecaMovel peca, Posicao posDestino) {
        Posicao posOrigem = (Posicao) peca.getParent();
        String posInicial = posOrigem.getNome();
        String posFinal = posDestino.getNome();

        if (posInicial.substring(1, 2).equals(posFinal.substring(1, 2))) {
            return Math.abs(Integer.valueOf(posInicial.substring(0, 1)) - Integer.valueOf(posFinal.substring(0, 1)));
        } else {
            return Math.abs(Integer.valueOf(posInicial.substring(1, 2)) - Integer.valueOf(posFinal.substring(1, 2)));
        }
    }

    private void desabilitarExercito(Exercito exercito) {
        for (Object o : this.getComponentsInLayer(LAYER_POSICAO)) {
            Posicao posicao = (Posicao) o;
            if (posicao.getComponentCount() > 0) {
                Peca peca = (Peca) posicao.getComponent(0);
                if (peca.getExercito().equals(exercito)) {
                    peca.setEnabled(false);
                    peca.setFocusable(false);
                    peca.setText("");
                }
            }
        }
        repaint();
    }

    private void habilitarExercito(Exercito exercito) {
        for (Object o : this.getComponentsInLayer(LAYER_POSICAO)) {
            Posicao posicao = (Posicao) o;
            if (posicao.getComponentCount() > 0) {
                Peca peca = (Peca) posicao.getComponent(0);
                if (peca.getExercito().equals(exercito)) {
                    peca.setEnabled(true);
                    peca.setText(peca.getDescricao());
                }
            }
        }
        repaint();
    }

    private void finalizarJogo() {
        desabilitarExercito(this.exercitoAnt);
        desabilitarExercito(this.exercitoAtual);
        this.exercitoAnt = null;
        this.exercitoAtual = null;
    }

    public void iniciarJogo() {
        pecasTimeAzul = new ArrayList(Constante.MAX_PECAS_EXERCITO);
        pecasTimeVerm = new ArrayList(Constante.MAX_PECAS_EXERCITO);
        exercitoAnt = new Exercito(Exercito.EXERCITO_VERMELHO);
        exercitoAtual = new Exercito(Exercito.EXERCITO_AZUL);
        construirPosicoes();
        construirPecas();
        autoPosicionarPecas();
    }

    private boolean isFaixaValida(PecaMovel peca, Posicao posDestino) {
        String posA = ((Posicao) peca.getParent()).getNome();
        String posB = posDestino.getNome();

        if (posA.substring(0, 1).equals(posB.substring(0, 1))) { //Deslocamento vertical
            return true;
        } else if (posA.substring(1, 2).equals(posB.substring(1, 2))) {//Deslocamento horizontal
            return true;
        } else if (Math.abs(Integer.valueOf(posA) - Integer.valueOf(posB)) % 11 == 0) { //Deslocamento diagonal (da esquerda pra direita)
            return true;
        } else if (Math.abs(Integer.valueOf(posA) - Integer.valueOf(posB)) % 9 == 0) { //Deslocamento diagonal (da direita pra esquerda)
            return true;
        } else {
            return false;
        }
    }

    public void removeFromLayer(int layer) {
        for(Component c : getComponentsInLayer(layer)){
            remove(c);
        }
    }
}