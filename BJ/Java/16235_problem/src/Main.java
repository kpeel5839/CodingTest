import java.util.*;
import java.io.*;

public class Main {
    public static int n, m, k, treeCount = 0;
    public static int[][] food, map;
    public static List[][] tree;
    public static int[] dx = {0 , 1 , 1 , 1 , 0 , -1 , -1, -1};
    public static int[] dy = {-1 , -1 , 0 , 1 , 1 , 1 , 0 , -1};

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(input.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        food = new int[n][n]; //이거는 겨울마다 로봇이 주는 양분 map
        map = new int[n][n]; //이거는 내가 양분들을 정리해놓을 곳
        tree = new ArrayList[n][n]; //이거는 내가 나무들을 정리해놓을 곳

        for (int i = 0; i < n; i++) { //이렇게 양분들 입력 받고
            st = new StringTokenizer(input.readLine());
            for (int j = 0; j < n; j++) {
                food[i][j] = Integer.parseInt(st.nextToken());
                map[i][j] = 5; //가장 처음의 양분은 5만큼 존재
                tree[i][j] = new ArrayList<Integer>();
            }
        }

        for (int i = 0; i < m; i++) { //나무 입력받기 행 , 열 순으로 들어오고 나이가 들어옴
            st = new StringTokenizer(input.readLine());
            tree[Integer.parseInt(st.nextToken()) - 1][Integer.parseInt(st.nextToken()) - 1].add(Integer.parseInt(st.nextToken())); //이런식으로 해당 칸의 list를 채워넣음
        }

        for (int i = 0; i < k; i++) { //년도 만큼 돌면서
            springAndSummer();
            fall();
            winter();
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                treeCount += tree[i][j].size();
            }
        }

        System.out.println(treeCount);
    }

    public static void springAndSummer() { //여름과 봄은 같이
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) { //자신의 양분만큼 못 먹으면 죽고 그때부터 자신의 나이만큼의 나누기 2를 한다음 그걸 양분으로 준다 , 1은  양분을 못남기고 2부터 남길 수 있는 것
                boolean die = false;
                int index = 0;
                Collections.sort(tree[i][j]);
                for (Integer age : new ArrayList<Integer>(tree[i][j])) {
                    if (die != true && map[i][j] >= age) { //양분을 먹을 수 있으면 양분을 먹고
                        map[i][j] -= age;
                        tree[i][j].set(index, age + 1); //나이 증가시켜줌
                        index++;
                    } else { //아얘 양분 못먹을 것 같다 싶으면 바로 죽어버림
                        die = true;
                    }
                    if (die == true) { //die가 true일 때부터 죽은 것 그 뒤부터는 다 죽음
                        tree[i][j].remove(index); //죽는 것 지금부터 , 이러면서 양분을 남겨야함 5 , 4 , 2 , -1 , 4살 떄 죽는 거 맞는데
                        map[i][j] += Math.round(age / 2); //죽으면서 양분을 남김
                    }
                }
            }
        }
    }

    public static void fall() { //fall은 그냥 모든 tree[i][j]의 리스트를 돌면서 5의 배수의 리스트가 존재하면 주변에다가 나무를 생기게끔하면됨 , 1짜리
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (Integer age : new ArrayList<Integer>(tree[i][j])) { //여기서 이제 5의 배수인지 판단하고 , 순차적으로 돌면서 5의 배수이면 다 추가해준다.
                    if(age % 5 == 0) {
                        for (int c = i - 1; c <= i + 1; c++) {
                            for (int v = j - 1; v <= j + 1; v++) {
                                if ((c < 0 || c >= n) || (v < 0 || v >= n) || (c == i && v == j)) {
                                    continue;
                                }
                                tree[c][v].add(1);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void winter() { //winter는 그냥 양분을 채워넣어주면 됨 , 문제 x ..
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                map[i][j] += food[i][j];
            }
        }
    }
}
