package ch.hsr.tellstrack.model;

import java.util.List;

/**
 * Created by Alexander on 21/02/2018.
 */

public class ConnectionList{

    public List<Connection> connections;

    public List<Connection> getConnections() {
        return connections;
    }

    private void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    @Override
    public String toString(){

        String connections = "";

        for (int i = 0; i < getConnections().size(); i++) {
            connections += i + ". " + getConnections().get(i).toString() + "\r\n";
        }

        return connections;
    }
}
