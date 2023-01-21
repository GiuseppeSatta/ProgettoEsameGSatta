import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Test2 {
    public static void main(String[] args) throws IOException {
        System.out.println("""
                Scegliere un'operazione:
                0 - Aggiungi nuova scheda
                1 - Rimuovi schede
                2 - Modifica scheda
                3 - Salva e chiudi\s""");
    }
}
