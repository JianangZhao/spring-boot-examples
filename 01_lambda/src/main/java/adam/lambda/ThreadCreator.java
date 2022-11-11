package adam.lambda;

public class ThreadCreator {
    public static void createAndStartThread(){
        new Thread(() -> System.out.println("new one thread")).start();
    }
}
