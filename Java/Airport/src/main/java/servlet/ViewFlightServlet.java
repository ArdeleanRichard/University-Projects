package servlet;

import dao.FlightDAO;
import model.Flight;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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

public class ViewFlightServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        out.println("<a href='save'>Add New Flight</a>");
        out.println("<h1>Flights List</h1>");

        FlightDAO flightDAO = new FlightDAO();

        List<Flight> list= flightDAO.getAllData();
        out.print("<table border='1' width='100%'");
        out.print("<tr><th>Id</th><th>FlightNumber</th><th>AirplaneType</th><th>DepartureCity</th><th>ArrivalCity</th><th>DepartureDate</th><th>ArrivalDate</th><th>DepartureHour</th><th>ArrivalHour</th><th>Edit</th><th>Delete</th></tr>");
        for(Flight f:list){
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
                    "</td><td><a href='edit?id="+f.getId()+"'>edit</a></td> <td><a href='delete?id="+f.getId()+"'>delete</a></td></tr>");
        }
        out.print("</table>");

        out.close();
    }
}