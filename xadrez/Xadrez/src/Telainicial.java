import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Telainicial { 
	JFrame frame = new JFrame();
	imagens imagens = new imagens();
	JPanel fundoprincipal = new JPanel();
	boolean esperar = true;
	ImageIcon carangueijoreal = imagens.carangueijoreal();
    ImageIcon carangueijodesenho = imagens.carangueijodesenho();
	
	void iniciar()
	{
		frame.setTitle("Xadrez");
		frame.setLayout(null);
		frame.getContentPane().setBackground(Color.gray);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		fundoprincipal.setLayout(null);
		fundoprincipal.setBorder(null);
		fundoprincipal.setBounds(0, 0, 1500, 800);
		fundoprincipal.setVisible(true);
		fundoprincipal.setBackground(Color.pink);
		JLabel imagemdofundo = new JLabel();
		imagemdofundo.setIcon(imagens.praia_fundo());
		imagemdofundo.setVisible(true);
		imagemdofundo.setBounds(0, 0, 1500, 800);
		titulo(imagemdofundo);
		instrucao(imagemdofundo);
		fundoprincipal.add(imagemdofundo);
		frame.setBounds(fundoprincipal.getBounds());
		frame.add(fundoprincipal);
		adicionar_carangueijo(imagemdofundo);
		frame.invalidate();
		frame.validate();
		frame.repaint();	
	}
	
	void titulo(JLabel fundo)
	{
		JLabel titulo = new JLabel();
		titulo.setText("TROPICAL");
		titulo.setFont(new Font("Courier", Font.BOLD, 150));
		titulo.setBounds(100, -130, 1500, 500);
		titulo.setForeground(Color.white);
		titulo.setVisible(true);
		fundo.add(titulo);
		JLabel titulo2 = new JLabel();
		titulo2.setText("CHESS");
		titulo2.setFont(new Font("Courier", Font.BOLD, 150));
		titulo2.setBounds(250, 20, 1500, 500);
		titulo2.setForeground(Color.white);
		titulo2.setVisible(true);
		fundo.add(titulo2);
	}
	
	void adicionar_carangueijo(JLabel fundo)
	{
		int counter =0;	
		
		JLabel carangueijo = new JLabel();
		JLabel texto = new JLabel();
		texto.setText("CLICKOU NO PATO");
		texto.setBounds(1200, 240, 300, 100);
		texto.setForeground(Color.RED);
		texto.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		texto.setVisible(false);
		fundo.add(texto);
		
		JButton buttonS = new JButton();
		buttonS.setBounds(0, 0, 400, 300);
		buttonS.setFocusable(false);
		buttonS.setBorder(null);
		buttonS.setBackground(null);
		buttonS.setForeground(null);
		buttonS.setContentAreaFilled(false);
		buttonS.setOpaque(false);
		buttonS.addActionListener(new java.awt.event.ActionListener() {
			
            public void actionPerformed(java.awt.event.ActionEvent evt) {
		esperar = false;
		frame.dispose();
            }
          });

		buttonS.add(carangueijo);
		fundo.add(buttonS);

		while(esperar)
		{
		while(counter<=1000) {
			carangueijo.setIcon(carangueijodesenho);
			carangueijo.invalidate();
			carangueijo.validate();
			carangueijo.repaint();
			buttonS.setBounds(counter, 400, 400, 300);//valor incrementado
			if(esperar==false)
            {
                counter=1213;
            }
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			counter +=10;
			}
		while(counter>0) {
			carangueijo.setIcon(carangueijoreal);
			carangueijo.invalidate();
			carangueijo.validate();
			carangueijo.repaint();
			if(esperar==false)
            {
                counter=0;
            }
			buttonS.setBounds(counter, 420, 400, 300); //valor decrementado
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			counter=counter-10;
			}
		
	}
}
	
	void instrucao(JLabel fundo)
	{

		JLabel texto = new JLabel();
		texto.setText("Clicke No Carangueijo");
		texto.setBounds(1150, 240, 300, 100);
		texto.setForeground(Color.WHITE);
		texto.setFont(new Font("TimesRoman", Font.BOLD, 30));
		JLabel texto2 = new JLabel();
		texto2.setText("Para Iniciar");
		texto2.setBounds(1200, 270, 300, 100);
		texto2.setForeground(Color.WHITE);
		texto2.setFont(new Font("TimesRoman", Font.BOLD, 30));
		fundo.add(texto);
		fundo.add(texto2);
	}
}
	
