package ch.hsr.tellstrack.model;

/**
 * Created by Alexander on 20/02/2018.
 */

public class Prognosis {
    public String arrival;
    public Number capacity1st;
    public Number capacity2nd;
    public String departure;
    public String platform;

    public String getArrival(){
        return this.arrival;
    }
    public void setArrival(String arrival){
        this.arrival = arrival;
    }
    public Number getCapacity1st(){
        return this.capacity1st;
    }
    public void setCapacity1st(Number capacity1st){
        this.capacity1st = capacity1st;
    }
    public Number getCapacity2nd(){
        return this.capacity2nd;
    }
    public void setCapacity2nd(Number capacity2nd){
        this.capacity2nd = capacity2nd;
    }
    public String getDeparture(){
        return this.departure;
    }
    public void setDeparture(String departure){
        this.departure = departure;
    }
    public String getPlatform(){
        return this.platform;
    }
    public void setPlatform(String platform){
        this.platform = platform;
    }
}
