public class Test2 {
    public static void main(String[] args) {
        String ex="	Abdominals - Lower	Full Reverse Crunch	Advanced	Core	Push	FW	M";
        String[] data = ex.split("\t");
        for(String i:data){
            System.out.println(i);
        }
    }
}
