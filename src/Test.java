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
            System.out.println("Il file 'schede.dat' non e' valido");
            return;
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
            file.delete();
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("schede.dat"));
            outputStream.writeObject(schede);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}