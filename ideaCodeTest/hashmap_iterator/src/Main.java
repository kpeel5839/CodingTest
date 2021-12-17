import java.awt.*;
import java.util.*;
import java.io.*;
import java.util.List;

public class Main {
    //실험으로는 일단 , Point 를 선언한 뒤에 compareTo를 Override를 해서 정렬할 수 있는 형태로 만들어 놓는다.
    //HashMap을 통해서 배열의 index와 , 해당 index의 숫자를 체크한다.
    //그리고 순서대로 list에다가 집어넣는다.
    //그다음 Collections.sort()를 통해서 list를 정렬한다.
    public static HashMap<Integer, Integer> countIndex = new HashMap<>();
    public static List<Point> sortList = new ArrayList<>();
    public static class Point implements Comparable<Point>{
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
            return "index : " + index + " count : " + count;
        }
    }
    public static void main(String[] args) throws IOException{
        int[][] map = new int[3][3];
        int[][] result = new int[100][100];
        for(int i = 0; i < result.length; i++){
            for(int j = 0; j < result[i].length; j++){
                result[i][j] = -1;
            }
        }
        Random random = new Random();
        for(int i = 1; i < 4; i++){
            for(int j = 1; j < 4; j++){
                map[i - 1][j - 1] = random.nextInt(11);
                System.out.print(map[i - 1][j - 1] + " " );
            }
            System.out.println();
        }
        for(int i = 0; i < 3; i++){
            countIndex = new HashMap<>();
            sortList = new ArrayList<>();
            int index = 0;
            System.out.println("next");
            for(int  j =0 ; j < 3; j++){
                if(countIndex.containsKey(map[i][j])){
                     countIndex.put(map[i][j], countIndex.get(map[i][j]) + 1);
                }
                else{
                    countIndex.put(map[i][j] , 1);
                }
            }
            System.out.println(countIndex);
            Iterator it = countIndex.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry entry = (Map.Entry)it.next();
                sortList.add(new Point((int)entry.getKey() , (int)entry.getValue()));
            }
            Collections.sort(sortList);
            for(Point point : sortList){
                result[i][index] = point.index;
                result[i][index + 1] = point.count;
                index += 2;
                if(index == 100){
                    break;
                }
            }
        }
        for(int i = 0; i < 30; i++){
            for(int j = 0; j < 30; j++){
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
    }
}
