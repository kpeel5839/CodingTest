import java.io.*;
import java.util.*;

public class Main {
    public static Queue<Point> moveLog = new LinkedList<>();
    public static int length = 2;

    public static class Point{
        public int y;
        public int x;

        public Point(int y, int x){
            this.y = y;
            this.x = x;
        }
        @Override
        public String toString(){
            return "y : " + y + " x : " +x;
        }
    }

    public static int n = 5;
    public static int[][] map = new int[n][n];

    public static void move(){
        for(int i = 0; i < 5; i++){
            moveLog.add(new Point(0, i));
        }
    }
    public static void main(String[] args) {
        move();
        int count = 0;
        System.out.println(moveLog.size() - length);
        for(Point point : moveLog){ //현 length 가 만일 2라면? 마지막 움직인게 0 ,4 라면? 그러면
            if(count == 3){
                System.out.println(point);
            }
            count++;
        }
    }
}
