package ch.hsr.tellstrack.model;

/**
 * Created by Alexander on 20/02/2018.
 *
 * https://transport.opendata.ch/docs.html#section
 * A connection consists of one or multiple sections.
 */

public class Section {
    public Journey journey;

    public ConnectionStation departure;

    public ConnectionStation arrival;

    public ConnectionStation getArrival() {
        return arrival;
    }

    public void setArrival(ConnectionStation arrival) {
        this.arrival = arrival;
    }

    public ConnectionStation getDeparture() {
        return departure;
    }

    public void setDeparture(ConnectionStation departure) {
        this.departure = departure;
    }

    public Journey getJourney() {
        return journey;
    }

    public void setJourney(Journey journey) {
        this.journey = journey;
    }
}
