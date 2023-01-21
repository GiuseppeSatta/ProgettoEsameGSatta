import java.io.*;
import java.util.TreeSet;

public class Scheda implements Serializable,Comparable<Scheda> {
    TreeSet<Esercizio> esercizi;
    String nomeUtente;

    public Scheda(){
        esercizi=new TreeSet<>();
    }

    public Scheda(String newName, Scheda s){
        esercizi=new TreeSet<>(s.esercizi);
        nomeUtente=newName;
    }

    public Scheda(String newName){
        esercizi=new TreeSet<>();
        nomeUtente=newName;
    }

    public boolean addEsercizio(Esercizio ex){
        return esercizi.add(ex);
    }

    public boolean addEsercizio(String ex){
        try {
            BufferedReader r = new BufferedReader(new FileReader("esercizi.txt"));
            String line;
            while((line = r.readLine())!=null) {
                if (line.contains("\t" + ex + "\t")) {
                    return esercizi.add(new Esercizio(line));
                }
            }
        }catch (FileNotFoundException e){
            System.out.println("ERRORE: File 'esercizi.txt' non trovato");
            return false;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
        System.out.println("Esercizio non trovato nel file 'esercizi.txt'");
        return false;
    }

    public boolean removeEsercizio(Esercizio ex){
        return esercizi.remove(ex);
    }

    public boolean removeEsercizio(String ex){
        Esercizio test=new Esercizio();
        test.setName(ex);
        return esercizi.remove(test);
    }

    @Override
    public int compareTo(Scheda o) {
        return this.nomeUtente.compareTo(o.nomeUtente);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass()!=this.getClass())
            return false;
        return this.nomeUtente.equals(((Scheda) obj).nomeUtente);
    }

    @Override
    public String toString() {
        StringBuilder val= new StringBuilder();
        val.append(nomeUtente);
        for(Esercizio i:esercizi){
            val.append("\n").append(i.getName());
        }
        return val.toString();
    }
}
