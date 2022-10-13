import dao.CityDAO;
import dao.FlightDAO;
import dao.UserDAO;
import model.City;
import model.Flight;
import org.xml.sax.SAXException;
import servlet.WebService;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Main {

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, ParseException {
        System.out.println(new UserDAO().findById(Long.valueOf(1)));
        System.out.println(new UserDAO().getUser("a","a"));
        System.out.println(new FlightDAO().findById(Long.valueOf(1)));

        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        FlightDAO flightDAO = new FlightDAO();

        List<Flight> fs = flightDAO.getAllData();

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date date = inputFormat.parse(fs.get(0).getArrivalDate().toString());

        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String outputString = outputFormat.format(date);
        System.out.println(outputString);


        for(Flight f: (new FlightDAO()).getAllData())
            System.out.println(f.toString());
            System.out.println((new WebService()).getLocalTimeByLocation("40.71417","-74.00639"));
        System.out.println((new WebService()).getOffsetByLocation("40.71417","-74.00639"));
    }
}
