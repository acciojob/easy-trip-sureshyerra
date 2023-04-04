package com.driver.repository;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Repository
public class AirportRepository {
    HashMap<String, Airport> airportDb = new HashMap<>();
    HashMap<Integer,Flight> flightDb = new HashMap<>();
    HashMap<Integer,Passenger> passengerDb = new HashMap<>();
    HashMap<Integer, List<Integer>> ticketDb = new HashMap<>();

    public void addAirport(Airport airport){
        String name = airport.getAirportName();
        airportDb.put(name,airport);
        return;
    }
    public String addTicket(int flightId,int passengerId){
        Flight flight = new Flight(flightId);
        if(ticketDb.containsKey(flightId)) {
            if (flight.getMaxCapacity() > ticketDb.get(flightId).size()) {
                return "FAILURE";
            }
        }
        else if(ticketDb.containsKey(flightId)){
            List<Integer> passengers  = ticketDb.get(flightId);
            for(int p : passengers){
                if (p == passengerId){
                    return "FAILURE";
                }
            }

        }
        List<Integer> passengers = new ArrayList<>();
        passengers.add(passengerId);
        ticketDb.put(flightId,passengers);
        return "SUCCESS";

    }
    public String addFlight(Flight flight){
        int id = flight.getFlightId();
        flightDb.put(id,flight);
        return "SUCCESS";
    }
    public String addPassenger(Passenger passenger){
        int id = passenger.getPassengerId();
        passengerDb.put(id,passenger);
        return "SUCCESS";
    }
    public String cancelTicket(int flightId,int passengerId){

        if(ticketDb.containsKey(flightId)) {
            List<Integer> passengers = ticketDb.get(flightId);
            if (passengers == null) {
                return "FAILURE";
            }
            for (int p : passengers) {
                if (p == passengerId) {
                    passengers.remove(passengerId);
                }
            }
            ticketDb.put(flightId, passengers);
            return "SUCCESS";
        }
        return "FAILURE";


    }
    public String largestairport(){
        int max = 0;
        String ans = "";
        for (Airport airport : airportDb.values()){
            if(airport.getNoOfTerminals() > max){
                max = airport.getNoOfTerminals();
                ans = airport.getAirportName();
            } else if (airport.getNoOfTerminals() == max) {
                if(airport.getAirportName().compareTo(ans) <0){
                    ans = airport.getAirportName();
                }

            }
        }
        return ans;
    }
    public double distance(City fromcity,City tocity){
        double min = -1;
        for (int flightid : ticketDb.keySet()){
            Flight flight = new Flight(flightid);
            if(flight.getFromCity().equals(fromcity)&& flight.getToCity().equals(tocity)){
                min =  Math.min(flight.getDuration(),min);
            }
        }
        return min;
    }
    public  int noOfPeople(Date date, String airportName){
        int ans = 0;
        Airport airport = new Airport(airportName);
        for (int f : flightDb.keySet()){
            Flight flight = new Flight(f);
            if(airport.getCity().equals(flight.getFromCity()) || airport.getCity().equals(flight.getToCity())){
                if(flight.getFlightDate().equals(date)){
                    ans+=ticketDb.get(flight.getFlightId()).size();
                }
            }
        }
        return ans;
    }
    public int calculatefare(int flightId){
        int fare = 0;
        for (int ticket :ticketDb.keySet()){
            if(ticket == flightId){
               List<Integer>passengers =  ticketDb.get(flightId);
               fare = 3000+passengers.size()*50;

            }
        }
        return fare;
    }
    public  int countOfBookings(int passengerId){
        int cnt = 0;
        for(int flightid : ticketDb.keySet()){
            List<Integer>passengers = ticketDb.get(flightid);
            for(int p : passengers){
                if(p == passengerId){
                    cnt++;
                }
            }
        }
        return cnt;
    }
    public String getAirportName(int flightId){
        String ans = "";
        Flight flight = new Flight(flightId);
        for (Airport airport : airportDb.values()){
            if(flight.getFromCity().equals(airport.getCity())){
                ans = airport.getAirportName();
                return ans;
            }

        }
        return null ;

    }
    public int revenue(int flightId){
        int noOfPeopleBooked = ticketDb.get(flightId).size();
        int variableFare = (noOfPeopleBooked*(noOfPeopleBooked+1))*25;
        int fixedFare = 3000*noOfPeopleBooked;
        int totalFare = variableFare + fixedFare;

        return totalFare;
    }
}
