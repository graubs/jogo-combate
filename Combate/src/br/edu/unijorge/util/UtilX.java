package br.edu.unijorge.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.player.Player;

/**
 * Classe que contém métodos estáticos utilitários.
 * @author Glauber
 */
public abstract class UtilX {

    public static Double getScreenWidth() {
        return (Double) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    }

    public static Double getScreenHeight() {
        return (Double) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    }

    public static Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    public static void centerForm(Frame frame) {
        frame.setLocation(
                (getScreenWidth().intValue() / 2) - (frame.getWidth() / 2),
                ((getScreenHeight().intValue()) / 2) - (frame.getHeight() / 2));
    }

    public static void centerX(Frame frame) {
        frame.setLocation(
                (getScreenWidth().intValue() / 2) - (frame.getWidth() / 2),
                1);
    }

    public static void centerChild(Component parent, Component child) {
        child.setLocation(
                ((parent.getWidth() / 2) - (child.getWidth() / 2)) + parent.getLocation().x,
                ((parent.getHeight() / 2) - (child.getHeight() / 2)) + parent.getLocation().y);
    }

    public static void centerChildX(Component parent, Component child) {
        child.setLocation(
                ((parent.getWidth() / 2) - (child.getWidth() / 2)) + parent.getLocation().x,
                0);
    }

    public static String getTextoSemAcento(String texto) {
        String[] letrasA = new String[]{
            "Á", "É", "Í", "Ó", "Ú",
            "á", "é", "í", "ó", "ú",
            "À", "È", "Ì", "Ò", "Ù",
            "à", "è", "ì", "ò", "ù",
            "Â", "Ê", "Î", "Ô", "Û",
            "â", "ê", "î", "ô", "û",
            "Ã", "Õ",
            "ã", "õ",
            " "};

        String[] letrasS = new String[]{
            "A", "E", "I", "O", "U",
            "a", "e", "i", "o", "u",
            "A", "E", "I", "O", "U",
            "a", "e", "i", "o", "u",
            "A", "E", "I", "O", "U",
            "a", "e", "i", "o", "u",
            "A", "O",
            "a", "o",
            "_"};

        String novoTexto = "";

        for (int i = 0; i < texto.length(); i++) {
            String letra = texto.substring(i, i + 1);
            for (int j = 0; j < letrasA.length; j++) {
                if (letra.equals(letrasA[j])) {
                    letra = letrasS[j];
                }
            }
            novoTexto += letra;
        }
        return novoTexto;
    }

    public static InetAddress getIpAddress() {
        InetAddress ip = null;
        try {
            ip = Inet4Address.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(UtilX.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ip;
    }

    public static void beep() {
        Toolkit.getDefaultToolkit().beep();
    }

    public synchronized static void tocarSom(final String file) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    InputStream in = (InputStream) (new FileInputStream(UtilX.class.getResource(file).getFile()));
                    Player p = new Player(in);
                    p.play();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }).start();
    }
}
