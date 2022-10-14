package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileUtils {

    public static String readString(String path) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            boolean newFile = file.createNewFile();
            if (!newFile) {
                return "";
            }
        }
        FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

}
