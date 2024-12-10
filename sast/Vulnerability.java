import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.xml.sax.InputSource;
import java.io.StringReader;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VulnerableExample {

    // SQL Injection
    public void sqlInjection(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "user", "password");
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM users WHERE userId = '" + userId + "'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                response.getWriter().println("User: " + rs.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // XPath Injection
    public void xpathInjection(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String xmlString = "<users><user><id>1</id><name>John</name></user></users>";
        String userId = request.getParameter("userId");
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = "/users/user[id/text()=$userId]/name/text()";
            xPath.setXPathVariableResolver(v -> {
                if ("userId".equals(v.getLocalPart())) {
                    return userId;
                }
                return null;
            });
            String result = xPath.evaluate(expression, new InputSource(new StringReader(xmlString)));
            response.getWriter().println("User: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Cross-Site Scripting (XSS)
    public void xss(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userInput = request.getParameter("input");
        response.getWriter().println("User input: " + userInput);
    }

    // Insecure Deserialization
    public void insecureDeserialization(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
            Object obj = ois.readObject();
            response.getWriter().println("Deserialized object: " + obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Command Injection
    public void commandInjection(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cmd = request.getParameter("cmd");
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.getWriter().println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
