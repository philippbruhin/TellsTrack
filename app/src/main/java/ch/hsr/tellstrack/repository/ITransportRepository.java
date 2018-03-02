package ch.hsr.tellstrack.repository;

import ch.hsr.tellstrack.model.ConnectionList;
import ch.hsr.tellstrack.model.StationList;

/**
 * Created by philipp.bruhin on 01.03.2018.
 */

public interface ITransportRepository {

    StationList findStations(String query) throws OpenDataTransportException;

    ConnectionList searchConnections(String from, String to) throws OpenDataTransportException;

    ConnectionList searchConnections(String from, String to, String via, String date, String time, Boolean isDeparture) throws OpenDataTransportException;

}
