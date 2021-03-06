package ch.hsr.tellstrack.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ConnectionDetail implements Serializable {

    public ArrayList<ConnectionSection> ConnectionSections;


    public ConnectionDetail(Connection c) {

        ConnectionSections = new ArrayList<>();

        for (Section s : c.getSections()) {

            ConnectionSection cs = new ConnectionSection();

            try {
                cs.From = c.getFrom().getStation().getName();
                cs.From_Latitude = c.getFrom().getLocation().getCoordinate().getX().toString();
                cs.From_Longitude = c.getFrom().getLocation().getCoordinate().getY().toString();
                cs.StartTime = c.getFrom().getDeparture();

                cs.ToEnd = c.getTo().getStation().getName();
                cs.ToEnd_Latitude = c.getTo().getLocation().getCoordinate().getX().toString();
                cs.ToEnd_Longitude = c.getTo().getLocation().getCoordinate().getY().toString();
                cs.EndTime = c.getTo().getArrival();
                cs.Duration = c.getDuration();
                cs.Products = c.getProducts().toString();

                cs.DepartureTime = s.getDeparture().getDeparture();
                cs.Departure = s.getDeparture().getStation().getName();
                cs.Dep_Latitude = s.getDeparture().getLocation().getCoordinate().getX().toString();
                cs.Dep_Longitude = s.getDeparture().getLocation().getCoordinate().getY().toString();
                cs.DeparturePlatform = s.getDeparture().getPlatform();
                cs.DeparturePrognosis = s.getDeparture().getPrognosis().getDeparture();

                cs.ArrivalTime = s.getArrival().getArrival();
                cs.Arrival = s.getArrival().getStation().getName();
                cs.ArrivalPlatform = s.getArrival().getPlatform();
                cs.ArrivalPrognosis = s.getArrival().getPrognosis().getArrival();

                cs.Capacity1st = s.getJourney().getCapacity1st();
                cs.Capacity2nd = s.getJourney().getCapacity2nd();
                cs.Name = s.getJourney().getName();
                cs.To = s.getJourney().getTo();
                cs.CategoryCode = s.getJourney().getCategoryCode();

                ConnectionSections.add(cs);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
