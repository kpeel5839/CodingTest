import java.util.*;
import java.io.*;

public class Main {
    public static int r, c, k;
    public static int[][] map = new int[100][100];

    public static class Point implements Comparable<Point> {
        int index;
        int count;
        public Point(int index , int count){
            this.index = index;
            this.count = count;
        }

        @Override
        public int compareTo(Point other){
            if(this.count > other.count){
                return 1;
            }
            else if(this.count == other.count){
                return this.index - other.index;
            }
            else{
                return -1;
            }
        }
        @Override
        public String toString(){
            return "index : " + index + " count :" + count;
        }
    }
    //설계를 먼저해보자.
    //
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(input.readLine());
        r = Integer.parseInt(st.nextToken()) - 1;
        c = Integer.parseInt(st.nextToken()) - 1;
        k = Integer.parseInt(st.nextToken());

        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(input.readLine());
            for (int j = 0; j < 3; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        } // 입력 받기 완료
    }
}
