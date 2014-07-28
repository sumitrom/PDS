/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sumi.servlet;

import java.io.IOException;
import static java.lang.System.out;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.sumi.rpc.RPCClient;
import org.sumi.rpc.procedures.RemoteProcedure;
import org.sumi.rpc.rpc.exceptions.LoginException;

/**
 *
 * @author Sumitro
 */
@WebServlet(name = "ApptDetailServlet", urlPatterns = {"/ApptDetailServlet"})
public class ApptDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int siteid = Integer.parseInt(request.getParameter("VAMC"));
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;

        String vistaip = "";
        int vistaport = 0;
        String accesscode = "";
        String verifycode = "";

        HttpSession session = request.getSession();

        try {

            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(ApptDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            String url = "jdbc:mysql://localhost/test";
            try {
                conn = DriverManager.getConnection(url, "pdsuser", "pdsadmin");
            } catch (SQLException ex) {
                Logger.getLogger(ApptDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ApptDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        String query = "select ipaddress, port, accesscode, verifycode from test.site where siteid =\"" + siteid + "\"";
        try {
            statement = conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ApptDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rs = statement.executeQuery(query);

            while (rs.next()) {
                vistaip = rs.getString("ipaddress");
                vistaport = rs.getInt("port");
                accesscode = rs.getString("accesscode");
                verifycode = rs.getString("verifycode");
            }

            conn.close();
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ApptDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Connecting to " + vistaip + " at port " + vistaport);
        RPCClient c = new RPCClient(vistaip, vistaport);

        try {
            c.login(accesscode, verifycode);
            System.out.println("Connected: " + c.isConnected());
            RemoteProcedure r = new RemoteProcedure("XUS GET USER INFO");
            c.call(r);
            String resp = r.getResponse();
            String token[] = resp.split("\r\n");
            String username = token[1];
            String conninfo = (" * Connected to " + vistaip + " at port " + vistaport + " as " + username);
            System.out.println(conninfo);
            session.setAttribute("cxn", c);
            session.setAttribute("uname", username);
            session.setAttribute("conninfo", conninfo);
            RequestDispatcher requestDispatcher;
            requestDispatcher = request.getRequestDispatcher("WEB-INF/connected.jsp");
            requestDispatcher.forward(request, response);

        } catch (LoginException ex) {
            Logger.getLogger(ApptDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
            out.println("<p><br><br>Connected: " + c.isConnected() + "</p><br>");
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            out.println("<p>The following error occured: " + ex.getMessage() + "</p><br>");
            c.disconnect();
            session.setAttribute("err_msg", ex.getMessage());
            RequestDispatcher requestDispatcher;
            requestDispatcher = request.getRequestDispatcher("WEB-INF/error.jsp");
            requestDispatcher.forward(request, response);
            c.disconnect();
        }

              c.disconnect();
              System.out.println("Connected: " + c.isConnected());
    }
}
