package ch.hsr.tellstrack.repository;

/**
 * Created by philipp.bruhin on 01.03.2018.
 */

public class TransportRepositoryFactory {

    public static ITransportRepository CreateOpenDataTransportRepository(){
        return new OpenDataTransportRepository();
    }

}
