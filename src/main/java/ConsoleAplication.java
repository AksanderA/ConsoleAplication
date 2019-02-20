import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ConsoleAplication {
    private static List<WalkFileTree> listFileTree = new ArrayList<>();
    private static String nameFile;
    public static void main(String[] args) throws InterruptedException {
        nameFile = args[1];
        try{
            FileInputStream fiStream = new FileInputStream(args[0]);
            BufferedReader br = new BufferedReader(new InputStreamReader(fiStream));
            String strLine;
            int counter = 1;
            Runtime.getRuntime().addShutdownHook(new SaveDataBeforeClosing());
            while ((strLine = br.readLine()) != null) {
                WalkFileTree walkFileTree = new WalkFileTree(Paths.get(strLine), counter++);
                Thread thread = new Thread(walkFileTree);
                thread.start();
                thread.join();
                listFileTree.add(walkFileTree);
                System.out.println(listFileTree.size());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
       // writeFile();
    }

    public static void writeFile() {
        try {
            FileWriter writer = new FileWriter(nameFile);
            // создаем CsvBeanWriter со стандартными настройками (кодировка, переносы строк, разделители и т.д.)
            ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(writer, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
            String[] header = new String[]{"counterStrings", "numberFiles", "sourcePath"};

            // создаем заголовок
            csvBeanWriter.writeHeader(header);

            for (WalkFileTree wfl : listFileTree) {
                System.out.println(wfl.getCounterStrings() + " " + wfl.getNumberFiles() + " " +wfl.getSourcePath());
                csvBeanWriter.write(wfl, header, getProcessors());
            }
            csvBeanWriter.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
        private static CellProcessor[] getProcessors() {
            return new CellProcessor[]{
                    new UniqueHashCode(),
                    new NotNull(),
                    new Optional()
            };
        }

    }

