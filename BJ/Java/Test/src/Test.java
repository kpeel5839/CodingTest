import java.io.IOException;
import java.util.Scanner;
import java.util.function.Supplier;

public class Test {

    public <T> T repeat(Supplier<T> reader) {
        try {
            return reader.get();
        } catch(Exception exception) {
            System.out.println(exception.getMessage());
            return repeat(reader);
        }
    }

    public int input() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        return validate(n);
    }

    public int validate(int n) {
        if (n == 1) {
            throw new IllegalArgumentException("fuck 2");
        }

        return n;
    }
}
