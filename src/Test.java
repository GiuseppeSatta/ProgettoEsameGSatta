import java.util.Scanner;

public class Test {
    static Scanner scan=new Scanner(System.in);
    public static void main(String[] args) {
        Esercizio esercizio=new Esercizio(scan.nextLine());
        System.out.println(esercizio);
    }
}