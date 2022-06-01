import java.util.*;
import java.io.*;
import java.util.function.Function;

public class Main {
    static int count = 0;

    static int fib(int n) {
        if (n == 1 || n == 2) {
            count++;
            return 1;
        } else {
            return fib(n - 1) + fib(n - 2);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        int n = fun.apply(br.readLine());
        fib(n);

        System.out.println(count + " " + (n - 2));
    }
}
