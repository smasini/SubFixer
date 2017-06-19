package logic;

import models.Time;
import models.SubSection;
import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.input.BOMInputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Simone Masini on 12/06/2017.
 */
public class SubChanger {

    private Callback callback;
    private String filePath, filePathDest;
    private boolean anticipate;
    private Time fromTime, toTime;
    private boolean checkEndTime;
    private int changeValue;
    private List<SubSection> subSections;

    public SubChanger(String filePath, boolean anticipate, String fromTime, String toTime, int changeValue) {
        this.filePath = filePath;
        this.anticipate = anticipate;
        this.fromTime = new Time(fromTime);
        this.toTime = new Time(toTime);
        this.changeValue = changeValue;
        this.checkEndTime = !this.toTime.isAllZero();
    }

    public void parseAndSave(){
        parseFile();
        if(save()) {
            if (callback != null) {
                callback.onComplete();
            }
        }else{
            if (callback != null) {
                callback.onError();
            }
        }
    }

    private void parseFile(){
        subSections = new ArrayList<SubSection>();
        filePathDest = Utility.getNewFileName(filePath);
        File file = new File(filePathDest);
        SubConverter.transform(new File(filePath), file, "UTF-8");
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                subSections.add(changeSection(getSection(scan)));
            }
            scan.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Impossibile leggere il file");
        }
    }

    private SubSection getSection(Scanner scanner){
        return getSection(scanner, null);
    }

    private SubSection getSection(Scanner scanner, SubSection section){
        if(section == null){
            section = new SubSection();
        }
        String line = scanner.nextLine();
        if(line.equals("\n") || line.equals("")){
            return section;
        }
        if(!section.isIdSetup() && Utility.isNumber(line)){
            section.setId(Integer.parseInt(line));
        }else if(!section.isTimeSetup() && section.getSubsLines().size() == 0){
            section.setRangeTime(line);
        }else{
            section.getSubsLines().add(line);
        }
        return getSection(scanner, section);
    }

    private SubSection changeSection(SubSection section){
        if(section.getSubTimeStart().compare(fromTime) >= 0 && (!checkEndTime || section.getSubTimeEnd().compare(toTime) <= 0)){
            section.getSubTimeStart().changeTime(changeValue, anticipate);
            section.getSubTimeEnd().changeTime(changeValue, anticipate);
        }
        return section;
    }

    private boolean save(){
        try {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(filePathDest, "UTF-8");
                for (SubSection subSection : subSections) {
                    writer.println(subSection.getId());
                    writer.println(subSection.printRangeTime());
                    for (String line : subSection.getSubsLines()) {
                        writer.println(line);
                    }
                    writer.println();
                }
            }
            finally {
                if(writer!=null){
                    writer.close();
                }
            }
            return true;
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
