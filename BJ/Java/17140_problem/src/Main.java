import java.util.*;
import java.io.*;

public class Main {
    public static int r, c, k , time = 0;
    public static boolean finish = false;
    public static int[][] map = new int[100][100] , tempMap = new int[100][100];
    public static HashMap<Integer , Integer> countMap = new HashMap<>();
    public static List<Point> sortList = new ArrayList<>();

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
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(input.readLine());
        r = Integer.parseInt(st.nextToken()) - 1;
        c = Integer.parseInt(st.nextToken()) - 1;
        k = Integer.parseInt(st.nextToken());

        for(int i = 0; i < 100; i++){
            for(int j = 0; j < 100; j++){
                map[i][j] = -1;
                tempMap[i][j] = -1;
            }
        } //일단 -1로 다 초기화 완료

        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(input.readLine());
            for (int j = 0; j < 3; j++) {
                tempMap[i][j] = Integer.parseInt(st.nextToken());
            }
        } // 입력 받기 완료

        check();
        //설계
        //1. tempMap에서 읽는다.
        //2. 그러면서 실제 map에다가 집어 넣는다.
        //3. 다 집어 넣으면 다시 tempMap에다가 옮긴 다음에 map을 -1로 전부 초기화한다.
        if(finish == false){
            while(true){
                time++;
                for(int i = 0; i < 100; i++){
                    if(tempMap[0][i] == -1){
                        operation(0);
                        break;
                    }
                    if(tempMap[i][0] == -1){ //둘이 -1을 동시에 만났다 그럴떄에는 R연산이 실행되어야 함
                        operation(1);
                        break;
                    }
                    if(i == 100 - 1){
                        operation(0);
                    }
                }
                check();

                if(finish == true){
                    break;
                }
                if(time == 100){
                    break;
                }
            }
        }
        if(finish == false){
            System.out.println(-1);
        }
        else {
            System.out.println(time);
        }
    }
    public static void check(){
        if(tempMap[r][c] == k){
            finish = true;
        }
    }

    public static void operation(int op){
        if(op == 0){ //R연산
            int max = 0;
            for (int i = 0; i < 100; i++) {
                countMap = new HashMap<>();
                sortList = new ArrayList<>();
                int index = 0;
                if(tempMap[i][0] == -1){
                    break;
                }
                for (int j = 0; j < 100; j++) {
                    if(tempMap[i][j] == -1){
                        break;
                    }
                    else if(tempMap[i][j] == 0){ //0은 HashMap에다가 넣지 않음
                        continue;
                    }
                    else if(countMap.containsKey(tempMap[i][j])) {
                        countMap.put(tempMap[i][j], countMap.get(tempMap[i][j]) + 1);
                    } else {
                        countMap.put(tempMap[i][j], 1);
                    }
                }
                Iterator it = countMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    sortList.add(new Point((int) entry.getKey(), (int) entry.getValue()));
                }
                Collections.sort(sortList);
                if(max < sortList.size() * 2){
                    max = sortList.size() * 2;
                }
                for (Point point : sortList) {
                    map[i][index] = point.index;
                    map[i][index + 1] = point.count;
                    index += 2;
                    if (index == 100) {
                        break;
                    }
                }
            }
            fill(max , 0);
            copy();
        }
        else{ //C연산
            int max = 0;
            for (int i = 0; i < 100; i++) {
                countMap = new HashMap<>();
                sortList = new ArrayList<>();
                int index = 0;
                if(tempMap[0][i] == -1){
                    break;
                }
                for (int j = 0; j < 100; j++) {
                    if(tempMap[j][i] == -1){
                        break;
                    }
                    else if(tempMap[j][i] == 0){ //0은 HashMap에다가 넣지 않음
                        continue;
                    }
                    else if(countMap.containsKey(tempMap[j][i])) {
                        countMap.put(tempMap[j][i], countMap.get(tempMap[j][i]) + 1);
                    } else {
                        countMap.put(tempMap[j][i], 1);
                    }
                }
                Iterator it = countMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    sortList.add(new Point((int) entry.getKey(), (int) entry.getValue()));
                }
                Collections.sort(sortList);
                if(max < sortList.size() * 2){
                    max = sortList.size() * 2;
                }
                for (Point point : sortList) {
                    map[index][i] = point.index;
                    map[index + 1][i] = point.count;
                    index += 2;
                    if (index == 100) {
                        break;
                    }
                }
            }
            fill(max , 1);
            copy();
        }
    }
    public static void copy(){
        for(int i = 0; i < 100; i++){
            for(int j = 0; j < 100; j++){
                tempMap[i][j] = map[i][j];
                map[i][j] = -1;
            }
        }
    }
    public static void fill(int max , int op){
        if(op == 0){ //R연산 (오른쪽으로)
            for(int i = 0; i < map.length; i++){
                if(map[i][0] == -1){
                    break;
                }
                for(int j = 0; j < max; j++){
                    if(map[i][j] == 0){
                        continue;
                    }
                    else if(map[i][j] == -1){
                        map[i][j] = 0;
                    }
                }
            }
        }
        else{ //C연산 (아래쪽으로)
            for(int i = 0; i < map.length; i++){
                if(map[0][i] == -1){
                    break;
                }
                for(int j = 0; j < max; j++){
                    if(map[j][i] == 0){
                        continue;
                    }
                    else if(map[j][i] == -1){
                        map[j][i] = 0;
                    }
                }
            }
        }
    }
}
