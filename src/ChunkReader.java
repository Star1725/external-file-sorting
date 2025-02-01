import java.io.*;

//Этот класс используется для построчного чтения чисел из файла, который уже отсортирован.
//Он работает как итератор, предоставляя следующий элемент, но не загружает весь файл в память благодаря BufferedReader.
public class ChunkReader {
    private final BufferedReader reader;
    private Long nextNumber;//следующее число в файле, которое уже было считано, но пока не извлечено

    public ChunkReader(File file) throws FileNotFoundException {
        this.reader = new BufferedReader(new FileReader(file));
        this.nextNumber = readNextNumber();
    }

    //Проверяет, есть ли ещё числа в файле. Если nextValue == null, значит, достигнут конец файла.
    public boolean hasNextNumber(){
        return nextNumber != null;
    }

    //Позволяет посмотреть на следующее число, но не "сдвигает" указатель.
    public Long showsFollowNumbs() {
        return nextNumber;
    }

    //Возвращает текущее число (nextNumber).
    //Сдвигает указатель, загружая в nextNumber следующее число из файла.
    public Long getNextNumber(){
        long currentNumber = nextNumber;
        nextNumber = readNextNumber();
        return currentNumber;
    }

    //читает строки из файла
    private Long readNextNumber() {
        try {
            assert reader != null;
            String line = reader.readLine();
            if (line != null) {
                return Long.parseLong(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
