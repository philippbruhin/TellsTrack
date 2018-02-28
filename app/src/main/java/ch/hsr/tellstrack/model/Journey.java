package ch.hsr.tellstrack.model;

/**
 * Created by Alexander on 20/02/2018.
 *
 * https://transport.opendata.ch/docs.html#journey
 * The actual transportation of a section, e.g. a bus or a train between two stations.
 */

public class Journey {
    public String name;
    public String category;
    public String categoryCode;
    public String number;
    public String operator;
    public String to;
    public String capacity1st;
    public String capacity2nd;
}
