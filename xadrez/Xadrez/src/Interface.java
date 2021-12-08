	import java.awt.Color;
	import java.awt.event.MouseEvent;
	import java.awt.event.MouseListener;
	import java.awt.*;
	import javax.swing.*;
		
	public class Interface extends JFrame implements MouseListener {
		JPanel quadrados[][] = new JPanel[8][8];
		boolean selecionado = false;  // true = uma peça ja foi selecionada, false = nenhuma peça selecionada ainda
		int coordenadaX = 0;
		int coordenadaY = 0;
		int cord_x=0;
		int cord_y=0;
		Color cor_original_quadrado;
		public boolean status=false;
		JLabel turno_cor = new JLabel();
		int coordenadaXguardar = 0;
        int coordenadaYguardar = 0;
    	imagens imagens = new imagens();                             //Implementação das imagens das peças
    	ImageIcon peao_imagemp = imagens.peao_imagemp();				
    	ImageIcon bispo_imagemp = imagens.bispo_imagemp();
    	ImageIcon torre_imagemp = imagens.torre_imagemp();
    	ImageIcon cavalo_imagemp = imagens.cavalo_imagemp();
    	ImageIcon rainha_imagemp = imagens.rainha_imagemp();
    	ImageIcon rei_imagemp = imagens.rei_imagemp();
    	ImageIcon peao_imagemb = imagens.peao_imagemb();				
    	ImageIcon bispo_imagemb = imagens.bispo_imagemb();
    	ImageIcon torre_imagemb = imagens.torre_imagemb();
    	ImageIcon cavalo_imagemb = imagens.cavalo_imagemb();
    	ImageIcon rainha_imagemb = imagens.rainha_imagemb();
    	ImageIcon rei_imagemb = imagens.rei_imagemb();
    	ImageIcon check_imagem = imagens.check_imagem();
    	ImageIcon praia_fundo = imagens.praia_fundo();
    	int capturadas_preto[] = new int[5];
        int capturadas_branco[] = new int[5];
        JLabel capt_branco = new JLabel();
        JLabel capt_preto = new JLabel();
        JTextArea jogadas = new JTextArea();
        ImageIcon banner = imagens.banner();
        boolean empate_proposto = false;
        boolean desistencia = false;
        JFrame frame = new JFrame();

		void iniciar() {			
			fazer_janela(frame);
			fazer_tabuleiro(frame,quadrados);
		}
		
		void fechar_janela()
        {
            frame.dispose();
        }
		
		void fazer_janela(JFrame frame)  // configurações do frame
		{
			frame.setTitle("Xadrez");
			frame.setLayout(null);
			frame.getContentPane().setBackground(Color.gray);
			frame.setResizable(false);
			frame.setVisible(true);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		}
		

		void fazer_tabuleiro(JFrame frame,JPanel quadrados[][])  // faz o tabuleiro de xadrez
		{			
			JLabel fundo = new JLabel();
			fundo.setLayout(null);
			fundo.setBorder(null);
			fundo.setBounds(0, 0, 1500, 800);
			fundo.setIcon(praia_fundo);			
			frame.setBounds(fundo.getBounds());
            JLabel fundo2 = new JLabel();
            fundo2.setIcon(banner);
            fundo2.setBounds(840, 500, 500, 300);
            turno_cor.setBounds(30, 125, 300, 40);
            turno_cor.setForeground(Color.WHITE);
            turno_cor.setFont(new Font("SansSerif", Font.BOLD, 25));
            turno_cor.setText("Turno Jogador Branco");
            turno_cor.setVisible(true);
            fundo2.add(turno_cor);
            fundo.add(fundo2);
            mostrar_capturadas(fundo);
            mostrar_jogadas(fundo);
            this.desistencia_empate(fundo);
			
			int x =0, y=0, cor_do_quadrado = 0, i=0, j = 0 ;
			for(y = 80; y <= 640; y += 80)
			{
				if(cor_do_quadrado == 0) //altera a cor do quadrado entre branco e preto
				{
					cor_do_quadrado = 1;
				}
				else
				{
					cor_do_quadrado = 0;
				}
				for(x = 80; x <= 640 ;x += 80)
				{
					
					quadrados[i][j] = fazer_quadrado(x,y,cor_do_quadrado,i,j);  // chama função responsavel por fazer cada quadrado do tabuleiro
					if(j<7)
					{
						j++;   // incrementa coluna
					}
					else 
					{
						j=0;
						if(i<7)
						{
							i++;  //incrementa linha
						}
						
					}
					if(cor_do_quadrado == 0) //altera a cor do quadrado entre branco e preto
					{
						cor_do_quadrado = 1;
					}
					else
					{
						cor_do_quadrado = 0;
					}
				}
			}
			for(i=0; i<=7; i++)
			{
				for(j=0; j<=7; j++)
				{
					fundo.add(quadrados[i][j]); // adiciona os quadrados ao frame, formando um tabuleiro
				}
				
			}
			frame.add(fundo);
			frame.invalidate();
			frame.validate();
			frame.repaint();
		}
		
		JPanel fazer_quadrado(int x, int y,int cor_do_quadrado,int cord_x, int cord_y) // função responsavel por fazer cada quadrado do tabuleiro
		{
			JPanel quadrado = new JPanel();              // cria o panel que sera o quadrado do tabuleiro
			JLabel imagem = new JLabel();               // cria um label que contem a imagem da peça na casa
			imagem.setSize(80, 80);
			imagem.setVisible(false); 
			JLabel check = new JLabel();
			check.setName("possivel");
			check.setBounds(80, 80, 80, 80);
			check.setVisible(false);
			check.setIcon(check_imagem);
			quadrado.add(check);

			int codigo;
			codigo = (cord_x * 100 ) + cord_y;    // codigo que sera armazenado como nome do panel para posterior intentificação
			quadrado.setBounds(x, y, 80, 80);     //  segue a ordem coordenada x * 100 + coordenada y
			quadrado.setVisible(true);
			quadrado.setOpaque(true);
			quadrado.addMouseListener(this);
			quadrado.setName(String.valueOf(codigo));
			quadrado.add(imagem);
			//System.out.println("x:" + x + "y:"+ y);
			if(cor_do_quadrado == 0)                  // usa cor do quadrado para colocar a cor certa em cada quadrado
			{
				quadrado.setBackground(new Color(0x94e4f7)); //"branco"
			}
			else
			{
				quadrado.setBackground(new Color(0x4172fa));//"preto"
			}
			return quadrado;
		}
		void indentificar_componente(Component c)   // recebe o componente (quadrado) clickado pelo mouse e pega suas cordenadas
        {
            String codigo;
              //System.out.println("codigo: " + c.getName());
              codigo = c.getName();

              int codigo_n = Integer.parseInt(codigo);
               cord_x = codigo_n/100;
               cord_y = codigo_n%100;
               coordenadaX =  coordenadaXguardar;
               coordenadaY =  coordenadaYguardar;
              if (cord_x!=coordenadaX||cord_y!=coordenadaY)
              {
                selecionado = false;
                status=true;
                quadrados[coordenadaXguardar][coordenadaYguardar].setBackground(cor_original_quadrado);
                this.voltar_possiveis();
              }
              else
              {
                selecionado = false;
                status=false;
                c.setBackground(cor_original_quadrado);
                this.voltar_possiveis();
              }

        }
		
		void salvar_componente(Component c)   // recebe o componente (quadrado) clickado pelo mouse e pega suas cordenadas
		{
			String codigo;
			  codigo = c.getName();
			  int codigo_n = Integer.parseInt(codigo);
			  coordenadaXguardar = codigo_n/100;
	          coordenadaYguardar = codigo_n%100;
			  cor_original_quadrado = quadrados[coordenadaXguardar][coordenadaYguardar].getBackground();
			  c.setBackground(new Color(0xf0e489));
				selecionado = true;
				status=false;

			
		}
		
		void mostrar_possiveis(int linha, int coluna, boolean tempeça) // posição par = linha posição impar = coluna
		{
			if(tempeça)
			{
				quadrados[linha][coluna].setBackground(new Color(0xf07d7d));
			}
			else {
				quadrados[linha][coluna].getComponent(0).setVisible(true);
			}
			 quadrados[linha][coluna].invalidate();
			 quadrados[linha][coluna].validate();
			 quadrados[linha][coluna].repaint();
		}
		
		String opções_peão()
        {

            String[] peças = {"Rainha","Bispo","Cavalo","Torre"}; 
            JOptionPane.setDefaultLocale(null);
            int resposta=-1;
            do {
                resposta = JOptionPane.showOptionDialog(
                null,
                "Em qual peça deseja transformar seu peão?", 
                "Troca de Peão", 
                JOptionPane.DEFAULT_OPTION, 
                0, 
                peao_imagemb, 
                peças, 
               peças[0]);
              System.out.println(resposta);
            }while(resposta==-1);

              switch (resposta)
              {
              case 0: return "Rainha";
              case 1: return "Bispo";
              case 2: return "Cavalo";
              case 3: return "Torre";
              }
             return "fechou";
         }
		
		void voltar_possiveis()
		{
			for(int linha=0; linha<=7; linha++)
			{
				for(int coluna=0; coluna<=7; coluna++)
				{
				if(linha%2==0)
				{
					if(coluna%2==0)   //linha par coluna par
					{
						quadrados[linha][coluna].setBackground(new Color(0x4172fa));
					}
						
					else       //linha par coluna impar
					{
						quadrados[linha][coluna].setBackground(new Color(0x94e4f7));
					}
				}
				else
				{
					if(coluna%2==0)  //linha impar coluna par
					{
						quadrados[linha][coluna].setBackground(new Color(0x94e4f7));
					}
					else        //linha impar coluna impar
					{
						quadrados[linha][coluna].setBackground(new Color(0x4172fa));
					}
				}
				quadrados[linha][coluna].getComponent(0).setVisible(false);
				quadrados[linha][coluna].invalidate();
				quadrados[linha][coluna].validate();
				quadrados[linha][coluna].repaint();
			}	
		}}
		
		void mudar_imagem(int linha,int coluna,String peça,String cor)
		{
	  //ImageIcon fundop = new ImageIcon("praiafundo.jpeg");
			quadrados[linha][coluna].remove(1);
			JLabel peçaimagem = new JLabel();               // cria um label que contem a imagem da peça na casa
			
			
			if(cor == "preto")
			{
				switch(peça)
				{
				case "Peão":
				//peçaimagem.setText("PEÃO");break;
				peçaimagem.setIcon(peao_imagemp);break;
				case "Bispo":
				//peçaimagem.setText("BISPO");break;
				peçaimagem.setIcon(bispo_imagemp);break;
				case "Torre":
				//	peçaimagem.setText("TORRE");break;
					peçaimagem.setIcon(torre_imagemp);break;
				case "Cavalo":
				//	peçaimagem.setText("CAVALO");break;
					peçaimagem.setIcon(cavalo_imagemp);break;
				case "Rainha":
				//	peçaimagem.setText("RAINHA");break;
					peçaimagem.setIcon(rainha_imagemp);break;
				case "Rei":
				//	peçaimagem.setText("REI");break;
					peçaimagem.setIcon(rei_imagemp);break;
				case "Vazio":
					peçaimagem.setText(" ");break;
				
				}	
			}
	            else
	            {
	            	switch(peça)
	    			{
	    			case "Peão":
	    			//peçaimagem.setText("PEÃO");break;
	    			peçaimagem.setIcon(peao_imagemb);break;
	    			case "Bispo":
	    			//peçaimagem.setText("BISPO");break;
	    			peçaimagem.setIcon(bispo_imagemb);break;
	    			case "Torre":
	    			//	peçaimagem.setText("TORRE");break;
	    				peçaimagem.setIcon(torre_imagemb);break;
	    			case "Cavalo":
	    			//	peçaimagem.setText("CAVALO");break;
	    				peçaimagem.setIcon(cavalo_imagemb);break;
	    			case "Rainha":
	    			//	peçaimagem.setText("RAINHA");break;
	    				peçaimagem.setIcon(rainha_imagemb);break;
	    			case "Rei":
	    			//	peçaimagem.setText("REI");break;
	    				peçaimagem.setIcon(rei_imagemb);break;
	    			case "Vazio":
	    				peçaimagem.setText(" ");break;
	    			
	    			}	
	            }
			
		
			peçaimagem.setLocation(0,0);
			peçaimagem.setVisible(true); 
			quadrados[linha][coluna].add(peçaimagem);
			
			
			quadrados[linha][coluna].invalidate();
			quadrados[linha][coluna].validate();
			quadrados[linha][coluna].repaint();
		}
		
		void mostrar_capturadas(JLabel fundo)
        {
            //jogador branco
            JLabel painel = new JLabel();
            painel.setBounds(800, 100, 500, 400);
            painel.setIcon(imagens.prancha_imagem());
            painel.setLayout(null);
            JLabel texto = new JLabel();
            texto.setText("Jogador Branco");
            texto.setFont(new Font("Javanese Text", Font.BOLD,20));
            texto.setBounds(170, 10, 200, 30);
            JLabel texto2 = new JLabel();
            texto2.setText("Capturadas:");
            texto2.setFont(new Font("Javanese Text", Font.BOLD, 15));
            texto2.setBounds(60, 35, 100, 30);
            painel.add(texto);
            painel.add(texto2);
            JLabel pecas = new JLabel();
            pecas.setIcon(imagens.todaspretas());
            pecas.setBounds(0, 50, 450, 80);
            painel.add(pecas);
            capt_branco.setBounds(30, 85, 450, 80);
            capt_branco.setFont(new Font("Sans Serif", Font.BOLD, 25));
            painel.add(capt_branco);
            
            // jogador preto
            JLabel texto3 = new JLabel();
            texto3.setText("Jogador Preto");
            texto3.setFont(new Font("Javanese Text", Font.BOLD,20));
            texto3.setBounds(170, 220, 200, 30);
            JLabel texto4 = new JLabel();
            texto4.setText("Capturadas:");
            texto4.setFont(new Font("Javanese Text", Font.BOLD, 15));
            texto4.setBounds(60, 245, 100, 30);
            painel.add(texto3);
            painel.add(texto4);
            JLabel pecas2 = new JLabel();
            pecas2.setIcon(imagens.todasbrancas());
            pecas2.setBounds(0, 260, 450, 80);
            painel.add(pecas2);
            capt_preto.setBounds(30, 295, 450, 80);
            capt_preto.setFont(new Font("Sans Serif", Font.BOLD, 25));
            painel.add(capt_preto);
            fundo.add(painel);
            atualiza_capturadas("branco"," ");
            atualiza_capturadas("preto"," ");
            
        }
        void atualiza_capturadas(String Cor, String peça)
        {
            if(Cor=="preto")
            {
                switch(peça)
                {
                case "Rainha": capturadas_branco[0]++; break;
                case "Torre": capturadas_branco[1]++; break;
                case "Bispo": capturadas_branco[2]++; break;
                case "Cavalo": capturadas_branco[3]++; break;
                case "Peão": capturadas_branco[4]++; break;
                }
            }
            else
            {
                switch(peça)
                {
                case "Rainha": capturadas_preto[0]++; break;
                case "Torre": capturadas_preto[1]++; break;
                case "Bispo": capturadas_preto[2]++; break;
                case "Cavalo": capturadas_preto[3]++; break;
                case "Peão": capturadas_preto[4]++; break;
                }
            }
            capt_branco.setText("x"+capturadas_branco[0]+"         "+"x"+capturadas_branco[1]+"         "+"x"+capturadas_branco[2]+"          "+"x"+capturadas_branco[3]+"         "+"x"+capturadas_branco[4]);
            capt_preto.setText("x"+capturadas_preto[0]+"         "+"x"+capturadas_preto[1]+"         "+"x"+capturadas_preto[2]+"          "+"x"+capturadas_preto[3]+"         "+"x"+capturadas_preto[4]);
            capt_branco.repaint();
            capt_preto.repaint();
                
        }
        
        void desistencia_empate(JLabel fundo)
        {
            JButton button = new JButton();
            JLabel empate = new JLabel("Sugerir Empate");
            JLabel desistir = new JLabel("Desistir");
            empate.setFont(new Font("Sans Serif", Font.BOLD, 13));
            desistir.setFont(new Font("Sans Serif", Font.BOLD, 15));
            button.setBounds(900, 530, 140, 30);
            button.setIcon(imagens.banner2());
            
            button.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                          empate_proposto = opções_empate();
                    }
            });
            
            button.add(empate);
            JButton button2 = new JButton();
            button2.setBounds(1050, 530, 140, 30);
            button2.setIcon(imagens.banner3());
            
            button2.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                        desistencia = opções_desistencia();
                    }
            });
            
            button2.add(desistir);
            fundo.add(button);
            fundo.add(button2);
        }
        boolean opções_empate()
        {
                String[] opt = {"ACEITAR","RECUSAR"}; 
                JOptionPane.setDefaultLocale(null);
                int resposta=-1;
                do {
                    resposta = JOptionPane.showOptionDialog(
                    null,
                    "Empate foi proposto. Deseja aceitar?", 
                    "Proposta de Empate", 
                    JOptionPane.DEFAULT_OPTION, 
                    0, 
                    imagens.carangueijodesenho(), 
                    opt, 
                   opt[0]);
                  System.out.println(resposta);
                }while(resposta==-1);
                  
                  switch (resposta)
                  {
                  case 0: return true;
                  case 1: return false;
                  }
                 return false;      
         }
        
        boolean opções_desistencia()
        {
                String[] opt = {"Sim, tenho certeza","Não, continuarei tentando"}; 
                JOptionPane.setDefaultLocale(null);
                int resposta=-1;
                do {
                    resposta = JOptionPane.showOptionDialog(
                    null,
                    "Tem certeza que deseja desistir da partida?", 
                    "Confirmação de desistência", 
                    JOptionPane.DEFAULT_OPTION, 
                    0, 
                    imagens.carangueijodesenho(), 
                    opt, 
                   opt[0]);
                  System.out.println(resposta);
                }while(resposta==-1);
                  
                  switch (resposta)
                  {
                  case 0: return true;
                  case 1: return false;
                  }
                 return false;      
         }
        
        
        void mostrar_jogadas(JLabel fundo)
        {
            jogadas.setOpaque(false);
            jogadas.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            jogadas.setForeground(Color.white);
            jogadas.setFont(new Font("Sans Serif", Font.BOLD, 15));
            jogadas.setEditable(false);
            JScrollPane scroll = new JScrollPane(jogadas);
            
            scroll.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            scroll.setBounds(1380, 80, 100, 600);
            scroll.setOpaque(false);
            scroll.getViewport().setOpaque(false);
            scroll.getVerticalScrollBar().setBackground(new Color(0x94e4f7));
            scroll.getHorizontalScrollBar().setBackground(new Color(0x94e4f7));
            fundo.add(scroll);
            jogadas.append("JOGADAS:" +"\n");
            atualizar_jogadas(" ");
        }
        
        void atualizar_jogadas(String jogada)
        {
            jogadas.append("  "+ jogada +"\n");
            jogadas.repaint();
        }
		
		
        void mudar_turno(String cor)
        {

            if(cor == "preto")
            {
                turno_cor.setText("Turno Jogador Preto");
                turno_cor.setForeground(Color.BLACK);
            }
                else
                {
                    turno_cor.setText("Turno Jogador Branco");
                    turno_cor.setForeground(Color.WHITE);
                }
            turno_cor.invalidate();
            turno_cor.validate();
            turno_cor.repaint();
        }
		

		@Override
		public void mouseClicked(MouseEvent event) {
			// TODO Auto-generated method stub
		
		    Component c = event.getComponent();
		    if (selecionado)
		    {
		    	indentificar_componente(c);
		    }
		    else
		    {
		    	salvar_componente(c);
		    }
		    
		   
			
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}

