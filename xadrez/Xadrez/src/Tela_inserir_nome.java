import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Tela_inserir_nome {

    imagens imagens = new imagens();
    protected String nome_preto;
    protected String nome_branco;
    boolean esperar=true;

void iniciar(){
    JFrame frame = new JFrame();
    frame.setTitle("Xadrez");
    frame.setLayout(null);
    frame.getContentPane().setBackground(Color.gray);
    frame.setResizable(false);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JLabel imagemdofundo = new JLabel();
    imagemdofundo.setIcon(imagens.pedra_fundo());
    imagemdofundo.setVisible(true);
    imagemdofundo.setBounds(0, 0, 1500, 800);
    frame.add(imagemdofundo);
    frame.setBounds(imagemdofundo.getBounds());
    frame.setLocationRelativeTo(null);
    JLabel texto = new JLabel();
    texto.setText("Nome jogador branco:");
    texto.setFont(new Font("Courier", Font.BOLD, 40));
    texto.setBounds(100, -130, 1500, 500);
    texto.setForeground(Color.white);
    texto.setVisible(true);
    imagemdofundo.add(texto);
    JLabel texto2 = new JLabel();
    texto2.setText("Nome jogador preto:");
    texto2.setFont(new Font("Courier", Font.BOLD, 40));
    texto2.setBounds(900, -130, 1500, 500);
    texto2.setForeground(Color.white);
    texto2.setVisible(true);
    imagemdofundo.add(texto2);
    JTextField textfield = new JTextField();
    textfield.setBounds(100, 160, 300, 30);
    imagemdofundo.add(textfield);
    JTextField textfield2 = new JTextField();
    textfield2.setBounds(900, 160, 300, 30);
    imagemdofundo.add(textfield2);
    JButton button = new JButton("Jogar Agora");
    button.setBounds(500, 300, 300, 30);
    button.setFocusable(false);
    button.setVisible(true);
    button.setBackground(Color.white);
    button.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
        	nome_branco = textfield.getText();
            nome_preto = textfield2.getText();
        	if(nome_branco.length()>1&&nome_preto.length()>1)
            {
        		esperar=false;
                frame.dispose();
            }   
            }
    });
    imagemdofundo.add(button);
    frame.revalidate();
    frame.repaint();
    while(esperar) {
    	System.out.flush();
    }
    
}

public String getNomebranco() {
	return nome_branco;
}

public String getNomepreto() {
	return nome_preto;
}

}