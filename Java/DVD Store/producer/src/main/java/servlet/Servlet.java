package ro.tuc.dsrl.ds.handson.assig.three.producer.servlet;

import ro.tuc.dsrl.ds.handson.assig.three.producer.model.DVD;
import ro.tuc.dsrl.ds.handson.assig.three.producer.start.Producer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeoutException;

public class Servlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.print("<h1>Add New DVD</h1>");
        out.print("<form action='save' method='post'>");
        out.print("<table>");
        out.print("<tr><td>Title:</td><td><input type='text' name='title'/></td></tr>");
        out.print("<tr><td>Year:</td><td><input type='number' name='year'/></td></tr>");
        out.print("<tr><td>Price:</td><td><input type='text' name='price'/></td></tr>");
        out.print("</td></tr>");
        out.print("<tr><td colspan='2'><input type='submit' value='Save DVD'/></td></tr>");
        out.print("</table>");
        out.print("</form>");
        out.print("<br/>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();

        String title = request.getParameter("title");
        Integer year = Integer.parseInt(request.getParameter("year"));
        Double price = Double.parseDouble(request.getParameter("price"));

        DVD dvd = new DVD(title, year, price);
        try {
            Producer.produce(dvd);
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        out.close();
    }
}
