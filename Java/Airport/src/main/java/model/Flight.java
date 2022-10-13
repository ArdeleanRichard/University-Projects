package model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "flight")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    private Long id;

    @Column(name = "number")
    private String flightNumber;

    @Column(name = "airplane_type")
    private String airplaneType;

    @OneToOne
    @JoinColumn(name = "departure_city")
    private City departureCity;

    @OneToOne
    @JoinColumn(name = "arrival_city")
    private City arrivalCity;

    @Column(name = "departure_date")
    private Date departureDate;

    @Column(name = "arrival_date")
    private Date arrivalDate;

    @Column(name = "departure_hour")
    private Integer departureHour;

    @Column(name = "arrival_hour")
    private Integer arrivalHour;

    public Flight(String flightNumber, String airplaneType, City departureCity, City arrivalCity, Date departureDate, Date arrivalDate, Integer departureHour, Integer arrivalHour) {
        this.flightNumber = flightNumber;
        this.airplaneType = airplaneType;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departureHour = departureHour;
        this.arrivalHour = arrivalHour;
    }

    public Flight() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAirplaneType() {
        return airplaneType;
    }

    public void setAirplaneType(String airplaneType) {
        this.airplaneType = airplaneType;
    }

    public City getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(City departureCity) {
        this.departureCity = departureCity;
    }

    public City getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(City arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Integer getDepartureHour() {
        return departureHour;
    }

    public void setDepartureHour(Integer departureHour) {
        this.departureHour = departureHour;
    }

    public Integer getArrivalHour() {
        return arrivalHour;
    }

    public void setArrivalHour(Integer arrivalHour) {
        this.arrivalHour = arrivalHour;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", flightNumber='" + flightNumber + '\'' +
                ", airplaneType='" + airplaneType + '\'' +
                ", departureCity='" + departureCity + '\'' +
                ", arrivalCity='" + arrivalCity + '\'' +
                ", departureDate='" + departureDate + '\'' +
                ", arrivalDate='" + arrivalDate + '\'' +
                ", departureHour='" + departureHour + '\'' +
                ", arrivalHour='" + arrivalHour + '\'' +
                '}';
    }
}
