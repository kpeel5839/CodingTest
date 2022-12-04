import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] arg2s) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        Map<Character, Integer> map = new HashMap<>();
        List<int[]> list = new LinkedList<>();
        map.put('c', 3);
        map.put('r', 4);

        list.add(new int[] {(int) 'c', map.get('c')});
        list.add(new int[] {(int) 'r', map.get('r')});

        System.out.println(Arrays.toString(list.get(0)));
        System.out.println((char) list.get(0)[0]);
    }
}