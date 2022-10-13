package servlet;

import dao.FlightDAO;
import model.Flight;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserViewServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        WebService webService = new WebService();
        out.println("<h2>Find local time of city</h2>");
        out.println("<form action='time' method='post'>");
        out.println("<td>Flight Number:</td><td><input type='text' name='flightnumber'/></td>");
        out.println("<td>Departure or Arrival:</td><td><input type='text' name='citytype'/></td>");
        out.print("<tr><td colspan='2'><input type='submit' value='Find Time'/></td></tr>");
        out.print("</form>");

        FlightDAO flightDAO = new FlightDAO();
        List<Flight> list= flightDAO.getAllData();
        out.println("<h2>Flights List</h2>");

        out.print("<table border='1' width='100%'");
        out.print("<tr><th>Id</th><th>FlightNumber</th><th>AirplaneType</th><th>DepartureCity</th><th>ArrivalCity</th><th>DepartureDate</th><th>ArrivalDate</th><th>DepartureHour</th><th>ArrivalHour</th><th>DepartureLocalTime</th><th>ArrivalLocalTime</th></tr>");
        for(Flight f:list){
            try {
                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                Date arrivalDate = null;
                Date departureDate = null;
                try {
                    arrivalDate = inputFormat.parse(f.getArrivalDate().toString());
                    departureDate = inputFormat.parse(f.getDepartureDate().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");
                out.print("<tr><td>"+f.getId()+
                        "</td><td>"+f.getFlightNumber()+
                        "</td><td>"+f.getAirplaneType()+
                        "</td><td>"+f.getDepartureCity().getName()+
                        "</td><td>"+f.getArrivalCity().getName()+
                        "</td><td>"+outputFormat.format(arrivalDate)+
                        "</td><td>"+outputFormat.format(departureDate)+
                        "</td><td>"+f.getDepartureHour()+
                        "</td><td>"+f.getArrivalHour()+
                        "</td><td>"+webService.getLocalTimeByLocation(f.getDepartureCity().getLatitude().toString(), f.getDepartureCity().getLongitude().toString())+"</td>" +
                        "<td>"+webService.getLocalTimeByLocation(f.getArrivalCity().getLatitude().toString(), f.getArrivalCity().getLongitude().toString())+"</td></tr>");
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        }
        out.print("</table>");

        out.close();
    }
}
