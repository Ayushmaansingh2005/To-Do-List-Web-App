import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/TaskAddServlet")
public class TaskAddServlet extends HttpServlet {
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

        try {
            JSONObject json = new JSONObject(sb.toString());
            String content = json.getString("content");
            String dueDate = json.optString("dueDate", null);
            String priority = json.optString("priority", null);

            Class.forName("com.mysql.cj.jdbc.Driver");

            String query = "INSERT INTO tasks(content, due_date, priority) VALUES (?, ?, ?)";
            try (Connection conn = DriverManager.getConnection(url, username, dbPassword);
                 PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, content);
                stmt.setString(2, dueDate);
                stmt.setString(3, priority);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    ResultSet generatedKeys = stmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        JSONObject response = new JSONObject();
                        response.put("success", true);
                        response.put("id", id);
                        out.write(response.toString());
                    } else {
                        res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        out.write("{\"error\":\"Failed to retrieve task ID\"}");
                    }
                } else {
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.write("{\"success\": false, \"error\": \"Task insertion failed\"}");
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
