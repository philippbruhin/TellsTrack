package ch.hsr.tellstrack.model;

/**
 * Created by Alexander on 20/02/2018.
 *
 * Documentation: https://transport.opendata.ch/docs.html#location
 */

public class Station {
    public Coordinate coordinate;
    public String distance;
    public String id;
    public String name;
    public String score;

    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getDistance() {
        return this.distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return this.score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}