package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simone Masini on 12/06/2017.
 */
public class SubSection {

    private int id;
    private List<String> subsLines;
    private SubTime subTimeStart, subTimeEnd;
    private boolean isIdSetup, isTimeSetup;

    public SubSection() {
        isIdSetup = false;
        isTimeSetup = false;
        subsLines = new ArrayList<String>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        isIdSetup = true;
        this.id = id;
    }

    public void setRangeTime(String rangeTime) {
        isTimeSetup = true;
        subTimeStart = new SubTime(rangeTime, true);
        subTimeEnd = new SubTime(rangeTime, false);
    }

    public List<String> getSubsLines() {
        return subsLines;
    }

    public void setSubsLines(List<String> subsLines) {
        this.subsLines = subsLines;
    }

    public boolean isIdSetup() {
        return isIdSetup;
    }

    public boolean isTimeSetup() {
        return isTimeSetup;
    }

    public SubTime getSubTimeStart() {
        return subTimeStart;
    }

    public SubTime getSubTimeEnd() {
        return subTimeEnd;
    }

    public String printRangeTime(){
        return String.format("%s --> %s", subTimeStart.printTime(), subTimeEnd.printTime());
    }

}
