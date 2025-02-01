import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Sorter {
    private static int MAX_CHUNK_SIZE = 100000;//максимальный размер чанка, который помещается в память
    private static final int COUNT_THREADS = Runtime.getRuntime().availableProcessors();


    public File sortFile(File file){
        //деление файла на части с параллельной сортировкой
        List<File> sortedChunks = splitSortedFile(file);

        //параллельное слияние отсортированных частей
        return mergeSortedChunks(sortedChunks);
    }


    private List<File> splitSortedFile(File file) {

        List<File> sortedChunks = new ArrayList<File>();

        return sortedChunks;
    }

    private File mergeSortedChunks(List<File> sortedChunks) {

        return null;
    }
}
