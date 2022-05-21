import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        float n = sc.nextInt();

        System.out.println((int) (n * 0.78));
        System.out.println((int) (n - (n * 0.2) * 0.22));
    }
}
