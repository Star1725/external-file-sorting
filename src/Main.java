import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        Generator generator = new Generator();
        Sorter sorter = new Sorter();
        try {
            File file = generator.generate("Numbs", 100);
            sorter.sortFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}