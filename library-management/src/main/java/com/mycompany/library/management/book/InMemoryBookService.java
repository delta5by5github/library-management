import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class InMemoryBookService implements BookService {

    private List<book> books;

    public InMemoryBookService() {
        books = new ArrayList<>();
        books.add(new book(1L, "The Hitchhiker's Guide to the Galaxy", "Douglas Adams"));
        books.add(new book(2L, "1984", "George Orwell"));
        books.add(new book(3L, "Brave New World", "Aldous Huxley"));
    }

    @Override
    public List<Book> findAllBooks() {
        return books;
    }
}