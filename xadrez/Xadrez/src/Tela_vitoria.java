import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Tela_vitoria {
    imagens imagens = new imagens(); 


void iniciar(String escrita,String motivo) //caso vitoria, escrita = vitoria do jogador ,motivo = nome do jogador , caso de empate escrita= empate por, motivo = motivo do empate
{
    JFrame frame = new JFrame();
    frame.setTitle("Xadrez");
    frame.setLayout(null);
    frame.getContentPane().setBackground(Color.gray);
    frame.setResizable(false);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JLabel imagemdofundo = new JLabel();
    imagemdofundo.setIcon(imagens.praia_fundo2());
    imagemdofundo.setVisible(true);
    imagemdofundo.setBounds(0, 0, 1500, 800);
    frame.add(imagemdofundo);
    frame.setBounds(imagemdofundo.getBounds());
    frame.setLocationRelativeTo(null);
    JLabel texto = new JLabel();
    JLabel texto2 = new JLabel();
    JLabel peças = new JLabel();
    peças.setBounds(550, 400, 800, 500);
    peças.setVisible(true);


        texto.setText(escrita);
        texto2.setText(motivo);
        texto.setFont(new Font("Gabriola", Font.BOLD, 100));
        texto.setBounds(130, -130, 1500, 500);
        texto.setForeground(Color.white);
        texto2.setFont(new Font("Gabriola", Font.BOLD, 150));
        texto2.setBounds(100, -10, 1500, 500);
        texto2.setForeground(Color.white);
        peças.setIcon(imagens.todaspeças());

    imagemdofundo.add(peças);
    imagemdofundo.add(texto);
    imagemdofundo.add(texto2);
    frame.invalidate();
    frame.validate();
    frame.repaint();
}
}
