package adam.lambda;

import lombok.val;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamDemo {
    public static void main(String[] args) {
//        ThreadCreator.createAndStartThread();
//        testSkip(AuthorInfoList.getAuthors());
//        testLimited(AuthorInfoList.getAuthors());
//        testSorted(AuthorInfoList.getAuthors());
//        testFilter(AuthorInfoList.getAuthors());
//        testFlatMap(AuthorInfoList.getAuthors());
//        testStreamToList(AuthorInfoList.getAuthors());
//        testStreamToSet(AuthorInfoList.getAuthors());
//        testStreamToMap(AuthorInfoList.getAuthors());
//        testAnyMatch(AuthorInfoList.getAuthors());
//        testAllMatch(AuthorInfoList.getAuthors());
//        testNoneMatch(AuthorInfoList.getAuthors());
//        testFindAny(AuthorInfoList.getAuthors());
        testReduce(AuthorInfoList.getAuthors());

    }

    private static void testAnyMatch(List<Author> authors) {
        boolean b = authors.stream().distinct()
                .anyMatch(author -> author.getAge() > 30);
        System.out.println(b);
    }

    private static void testFindAny(List<Author> authors) {
        Optional<Author> any = authors.stream().distinct()
                .findAny();
        System.out.println(any.isPresent()?any.get():"No present");
    }

    private static void testReduce(List<Author> authors) {
        // find the min age author
        Optional<Author> reduce = authors.stream().distinct()
                .reduce((author, author2) -> author.getAge() - author2.getAge() <= 0 ? author : author2);
//        System.out.println(reduce.isPresent()?reduce.get():"Not present");

        //该函数式接口需要两个参数，返回一个结果(reduce中返回的结果会作为下次累加器计算的第一个参数)，也就是累加器
        int reduce1 = authors.stream().distinct().mapToInt(value -> value.getAge())
                .reduce(10, new IntBinaryOperator() {
                    @Override
                    public int applyAsInt(int left, int right) {
                        return left + right;
                    }
                });
//        System.out.println(reduce1);

        ArrayList<Integer> reduce2 = Stream.of(1, 2, 3, 4).reduce(new ArrayList<Integer>(), new BiFunction<ArrayList<Integer>, Integer, ArrayList<Integer>>() {
            @Override
            public ArrayList<Integer> apply(ArrayList<Integer> integers, Integer integer) {
                System.out.println("BiFunction");
                integers.add(integer);
                System.out.println(integer);
                System.out.println(integers);
                System.out.println("---------");
                return integers;
            }
        }, new BinaryOperator<ArrayList<Integer>>() {
            @Override
            public ArrayList<Integer> apply(ArrayList<Integer> integers, ArrayList<Integer> integers2) {
                System.out.println("BinaryOperator");
                integers.addAll(integers2);
                System.out.println(integers);
                System.out.println(integers2);
                System.out.println("---------");
                return integers;
            }
        });
        System.out.println("result is:"+reduce2);

    }


    private static void testNoneMatch(List<Author> authors) {
    }

    private static void testAllMatch(List<Author> authors) {
    }

    private static void testStreamToMap(List<Author> authors) {
        Map<String, List<Book>> collect = authors.stream()
                .distinct()
                .collect(Collectors.toMap(author -> author.getName(), author -> author.getBooks()));
        System.out.println(collect);
    }

    private static void testStreamToSet(List<Author> authors) {
        Set<String> collect = authors.stream().flatMap(new Function<Author, Stream<Book>>() {
            @Override
            public Stream<Book> apply(Author author) {
                return author.getBooks().stream();
            }
        }).map(book -> book.getName()).collect(Collectors.toSet());
        System.out.println(collect);
    }

    private static void testStreamToList(List<Author> authors) {
        List<String> collect = authors.stream()
                .map(author -> author.getName())
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    private static void testFlatMap(List<Author> authors) {
        authors.stream().flatMap(author -> author.getBooks().stream())
                .distinct()
                .forEach(book -> System.out.println("author's book name is:"+book.getName()));
    }

    private static void testFilter(List<Author> authors) {
        authors.stream().distinct()
                .filter(author -> author.getAge() < 16)
                .forEach(author -> System.out.println("author age < 16 is:"+author.getName()));
    }

    private static void testSorted(List<Author> authors) {
        authors.stream().distinct()
                .sorted((o1, o2) -> o2.getAge() - o1.getAge())
                .forEach(author -> System.out.println("Sort by author's age desc:"+author.getAge()));
    }

    private static void testLimited(List<Author> authors) {
        authors.stream().distinct()
                .sorted()
                .limit(2)
                .forEach(author -> System.out.println("the first two author's name:"+author.getName()));
    }

    private static void testSkip(List<Author> authors){
        authors.stream().distinct()
                .sorted() //the Author class should implement the comparable
                .skip(1)
                .forEach(author -> System.out.println("the author's age except the first one:"+author.getAge()));
    }
}