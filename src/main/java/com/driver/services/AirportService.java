package com.driver.services;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import com.driver.repository.AirportRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AirportService {
    AirportRepository airportRepository = new AirportRepository();
    public void addAirport(Airport airport){
        airportRepository.addAirport(airport);
        return;
    }
    public String addTicket(int flightId,int passengerId){
        return airportRepository.addTicket(flightId,passengerId);
    }
    public String addFlight(Flight flight){
        return airportRepository.addFlight(flight);

    }
    public String addPassenger(Passenger passenger){
         return airportRepository.addPassenger(passenger);

    }
    public String cancelTicket(int flightId,int passengerId){
        return airportRepository.cancelTicket(flightId,passengerId);
    }
    public String largestairport(){
        return airportRepository.largestairport();
    }
    public double distance(City fromcity, City tocity){
        return airportRepository.distance(fromcity,tocity);
    }
    public  int noOfPeople(Date date, String airportName){
        return airportRepository.noOfPeople(date,airportName);
    }
    public int calculatefare(int flightId){
        return airportRepository.calculatefare(flightId);
    }
    public  int countOfBookings(int passengerId){
        return airportRepository.countOfBookings(passengerId);
    }
    public String getAirportName(int flightId){
        return airportRepository.getAirportName(flightId);
    }
    public int revenue(int flightId){
        return airportRepository.revenue(flightId);
    }
}
