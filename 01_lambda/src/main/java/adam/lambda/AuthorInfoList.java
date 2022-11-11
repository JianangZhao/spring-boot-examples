package adam.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AuthorInfoList {

    public static List<Author> getAuthors() {
        Author author1 = new Author(1l, "adam", 33, "java developer", null);
        Author author2 = new Author(2l, "yan", 30, "medication", null);
        Author author3 = new Author(3l, "jia", 6, "children book", null);

        List<Book> book1 = new ArrayList<>();
        List<Book> book2 = new ArrayList<>();
        List<Book> book3 = new ArrayList<>();

        book1.add(new Book(1l, "java", "java book", 7, "This is one book about java"));
        book2.add(new Book(2l, "medication", "medication book", 7, "This is one book about children"));
        book3.add(new Book(3l, "children", "children book", 7, "This is one book about children"));


        author1.setBooks(book1);
        author2.setBooks(book2);
        author2.setBooks(book3);
        author3.setBooks(book3);

        return new ArrayList<>(Arrays.asList(author1, author2, author3));
    }
}
