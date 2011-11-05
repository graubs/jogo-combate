/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainForm.java
 *
 * Created on 09/09/2011, 23:44:26
 */
package br.edu.unijorge.form;

import br.edu.unijorge.constante.Constante;
import br.edu.unijorge.util.UtilX;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Glauber
 */
public class MainForm extends javax.swing.JFrame {

    public static final int 
            LARGURA_PADRAO = 660,
            ALTURA_PADRAO = 740,
            LAYER_TABULEIRO = 0,
            LAYER_MSG = 400;

    public MainForm() {
        initComponents();
        Dimension d = new Dimension(LARGURA_PADRAO, ALTURA_PADRAO);
        setMinimumSize(d);
        setPreferredSize(d);
        setMaximumSize(d);
        setResizable(false);
        UtilX.centerChildX(this, tabuleiro);
        jlJogadorInfo.setLocation(
                ((this.getWidth() / 2) - (jlJogadorInfo.getWidth() / 2)) + this.getLocation().x,
                jlJogadorInfo.getLocation().y);
        UtilX.centerX(this);
    }

    private void inicializarTabuleiro() {
        tabuleiro.iniciarJogo();
        repaint();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlJogadorInfo = new javax.swing.JLabel();
        tabuleiro = new br.edu.unijorge.entidade.Tabuleiro();
        jmbMenu = new javax.swing.JMenuBar();
        jmJogo = new javax.swing.JMenu();
        jmiNovoJogo = new javax.swing.JMenuItem();
        jsJogo1 = new javax.swing.JPopupMenu.Separator();
        jmiSair = new javax.swing.JMenuItem();
        jmAjuda = new javax.swing.JMenu();
        jmiRegras = new javax.swing.JMenuItem();
        jmiSobre = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("COMBATE ***");
        setIconImage(new ImageIcon(getClass().getResource(Constante.IMAGEM_ICONE)).getImage());
        setMinimumSize(new java.awt.Dimension(670, 720));
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        getContentPane().setLayout(null);

        jlJogadorInfo.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jlJogadorInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlJogadorInfo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        getContentPane().add(jlJogadorInfo);
        jlJogadorInfo.setBounds(0, 650, 650, 30);
        getContentPane().add(tabuleiro);
        tabuleiro.setBounds(0, 0, 650, 650);

        jmJogo.setText("Jogo");

        jmiNovoJogo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jmiNovoJogo.setText("Novo Jogo");
        jmiNovoJogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jmiNovoJogoMouseReleased1(evt);
            }
        });
        jmJogo.add(jmiNovoJogo);
        jmJogo.add(jsJogo1);

        jmiSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        jmiSair.setText("Sair");
        jmiSair.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jmiSairMouseReleased(evt);
            }
        });
        jmJogo.add(jmiSair);

        jmbMenu.add(jmJogo);

        jmAjuda.setText("Ajuda");

        jmiRegras.setText("Regras...");
        jmiRegras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jmiRegrasMouseReleased(evt);
            }
        });
        jmAjuda.add(jmiRegras);

        jmiSobre.setText("Sobre...");
        jmiSobre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jmiSobreMouseReleased(evt);
            }
        });
        jmAjuda.add(jmiSobre);

        jmbMenu.add(jmAjuda);

        setJMenuBar(jmbMenu);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmiSairMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmiSairMouseReleased
        if (JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja encerrar o jogo?",
                "Encerrar Jogo",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            this.dispose();
        }
    }//GEN-LAST:event_jmiSairMouseReleased

    private void jmiNovoJogoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmiNovoJogoMouseReleased
        inicializarTabuleiro();
}//GEN-LAST:event_jmiNovoJogoMouseReleased

    private void jmiRegrasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmiRegrasMouseReleased
        if(rf == null){
            rf = new RegrasForm(this, false);
            rf.setVisible(true);
        }else{
            rf.setVisible(true);
        }
    }//GEN-LAST:event_jmiRegrasMouseReleased

    private void jmiSobreMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmiSobreMouseReleased
        if (sf == null) {
            sf = new SobreForm(this, true);
            sf.setVisible(true);
        }else{
            sf.setVisible(true);
        }
    }//GEN-LAST:event_jmiSobreMouseReleased
    
    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        Component c = evt.getComponent();
        System.out.print(c);
    }//GEN-LAST:event_formMouseReleased

    private void jmiNovoJogoMouseReleased1(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmiNovoJogoMouseReleased1
        inicializarTabuleiro();
    }//GEN-LAST:event_jmiNovoJogoMouseReleased1

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

    public JLabel getJlJogadorInfo() {
        return jlJogadorInfo;
    }

    public void setJlJogadorInfo(JLabel jlJogadorInfo) {
        this.jlJogadorInfo = jlJogadorInfo;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jlJogadorInfo;
    private javax.swing.JMenu jmAjuda;
    private javax.swing.JMenu jmJogo;
    private javax.swing.JMenuBar jmbMenu;
    private javax.swing.JMenuItem jmiNovoJogo;
    private javax.swing.JMenuItem jmiRegras;
    private javax.swing.JMenuItem jmiSair;
    private javax.swing.JMenuItem jmiSobre;
    private javax.swing.JPopupMenu.Separator jsJogo1;
    private br.edu.unijorge.entidade.Tabuleiro tabuleiro;
    // End of variables declaration//GEN-END:variables
    RegrasForm rf;
    SobreForm sf;
}
