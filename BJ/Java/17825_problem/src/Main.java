import java.util.*;
import java.io.*;

public class Main { //개 삽질한 것
    public static int[][] map = new int[7][], horseMap = new int[7][];
    public static int diceCount = 10;
    public static int[] dice = new int[diceCount];
    public static String resultSequence;
    public static HashSet<String> sequence = new HashSet<>();
    public static int max = 0;
    public static List<Point> horseList = new ArrayList<>(); //그냥 sequence 대로 1번말 , 2번말...하자
    public static int[][] resultHorseMap = new int[7][];

    public static class Point {
        int y;
        int x;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        @Override
        public String toString() {
            return "y : " + y + " x : " + x;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        map[0] = new int[]{0, 2, 4, 6, 8, 10};
        map[1] = new int[]{12, 14, 16, 18, 20};
        map[2] = new int[]{22, 24, 26, 28, 30};
        map[3] = new int[]{32, 34, 36, 38, 40};
        map[4] = new int[]{22, 24, 25, 30, 35, 40, 0};
        map[5] = new int[]{13, 16, 19, 25};
        map[6] = new int[]{28, 27, 26, 25};

        for (int i = 0; i < map.length; i++) {
            horseMap[i] = new int[map[i].length];
            resultHorseMap[i] = new int[map[i].length];
        } //deep copy

        st = new StringTokenizer(input.readLine());
        for (int i = 0; i < diceCount; i++) {
            dice[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < 4; i++) {
            horseList.add(new Point(0, 0));
        }
        getSequence("");
        instruct();
        System.out.println(max);
        System.out.println(resultSequence);
        for(int i = 0; i < horseMap.length; i++){
            for(int j = 0; j < horseMap[i].length; j++){
                System.out.print(resultHorseMap[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void getSequence(String s) {
        if (s.length() == diceCount) {
            sequence.add(s);
            return;
        }
        for (int i = 0; i < 4; i++) {
            s += i;
            getSequence(s);
            s = s.substring(0, s.length() - 1);
        }
    }

    public static void instruct() {
        for (String s : sequence) {
            int sum = 0;
            boolean valid = true;
            for (int i = 0; i < s.length(); i++) {
                int horseNumber = Integer.parseInt(Character.toString(s.charAt(i)));
                Point horse = horseList.get(horseNumber);
                Point point = move(horse.y, horse.x, dice[i]);
                if (point == null) {
                    valid = false;
                    break;
                }
                horseMap[horse.y][horse.x] = 0;
                horseMap[point.y][point.x] = horseNumber + 1;
                horseList.set(horseNumber, new Point(point.y, point.x));
                sum += map[point.y][point.x];
            }
            if (valid == true && max < sum) {
                max = sum;
                resultSequence = s;
                for (int i = 0; i < resultHorseMap.length; i++) {
                    for (int j = 0; j < resultHorseMap[i].length; j++) {
                        resultHorseMap[i][j] = horseMap[i][j];
                    }
                }
            }
            clean();
        }
    }

    public static void clean() {
        for (int i = 0; i < horseMap.length; i++) {
            for (int j = 0; j < horseMap[i].length; j++) {
                horseMap[i][j] = 0;
            }
        }
        horseList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            horseList.add(new Point(0, 0));
        }
    }

    public static Point move(int y, int x, int count) { //(y, x) 에서 count만큼 움직이게끔 하면 됨  , 도착 지점이나 가려고 하는 칸에 말이 있으면 null 반환
        //move에서 잘하면 바로 싹 풀릴 것 같음
        if (y == 4 && x == 6) {
            return null;
        }
        if (y == 0 && x == 5) { //5로 가야함
            y = 5;
            x = 0;
            count--;
        }
        if (y == 1 && x == 4) { //4으로 가야함
            y = 4;
            x = 0;
            count--;
        }
        if (y == 2 && x == 4) { //6으로 가야함
            y = 6;
            x = 0;
            count--;
        }
        int remain = map[y].length - x;
        if (count >= remain) {
            count = count - remain;
            if (y == 5) {
                y = 4;
                x = 3;
            } else if (y == 6) {
                y = 4;
                x = 3;
            } else if (y == 3) {
                y = 4;
                x = 6;
            } else if (y == 4) {
                y = 4;
                x = 6;
                count = 0;
            } else {
                y += 1;
                x = 0;
            }
            if (count != 0) {
                Point point = move(y, x, count);
                if (point == null) {
                    return null;
                }
                y = point.y;
                x = point.x;
            }
        } else {
            x += count;
        }
        if (y != 4 && x != 6 && horseMap[y][x] != 0) {
            return null;
        }
        return new Point(y, x);
    }
}
