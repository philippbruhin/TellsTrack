package ch.hsr.tellstrack.model;

import java.util.List;

/**
 * Created by Alexander on 20/02/2018.
 */

public class Connection {
    public Number capacity1st;
    public Number capacity2nd;
    public String duration;
    public ConnectionStation from;
    public List<String> products;
    public List<Section> sections;
    public Service service;
    public ConnectionStation to;
    public Number transfers;
}

