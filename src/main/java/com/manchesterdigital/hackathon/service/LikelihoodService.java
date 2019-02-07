package com.manchesterdigital.hackathon.service;

import com.manchesterdigital.hackathon.domain.GridReference;
import com.manchesterdigital.hackathon.domain.ParkingData;
import com.manchesterdigital.hackathon.repository.CSVDocumentService;
import com.manchesterdigital.hackathon.repository.LatLongToGridReferenceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikelihoodService {

    CSVDocumentService csvDocumentService;
    LatLongToGridReferenceService latLongToGridReferenceService;

    double likelihoodOfTicket;

    public LikelihoodService(CSVDocumentService csvDocumentService, LatLongToGridReferenceService latLongToGridReferenceService) {
        this.csvDocumentService = csvDocumentService;
        this.latLongToGridReferenceService = latLongToGridReferenceService;
    }

    public double getLikelihood(double latitude, double longitude) {

        List<ParkingData> parkingData = csvDocumentService.getCarParkData();

        GridReference gridReference = latLongToGridReferenceService.getGridreferenceForLatLong(latitude, longitude);

        for (ParkingData value: parkingData ) {

            if(value.getGridRef().equals(gridReference.getGridRef())){
                return (Double.parseDouble(value.getAvgNumberOfTicketsPerDay())/
                        Double.parseDouble(value.getNumberOfTicketsIssued()))*100;
            }
        }
            return 0.0;
    }

}
