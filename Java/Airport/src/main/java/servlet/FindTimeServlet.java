package servlet;

import dao.CityDAO;
import dao.FlightDAO;
import model.City;
import model.Flight;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;


public class FindTimeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();

        String number = request.getParameter("flightnumber");
        String city = request.getParameter("citytype");

        WebService webService = new WebService();
        FlightDAO flightDAO = new FlightDAO();
        Flight f = flightDAO.findByFlightNumber(number);
        City depatureCity = f.getDepartureCity();
        City arrivalCity = f.getArrivalCity();
        String departureOffset = "";
        String arrivalOffset = "";
        try {
            departureOffset = webService.getOffsetByLocation(depatureCity.getLatitude().toString(), depatureCity.getLongitude().toString());
            arrivalOffset = webService.getOffsetByLocation(arrivalCity.getLatitude().toString(), arrivalCity.getLongitude().toString());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        Integer doff = Integer.parseInt(departureOffset);
        Integer aoff = Integer.parseInt(arrivalOffset);

        Integer dhour = f.getDepartureHour();
        Integer ahour = f.getArrivalHour();

        Integer localArrivalHour = ahour+aoff;
        Integer localDepartureHour = dhour+doff;
        if(localArrivalHour<0)
            localArrivalHour=localArrivalHour+24;
        else
            if(localArrivalHour>24)
                localArrivalHour=localArrivalHour-24;

        if(localDepartureHour<0)
            localDepartureHour=localDepartureHour+24;
        else
            if(localDepartureHour>24)
                localDepartureHour=localDepartureHour-24;

        if(city.equals("Departure"))
        {
            out.print("<p>Local time of departure in "+depatureCity.getName()+" is "+ localDepartureHour +"</p>");
        }
        else if(city.equals("Arrival"))
        {
            out.print("<p>Local time of arrival in "+arrivalCity.getName()+" is "+ localArrivalHour +"</p>");
        }
        else
        {
            out.print("<p>You should introduce Departure or Arrival</p>");
            request.getRequestDispatcher("viewflights").include(request, response);
        }
        out.close();
    }

}