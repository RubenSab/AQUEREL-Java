import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        Instant now = Instant.now();
        System.out.println((double) now.getEpochSecond());
        System.out.println((double) now.getNano());
    }
}
