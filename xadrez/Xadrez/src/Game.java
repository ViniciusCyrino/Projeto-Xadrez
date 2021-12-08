import java.util.Scanner;

public class Game  {
	Jogadores jogador_preto = new Jogadores(null, "preto");
	Jogadores jogador_branco = new Jogadores(null, "branco");
	int contadorjogadas = 0; // Contador do numero de jogadas totais
	Scanner scanner = new Scanner(System.in);
	Pe�as[][] celula = new Pe�as[8][8]; // Tabuleiro, uma matriz de Pe�as com 64 posi��es
	Pe�as pe�a = new Pe�as();
	int[] coordenadasReipreto = new int[2];
	int[] coordenadasReibranco = new int[2];
	int contador_empate = 0;
	Interface inte = new Interface();
	Telainicial inicial = new Telainicial();
	Tela_inserir_nome insere = new Tela_inserir_nome();
	Tela_vitoria vitoria = new Tela_vitoria();
	

	// Controla a progress�o de jogadas e os turnos dos jogadores
	public void controle()  {
		boolean aux = true;
		int turno = 1; // Faz o controle dos turnos dos jogadores
		inicial.iniciar();
		insere.iniciar();
		jogador_preto.setNome(insere.getNomepreto());
		jogador_branco.setNome(insere.getNomebranco());
		inte.iniciar();
		this.iniciaTabuleiro(); // Inicializa o tabuleiro com as pe�as nas posi��es iniciais
		// V�o sendo feitas a jogadas at� o jogo acabar
		while (aux == true) {
			// Faz o controle do turno
			if (turno == 1) {
				inte.mudar_turno(jogador_branco.getcor()); // Muda o turno na interface grafica
				aux = this.jogada(jogador_branco);
				turno = 2; // Muda o turno para o jogador preto
				contadorjogadas++; // Incrementa-se o contador de jogadas
			} else {
				inte.mudar_turno(jogador_preto.getcor()); // Muda o turno na interface grafica
				aux = this.jogada(jogador_preto);
				turno = 1; // Muda o turno para o jogador branco
				contadorjogadas++; // Incrementa-se o contador de jogadas
			}
		}
		
	}

	// Onde realizam-se as jogadas
	public boolean jogada(Jogadores jogador)  {
		int controle = 0; // Variavel de controle
		int linhainicio = inte.coordenadaX;
		int colunainicio = inte.coordenadaY;
		int linhafim = inte.cord_x;
		int colunafim = inte.cord_y;
		boolean status = inte.status;
		boolean selecionado = inte.selecionado;
		
		// Se o rei est� em check confere se est� tambem em checkmate
		if (this.check_mate(jogador.getcor())) {
			this.registra_vitoria(jogador.getcor());
			inte.fechar_janela();
			if(jogador.getcor()=="preto") {
				vitoria.iniciar("Vitoria do jogador",jogador_branco.getnome());
			}else {
				vitoria.iniciar("Vitoria do jogador", jogador_preto.getnome());
			}
			return false; // Retorna false, e encerra o programa
		}

		// Confere se o rei est� afogado
		if (this.afogamento(jogador.getcor()) == false) {
			inte.fechar_janela();
			this.registra_empate(); // Regista o empate no registro das jogadas
			vitoria.iniciar(" ","Empate por afogamento");
			return false; // Retorna false, e encerra o programa
		}

		// Confere se ocorreu um empate por material insuficiente
		if (this.material_insuficiente(jogador.getcor())) {
			inte.fechar_janela();
			this.registra_empate(); // Regista o empate no registro das jogadas
			vitoria.iniciar(" ","Empate por material insuficiente");
			return false; // Retorna false, e encerra o programa
		}

		// Confere se ocorreu um empate por 50 movimentos sem captura ou andar com pe�o
		if (contador_empate == 50) {
			inte.fechar_janela();
			this.registra_empate(); // Regista o empate no registro das jogadas
			vitoria.iniciar(" ","Empate por 50 movimentos");
			return false; // Retorna false, e encerra o programa
		}
		
		// Este while serve para printar no tabuleiro as op��es de movimento das pe�as
					// A variavel status vira "true" quando o movimento da pe�a � escolhido pelo
					// jogador
					while (status == false) {
						status = inte.status;
						System.out.flush();

						if (selecionado) { //A variavel selecinado � true quando uma pe�a foi escolhida
							linhainicio = inte.coordenadaXguardar;
							colunainicio = inte.coordenadaYguardar;
							this.marcar_movimentos(linhainicio, colunainicio); //Printa na tela as op��es da pe�a escolhida
						}

						selecionado = inte.selecionado;
						System.out.flush();
						if(inte.empate_proposto) {
							inte.fechar_janela();
							vitoria.iniciar(" ","Empate por acordo");
							this.registra_empate();
							return false;
						}
						if(inte.desistencia) {
							inte.fechar_janela();
							if(jogador.getcor()=="preto") {
								vitoria.iniciar("Vitoria do jogador",jogador_branco.getnome());
								this.registra_vitoria(jogador.getcor());
							}else {
								vitoria.iniciar("Vitoria do jogador", jogador_preto.getnome());
								this.registra_vitoria(jogador.getcor());
							}
							return false;
						}
						System.out.flush();
					}
		

		while (controle == 0) { // Continua no loop at� uma jogada legal ser feita
			//Variaveis que contem as coordenadas escolhidas pelo jogador
			linhainicio = inte.coordenadaX;
			colunainicio = inte.coordenadaY;
			linhafim = inte.cord_x;
			colunafim = inte.cord_y;
			System.out.flush();
			
			status = inte.status;
			System.out.flush();

			// Verifica se o jogador escolheu uma pe�a valida, e se o movimento escolhido n�o � ficar parado
			if (celula[linhainicio][colunainicio] != null
					&& celula[linhainicio][colunainicio].getcor() == jogador.getcor()
					&& this.posi��es_diferentes(linhainicio, colunainicio, linhafim, colunafim)) {
				// Caso seja vai para a fun��o de verificar o movimento
				controle = this.menujogada(linhainicio, colunainicio, linhafim, colunafim); 																			 
			}
		}

		this.desambigua(linhainicio, colunainicio, linhafim, colunafim); // Chama a fun��o que registra a jogada

		if (controle < 3) {
			if (celula[linhainicio][colunainicio].getnome() == "Pe�o") {
				contador_empate = 0;
			}
			this.troca(linhainicio, colunainicio, linhafim, colunafim);
		} else if (controle == 10) {
			this.moveRoque(linhainicio, colunainicio, linhafim, colunafim);
		} else if (controle == 25) {
			this.trocaPe�as(linhainicio, colunainicio, linhafim, colunafim);
		} else {
			this.moveEnPassan(linhainicio, colunainicio, linhafim, colunafim);
		}

		if (jogador.getcor() == "preto" && this.check(coordenadasReibranco[0], coordenadasReibranco[1],
				coordenadasReibranco[0], coordenadasReibranco[1]) == 0) {
			this.registra_check_checkmate("check");
		} else if (jogador.getcor() == "branco" && this.check(coordenadasReipreto[0], coordenadasReipreto[1],
				coordenadasReipreto[0], coordenadasReipreto[1]) == 0) {
			this.registra_check_checkmate("check");
		}
		inte.atualizar_jogadas(Jogadores.get_jogadas(contadorjogadas));
		inte.status = false;
		return true;
	}

