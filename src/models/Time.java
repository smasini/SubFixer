package models;

/**
 * Created by Simone Masini on 12/06/2017.
 */
public class Time {

    private int hour, minute, second;

    public Time(String time) {
        this.hour = 0;
        this.minute = 0;
        this.second = 0;
        if(time != null && !time.equals("")){
            String[] arrs1 = time.split(":");
            if (arrs1.length >= 3) {
                hour = Integer.parseInt(arrs1[0]);
                minute = Integer.parseInt(arrs1[1]);
                second = Integer.parseInt(arrs1[2]);
            }
        }
    }

    public boolean isAllZero(){
        return hour == 0 && minute == 0 && second == 0;
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
}
