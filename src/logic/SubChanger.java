package logic;

import models.Time;
import models.SubSection;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Simone Masini on 12/06/2017.
 */
public class SubChanger {

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
        save();
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


    private void save(){
        FileOutputStream file = null;
        try {
            file = new FileOutputStream(filePathDest);
        } catch (FileNotFoundException ex) {
            System.out.print("Errore salvataggio");
        }if(file!=null) {
            PrintStream output = new PrintStream(file);
            for (SubSection subSection : subSections) {
                output.println(subSection.getId());
                output.println(subSection.printRangeTime());
                for (String line : subSection.getSubsLines()) {
                    output.println(line);
                }
                output.println();
            }
            output.close();
            try {
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
