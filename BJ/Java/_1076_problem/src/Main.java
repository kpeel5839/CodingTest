import java.util.*;
import java.io.*;

// 1076 : 저항

/**
 * black	0	1
 * brown	1	10
 * red 	    2	100
 * orange	3	1,000
 * yellow	4	10,000
 * green	5	100,000
 * blue  	6	1,000,000
 * violet	7	10,000,000
 * grey	    8	100,000,000
 * white	9	1,000,000,000
 */

/**
 * Example
 * orange
 * red
 * blue
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1076_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        Map<String, Integer> colorToValue = new HashMap<>();
        colorToValue.put("black", 0);
        colorToValue.put("brown", 1);
        colorToValue.put("red", 2);
        colorToValue.put("orange", 3);
        colorToValue.put("yellow", 4);
        colorToValue.put("green", 5);
        colorToValue.put("blue", 6);
        colorToValue.put("violet", 7);
        colorToValue.put("grey", 8);
        colorToValue.put("white", 9);

        long answer = 0;
        for (int i = 0; i <= 1; i++) {
            answer *= 10;
            answer += colorToValue.get(br.readLine());
        }

        answer *= (int) Math.pow(10, colorToValue.get(br.readLine()));
        System.out.println(answer);
    }
}
