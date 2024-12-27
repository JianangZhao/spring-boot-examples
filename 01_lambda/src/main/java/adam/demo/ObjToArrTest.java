package adam.demo;

import adam.lambda.Book;

import java.util.ArrayList;
import java.util.List;

public class ObjToArrTest {
    public static void main(String[] args) {
        List<Book> list = new ArrayList<>();
        Book book1 = new Book(1L, "java", "program", 78, "developer");
        list.add(book1);

        for (int i = 0; i < 10000; i++) {
            Book book = new Book(Long.valueOf(i), "java", "program", 78, "developer");
            list.add(book);
        }

        Object[][] objArr = new Object[list.size()][5];

        long s = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Object[] o = new Object[5];
            o[0] = list.get(i).getId().toString();
            o[1] = list.get(i).getName();
            o[2] = list.get(i).getCategory();
            o[3] = list.get(i).getScore().toString();
            o[4] = list.get(i).getIntro();
            objArr[i] = o;
        }
        long e = System.currentTimeMillis();
//        System.out.println(Arrays.toString(objArr));
        System.out.println("total time costing: " + (e - s) + " mill seconds.");
    }
}
