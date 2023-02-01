import java.io.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;
public class Palestra {
    static Scanner scan=new Scanner(System.in);
    private static final String ANNULLA = "0";
    private static final String FINE = "4";
    public static void main(String[] args) {

        TreeSet<Scheda> schede=loadSchede();


        if(!schede.isEmpty()){
            System.out.println("Schede correnti:");


            for(Scheda i:schede){
                System.out.println(i.toString());
            }
        }
        String i;
        do {
            System.out.println("""
                    Scegliere un'operazione:
                    0 - Aggiungi nuova scheda
                    1 - Rimuovi schede
                    2 - Modifica scheda
                    3 - Copia Scheda
                    4 - Salva e chiudi\s""");

            i = scan.nextLine();

            switch (i) {
                case "0" -> aggiungiScheda(schede);
                case "1" -> schede = rimuoviSchede(schede);
                case "2" -> schede = modificaScheda(schede);
                case "3" -> copiaScheda(schede);
                case "4" -> i=save(schede);
                default -> System.out.println("Scelta non valida");
            }
        } while(!i.equals("4"));

    }

    static TreeSet<Scheda> loadSchede(){
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("schede.dat"));
            @SuppressWarnings("unchecked") TreeSet<Scheda> schede=(TreeSet<Scheda>)inputStream.readObject();
            inputStream.close();
            return schede;

        }catch (FileNotFoundException e){
            return new TreeSet<>();

        }catch (IOException | ClassNotFoundException | ClassCastException e){
            System.out.println("Il file 'schede.dat' non e' valido, sovrascrivere? (S/N)");

            if(scan.nextLine().toUpperCase().charAt(0)=='N'){
                System.out.println("Terminazione programma");
                System.exit(0);
            }

            File del=new File("schede.dat");
            if(!del.delete()) {
                System.out.println("Impossibile sovrascrivere");
                System.exit(0);
            }
            return new TreeSet<>();

        }
    }

    static String save(TreeSet<Scheda> schede) {
        try {

            File file=new File("schede.dat");

            if(!file.exists()){         //se il file non esiste, si tenta di crearlo

                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
                outputStream.writeObject(schede);
                return FINE;
            }
            System.out.println("Sovrascrivere? (S/N)");
            if(scan.nextLine().trim().equalsIgnoreCase("N"))
                return ANNULLA;
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
                    return FINE;

                System.out.println("Inserire nome nuovo file");
                file=new File(scan.nextLine()+".dat");

                while(file.exists()){
                    System.out.println("File gia' esistente, inserire un altro nome");
                    file=new File(scan.nextLine()+".dat");
                }

            }

            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(schede);
            return FINE;
        } catch (IOException e){
            e.printStackTrace();
            return ANNULLA;
        }
    }
    static void aggiungiScheda(TreeSet<Scheda >schede){
        System.out.println("Inserire nome utente (inserire 0 per annullare)");
        String nome;

        if((nome=scan.nextLine()).trim().equals("0"))
            return;

        Scheda nuova=new Scheda(nome);

        if(schede.contains(nuova)){
            System.out.println("Scheda gia' esistente");
            return;
        }
        schede.add(switchAggiungiScheda(nuova));
    }
    static TreeSet<Scheda> rimuoviSchede(TreeSet<Scheda> schede){

        if(schede.isEmpty()){
            System.out.println("Non e' presente nessuna scheda");
            return schede;
        }

        TreeSet<Scheda> temp=new TreeSet<>(schede);
        String scheda;

        System.out.println("""
                Digitare i nomi delle schede da rimuovere.
                Per confermare, digitare 0;
                Per annullare, digitare ANNULLA\s""");

        while(!(scheda=scan.nextLine().trim()).equals("0")){

            if(scheda.trim().equals("ANNULLA")) {
                System.out.println("Eliminazioni annullate.");
                return temp;
            }

            if(schede.remove(new Scheda(scheda)))
                System.out.println("Scheda rimossa correttamente");
            else
                System.out.println("Scheda non presente");
        }

        return schede;
    }
    static TreeSet<Scheda> modificaScheda(TreeSet<Scheda> schede){

        if(schede.isEmpty()){
            System.out.println("Non e' presente nessuna scheda");
            return schede;
        }

        System.out.println("Inserire il nome della scheda da modificare.\n" +
                "Per annullare, digitare ANNULLA");
        String nomeScheda=scan.nextLine();
        TreeSet<Scheda> temp=new TreeSet<>(schede);
        while(!schede.contains(new Scheda(nomeScheda))){
            if (nomeScheda.equals("ANNULLA"))
                return temp;
            System.out.println("Scheda non presente. Inserire nome scheda");
            nomeScheda= scan.nextLine();
        }
        Scheda scheda=null;
        Scheda test=new Scheda(nomeScheda);
        Iterator<Scheda> schedaIterator= schede.iterator();
        
        for(int i=0;i<schede.size();i++){
            if((scheda=schedaIterator.next()).equals(test))
                break;
        }
        System.out.println(scheda);
        schede.remove(scheda);
        switchAggiungiScheda(scheda);
        schede.add(scheda);
        
        System.out.println("Scheda modificata:\n"+scheda+"\nConfermare modifiche? (S/N)");
        if(scan.nextLine().equalsIgnoreCase("N"))
            return temp;
        return schede;
    }
    static void copiaScheda(TreeSet<Scheda> schede){
        if(schede.isEmpty()){
            System.out.println("Non e' presente nessuna scheda");
            return;
        }
        String nomeScheda;
        boolean test;
        do {
            System.out.println("Inserire il nome della scheda da copiare, ANNULLA per annullare");
            nomeScheda= scan.nextLine();
            if(nomeScheda.equals("ANNULLA"))
                return;
            if(!(test=schede.contains(new Scheda(nomeScheda))))
                System.out.println("Scheda non presente");                
        }while (!test);
        
        Scheda scheda = new Scheda();
        Scheda testScheda=new Scheda(nomeScheda);
        Iterator<Scheda> schedaIterator= schede.iterator();
        
        
        for(int i=0;i<schede.size();i++){
            
            if((scheda=schedaIterator.next()).equals(testScheda))
                break;
        
        }
        Scheda nuovaScheda;
        do {
            System.out.println("Inserire il nome della nuova scheda, ANNULLA per annullare");
            nomeScheda = scan.nextLine();
            if (nomeScheda.equals("ANNULLA"))
                return;
            nuovaScheda=new Scheda(nomeScheda);
            test=schede.contains(nuovaScheda);
            if(test)
                System.out.println("Nome scheda già occupato");
        }while(test);
        schede.add(new Scheda(nomeScheda,scheda));
    }
    
    static Scheda switchAggiungiScheda(Scheda nuova){
        String i;

        do{
            System.out.println("""
                    Scegliere un'operazione:
                    0 - Visualizza lista esercizi
                    1 - Aggiungi esercizio
                    2 - Rimuovi esercizio
                    3 - Fine
                    \s""");

            switch(i=scan.nextLine()){
                case "0":
                    System.out.println("""
                            Inserire filtro ricerca
                            Digitare 'tutti' per vedere tutti gli esercizi o per rimuovere i filtri inseriti;
                            Digitare il nome di una categoria (nome di un gruppo muscolare, livello di difficolta', ecc.)
                            per filtrare per categoria;
                            Digitare piu' categorie consecutivamente per aggiungere più filtri;
                            Digitare 'Indietro' per tornare indietro\s""");
                    Esercizio.listaEsercizi();
                    System.out.println("""
                    Scegliere un'operazione:
                    0 - Visualizza lista esercizi
                    1 - Aggiungi esercizio
                    2 - Rimuovi esercizio
                    3 - Fine
                    \s""");
                    break;

                case "1":
                    System.out.println("Inserire nome esercizio");
                    if(!nuova.addEsercizio(scan.nextLine().trim()))
                        System.out.println("Esercizio gia' presente");
                    else
                        System.out.println("Esercizio aggiunto con successo");
                    break;

                case "2":
                    System.out.println("Inserire nome esercizio");
                    if(!nuova.removeEsercizio(scan.nextLine().trim()))
                        System.out.println("Esercizio non presente");
                    else
                        System.out.println("Esercizio rimosso con successo");
                    break;

                case "3":
                    break;

                default:
                    System.out.println("Scelta non valida");

            }
        }while(!i.equals("3"));
        return nuova;
    }
}