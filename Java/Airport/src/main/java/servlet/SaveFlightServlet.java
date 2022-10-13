package servlet;

import dao.CityDAO;
import dao.FlightDAO;
import model.City;
import model.Flight;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SaveFlightServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.print("<h1>Add New Flight</h1>");
        out.print("<form action='save' method='post'>");
        out.print("<table>");
        out.print("<tr><td>Flight Number:</td><td><input type='text' name='number'/></td></tr>");
        out.print("<tr><td>Airplane Type:</td><td><input type='text' name='airplane_type'/></td></tr>");
        out.print("<tr><td>Departure City:</td><td><input type='text' name='departure_city'/></td></tr>");
        out.print("<tr><td>Arrival City:</td><td><input type='text' name='arrival_city'/></td></tr>");
        out.print("<tr><td>Departure Date:</td><td><input type='date' name='departure_date'/></td></tr>");
        out.print("<tr><td>Arrival Date:</td><td><input type='date' name='arrival_date'/></td></tr>");
        out.print("<tr><td>Departure Hour:</td><td><input type='number' name='departure_hour'/></td></tr>");
        out.print("<tr><td>Arrival Hour:</td><td><input type='number' name='arrival_hour'/></td></tr>");
        out.print("</td></tr>");
        out.print("<tr><td colspan='2'><input type='submit' value='Save Flight'/></td></tr>");
        out.print("</table>");
        out.print("</form>");
        out.print("<br/>");
        out.print("<a href='view'>View flights</a>");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();

        String number = request.getParameter("number");
        String airplane_type = request.getParameter("airplane_type");
        String departure_city = request.getParameter("departure_city");
        String arrival_city = request.getParameter("arrival_city");
        String departure_date = request.getParameter("departure_date");
        String arrival_date = request.getParameter("arrival_date");
        Integer departure_hour = 0;
        Integer arrival_hour = 0;

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date departureDate = null;
        Date arrivalDate = null;
        try {
            departureDate = format.parse(departure_date);
            arrivalDate = format.parse(arrival_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            departure_hour = Integer.parseInt(request.getParameter("departure_hour"));
            arrival_hour = Integer.parseInt(request.getParameter("arrival_hour"));
            if(departure_hour<0 || departure_hour >23 || arrival_hour<0 || arrival_hour >23)
                out.print("<p>Hour must be between 0 and 23</p>");
            else
            {
                CityDAO cityDAO = new CityDAO();
                City departureCity = cityDAO.findByName(departure_city);
                City arrivalCity = cityDAO.findByName(arrival_city);
                Flight flight=new Flight(number, airplane_type, departureCity, arrivalCity, departureDate, arrivalDate, departure_hour, arrival_hour);
                FlightDAO flightDAO = new FlightDAO();

                Long status = flightDAO.insert(flight);
                if(status>0){
                    out.print("<p>Record saved successfully!</p>");
                }else{
                    out.println("Sorry! unable to save record");
                }
            }
        }
        catch (NumberFormatException e) {
            out.print("<p>Hour is not a number</p>");
        }



        out.close();
    }

}