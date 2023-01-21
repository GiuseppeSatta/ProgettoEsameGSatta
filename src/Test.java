import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.function.Predicate;

class Test {
    private static ArrayList<String> inzializzaLista(){
        String line;
        ArrayList<String> lines=new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("esercizi.txt"));
            while((line=reader.readLine())!=null) {
                if (line.contains("\""))
                    break;
                lines.add(line.trim());
            }
            reader.close();
        }catch (FileNotFoundException e) {
            System.out.println("ERRORE: File 'esercizi.txt' non trovato");
            return new ArrayList<>();
        }catch (IOException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
        return lines;
    }
    public static void listaEsercizi(){
        Scanner scan=new Scanner(System.in);
        String filtro;
        StringBuilder filtri= new StringBuilder();
        ArrayList<String> risultato=inzializzaLista();

        if(risultato.isEmpty())
            return;

        for(String i:risultato){
            System.out.println(i);
        }

        while(!((filtro= scan.nextLine()).equalsIgnoreCase("INDIETRO"))){
            if(filtro.length()==1)
                filtro= filtro + "\t";

            if(filtro.equalsIgnoreCase("tutti")) {
                risultato = inzializzaLista();
                filtri = new StringBuilder();
            }

            else {

                filtri.append(filtro).append(",\n");
                if (!risultato.isEmpty()) {
                    removeFiltro(filtro, risultato);
                }

            }

            for(String i:risultato)
                System.out.println(i);
            if(!filtro.equalsIgnoreCase("tutti"))
                System.out.println("Filtri:\n"+filtri.substring(0,(filtri.toString().length())-2));
        }
    }
    static void removeFiltro(String filtro, ArrayList<String> risultato){
        final String fil=filtro;
        Predicate<String> predicate= s -> !s.contains(fil);
        risultato.removeIf(predicate);
    }
    public static void main(String[] args){
        TreeSet<Scheda> schede=new TreeSet<>();
        String nomeScheda="Me";
        Scheda scheda=new Scheda(nomeScheda);
        scheda.addEsercizio("Full Reverse Crunch");
        scheda.addEsercizio("Incline Hip Thrust");
        schede.add(scheda);
        schede.add(new Scheda("You",scheda));
        scheda=null;
        nomeScheda="You";
        Scheda test=new Scheda(nomeScheda);
        Iterator<Scheda> schedaIterator= schede.iterator();
        int i;
        for(i=0;i<schede.size();i++){
            if((scheda=schedaIterator.next()).equals(test))
                break;
        }
        System.out.println(scheda);
    }
}
