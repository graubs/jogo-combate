package br.edu.unijorge.entidade;

import br.edu.unijorge.constante.Constante;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;

/**
 * Abstrai a peça (peão) do jogo. Possui características como Exército, Título e Valor.
 * Obriga as sub-classes a implementar o método mouseReleased da Interface <code>MouseListener</code>.
 *
 * @author Glauber
 */
public abstract class Peca extends JToggleButton {

    public static final int LARGURA = 60, //Largura padrão do componente
            ALTURA = 60; //Altura padrão do componente
    private Exercito exercito;
    private int valor;
    private String id;
    private String titulo;

    //Construtor da classe
    public Peca(Exercito exercito, String titulo, int valor, String id) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.exercito = exercito;
        this.titulo = titulo;
        this.valor = valor;
        this.id = id;
        setText(getDescricao());
        setFont(new Font(Constante.FONTE_TIPO_PADRAO, Constante.FONTE_ESTILO_PADRAO, Constante.FONTE_TAMANHO_PADRAO));
        setBackground(exercito.getColor());
        //setBounds(0, 0, LARGURA, ALTURA);
//        setPreferredSize(new Dimension(LARGURA, ALTURA));
//        setMaximumSize(new Dimension(LARGURA, ALTURA));
//        setMinimumSize(new Dimension(LARGURA, ALTURA));
        setBorder(new BevelBorder(BevelBorder.RAISED));
        addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pecaMouseReleased(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pecaMouseClicked(evt);
            }
        });
    }

    /**
     * Compara uma peca a outra (recebida no argumento). Para fazer isso, compara se o
     * id (valor da peça concatenado com um id sequencial).
     * @param obj Object que será comparado com a instância dessa classe
     * @return <code>true</code> se as peças forem iguais, <code>false</code> caso contrário.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Peca)) {
            return false;
        }

        return this.id.equals(((Peca) obj).getId());
    }

    /**
     * Retorna uma coleção de <code>Peca</code> que fazem parte do mesmo exército
     * dessa peça.
     * @return Uma <code>List<Peca></code>
     */
//    private List<Peca> getTime() {
//        Component[] todasAsPecas = ((JLayeredPane) ((Component) this.getParent()).getParent()).getComponentsInLayer(Tabuleiro.LAYER_POSICAO);
//        List<Peca> pecasTime = new ArrayList<Peca>();
//
//        for (int i = 0; i < todasAsPecas.length; i++) {
//            Posicao pos = (Posicao) todasAsPecas[i];
//            if (pos.getComponentCount() > 0) {
//                Peca pca = (Peca) pos.getComponent(0);
//                if (pca.getExercito().equals(exercito)) {
//                    pecasTime.add(pca);
//                }
//            }
//        }
//
//        return pecasTime;
//    }
    /**
     * Método acionado no evento mouseReleased da implementação da Interface <code>MouseListener</code>.
     * Garante que somente uma peça (Classe que extende JToggleButton) será selecionada.
     */
//    protected void garantirUnicidadeSelecao() {
//        List<Peca> todasAsPecas = new ArrayList<Peca>();
//        todasAsPecas.addAll(Tabuleiro.getInstance().getPecasTimeAzul());
//        todasAsPecas.addAll(Tabuleiro.getInstance().getPecasTimeVerm());
//
//        for (Peca p : todasAsPecas) {
//            Peca b = (Peca) p;
//            if (p.equals(this)) {
//                b.setSelected(true);
//            } else {
//                b.setSelected(false);
//                
//            }
//        }
//        repaint();
//    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.exercito != null ? this.exercito.hashCode() : 0);
        hash = 83 * hash + this.valor;
        hash = 83 * hash + (this.titulo != null ? this.titulo.hashCode() : 0);
        return hash;
    }

    public Exercito getExercito() {
        return exercito;
    }

    public void setExercito(Exercito exercito) {
        this.exercito = exercito;
    }

    public int getValor() {
        return valor;
    }

    public String getTitulo() {
        return titulo;
    }

    protected void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = this.valor + "-" + id;
    }

    /**
     * Retorna uma descrição da peça. Essa descrição é composta pelo título
     * da peça (Sargento, General, Capitão, etc) concatenado com o seu valor
     * (2, 9 ,6, etc). Essa descrição é utilizada para preencher o rótulo do
     * componente
     * @return Uma <code>String</code> com a descrição da peça
     */
    public String getDescricao() {
        return this.titulo + "(" + this.valor + ")";
    }

    @Override
    public String toString() {
        return "Peça: " + getDescricao() + "Exército: " + getExercito().toString();
    }

    public abstract void pecaMouseReleased(MouseEvent evt);
    
    public abstract void pecaMouseClicked(MouseEvent evt);
}
