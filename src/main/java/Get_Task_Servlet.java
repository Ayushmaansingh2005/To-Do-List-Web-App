import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.*;

@WebServlet("/Get_Task_Servlet")
public class Get_Task_Servlet extends HttpServlet {
    private static final String url = "jdbc:mysql://localhost:3306/to_do_list";
    private static final String username = "root";
    private static final String dbPassword = "Vinit@123";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");

        JSONArray tasks = new JSONArray();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, username, dbPassword);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM tasks")) {

                while (rs.next()) {
                    JSONObject task = new JSONObject();
                    task.put("id", rs.getInt("id"));
                    task.put("content", rs.getString("content"));
                    task.put("dueDate", rs.getString("due_date"));
                    task.put("priority", rs.getString("priority"));
                    tasks.put(task);
                }

                res.getWriter().write(tasks.toString());
            }


        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            res.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
            e.printStackTrace();
        }

    }
}

