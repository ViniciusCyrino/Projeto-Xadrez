import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Main {

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        musica();
        Game jogo = new Game();
        jogo.controle();
        int aux=Jogadores.tamanho_vetor_jogadas();
        try {
        	Arquivodetexto arquivo = new Arquivodetexto();
        	for(int i=0;i<aux;i++) {
        		arquivo.escrever(Jogadores.get_jogadas(i));
        	}
        	arquivo.fechar();
        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }


}
    static void musica()
    {
        Thread music = new Thread() {
           public void run() {
              Clip clip;
              try {
                 AudioInputStream input = AudioSystem.getAudioInputStream(new File("music.wav"));
                 clip = AudioSystem.getClip();
                 clip.open(input);
                 clip.loop(Clip.LOOP_CONTINUOUSLY);
                 clip.start();
              } catch (UnsupportedAudioFileException e) {
                 e.printStackTrace();
              } catch (IOException e) {
                 e.printStackTrace();
              } catch (LineUnavailableException e) {
                 e.printStackTrace();
              }
           }
        };
        music.start();
}
    }