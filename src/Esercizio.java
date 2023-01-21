import java.io.Serializable;

public class Esercizio implements Serializable, Comparable<Esercizio> {
    enum Level{
        BEGINNER,
        INTERMEDIATE,
        ADVANCED;
        public static Level getLevel(String lvl) throws EnumConstantNotPresentException{
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

        public static ULC getULC(String ulc) throws EnumConstantNotPresentException {
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

        public static PushPull getPushPull(String pushPull) throws EnumConstantNotPresentException {
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

        public static Modality getModality(String modality) throws EnumConstantNotPresentException {
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

        public static Joint getJoint(String joint) throws EnumConstantNotPresentException {
            if (joint.equalsIgnoreCase("M"))
                return M;
            if (joint.equalsIgnoreCase("S"))
                return S;
            throw new EnumConstantNotPresentException(Joint.class, joint);
        }
    }
    Joint joint;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Esercizio(){}

    public Esercizio(String ex) {
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
            e.printStackTrace();
        }
    }
    public boolean equals(Esercizio ex){
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
}
