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

        Scheda test=new Scheda("FooBars");
        test.addEsercizio("Full Reverse Crunch");
        schede.add(test);

        for(Scheda i:schede){
            System.out.println(i.toString());
        }

        save(schede);
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
}