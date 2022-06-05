import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        HashMap<String, Double> map = new HashMap<>();

        map.put("A+", 4.3d);
        map.put("A0", 4.0d);
        map.put("A-", 3.7d);
        map.put("B+", 3.3d);
        map.put("B0", 3.0d);
        map.put("B-", 2.7d);
        map.put("C+", 2.3d);
        map.put("C0", 2.0d);
        map.put("C-", 1.7d);
        map.put("D+", 1.3d);
        map.put("D0", 1.0d);
        map.put("D-", 0.7d);
        map.put("F", 0.0d);

        System.out.println(map.get(br.readLine()));
    }
}