	public void marcar_movimentos(int linha, int coluna) {
		String nome = celula[linha][coluna].getnome();
		int aux;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				switch (nome) {
				case "Pe�o":
					if (this.posi��es_diferentes(linha, coluna, i, j)) {
						aux = this.verificaPe�o(linha, coluna, i, j, false);
						if (aux != 0) {
							aux = this.check(linha, coluna, i, j);
						}
						if (aux != 0 && celula[i][j] != null) {
							inte.mostrar_possiveis(i, j, true);
						} else if (aux != 0 && celula[i][j] == null) {
							inte.mostrar_possiveis(i, j, false); 
						}
					}
					break;
				case "Bispo":
					if (this.posi��es_diferentes(linha, coluna, i, j)) {
						aux = this.verificaBispo(linha, coluna, i, j);
						if (aux != 0) {
							aux = this.check(linha, coluna, i, j);
						}
						if (aux != 0 && celula[i][j] != null) {
							inte.mostrar_possiveis(i, j, true);
						} else if (aux != 0 && celula[i][j] == null) {
							inte.mostrar_possiveis(i, j, false); 
						}
					}
					break;
				case "Cavalo":
					if (this.posi��es_diferentes(linha, coluna, i, j)) {
						aux = this.verificaCavalo(linha, coluna, i, j);
						if (aux != 0) {
							aux = this.check(linha, coluna, i, j);
						}
						if (aux != 0 && celula[i][j] != null) {
							inte.mostrar_possiveis(i, j, true);
						} else if (aux != 0 && celula[i][j] == null) {
							inte.mostrar_possiveis(i, j, false); 
						}
					}
					break;
				case "Torre":
					if (this.posi��es_diferentes(linha, coluna, i, j)) {
						aux = this.verificaTorre(linha, coluna, i, j);
						if (aux != 0) {
							aux = this.check(linha, coluna, i, j);
						}
						if (aux != 0 && celula[i][j] != null) {
							inte.mostrar_possiveis(i, j, true);
						} else if (aux != 0 && celula[i][j] == null) {
							inte.mostrar_possiveis(i, j, false); 
						}
					}
					break;
				case "Rainha":
					if (this.posi��es_diferentes(linha, coluna, i, j)) {
						int aux2 = 0;
						aux = this.verificaBispo(linha, coluna, i, j);
						aux2 = this.verificaTorre(linha, coluna, i, j);
						if (aux != 0) {
							aux = this.check(linha, coluna, i, j);
						}
						if (aux != 0 && celula[i][j] != null) {
							inte.mostrar_possiveis(i, j, true);
						} else if (aux != 0 && celula[i][j] == null) {
							inte.mostrar_possiveis(i, j, false); 
						}
						if (aux2 != 0 && celula[i][j] != null) {
							inte.mostrar_possiveis(i, j, true);
						} else if (aux2 != 0 && celula[i][j] == null) {
							inte.mostrar_possiveis(i, j, false); 
						}
					}
					break;
				case "Rei":
					if (this.posi��es_diferentes(linha, coluna, i, j)) {
						aux = this.verificaRei(linha, coluna, i, j);
						if (aux != 0) {
							aux = this.check(linha, coluna, i, j);
						}
						if (aux != 0 && celula[i][j] != null) {
							inte.mostrar_possiveis(i, j, true);
						} else if (aux != 0 && celula[i][j] == null) {
							inte.mostrar_possiveis(i, j, false); 
						}
					}
				}
			}
		}

	}

	public void moveEnPassan(int linhainicio, int colunainicio, int linhafim, int colunafim) {
		contador_empate = 0;
		
		
		// Adiciona a pe�a capturada no vetor correspondente
		if (celula[linhainicio][colunainicio].getcor() == "branco") {
			inte.atualiza_capturadas(celula[linhafim - 1][colunafim].getcor(), "Pe�o");
			jogador_branco.set_pe�as_capturadas(celula[linhafim - 1][colunafim]);
		} else {
			inte.atualiza_capturadas(celula[linhafim + 1][colunafim].getcor(), "Pe�o");
			jogador_preto.set_pe�as_capturadas(celula[linhafim + 1][colunafim]);
		}

		if (celula[linhainicio][colunainicio].getcor() == "branco") {
			// Muda a pe�a no tabuleiro grafico
			inte.mudar_imagem(linhainicio, colunainicio, "Vazio", "preto");
			inte.mudar_imagem(linhafim, colunafim, "Pe�o", "branco");
			inte.mudar_imagem(linhafim - 1, colunafim, "Vazio", "branco");
			// Muda no tabuleiro da engine
			celula[linhafim][colunafim] = celula[linhainicio][colunainicio];
			celula[linhainicio][colunainicio] = null;
			celula[linhafim - 1][colunafim] = null;
		} else {
			// Muda a pe�a no tabuleiro grafico
			inte.mudar_imagem(linhainicio, colunainicio, "Vazio", "preto");
			inte.mudar_imagem(linhafim, colunafim, "Pe�o", "preto");
			inte.mudar_imagem(linhafim + 1, colunafim, "Vazio", "branco");
			// Muda no tabuleiro da engine
			celula[linhafim][colunafim] = celula[linhainicio][colunainicio];
			celula[linhainicio][colunainicio] = null;
			celula[linhafim + 1][colunafim] = null;
		}
	}

	public boolean posi��es_diferentes(int linhainicio, int colunainicio, int linhafim, int colunafim) {
		if (linhainicio == linhafim) {
			if (colunainicio == colunafim) {
				return false;
			}
		}
		return true;
	}

	public void troca(int linhainicio, int colunainicio, int linhafim, int colunafim) {
		celula[linhainicio][colunainicio].setprimeiromovimento(); // Indica que a pe�a j� realizou algum movimento

		if (celula[linhainicio][colunainicio].getnome() == "Pe�o" && Math.abs(linhafim - linhainicio) == 1) {
			celula[linhainicio][colunainicio].set_en_passan();
		}
		pe�a = celula[linhafim][colunafim];
		// Adiciona a pe�a capturada no vetor correspondente
		if (celula[linhainicio][colunainicio].getcor() == "preto" && pe�a != null) {
			jogador_preto.set_pe�as_capturadas(pe�a);
			contador_empate = 0;
			inte.atualiza_capturadas(pe�a.getcor(), pe�a.getnome());

		} else if (pe�a != null) {
			jogador_branco.set_pe�as_capturadas(pe�a);
			contador_empate = 0;		
			inte.atualiza_capturadas(pe�a.getcor(), pe�a.getnome());
		}

		// Mantem as coordenadas dos reis atualizadas
		if (celula[linhainicio][colunainicio].getnome() == "Rei") {
			if (celula[linhainicio][colunainicio].getcor() == "preto") {
				coordenadasReipreto[0] = linhafim;
				coordenadasReipreto[1] = colunafim;
			} else {
				coordenadasReibranco[0] = linhafim;
				coordenadasReibranco[1] = colunafim;
			}
		}

		// Realiza a troca
		celula[linhafim][colunafim] = celula[linhainicio][colunainicio];
		celula[linhainicio][colunainicio] = null;

		// Muda a pe�a no tabuleiro grafico
		if (celula[linhafim][colunafim] != null) {
			inte.mudar_imagem(linhainicio, colunainicio, "Vazio", "preto");
			inte.mudar_imagem(linhafim, colunafim, celula[linhafim][colunafim].getnome(),
					celula[linhafim][colunafim].getcor());
		}
		contador_empate++;
	}

	public void moveRoque(int linhainicio, int colunainicio, int linhafim, int colunafim) {
		contador_empate++;
		// Identifica para qual lado o roque sera feito
		if (colunafim == 0) {
			if(celula[linhainicio][colunainicio].getcor()=="preto") {
				coordenadasReipreto[1]=2;
			}else {
				coordenadasReibranco[1]=2;
			}
			this.registra_roque(colunafim);
			// Muda a pe�a no tabuleiro grafico
			inte.mudar_imagem(linhainicio, colunainicio, "Vazio", "preto");
			inte.mudar_imagem(linhafim, colunafim, "Vazio", "preto");
			inte.mudar_imagem(linhafim, colunafim + 3, celula[linhafim][colunafim].getnome(),
					celula[linhafim][colunafim].getcor());
			inte.mudar_imagem(linhafim, colunainicio - 2, celula[linhafim][colunainicio].getnome(),
					celula[linhafim][colunainicio].getcor());
			// Muda no tabuleiro da engine
			celula[linhainicio][colunainicio - 2] = celula[linhainicio][colunainicio];
			celula[linhainicio][colunainicio] = null;
			celula[linhafim][colunafim + 3] = celula[linhafim][colunafim];
			celula[linhafim][colunafim] = null;
		} else {
			if(celula[linhainicio][colunainicio].getcor()=="preto") {
				coordenadasReipreto[1]=6;
			}else {
				coordenadasReibranco[1]=6;
			}
			this.registra_roque(colunafim);
			// Muda a pe�a no tabuleiro grafico
			inte.mudar_imagem(linhainicio, colunainicio, "Vazio", "preto");
			inte.mudar_imagem(linhafim, colunafim, "Vazio", "preto");
			inte.mudar_imagem(linhafim, colunafim - 2, celula[linhafim][colunafim].getnome(),
					celula[linhafim][colunafim].getcor());
			inte.mudar_imagem(linhafim, colunainicio + 2, celula[linhafim][colunainicio].getnome(),
					celula[linhafim][colunainicio].getcor());
			// Muda no tabuleiro da engine
			celula[linhainicio][colunainicio + 2] = celula[linhainicio][colunainicio];
			celula[linhainicio][colunainicio] = null;
			celula[linhafim][colunafim - 2] = celula[linhafim][colunafim];
			celula[linhafim][colunafim] = null;
		}
	}

	// Menu utilizado para chamar as fun��es de verifica��o de cada pe�a
	public int menujogada(int linhainicio, int colunainicio, int linhafim, int colunafim) {
		int aux = 0;
		// Pega-se o nome da pe�a escolhida e chama-se sua fun��o de verifica��o
		switch (celula[linhainicio][colunainicio].getnome()) {

		case "Pe�o":
			aux = this.verificaPe�o(linhainicio, colunainicio, linhafim, colunafim, false);
			if (aux == 12) {
				aux = this.check(linhainicio, colunainicio, linhafim, colunafim);
				if (aux == 1) {
					return 12;
				}
			}
			if (aux == 25) {
				aux = this.check(linhainicio, colunainicio, linhafim, colunafim);
				if (aux == 1) {
					return 25;
				}
			}
			if (aux != 0) {
				return this.check(linhainicio, colunainicio, linhafim, colunafim);
			}

			return 0;
		case "Torre":
			aux = this.verificaTorre(linhainicio, colunainicio, linhafim, colunafim);
			if (aux != 0) {
				return this.check(linhainicio, colunainicio, linhafim, colunafim);
			}
			return 0;
		case "Cavalo":
			aux = this.verificaCavalo(linhainicio, colunainicio, linhafim, colunafim);
			if (aux != 0) {
				return this.check(linhainicio, colunainicio, linhafim, colunafim);
			}
			return 0;
		case "Bispo":
			aux = this.verificaBispo(linhainicio, colunainicio, linhafim, colunafim);
			if (aux != 0) {
				return this.check(linhainicio, colunainicio, linhafim, colunafim);
			}
			return 0;
		case "Rainha":
			// Confere se a rainha vai se movimentar na diagonal ou em uma reta
			// Se entrar no if se movimentara como um bispo
			if ((linhainicio != linhafim) && (colunainicio != colunafim)) {
				aux = this.verificaBispo(linhainicio, colunainicio, linhafim, colunafim);
				if (aux != 0) {
					return this.check(linhainicio, colunainicio, linhafim, colunafim);
				}
				return 0;
			} else { // Se n�o entrar se movimenta como uma torre
				aux = this.verificaTorre(linhainicio, colunainicio, linhafim, colunafim);
				if (aux != 0) {
					return this.check(linhainicio, colunainicio, linhafim, colunafim);
				}
				return 0;
			}
		case "Rei":
			aux = this.verificaRei(linhainicio, colunainicio, linhafim, colunafim);
			// System.out.println("\n"+aux+"\n");
			if (aux != 0 && aux != 10) {
				return this.check(linhainicio, colunainicio, linhafim, colunafim);
			}
			if (aux == 10) {
				return 10;
			}
			return 0;
		}
		return 0; // Necessario por causa do compilador
	}

	// Verifica se o rei est� em check. Caso esteja retorna 0 e o movimento �
	// ilegal, caso � esteja retorna 1
	public int check(int linhainicio, int colunainicio, int linhafim, int colunafim) {

		// Variaveis que ir�o conter as coordenadas do rei
		int linhaRei = 0;
		int colunaRei = 0;
		
		// Guarda a cor da pe�a a ser testada
		String cor = celula[linhainicio][colunainicio].getcor();
		int aux = 0;

		// Guarda a pe�a inicial
		Pe�as pe�ainicio = new Pe�as();

		if (celula[linhainicio][colunainicio].getnome() == "Rei") {
			linhaRei = linhafim;
			colunaRei = colunafim;
			// System.out.println("\n"+linhaRei+" "+colunaRei);
		} else {
			if (cor == "preto") {
				linhaRei = coordenadasReipreto[0];
				colunaRei = coordenadasReipreto[1];
			} else {
				linhaRei = coordenadasReibranco[0];
				colunaRei = coordenadasReibranco[1];
			}
		}

		// Faz a jogada escolhida, para ver se ela deixara o rei em check
		pe�a = celula[linhafim][colunafim];
		pe�ainicio = celula[linhainicio][colunainicio];
		celula[linhafim][colunafim] = celula[linhainicio][colunainicio];
		celula[linhainicio][colunainicio] = null;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((celula[i][j] != null) && (celula[i][j].getcor() != cor)) {
					switch (celula[i][j].getnome()) {
					case "Pe�o":
						aux = this.verificaPe�o(i, j, linhaRei, colunaRei, true);
						break;
					case "Bispo":
						aux = this.verificaBispo(i, j, linhaRei, colunaRei);
						break;
					case "Torre":
						aux = this.verificaTorre(i, j, linhaRei, colunaRei);
						break;
					case "Cavalo":
						aux = this.verificaCavalo(i, j, linhaRei, colunaRei);
						break;
					case "Rainha":
						aux = this.verificaBispo(i, j, linhaRei, colunaRei);
						if (aux != 1) {
							aux = this.verificaTorre(i, j, linhaRei, colunaRei);
						}
						break;
					}
				}

				if (aux == 1) {
					celula[linhafim][colunafim] = pe�a;
					celula[linhainicio][colunainicio] = pe�ainicio;
					return 0;
				}
			}
		}
		celula[linhafim][colunafim] = pe�a;
		celula[linhainicio][colunainicio] = pe�ainicio;
		return 1;
	}

	// Verifica se o movimento da pe�a Pe�o � permitido
	public int verificaPe�o(int linhainicio, int colunainicio, int linhafim, int colunafim, boolean check) {

		String cor = celula[linhainicio][colunainicio].getcor();

		// Confere se o movimento do pe�o � valido
		boolean aux = celula[linhainicio][colunainicio].move(linhainicio, colunainicio, linhafim, colunafim, cor);
		if (aux == false) {
			return 0;
		}

		// Confere se o movimento � um enPassan
		if (colunainicio != colunafim && linhainicio != linhafim && contadorjogadas > 2) {
			if (this.enPassan(linhainicio, colunainicio, linhafim, colunafim)) { // Confere se o enPassan � valido
				return 12;
			}
		}

		// If= O pe�o esta apenas andando // Else= O pe�o esta atacando outra pe�a
		if (colunainicio == colunafim) {
			if ((Math.abs(linhainicio - linhafim) == 1) && (celula[linhafim][colunafim] != null)) { // Verifica se a
																									// casa de destino
																									// do pe�o esta
																									// ocupada
				return 0;
			} else { // Verifica se o caminho do pe�o esta bloqueado
				if ((cor == "preto")
						&& ((celula[linhafim][colunafim] != null) || (celula[linhainicio - 1][colunafim] != null))) {
					return 0;
				} else if ((cor == "branco")
						&& ((celula[linhafim][colunafim] != null) || (celula[linhainicio + 1][colunafim] != null))) {
					return 0;
				}
			}
		} else { // Em caso de ataque
			if ((Math.abs(linhainicio - linhafim) != 1) || (Math.abs(colunainicio - colunafim) != 1)) { // Confere se �
																										// um ataque
																										// valido
				return 0;
			} else {
				if ((celula[linhafim][colunafim] == null) || (celula[linhafim][colunafim].getcor() == cor)) {
					return 0;
				}
			}
		}

		// Caso o pe�o v� para ultima fileira oposta a sua, ele � trocado por outra pe�a
		if (linhafim == 7 || linhafim == 0) {
			return 25;
		}
		return 1;
	}

	public boolean material_insuficiente(String cor) {

		int contador = 0;
		if (cor == "branco" && jogador_branco.tamanho_vetor_capturadas() < 15) {
			return false;
		} else if (cor == "preto" && jogador_preto.tamanho_vetor_capturadas() < 15) {
			return false;
		}

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((celula[i][j] != null) && (celula[i][j].getcor() == cor)) {
					switch (celula[i][j].getnome()) { // ENTAO NAO EH CHECK MATE
					case "Pe�o":
						return false;
					case "Bispo":
						contador++;
						break;
					case "Torre":
						return false;
					case "Cavalo":
						contador++;
						break;
					case "Rainha":
						return false;
					}
				}
			}
		}

		if (contador > 2) {
			return false;
		}
		return true;
	}

	public boolean afogamento(String cor) {
		int aux = 0;
		int registro = 0;
		if (cor == "preto") {
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {

					if (i != 0 || j != 0) {
						if ((coordenadasReipreto[0] + i <= 7) && (coordenadasReipreto[0] + i >= 0)
								&& (coordenadasReipreto[1] + i <= 7) && (coordenadasReipreto[1] + i >= 0)) {
							aux = this.check(coordenadasReipreto[0], coordenadasReipreto[1], coordenadasReipreto[0] + i,
									coordenadasReipreto[1] + j);
							if (aux == 1) {
								registro = 1;
							}
						}
					}
				}
			}
		} else {
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {

					if (i != 0 || j != 0) {
						if ((coordenadasReibranco[0] + i <= 7) && (coordenadasReibranco[0] + i >= 0)
								&& (coordenadasReibranco[1] + i <= 7) && (coordenadasReibranco[1] + i >= 0)) {
							aux = this.check(coordenadasReibranco[0], coordenadasReibranco[1],
									coordenadasReibranco[0] + i, coordenadasReibranco[1] + j);
							if (aux == 1) {
								registro = 1;
							}
						}
					}
				}
			}
		}
		if (registro != 1) {
			return this.movimentos_possiveis(cor);
		}
		return true; // Se retornar true quer dizer que o rei n�o esta afogado
	}

	public boolean check_mate(String cor) {
		if (cor == "preto" && check(coordenadasReipreto[0], coordenadasReipreto[1], coordenadasReipreto[0],
				coordenadasReipreto[1]) == 0) {
			if (this.movimentos_possiveis(cor)) {
				
				return false; // Se n�o estiver em check mate return false
			}
			return true; // Caso esteja em check mate return true
		} else if (cor == "branco" && check(coordenadasReibranco[0], coordenadasReibranco[1], coordenadasReibranco[0],
				coordenadasReibranco[1]) == 0) {
			if (this.movimentos_possiveis(cor)) {
				
				return false; // Se n�o estiver em check mate return false
			}
			return true; // Caso esteja em check mate return true
		}
		
		return false;
	}

	public boolean movimentos_possiveis(String cor) {
		int aux = 0;
		int controle = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((celula[i][j] != null) && (celula[i][j].getcor() == cor)) { // ENCONTRA TODAS PE�AS DO JOGADOR EM
																				// AMEA�A DE CHECK MATE
					for (int auxi = 0; auxi < 8; auxi++) { // TESTA TODOS OS MOVIMENTOS POSSIVEIS NO TABULEIRO PARA CADA
															// UMA DE SUAS PE�AS
						for (int auxj = 0; auxj < 8; auxj++) { // SE QUALQUER MOVIMENTOR FOR LEGAL (FUN��O CHECK TORNA
																// MOVIMENTO ILEGAL CASO REI NAO SAIA DE CHECK)
							switch (celula[i][j].getnome()) { // ENTAO NAO EH CHECK MATE
							case "Pe�o":
								aux = this.verificaPe�o(i, j, auxi, auxj, true);
								if (aux != 0) {
									aux = this.check(i, j, auxi, auxj);
								}
								if (aux != 0) {
									System.out.println("Pe�o");
									controle = 1;
								}
								break;
							case "Bispo":
								aux = this.verificaBispo(i, j, auxi, auxj);
								if (aux != 0) {
									aux = this.check(i, j, auxi, auxj);
								}
								if (aux != 0) {
									System.out.println("Bispo");
									controle = 1;
								}
								break;
							case "Torre":
								aux = this.verificaTorre(i, j, auxi, auxj);
								if (aux != 0) {
									aux = this.check(i, j, auxi, auxj);
								}
								if (aux != 0) {
									System.out.println("Torre");
									controle = 1;
								}
								break;
							case "Cavalo":
								aux = this.verificaCavalo(i, j, auxi, auxj);
								if (aux != 0) {
									aux = this.check(i, j, auxi, auxj);
								}
								if (aux != 0) {
									System.out.println("Cavalo");
									controle = 1;
								}
								break;
							case "Rainha":
								aux = this.verificaBispo(i, j, auxi, auxj);
								if (aux == 0) {
									aux = this.verificaTorre(i, j, auxi, auxj);
								}						
								if(aux !=0) {
									aux = this.check(i, j, auxi, auxj);
								}
								if (aux != 0) {
									controle = 1;
								}
								break;
							case "Rei":
								aux = this.verificaRei(i, j, auxi, auxj);
								if (aux != 0) {
									aux = this.check(i, j, auxi, auxj);
								}
								if (aux != 0) {
									System.out.println("Rei");
									controle = 1;
								}
							}
						}
					}
				}
			}
		}
		if (controle != 0) {
			return true;
		}
		return false;
	}

	public boolean enPassan(int linhainicio, int colunainicio, int linhafim, int colunafim) {

		// A variavel "aux" guarda o registro da jogada anterior
		char aux = Jogadores.get_jogadas(contadorjogadas - 1).charAt(0);
		String cor = celula[linhainicio][colunainicio].getcor();
		if(colunafim!=this.coluna_string(aux)) {
			return false;
		}
		if(linhainicio==1||linhainicio==6) {
			return false;
		}
		 aux = Jogadores.get_jogadas(contadorjogadas - 1).charAt(1);

		// Verifica se o pe�o � preto ou branco
		if (cor == "branco") {
			if (celula[4][colunafim] != null && celula[linhafim][colunafim] == null
					&& celula[4][colunafim].getnome() == "Pe�o" && celula[4][colunafim].get_en_passan()) {
				if (aux == '5') {
					return true;
				}
			}
		} else {
			if (celula[3][colunafim] != null && celula[linhafim][colunafim] == null
					&& celula[3][colunafim].getnome() == "Pe�o" && celula[3][colunafim].get_en_passan()) {
				if (aux == '4') {
					return true;
				}
			}
		}
		return false;
	}

	// Realiza a troca do pe�o por outra pe�a escolhida
	public void trocaPe�as(int linhainicio, int colunainicio, int linhafim, int colunafim) {
		String aux = inte.op��es_pe�o();
		String cor = celula[linhainicio][colunainicio].getcor(); // Pega a cor do jogador que est� realizando a troca
		// Troca a pe�a "Pe�o" pela pe�a escolhida pelo jogador
		switch (aux) {
		case "Rainha":
			this.registra_promo��o(linhafim, colunafim, "D");
			celula[linhainicio][colunainicio] = new Rainha(cor);
			break;
		case "Bispo":
			this.registra_promo��o(linhafim, colunafim, "B");
			celula[linhainicio][colunainicio] = new Bispo(cor);
			break;
		case "Torre":
			this.registra_promo��o(linhafim, colunafim, "T");
			celula[linhainicio][colunainicio] = new Torre(cor);
			break;
		case "Cavalo":
			this.registra_promo��o(linhafim, colunafim, "C");
			celula[linhainicio][colunainicio] = new Cavalo(cor);
			break;
		}

		this.troca(linhainicio, colunainicio, linhafim, colunafim);
	}

	// Verifica se o movimento da pe�a Torre � permitido
	public int verificaTorre(int linhainicio, int colunainicio, int linhafim, int colunafim) {
		int inicio = 0;
		int fim = 0;
		// Faz a sele��o do inicio e do fim, serve para o for abaixo
		if (linhainicio == linhafim) {
			if (colunainicio < colunafim) {
				inicio = colunainicio;
				fim = colunafim;
			} else {
				inicio = colunafim;
				fim = colunainicio;
			}
			// Confere se o caminho est� livre para o movimento
			for (int i = inicio; i < fim - 1; i++) {
				inicio++;
				if (celula[linhainicio][inicio] != null) {
					return 0;
				}
			}
		} else if (colunainicio == colunafim) {
			if (linhainicio < linhafim) {
				inicio = linhainicio;
				fim = linhafim;
			} else {
				inicio = linhafim;
				fim = linhainicio;
			}
			// Confere se o caminho est� livre para o movimento
			for (int i = inicio; i < fim - 1; i++) {
				inicio++;
				if (celula[inicio][colunainicio] != null) {
					return 0;
				}
			}
		} else {
			return 0;
		}
		// Confere se o movimento da pe�a � possivel
		if (inicio + 1 != fim) {
			return 0;
		}

		// Confere se a torre esta atacando outra pe�a
		if ((celula[linhafim][colunafim] != null)
				&& (celula[linhafim][colunafim].getcor() == celula[linhainicio][colunainicio].getcor())) {
			return 0;
		}

		return 1;
	}

	// Verifica se o movimento da pe�a Cavalo � permitido
	public int verificaCavalo(int linhainicio, int colunainicio, int linhafim, int colunafim) {
		if (((Math.abs(linhainicio - linhafim) == 2) && (Math.abs(colunainicio - colunafim) == 1))
				|| ((Math.abs(linhainicio - linhafim) == 1) && (Math.abs(colunainicio - colunafim) == 2))) {
			if (celula[linhafim][colunafim] != null) {
				if (celula[linhafim][colunafim].getcor() == celula[linhainicio][colunainicio].getcor()) {
					return 0;
				}
				return 1;
			}
			return 1;
		}
		return 0;
	}

	// Verifica se o movimento da pe�a Bispo � permitido
	public int verificaBispo(int linhainicio, int colunainicio, int linhafim, int colunafim) {
		int auxColuna = Math.abs(colunainicio - colunafim);
		int auxLinha = Math.abs(linhainicio - linhafim);
		int aux = 0;
		if (auxColuna <= auxLinha) {
			aux = auxColuna;
		} else {
			aux = auxLinha;
		}

		String cor = celula[linhainicio][colunainicio].getcor();

		if (linhainicio > linhafim && colunainicio > colunafim) {
			for (int i = 2; i <= aux; i++) {
				linhainicio--;
				colunainicio--;
				if (celula[linhainicio][colunainicio] != null) {
					return 0;
				}
			}
			linhainicio--;
			colunainicio--;
		} else if (linhainicio > linhafim && colunainicio < colunafim) {
			// System.out.println(linhainicio+"\n"+colunainicio+"\n"+linhafim+"\n"+colunafim);
			for (int i = 2; i <= aux; i++) {
				linhainicio--;
				colunainicio++;
				if (celula[linhainicio][colunainicio] != null) {
					return 0;
				}
			}
			linhainicio--;
			colunainicio++;

		} else if (linhainicio < linhafim && colunainicio > colunafim) {

			for (int i = 2; i <= aux; i++) {
				linhainicio++;
				colunainicio--;
				if (celula[linhainicio][colunainicio] != null) {

					return 0;
				}
			}
			linhainicio++;
			colunainicio--;

		} else if (linhainicio < linhafim && colunainicio < colunafim) {

			for (int i = 2; i <= aux; i++) {
				linhainicio++;
				colunainicio++;
				if (celula[linhainicio][colunainicio] != null) {
					return 0;
				}
			}
			linhainicio++;
			colunainicio++;
		}

		if ((linhainicio != linhafim) || (colunainicio != colunafim)) {

			return 0;
		}

		if ((celula[linhafim][colunafim] != null) && (celula[linhafim][colunafim].getcor() == cor)) {
			return 0;
		}
		return 1;
	}

	public int verificaRei(int linhainicio, int colunainicio, int linhafim, int colunafim) {

		if (this.roque(linhainicio, colunainicio, linhafim, colunafim)) {
			return 10;
		}
		if ((Math.abs(linhainicio - linhafim) > 1) || (Math.abs(colunainicio - colunafim) > 1)) {
			return 0;
		}

		if (celula[linhafim][colunafim] != null) {
			if (celula[linhafim][colunafim].getcor() == celula[linhainicio][colunainicio].getcor()) {
				return 0;
			} else {
				return 1;
			}
		}
		return 1;
	}

	public boolean roque(int linhainicio, int colunainicio, int linhafim, int colunafim) {
		
		//A torre tem que estar na posi��o 0 ou 7 para ser um roque valido
		if(colunafim!=0&&colunafim!=7) {
			return false;
		}
		//Confere se o rei esta em check
		if(this.check(linhainicio, colunainicio, linhainicio, colunainicio)==0){
			return false;
		}
		//Confere se a pe�a � final � nula
		if(celula[linhafim][colunafim]==null) { 
			return false;
		}
		//Confere se a pe�a final � uma torre, caso n�o seja returna que o roque � invalido
		if(celula[linhafim][colunafim].getnome()!="Torre") { 
			return false;
		}
		//Confere se as pe�as s�o da mesma cor
		if(celula[linhainicio][colunainicio].getcor()!=celula[linhafim][colunafim].getcor()) {
			return false;
		}
		//Confere se � o primeiro movimento de ambas as pe�as
		if(celula[linhainicio][colunainicio].getprimeiromovimento()==false || celula[linhafim][colunafim].getprimeiromovimento()==false) {
			return false;
		}
		if(colunafim==0) {
			for(int i=1;i<=3;i++) {
				if(celula[linhainicio][i]!=null) {
					return false;
				}
				if(this.check(linhainicio, colunainicio, linhainicio, i)==0) {
					return false;
				}
			}
		}else {
			for(int i=5;i<=6;i++) {
				if(celula[linhainicio][i]!=null) {
					return false;
				}
				if(this.check(linhainicio, colunainicio, linhainicio, i)==0) {
					return false;
				}
			}
		}	
		return true;
	}

	// Seta o tabuleiro na posi��o original
	public void iniciaTabuleiro() {
		// Seta as pe�as brancas
		celula[0][0] = new Torre("branco");
		celula[0][1] = new Cavalo("branco");
		celula[0][2] = new Bispo("branco");
		celula[0][3] = new Rainha("branco");
		celula[0][4] = new Rei("branco");
		coordenadasReibranco[0] = 0;
		coordenadasReibranco[1] = 4;
		celula[0][5] = new Bispo("branco");
		celula[0][6] = new Cavalo("branco");
		celula[0][7] = new Torre("branco");
		for (int i = 0; i < 8; i++) {

			celula[1][i] = new Pe�o("branco");
		}

		// Seta as pe�as pretas
		celula[7][0] = new Torre("preto");
		celula[7][1] = new Cavalo("preto");
		celula[7][2] = new Bispo("preto");
		celula[7][3] = new Rainha("preto");
		celula[7][4] = new Rei("preto");
		coordenadasReipreto[0] = 7;
		coordenadasReipreto[1] = 4;
		celula[7][5] = new Bispo("preto");
		celula[7][6] = new Cavalo("preto");
		celula[7][7] = new Torre("preto");
		for (int i = 0; i < 8; i++) {

			celula[6][i] = new Pe�o("preto");
		}

		// Zera as posi��es
		for (int linha = 2; linha < 6; linha++) {
			for (int coluna = 0; coluna < 8; coluna++) {
				celula[linha][coluna] = null;
			}
		}
		this.imprime(); // Imprime o tabuleiro
	}

	public void imprime() {

		for (int linha = 0; linha < 8; linha++) {
			for (int coluna = 0; coluna < 8; coluna++) {
				if (celula[linha][coluna] == null) {
					inte.mudar_imagem(linha, coluna, "Vazio", "preto");
				} else {
					inte.mudar_imagem(linha, coluna, celula[linha][coluna].getnome(), celula[linha][coluna].getcor());
				}
			}
		}
	}

	public void registra(int linhainicio, int colunainicio, int linhafim, int colunafim) {
		String auxiliar;
		switch (celula[linhainicio][colunainicio].getnome()) {
		case "Pe�o":
			if (celula[linhafim][colunafim] != null) {
				auxiliar = colunaInt(colunainicio) + "x" + colunaInt(colunafim) + (linhafim + 1);
				
				Jogadores.set_jogadas(auxiliar);
			} else {
				auxiliar = colunaInt(colunafim) + (linhafim + 1);
				
				Jogadores.set_jogadas(auxiliar);
			}
			break;
		case "Torre":
			if (celula[linhafim][colunafim] != null) {
				auxiliar = "T" + "x" + colunaInt(colunafim) + (linhafim + 1);
				
				Jogadores.set_jogadas(auxiliar);
			} else {
				auxiliar = "T" + colunaInt(colunafim) + (linhafim + 1);
				
				Jogadores.set_jogadas(auxiliar);
			}
			break;
		case "Bispo":
			if (celula[linhafim][colunafim] != null) {
				auxiliar = "B" + "x" + colunaInt(colunafim) + (linhafim + 1);
				
				Jogadores.set_jogadas(auxiliar);
			} else {
				auxiliar = "B" + colunaInt(colunafim) + (linhafim + 1);
				
				Jogadores.set_jogadas(auxiliar);
			}
			break;
		case "Rainha":
			if (celula[linhafim][colunafim] != null) {
				auxiliar = "D" + "x" + colunaInt(colunafim) + (linhafim + 1);
				
				Jogadores.set_jogadas(auxiliar);
			} else {
				auxiliar = "D" + colunaInt(colunafim) + (linhafim + 1);
				
				Jogadores.set_jogadas(auxiliar);
			}
			break;
		case "Rei":
			if (celula[linhafim][colunafim] != null) {
				auxiliar = "R" + "x" + colunaInt(colunafim) + (linhafim + 1);
				
				Jogadores.set_jogadas(auxiliar);
			} else {
				auxiliar = "R" + colunaInt(colunafim) + (linhafim + 1);
				
				Jogadores.set_jogadas(auxiliar);
			}
			break;
		case "Cavalo":
			if (celula[linhafim][colunafim] != null) {
				auxiliar = "C" + "x" + colunaInt(colunafim) + (linhafim + 1);
				
				Jogadores.set_jogadas(auxiliar);
			} else {
				auxiliar = "C" + colunaInt(colunafim) + (linhafim + 1);
				
				Jogadores.set_jogadas(auxiliar);
			}
		}
	}

	public void registra_vitoria(String cor) {
		String auxiliar;
		if (cor == "branco") {
			auxiliar = "0-1";
			
			Jogadores.set_jogadas(auxiliar);
		} else {
			auxiliar = "1-0";
			
			Jogadores.set_jogadas(auxiliar);
		}
	}

	public void registra_check_checkmate(String aux) {
		String registra = Jogadores.get_jogadas(contadorjogadas);

		if (aux == "check") {
			registra = registra + "+";
			Jogadores.replace_jogadas(contadorjogadas, registra);
		} else {
			registra = registra + "#";
			Jogadores.replace_jogadas(contadorjogadas, registra);
		}
	}

	public void desambigua(int linhainicio, int colunainicio, int linhafim, int colunafim) {
		String nome = celula[linhainicio][colunainicio].getnome();
		String cor = celula[linhainicio][colunainicio].getcor();
		int controle = 0;
		int aux = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (celula[i][j] != null && celula[i][j].getnome() == nome && celula[i][j].getcor() == cor
						&& posi��es_diferentes(linhainicio, colunainicio, i, j)) {
					switch (nome) {
					case "Cavalo":
						aux = this.verificaCavalo(i, j, linhafim, colunafim);
						if (aux != 0) {
							controle = 1;
							this.registra_desambigua(linhainicio, colunainicio, linhafim, colunafim, i, j);
						}
						break;
					case "Torre":
						aux = this.verificaTorre(i, j, linhafim, colunafim);
						if (aux != 0) {
							controle = 1;
							this.registra_desambigua(linhainicio, colunainicio, linhafim, colunafim, i, j);
						}
						break;
					case "Bispo":
						aux = this.verificaBispo(i, j, linhafim, colunafim);
						if (aux != 0) {
							controle = 1;
							this.registra_desambigua(linhainicio, colunainicio, linhafim, colunafim, i, j);
						}
						break;
					case "Rainha":
						aux = this.verificaBispo(i, j, linhafim, colunafim);
						if (aux == 1) {
							controle = 1;
							this.registra_desambigua(linhainicio, colunainicio, linhafim, colunafim, i, j);
						} else {
							aux = this.verificaTorre(i, j, linhafim, colunafim);
							if (aux != 0) {
								controle = 1;
								this.registra_desambigua(linhainicio, colunainicio, linhafim, colunafim, i, j);
							}
						}
						break;
					}
				}

			}
		}
		if (controle == 0) {
			this.registra(linhainicio, colunainicio, linhafim, colunafim);
		}

	}

	public void registra_desambigua(int linhape�a1, int colunape�a1, int linhafim, int colunafim, int linhape�a2,
			int colunape�a2) {
		String auxiliar;
		switch (celula[linhape�a1][colunape�a1].getnome()) {
		case "Torre":
			if (colunape�a1 == colunape�a2) {
				if (celula[linhafim][colunafim] != null) {
					auxiliar = "T" + linhape�a1 + "x" + colunaInt(colunafim) + (linhafim + 1);
					
					Jogadores.set_jogadas(auxiliar);
				} else {
					auxiliar = "T" + linhape�a1 + colunaInt(colunafim) + (linhafim + 1);
					
					Jogadores.set_jogadas(auxiliar);
				}
			} else {
				if (celula[linhafim][colunafim] != null) {
					auxiliar = "T" + colunaInt(colunape�a1) + "x" + colunaInt(colunafim) + (linhafim + 1);
					
					Jogadores.set_jogadas(auxiliar);
				} else {
					auxiliar = "T" + colunaInt(colunape�a1) + colunaInt(colunafim) + (linhafim + 1);
					
					Jogadores.set_jogadas(auxiliar);
				}
			}
			break;
		case "Bispo":
			if (celula[linhafim][colunafim] != null) {
				if (celula[linhafim][colunafim] != null) {
					auxiliar = "B" + linhape�a1 + "x" + colunaInt(colunafim) + (linhafim + 1);
					
					Jogadores.set_jogadas(auxiliar);
				} else {
					auxiliar = "B" + linhape�a1 + colunaInt(colunafim) + (linhafim + 1);
					
					Jogadores.set_jogadas(auxiliar);
				}
			} else {
				if (celula[linhafim][colunafim] != null) {
					auxiliar = "B" + colunaInt(colunape�a1) + "x" + colunaInt(colunafim) + (linhafim + 1);
					
					Jogadores.set_jogadas(auxiliar);
				} else {
					auxiliar = "B" + colunaInt(colunape�a1) + colunaInt(colunafim) + (linhafim + 1);
					
					Jogadores.set_jogadas(auxiliar);
				}
			}
			break;
		case "Rainha":
			if (celula[linhafim][colunafim] != null) {
				if (celula[linhafim][colunafim] != null) {
					auxiliar = "D" + linhape�a1 + "x" + colunaInt(colunafim) + (linhafim + 1);
					
					Jogadores.set_jogadas(auxiliar);
				} else {
					auxiliar = "D" + linhape�a1 + colunaInt(colunafim) + (linhafim + 1);
					
					Jogadores.set_jogadas(auxiliar);
				}
			} else {
				if (celula[linhafim][colunafim] != null) {
					auxiliar = "D" + colunaInt(colunape�a1) + "x" + colunaInt(colunafim) + (linhafim + 1);
					
					Jogadores.set_jogadas(auxiliar);
				} else {
					auxiliar = "D" + colunaInt(colunape�a1) + colunaInt(colunafim) + (linhafim + 1);
					
					Jogadores.set_jogadas(auxiliar);
				}
			}
			break;
		case "Cavalo":
			if (celula[linhafim][colunafim] != null) {
				if (celula[linhafim][colunafim] != null) {
					auxiliar = "C" + linhape�a1 + "x" + colunaInt(colunafim) + (linhafim + 1);
					
					Jogadores.set_jogadas(auxiliar);
				} else {
					auxiliar = "C" + linhape�a1 + colunaInt(colunafim) + (linhafim + 1);
					
					Jogadores.set_jogadas(auxiliar);
				}
			} else {
				if (celula[linhafim][colunafim] != null) {
					auxiliar = "C" + colunaInt(colunape�a1) + "x" + colunaInt(colunafim) + (linhafim + 1);
					
					Jogadores.set_jogadas(auxiliar);
				} else {
					auxiliar = "C" + colunaInt(colunape�a1) + colunaInt(colunafim) + (linhafim + 1);
					
					Jogadores.set_jogadas(auxiliar);
				}
			}
		}
	}

	public void registra_roque(int aux) {
		String auxiliar;

		if (aux == 0) {
			auxiliar = "0" + "-" + "0";
			
			Jogadores.set_jogadas(auxiliar);
		} else {
			auxiliar = "0" + "-" + "0" + "-" + "0";
			
			Jogadores.set_jogadas(auxiliar);
		}
	}

	public void registra_promo��o(int linha, int coluna, String nome) {
		String auxiliar;

		auxiliar = colunaInt(coluna) + (linha + 1) + (nome);
		
		Jogadores.set_jogadas(auxiliar);

	}

	public void registra_empate() {
		String auxiliar;
		auxiliar = "1/2" + "-" + "1/2";
		
		Jogadores.set_jogadas(auxiliar);
	}

	public String colunaInt(int coluna) {
		switch (coluna) {

		case 0:
			return "a";
		case 1:
			return "b";
		case 2:
			return "c";
		case 3:
			return "d";
		case 4:
			return "e";
		case 5:
			return "f";
		case 6:
			return "g";
		case 7:
			return "h";
		}
		return null;
	}

	public int coluna_string(char coluna) {
		switch (coluna) {

		case 'a':
			return 0;
		case 'b':
			return 1;
		case 'c':
			return 2;
		case 'd':
			return 3;
		case 'e':
			return 4;
		case 'f':
			return 5;
		case 'g':
			return 6;
		case 'h':
			return 7;
		}
		return 0;
	}
}
