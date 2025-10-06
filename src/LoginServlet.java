import java.io.IOException;
import java.sql*;
import javax.servlet.SevletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LoginServlet")
publilc class LoginServlet extends HTTPServlet {

    private static final String URL = "jdbc:mysql://localhost:3306/bricolage";
    private static final USER = "root";
    private static final PASSWORD = "Hoang564"

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");
        String pass = request.getParameter("password");

        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("userID", rs.getInt("id"));
                session.setAttribute("username", rs.getString("username");
                response.sendRedirect("home.html");
            } else {
                response.sendRedirect("login.html");
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("login.html?error=1");
        }
    }
}