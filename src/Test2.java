import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Test2 {
    public static void main(String[] args) throws IOException {
        String test="test";
        ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream("schede.dat"));
        out.writeObject(test);
        out.close();
    }
}
