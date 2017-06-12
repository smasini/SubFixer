package logic;

import models.FromTime;
import models.SubSection;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import logic.Utility;

/**
 * Created by Simone Masini on 12/06/2017.
 */
public class SubChanger {

    private String filePath;
    private boolean anticipate;
    private FromTime fromTime;
    private int changeValue;
    private List<SubSection> subSections;

    public SubChanger(String filePath, boolean anticipate, String fromTime, int changeValue) {
        this.filePath = filePath;
        this.anticipate = anticipate;
        this.fromTime = new FromTime(fromTime);
        this.changeValue = changeValue;
    }

    public void parseAndSave(){
        parseFile();
        save();
    }

    private void parseFile(){
        subSections = new ArrayList<SubSection>();
        File file = new File(filePath);
        try {
            Scanner scan = new Scanner(file);
            //scan.useDelimiter("\r\n");
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
        if(section.getSubTimeStart().compare(fromTime) >= 0){
            section.getSubTimeStart().changeTime(changeValue, anticipate);
            section.getSubTimeEnd().changeTime(changeValue, anticipate);
        }
        return section;
    }


    private void save(){
        String outputName = Utility.getNewFileName(filePath);
        FileOutputStream file = null;
        try {
            file = new FileOutputStream(outputName);
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
        }
    }

}
