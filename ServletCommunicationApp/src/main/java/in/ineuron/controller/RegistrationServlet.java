package in.ineuron.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = { "/reg" }, 
initParams = { 
		@WebInitParam(name = "url", value = "jdbc:mysql://localhost:3306/ineuron"), 
		@WebInitParam(name = "user", value = "root"), 
		@WebInitParam(name = "password", value = "Password")
})
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	int rowCount = 0;
       
	static {
		System.out.println("RegistrationServlet is loading...");
	}
	
    public RegistrationServlet() {
    	System.out.println("RegistrationServlet is instantiating...");
        
    }
    
    @Override
	public void init() throws ServletException {
		System.out.println("RegistrationServlet is initializing...");
		ServletConfig config = getServletConfig();
		String url = config.getInitParameter("url");
		String user = config.getInitParameter("user");
		String password = config.getInitParameter("password");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, password);
		} catch(ClassNotFoundException cfe) {
			cfe.printStackTrace();
		}catch (SQLException se) {
			se.printStackTrace();
		}
		
    }
    

	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uname = request.getParameter("uname");
		Integer uage = Integer.parseInt(request.getParameter("uage"));
		String email = request.getParameter("email");
		String umobile = request.getParameter("umobile");
		
		String sqlInsertQuery= "insert into users values(?,?,?,?)";
		PreparedStatement pstm = null;
		
		//using JDBC API code to send the data to database
				try {
					if(connection!=null)
					pstm = connection.prepareStatement(sqlInsertQuery);
					if(pstm!=null) {
						pstm.setString(1,uname);
						pstm.setInt(2,uage);
						pstm.setString(3,email);
						pstm.setString(4,umobile);
						
						rowCount = pstm.executeUpdate();
						
					}
				}catch (SQLException se) {
					se.printStackTrace();
				} 
				
				catch (Exception e) {
					e.printStackTrace();
				}
		
	    response.setContentType("text/html");	
		PrintWriter out = response.getWriter();
		if(uage<18 || uage>30) {
		  response.sendError(504, "User age doesn't meet the criteria.");
		}
		else {
		out.println("<html><head><title></title></head>");
		out.println("<body>");
		out.println("<font color='red'>");
		out.println("<h2>Vishnu Consultancy Services</h2>");
		out.println("<h2>User Registration details</h2>");
		out.println("</font>");
		out.println("<table border='1'>");
		  out.println("<tr><td>User Name: </td><td>"+uname+"</td></tr>");
		  out.println("<tr><td>User Age: </td><td>"+uage+"</td></tr>");
		  out.println("<tr><td>Email: </td><td>"+email+"</td></tr>");
		  out.println("<tr><td>User Mobile: </td><td>"+umobile+"</td></tr>");
		  if(rowCount==1)
		  out.println("<tr><td>Registration Status: </td><td>Registration Successfully</td></tr>");
		  else
		  out.println("<tr><td>Registration Status: </td><td>Registration Failed</td></tr>");
		out.println("</table>");
		out.println("</body>");
		out.println("</html>");
		
		}
		out.close();
		
		
	}
	
	
	
}
