/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sumi.servlet;

import java.sql.*;
import java.io.IOException;
import java.sql.ResultSet;
import static java.sql.Types.NULL;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Sumitro
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    Connection conn = null;
    Statement statement = null;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String vetuser = request.getParameter("username");
        String vetpassword = request.getParameter("password");
        int dfn = 0;
        String lastname;
        String firstname;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost/test";
            conn = DriverManager.getConnection(url, "pdsuser", "pdsadmin");
            System.out.println("From LoginServlet: connection closed? " + conn.isClosed());

            try {
                String query = "select dfn, lastname, firstname from test.patient where username = \"" + vetuser + "\" and password = \"" + vetpassword + "\"";
                System.out.println("From LoginServlet: " + query);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    dfn = rs.getInt("dfn");
                    lastname = rs.getString("lastname");
                    firstname = rs.getString("firstname");
                    System.out.println("Patient DFN: " + dfn);
                    HttpSession session = request.getSession();
                    session.setAttribute("dfn", dfn);
                    session.setAttribute("lastname", lastname);
                    session.setAttribute("firstname", firstname);
                }

            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }

            if (NULL == dfn) {
                System.out.println("Invalid username and/or password");
                conn.close();
                System.out.println("From LoginServlet: connection closed? " + conn.isClosed());
                RequestDispatcher requestDispatcher;
                requestDispatcher = request.getRequestDispatcher("WEB-INF/loginerror.jsp");
                requestDispatcher.forward(request, response);
            } else {
                conn.close();
                System.out.println("From LoginServlet: connection closed? " + conn.isClosed());
                RequestDispatcher requestDispatcher;
                requestDispatcher = request.getRequestDispatcher("WEB-INF/selectsite.jsp");
                requestDispatcher.forward(request, response);
            }

        } catch (Exception e) {
            System.out.println("Some Crap Happened during connecting to the database: " + e.getMessage());
        }
    }
}
