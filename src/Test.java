import java.io.*;
import java.util.Scanner;
import java.util.TreeSet;

public class Test {
    static Scanner scan=new Scanner(System.in);
    public static void main(String[] args) {
        TreeSet<Scheda> schede;
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("schede.dat"));
            schede=(TreeSet<Scheda>)inputStream.readObject();
            inputStream.close();

        }catch (FileNotFoundException e){
            schede=new TreeSet<>();

        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            return;

        } catch (ClassCastException e){
            System.out.println("Il file 'schede.dat' non e' valido, sovrascrivere? (S/N)");

            if(scan.nextLine().toUpperCase().charAt(0)=='N'){
                System.out.println("Terminazione programma");
                return;
            }

            File del=new File("schede.dat");
            if(!del.delete()) {
                System.out.println("Impossibile sovrascrivere");
                return;
            }
            schede=new TreeSet<>();

        }

        if(!schede.isEmpty()){
            System.out.println("Schede correnti:");

            for(Scheda i:schede){
                System.out.println(i.toString());
            }
        }
        int i;
        do {
            System.out.println("""
                    Scegliere un'operazione:
                    0 - Aggiungi nuova scheda
                    1 - Rimuovi schede
                    2 - Modifica scheda
                    3 - Salva e chiudi\s""");

            while ((i = scan.nextInt()) < 0 || i > 3) {
                System.out.println("Scelta non valida;");
            }
            switch (i) {
                case 0:
                    schede = aggiungiScheda(schede);
                    break;
                case 1:
                    schede = rimuoviSchede(schede);
                    break;
                case 2:
                    schede = modificaScheda(schede);
                    break;
                case 3:
                    save(schede);
            }
        } while(i!=3);

    }
    static void save(TreeSet<Scheda> schede) {
        try {

            File file=new File("schede.dat");

            if(!file.exists()){         //se il file non esiste, si tenta di crearlo

                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
                outputStream.writeObject(schede);
                return;
            }

            if(!file.delete()){
                System.out.println("Impossibile sovrascrivere, creare nuovo file? (S/N)");
                char s=scan.nextLine().toUpperCase().charAt(0);

                while(s=='N'){
                    System.out.println("Si perderanno tutti i progressi dall'ultimo avvio. Procedere? (S/N)");

                    if(scan.nextLine().toUpperCase().charAt(0)!='N')
                        break;

                    System.out.println("Impossibile sovrascrivere, creare nuovo file? (S/N)");

                    s=scan.nextLine().toUpperCase().charAt(0);
                }

                if(s=='N')  //se si è usciti dal break, s=='N' è true
                    return;

                System.out.println("Inserire nome nuovo file");
                file=new File(scan.nextLine()+".dat");

                while(file.exists()){
                    System.out.println("File gia' esistente, inserire un altro nome");
                    file=new File(scan.nextLine()+".dat");
                }

            }

            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(schede);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    static TreeSet<Scheda> aggiungiScheda(TreeSet<Scheda schede>){

    }
}