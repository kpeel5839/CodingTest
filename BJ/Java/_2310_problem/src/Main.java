import java.util.*;
import java.io.*;

// 2310 : 어드벤처 게임

/**
 * Example
 * 3
 * E 0 2 0
 * L 10 3 0
 * T 15 1 2 0
 * 4
 * E 0 2 3 0
 * L 201 2 3 0
 * L 10 4 0
 * T 15 2 3 1 0
 * 0
 */
public class Main {
    public static final String YES = "Yes";
    public static final String NO = "No";
    public static List<ArrayList<Integer>> roomToRoom;
    public static HashMap<Integer, Integer> informationOfRoom;
    public static int[] haveGoldWhenReachThisRoom;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2310_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        while (true) {
            int numberOfRoom = Integer.parseInt(br.readLine());

            if (numberOfRoom == 0) {
                break;
            }

            roomToRoom = new ArrayList<>();
            informationOfRoom = new HashMap<>();
            haveGoldWhenReachThisRoom = new int[numberOfRoom];

            for (int i = 0; i < numberOfRoom; i++) {
                roomToRoom.add(new ArrayList<>());
                st = new StringTokenizer(br.readLine());
                char roomState = st.nextToken().charAt(0);

            }
        }

        bw.flush();
        bw.close();
    }
}
