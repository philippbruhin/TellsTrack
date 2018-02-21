package ch.hsr.tellstrack.model;

/**
 * Created by Alexander on 20/02/2018.
 */

public class SearchData {
    String from = "";
    String to = "";
    String via = "";
    String time = "";
    String date = "";
    boolean isArrival = false;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public boolean getIsArrival() {
        return isArrival;
    }

    public void setIsArrival(boolean isArrival) {
        this.isArrival = isArrival;
    }
}