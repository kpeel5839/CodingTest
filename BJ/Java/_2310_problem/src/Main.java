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

    public static boolean isMazeEscapePossible() {
        boolean isPossible = false;
        Arrays.fill(haveGoldWhenReachThisRoom, Integer.MIN_VALUE);
        Queue<int[]> states = new LinkedList<>();
        states.add(new int[] {-1, 0}); // {room number, gold}

        while (!states.isEmpty()) {
            int[] state = states.poll();

            if (state[0] == -1) { // start
                int haveGold = informationOfRoom.getOrDefault(0, 0);

                if (haveGold < 0) {
                    break;
                }

                if (roomToRoom.size() - 1 == 0) {
                    isPossible = true;
                    break;
                }

                haveGoldWhenReachThisRoom[0] = haveGold;
                states.add(new int[] {0, haveGold});
            } else { // not start
                for (Integer nextRoom : roomToRoom.get(state[0])) {
                    int thisRoomFee = informationOfRoom.getOrDefault(nextRoom, 0);
                    int haveGold = state[1];

                    if (haveGold + thisRoomFee < 0) {
                        continue;
                    }

                    if (thisRoomFee < 0) { // T 인 경우
                        haveGold += thisRoomFee;
                    } else if (haveGold < thisRoomFee) {
                        haveGold = thisRoomFee;
                    }

                    if (haveGold <= haveGoldWhenReachThisRoom[nextRoom]) {
                        continue;
                    }

                    if (roomToRoom.size() - 1 == nextRoom) {
                        isPossible = true;
                        break;
                    }

                    haveGoldWhenReachThisRoom[nextRoom] = haveGold;
                    states.add(new int[] {nextRoom, haveGold});
                }
            }
        }

        return isPossible;
    }

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
                char roomState = st.nextToken().charAt(0); // Room State 보고, 결정
                int fee = Integer.parseInt(st.nextToken());

                if (roomState == 'L') {
                    informationOfRoom.put(i, fee); // 이 이상이면 fee 를 채워주지는 않음
                }

                if (roomState == 'T') {
                    informationOfRoom.put(i, -1 * fee); // 음수로 넣어준다.
                }

                while (true) {
                    int connectRoom = Integer.parseInt(st.nextToken());

                    if (connectRoom == 0) {
                        break;
                    }

                    roomToRoom.get(i).add(connectRoom - 1); // room 연결
                }
            } // 정보는 다 채워넣음 이제 bfs 돌리기만하면됨

            if (isMazeEscapePossible()) {
                bw.write(YES + "\n");
            } else {
                bw.write(NO + "\n");
            }
        }

        bw.flush();
        bw.close();
    }
}
