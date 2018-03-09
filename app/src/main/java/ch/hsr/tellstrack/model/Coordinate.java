package ch.hsr.tellstrack.model;

/**
 * Created by Alexander on 20/02/2018.
 *
 * https://transport.opendata.ch/docs.html#coordinates
 */

public class Coordinate{
    public String type;
    public Number x;
    public Number y;


    public String getType(){
        return this.type;
    }
    public void setType(String type){
        this.type = type;
    }
    public Number getX(){
        return this.x;
    }
    public void setX(Number x){
        this.x = x;
    }
    public Number getY(){
        return this.y;
    }
    public void setY(Number y){
        this.y = y;
    }

}