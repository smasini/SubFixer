package logic;

import com.glaforge.i18n.io.CharsetToolkit;
import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.input.BOMInputStream;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Created by Simone Masini on 14/06/2017.
 */
public class SubConverter {

    public static void convertFile(File file){
        String filePathDest = Utility.getNewFileName(file.getAbsolutePath());
        File newFile = new File(filePathDest);
        transform(file, newFile, "UTF-8");
    }

    public static void transform(File source, File target, String tgtEncoding) {
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            try {
                InputStream inputStream = new FileInputStream(source);
                BOMInputStream bomInputStream = new BOMInputStream(inputStream);
                ByteOrderMark bom = bomInputStream.getBOM();
                br = new BufferedReader(new InputStreamReader(new BufferedInputStream(bomInputStream), getCharset(source)));
                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(target), tgtEncoding));
                char[] buffer = new char[16384];
                int read;
                while ((read = br.read(buffer)) != -1){
                    bw.write(buffer, 0, read);
                }
            } finally {
                try {
                    if (br != null)
                        br.close();
                } finally {
                    if (bw != null)
                        bw.close();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCharset(File file) {
        try {
            return CharsetToolkit.guessEncoding(file, 4096, StandardCharsets.UTF_8).name();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
