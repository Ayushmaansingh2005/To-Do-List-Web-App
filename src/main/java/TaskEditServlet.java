import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/TaskEditServlet")
public class TaskEditServlet extends HttpServlet {
    private static final String url = "jdbc:mysql://localhost:3306/to_do_list";
    private static final String username = "root";
    private static final String dbPassword = "Vinit@123";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        PrintWriter out = res.getWriter();

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        String requestBody = sb.toString().trim();
        System.out.println("Request Body: " + requestBody);  // For debugging

        if (requestBody.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"error\": \"Empty request body\"}");
            return;
        }

        try {
            JSONObject json = new JSONObject(requestBody);
            int taskId = json.getInt("taskId");
            String content = json.getString("content");
            String dueDate = json.optString("dueDate", null);
            String priority = json.optString("priority", null);
            if ("".equals(dueDate)) dueDate = null;

            if ("".equals(priority)) priority = null;


            Class.forName("com.mysql.cj.jdbc.Driver");


            String query = "UPDATE tasks SET content = ?, due_date = ?, priority = ? WHERE id = ?";

            try (Connection conn = DriverManager.getConnection(url, username, dbPassword);
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1,content);
                stmt.setString(2,dueDate);
                stmt.setString(3,priority);
                stmt.setInt(4, taskId);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    JSONObject response = new JSONObject();
                    response.put("success", true);
                    out.write(response.toString());
                } else {
                    res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.write("{\"success\": false, \"error\": \"No task found with given ID\"}");
                }
            }

        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JSONObject error = new JSONObject();
            error.put("error", "Exception: " + e.getMessage());
            out.write(error.toString());
            e.printStackTrace();
        }
    }
}
