package ch.hsr.tellstrack.model;

/**
 * Created by Alexander on 20/02/2018.
 *
 * Documentation: https://transport.opendata.ch/docs.html#service
 * Operation information for a connection.
 */

public class Service {
    private String regular;
    private String irregular;

    public String getIrregular() {
        return irregular;
    }

    public void setIrregular(String irregular) {
        this.irregular = irregular;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }
}
