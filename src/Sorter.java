import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Sorter {
    private static int MAX_CHUNK_SIZE = 10;//максимальный размер чанка, который помещается в память
    private static final int COUNT_THREADS = Runtime.getRuntime().availableProcessors();


    public File sortFile(File file) throws IOException {
        //деление файла на части с параллельной сортировкой
        List<File> sortedChunks = splitSortedFile(file);

        //параллельное слияние отсортированных частей
        return mergeSortedChunks(sortedChunks);
    }


    private List<File> splitSortedFile(File file) throws IOException {

        List<File> sortedChunks = new ArrayList<>();
        try(Scanner scanner = new Scanner(new FileInputStream(file))){
            List<Long> chunk = new ArrayList<>();
            while(scanner.hasNextLong()){
                chunk.clear();

                //читаем в чанк
                while (chunk.size() < MAX_CHUNK_SIZE && scanner.hasNextLong()){
                    chunk.add(scanner.nextLong());
                }

                //сортируем чанк
                Collections.sort(chunk);

                //записываем чанк в новый файл
                File chunkFile = File.createTempFile("sortedChunk", ".txt");
                try(PrintWriter writer = new PrintWriter(new FileWriter(chunkFile))){
                    for(Long numberInChunk : chunk){
                        writer.println(numberInChunk);
                    }
                }
                sortedChunks.add(chunkFile);
            }
        }
        return sortedChunks;
    }

    private File mergeSortedChunks(List<File> sortedChunks) {

        return null;
    }
}
