import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utilities {

    public static String[][] loadFile(String path) {
       ArrayList<String[]> lineInfo = new ArrayList<>();

       try {
           BufferedReader br = new BufferedReader(new FileReader(path));
           String line;
           while ((line = br.readLine()) != null)
               lineInfo.add(line.split("\\s+"));
           br.close();
       } catch (IOException ioe) {
           ioe.printStackTrace();
       }
       String info[][] = new String[lineInfo.size()][];
       for (int i = 0; i < info.length; i++)
           info[i] = lineInfo.get(i);

       return info;
    }

}
