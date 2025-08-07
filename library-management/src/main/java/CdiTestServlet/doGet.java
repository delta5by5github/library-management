import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/CdiTestServlet")
public class CdiTestServlet extends HttpServlet {

    @Inject
    private BookService bookService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>List of Books from CDI Service:</h1>");

        List<book> books = bookService.findAllBooks();
        for (book book : books) {
            out.println("<p>" + book.getTitle() + "</p>");
        }

        out.println("</body></html>");
    }
}