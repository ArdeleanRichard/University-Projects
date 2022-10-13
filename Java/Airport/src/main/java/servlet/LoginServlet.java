package servlet;

import model.User;
import dao.UserDAO;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // add code for GET method
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<form action='login' method='post'>");
        out.println("Enter username:<br>");
        out.println("<input type='text' name='username'><br>");
        out.println("Enter password:<br>");
        out.println(" <input type='password' name='password'>");
        out.println("<br><br>");
        out.println("<input type='submit' value='LOGIN'>");
        out.println("</form>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        HttpSession session = request.getSession(true);

        if ((!username.equals("") && !password.equals(""))) {
            UserDAO userDao = new UserDAO();
            User user = userDao.getUser(username, password);
            if (user != null) {
                session.setAttribute("USER", user);
                if (!user.getAdmin()) {
                    response.sendRedirect("user/viewflights");
                } else {
                    response.sendRedirect("admin/save");
                }
            } else {
                response.sendRedirect("index.html");
            }
        }

    }

    private void createAdminPage(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("<html>");

        out.println("<head>");
        out.println("<title>User Page</title>");
        out.println("</head>");

        out.println("<body>");
        out.println("Admin page");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    private void createUserPage(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("<html>");

        out.println("<head>");
        out.println("<title>User Page</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("user page");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
