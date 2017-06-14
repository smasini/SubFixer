package logic;

import models.Remain;
import java.io.*;

/**
 * Created by Simone Masini on 12/06/2017.
 */
public class Utility {

    public static boolean isNumber(String text){
        try{
            int i = Integer.parseInt(text.trim());
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static String getExtension(String filePath){
        String[] arrs = filePath.split(".");
        if(arrs.length>0){
            return arrs[arrs.length-1];
        }
        return "";
    }

    public static String getNewFileName(String filePath){
        return getNewFileName(filePath, 0);
    }

    public static String getNewFileName(String filePath, int existing){
        String ext = getExtension(filePath);
        if(ext.equals("")){
            ext = "srt";
        }
        String end = "_edited" + (existing > 0 ? existing : "") + "." + ext;
        String outputName = filePath.replace("." + ext, end);
        File file = new File(outputName);
        if(file.exists()){
            return getNewFileName(filePath, existing + 1);
        }
        return outputName;
    }

    public static Remain calculateRemains(int value, boolean anticipate){
        Remain remain = new Remain();
        remain.setValue(value);
        remain.setRemain(0);
        return calculateRemains(remain, anticipate);
    }

    public static Remain calculateRemains(Remain remain, boolean anticipate){
        if(remain.getValue() >= 0 && remain.getValue()<60){
            return remain;
        }
        if(anticipate){
            remain.setValue(remain.getValue() + 60);
            remain.setRemain(remain.getRemain()-1);
        }else{
            remain.setValue(remain.getValue() - 60);
            remain.setRemain(remain.getRemain()+1);
        }
        return calculateRemains(remain, anticipate);
    }

}
