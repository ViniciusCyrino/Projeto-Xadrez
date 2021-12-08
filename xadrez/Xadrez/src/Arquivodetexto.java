import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Arquivodetexto {
    FileWriter myWriter = new FileWriter("registrodejogadas.txt");
    
    Arquivodetexto() throws IOException
    {

    }
    void criar() throws IOException
    {
              File myObj = new File("registrodejogadas.txt");
              if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
              } else {
                System.out.println("File already exists.");
              }
    }
    void escrever(String jogada) throws IOException
    {
        myWriter.append(jogada);
        myWriter.append("\n");
    }
    void fechar() throws IOException
    {
        myWriter.close();
    }

}
