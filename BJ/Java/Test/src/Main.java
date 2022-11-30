import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] arg2s) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);
        Random random = new Random();
        System.out.println(random.nextInt(2));

        System.out.println(set.iterator().next());

    }
}