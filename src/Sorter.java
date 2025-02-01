import java.io.*;
import java.util.*;

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

    private File mergeSortedChunks(List<File> sortedChunks) throws FileNotFoundException {
        //Используем приоритетную очередь для слияния.
        //Элементы в очереди хранятся в порядке возрастания (сравнение происходит по showsFollowNumbs(), т.е. по текущему числу в файле).
        PriorityQueue<ChunkReader> priorityQueue = new PriorityQueue<>(Comparator.comparingLong(ChunkReader::showsFollowNumbs));
        //Добавление первого элемента из каждого файла в очередь
        for (File sortedChunk : sortedChunks) {
            ChunkReader reader = new ChunkReader(sortedChunk);
            //ChunkReader будет отдавать числа по очереди.
            if (reader.hasNextNumber()){
                priorityQueue.add(reader);
            }

        }

        //создаём файл для итоговой записи соединённых и отсортированных фалов
        File outputSortedFile = new File("sorted_numbs.txt");
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(outputSortedFile))) {
            while(!priorityQueue.isEmpty()){
                ChunkReader chunkReader = priorityQueue.poll();//извлекаем минимальный элемент
                long numberInChunk = chunkReader.getNextNumber();//читаем число и сдвигаемся на следующее
                writer.println(numberInChunk);//записываем его в итоговый файл
                if (chunkReader.hasNextNumber()){//если есть ещё элементы в файле
                    priorityQueue.add(chunkReader);//добавляем следующий элемент в очередь
                }
            }
        }

        for (File sortedChunk : sortedChunks) {
            sortedChunk.delete();
        }

        return outputSortedFile;
    }
}
