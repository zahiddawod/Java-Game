import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
    public static String[][] loadFile(String path) {
       StringBuilder builder = new StringBuilder();
       ArrayList<String[]> lineInfo = new ArrayList();
       int numOfLines = 0;

       try {
           BufferedReader br = new BufferedReader(new FileReader(path));
           String line;
           while ((line = br.readLine()) != null) {
               lineInfo.add(line.split("\\s+"));
               //lineInfo.get(numOfLines++).add(line.split("\\s+"));
               builder.append(line + "\n");
           }
           br.close();
       } catch (IOException ioe) {
           ioe.printStackTrace();
       }
       String info[][] = new String[lineInfo.size()][];
       for (int i = 0; i < info.length; i++)
           info[i] = lineInfo.get(i);

       return info;//builder.toString();
    }

    public static int parseInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            return 0;
        }
    }
}
