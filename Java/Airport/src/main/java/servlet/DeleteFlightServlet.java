package servlet;

import dao.FlightDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteFlightServlet extends HttpServlet {
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String flightId=request.getParameter("id");
            FlightDAO flightDAO = new FlightDAO();
            flightDAO.delete(flightDAO.findById(Long.parseLong(flightId)));
            response.sendRedirect("view");
        }
    }