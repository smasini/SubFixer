package models;

import logic.Utility;

/**
 * Created by Simone Masini on 12/06/2017.
 */
public class SubTime {

    private int hour, minute, second, milliseconds;
    private boolean startTime;

    public SubTime(String time, boolean startTime){
        this.startTime = startTime;
        parseStringTime(time);
    }

    private void parseStringTime(String time){
        int index = time.indexOf("-->");
        if(index < 0){
            return;
        }
        if(startTime){
            time = time.substring(0, index);
        }else{
            time = time.substring(index + 3);
        }
        time = time.trim();

        String[] arrs1 = time.split(":");
        if(arrs1.length >= 3) {
            hour = Integer.parseInt(arrs1[0]);
            minute = Integer.parseInt(arrs1[1]);
            String[] arrs2 = arrs1[2].split(",");
            if(arrs2.length >= 2) {
                second = Integer.parseInt(arrs2[0]);
                milliseconds = Integer.parseInt(arrs2[1]);
            }
        }
    }

    public int compare(Time fromTime){
        if(hour == fromTime.getHour() && minute == fromTime.getMinute() && second == fromTime.getSecond()){
            return 0;
        }
        if(hour > fromTime.getHour()){
            return 1;
        }
        if(hour == fromTime.getHour()){
            if(minute > fromTime.getMinute()){
                return 1;
            }
            else if(minute == fromTime.getMinute()){
                if(second > fromTime.getSecond()){
                    return 1;
                }
            }
        }
        return -1;
    }

    public void changeTime(int dealy, boolean anticipate){
        if(anticipate){
            second -= dealy;
        }else{
            second += dealy;
        }
        Remain secondRemain = Utility.calculateRemains(second, anticipate);
        second = secondRemain.getValue();

        minute += secondRemain.getRemain();
        Remain minuteRemain = Utility.calculateRemains(minute, anticipate);
        minute = minuteRemain.getValue();

        hour += minuteRemain.getRemain();
    }



    public String printTime(){
        return String.format("%02d:%02d:%02d,%03d", hour, minute, second, milliseconds);
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public int getMilliseconds() {
        return milliseconds;
    }

    public boolean isStartTime() {
        return startTime;
    }
}
