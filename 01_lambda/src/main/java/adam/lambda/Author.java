package adam.lambda;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Author implements Comparable<Author> {
    private Long id;
    private String name;
    private int age;
    private String intro;
    private List<Book> books;

    @Override
    public int compareTo(Author author) {
        return author.getAge() - this.getAge();
    }
}
