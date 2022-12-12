import java.util.*;
import java.io.*;

// 5949 : Adding Commas

/**
 * Example
 * 153920529
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_5949_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String largeNumber = br.readLine();
        StringBuilder addingCommasAtLargeNumber = new StringBuilder();

        for (int i = 1; i <= largeNumber.length(); i++) {
            addingCommasAtLargeNumber.insert(0, largeNumber.charAt(largeNumber.length() - i));

            if (i % 3 == 0 && i != largeNumber.length()) {
                addingCommasAtLargeNumber.insert(0, ",");
            }
        }

        System.out.println(addingCommasAtLargeNumber);
    }
}
