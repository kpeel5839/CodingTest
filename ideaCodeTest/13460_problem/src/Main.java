import java.io.*;
import java.util.*;

public class Main{
    public static class Point{
        int row;
        int col;

        public Point(){}

        public Point(int row , int col){
            this.row = row;
            this.col = col;
        }

//        @Override
//        public String toString(){
//            return "row : " + row + " col : " + col;
//        }
    }
    public static void bfs(){}
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(input.readLine());

        int row = Integer.parseInt(st.nextToken());
        int col = Integer.parseInt(st.nextToken());
        String[] stringMap = new String[row];
        String[][] map = new String[row][col];

        for(int i = 0; i < row; i++){
            stringMap[i] = input.readLine();
        }

        Point red;
        Point blue;
        Point hole;

//        Point red = new Point();
//        Point blue = new Point();
//        Point hole = new Point(); //잠깐 출력하기 위해서 임시로 해놓은 것

        for (int i= 0; i < row; i++){
            for(int j = 0; j < col; j++){
                map[i][j] = Character.toString(stringMap[i].charAt(j));
                if(map[i][j].equals("R")){
                    red = new Point(i , j);
                }
                if(map[i][j].equals("B")){
                    blue = new Point(i , j);
                }
                if(map[i][j].equals("O")){
                    hole = new Point(i , j);
                }
            }
        }
//
//        for(int i = 0; i < row; i++){
//            for (int j = 0; j< col; j++){
//                System.out.print(map[i][j] + " ");
//            }
//            System.out.println();
//        }
//        System.out.println(red);
//        System.out.println(blue);
//        System.out.println(hole);
    }
}