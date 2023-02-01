import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Predicate;

class Esercizio implements Serializable, Comparable<Esercizio> {
    enum Level{
        BEGINNER,
        INTERMEDIATE,
        ADVANCED;
        static Level getLevel(String lvl) throws EnumConstantNotPresentException{
            if(lvl.equalsIgnoreCase("BEGINNER"))
                return BEGINNER;
            if(lvl.equalsIgnoreCase("INTERMEDIATE"))
                return INTERMEDIATE;
            if(lvl.trim().equalsIgnoreCase("ADVANCED"))
                return ADVANCED;
            throw new EnumConstantNotPresentException(Level.class,lvl);
        }
    }
    Level level;
    String name;
    enum ULC {
        CORE,
        UPPER,
        LOWER;

        static ULC getULC(String ulc) throws EnumConstantNotPresentException {
            if (ulc.equalsIgnoreCase("CORE"))
                return CORE;
            if (ulc.equalsIgnoreCase("UPPER"))
                return UPPER;
            if (ulc.equalsIgnoreCase("LOWER"))
                return LOWER;
            throw new EnumConstantNotPresentException(ULC.class, ulc);
        }
    }
    ULC ulc;
    String muscleGroup;
    enum PushPull {
        PUSH,
        PULL,
        STATIC;

        static PushPull getPushPull(String pushPull) throws EnumConstantNotPresentException {
            if (pushPull.equalsIgnoreCase("PUSH"))
                return PUSH;
            if (pushPull.equalsIgnoreCase("PULL"))
                return PULL;
            if (pushPull.equalsIgnoreCase("STATIC"))
                return STATIC;
            throw new EnumConstantNotPresentException(PushPull.class, pushPull);
        }
    }
    PushPull pushPull;
    enum Modality {
        C,
        FW,
        M;

        static Modality getModality(String modality) throws EnumConstantNotPresentException {
            if (modality.equalsIgnoreCase("C"))
                return C;
            if (modality.equalsIgnoreCase("FW"))
                return FW;
            if (modality.equalsIgnoreCase("M"))
                return M;
            throw new EnumConstantNotPresentException(Modality.class, modality);
        }
    }

    Modality modality;

    enum Joint {
        M,
        S;

        static Joint getJoint(String joint) throws EnumConstantNotPresentException {
            if (joint.equalsIgnoreCase("M"))
                return M;
            if (joint.equalsIgnoreCase("S"))
                return S;
            throw new EnumConstantNotPresentException(Joint.class, joint);
        }
    }
    Joint joint;

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
    Esercizio(){}

    Esercizio(String ex) {
        if(ex==null)
            throw new NullPointerException();
        String[] data = ex.trim().split("\t");
        try {
            muscleGroup = data[0];
            name = data[1];
            level = Level.getLevel(data[2]);
            ulc = ULC.getULC(data[3]);
            pushPull = PushPull.getPushPull(data[4]);
            modality = Modality.getModality(data[5]);
            joint = Joint.getJoint(data[6]);
        } catch (EnumConstantNotPresentException | ArrayIndexOutOfBoundsException e){
            System.out.println("ERRORE: File 'esercizi.txt' non valido o corrotto");
            System.exit(0);
        }
    }
    boolean equals(Esercizio ex){
        return name.equals(ex.name);
    }
    @Override
    public boolean equals(Object obj){
        if(obj==null||obj.getClass()!=this.getClass())
            return false;
        return equals((Esercizio) obj);
    }
    public int compareTo(Esercizio o){
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return "Esercizio{" +
                "level=" + level +
                ", name='" + name + '\'' +
                ", ulc=" + ulc +
                ", muscleGroup='" + muscleGroup + '\'' +
                ", pushPull=" + pushPull +
                ", modality=" + modality +
                ", joint=" + joint +
                '}';
    }

    static void listaEsercizi(){
        Scanner scan=new Scanner(System.in);
        String filtro;
        StringBuilder filtri= new StringBuilder();
        ArrayList<String> risultato=inzializzaLista();

        if(risultato.isEmpty())
            return;


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
    private static void removeFiltro(String filtro, ArrayList<String> risultato){
        final String fil=filtro;
        Predicate<String> predicate= s -> !s.contains(fil);
        risultato.removeIf(predicate);
    }
}
