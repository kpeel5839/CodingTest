import java.util.*;
import java.io.*;

// 21608 : 상어 초등학교
/*
-- 전제조건
상어 초등학교에 교실이 하나 있고 N * N 크기의 격자로 나타낼 수 있다.
가장 왼쪽 윗 칸이 (1, 1) 이고 가장 오른쪽 아랫 칸은 (N, N)이다.
선생님은 학생의 순서를 정했고 각 학생이 좋아하는 학생 4명도 모두 조사했다.
각 학생이 좋아하는 학생 4명을 조사하고 다음과 같은 규칙을 이용해서 학생의 자리를 정하려고 한다.
한칸에는 학생 한명의 자리만 있을 수 있다.
1. 비어있는 칸 중에서 좋아하는 학생이 인접한 칸에 가장 많은 칸으로 자리를 정한다. (상하좌우)
2. 만일 1번 조건을 만족하는 칸이 여러개이면 인접한 칸 중에서 비어있는 칸이 가장 많은 칸으로 자리를 정한다.
3. 그리고 2번을 조건도 만족하는 칸이 여러개인 경우 가장 왼쪽 위에 있는 자리로 정한다.
그래서 결국은 위에 조건대로 모두 앉히고 학생들의 만족도의 총합을 구한다.
만일 좋아하는 학생이 인접한 칸에 0명이면 0 , 1 -> 1 , 2 -> 10 , 3 -> 100 , 4 -> 1000 이다.
학생들의 만족도의 총합을 구하자.
-- 틀 설계
map을 [n][n] 크기로 만든다.
들어온 순서대로 해당 학생이 좋아하는 학생의 리스트를 저장하기 위해서 like 배열을 만들어서[i][j] 일 때
i = 학생의 번호 [i][j] = i학생이 좋아하는 학생의 번호이다. 그래서 like = new int[n + 1][4]; 로 선언하면 될 듯하다.
그러고서 순서대로 모든 학생들을 다 배치할 때까지 계속 반복문을 돌 것인데
여기서 3개의 조건을 알아야한다 첫 번째 주변에 인접한 좋아하는 친구들이 많은가
그런칸이 많다면 주변에 빈칸이 많은 걸로 가야한다, 그것도 많다면 가장 왼쪽 위에 있는 것으로 가야한다.
max 값과 space 값의 max 값을 찾아 놓는다. 그 다음에 point에서 max , space 값을 찾는데 다르다 그러면
for 문을 돌면서 삭제하는 것임
friendMax를 찾고나서 size == 1 아니면 spaceMax 찾고 그럭고서도 size == 1 아니면 collections.sort 하면 될 듯하다.
그래서 한칸 씩 돌면서 4방향 다 보고 인접한 칸과 friend 값과 space 값을 채워넣어서 list에다가 넣고 제거해서 결국 결정해서 집어넣는 그런 식의 느낌으로 가면 될 듯
그렇게 선택된 학생을 자리에다가 집어넣고
마지막에는 한자리 한자리 돌아보면서 결과를 도출해낸다.
-- 해맸던 점
spaceMax를 잘 못 구했었음 , max 를 걸러내고 거기서 space가 가장 높은 것을 골라야하는데
그게 아니라 그냥 max를 걸러내기 전에 spaceMax를 값을 정해서 값이 잘 못나왔었고
또 dy , dx 값에다가 j , c 를 넣었어야 했는데 또 잘못해서 그것때문에 살짝 애먹었음
 */
public class Main {
    public static int n , ans = 0;
    public static int[][] map , like;
    public static int[] dx = {0, 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0} , sequence;
    public static class Point implements Comparable<Point>{
        int y;
        int x;
        int friend;
        int space;
        public Point(int y , int x , int friend , int space){
            this.y = y;
            this.x = x;
            this.friend = friend;
            this.space = space;
        }
        @Override
        public int compareTo(Point other){
            if(this.y < other.y){
                return -1;
            }else if(this.y == other.y){
                return this.x - other.x;
            }else{
                return 1;
            }
        }
        @Override
        public String toString(){
            return "y : " + y + " x: " + x + " friendCount : " + friend + " spaceCount : " + space;
        }
    }
    public static void insert(int studentNumber){
        /*
        여기서는 이제 studentNumber가 주어진다.
        그러면 이제 해당 student 를 격자에 집어넣으면 된다.
        일단 격자의 한칸 한칸을 도는데 해당 격자의 주변이 빈칸이면 spaceCount++ 을 해주고
        그리고 만일 좋아하는 친구가 있으면 friend++ 를 해준다.
        그러면서 이제 4칸을 다 돌면 new Point(i , j , friendCount, spaceCount) 로 list에다가 넣어주고 friendMax , spaceMax를 갱신해준다.
        끝까지 돈다음에 먼저 for 문을 돌면서 max와 일치하지 않는 것들을 삭제해주고 , size == 1 이 아니라면 spaceMax와 동일한 것을 삭제해주고 그럼에도 불구하고 size != 1이라면
        collections.sort 를 한 다음에 첫번째 인덱스를 선택해서 넣어주면 된다.
         */
        int friendMax = 0;
        List<Point> pointList = new ArrayList<>();
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(map[i][j] == 0){
                    int spaceCount = 0;
                    int friendCount = 0;
                    for(int c = 0; c < 4; c++){
                        int ny = i + dy[c];
                        int nx = j + dx[c];
                        if(ny < 0 || ny >= n || nx < 0 || nx >= n){
                            continue;
                        }
                        if(map[ny][nx] == 0){
                            spaceCount++;
                            continue;
                        } else{
                            for(int v = 0; v < 4; v++){
                                if(map[ny][nx] == like[studentNumber][v]){
                                    friendCount++;
                                    break;
                                }
                            }
                        }
                    }
                    if(friendMax < friendCount){
                        friendMax = friendCount;
                    }
                    pointList.add(new Point(i , j , friendCount , spaceCount));
                }
            }
        }
        int spaceMax = 0;
        for(int i = 0; i < 2; i++) {
            for (int j = 0; j < pointList.size(); j++) {
                Point point = pointList.get(j);
                if(i == 0) {
                    if (friendMax != point.friend){
                        pointList.remove(j);
                        j--;
                    }else{
                        if(spaceMax < point.space) spaceMax = point.space;
                    }
                }else{
                    if (spaceMax != point.space){
                        pointList.remove(j);
                        j--;
                    }
                }
            }
            if(pointList.size() == 1){
                break;
            }
        }

        if(pointList.size() != 1){
            Collections.sort(pointList);
        }

        Point point = pointList.get(0);
        map[point.y][point.x] = studentNumber;
    }
    public static void calAns(){
        /*
        모든 격자를 돌면서 4방향 다 like[map[i][j]][0 ~ 3] 까지 다 탐색한다.
        그러고서 좋아하는 친구가 몇명 있는지 세서 Math.pow(10,n - 1)로 ans에다가 더한다.
        0 일때만 0으로 특별하게 처리
         */
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                int friendCount = 0;
                for(int c = 0; c < 4; c++){
                    int ny = i + dy[c];
                    int nx = j + dx[c];
                    if(ny < 0 || ny >= n || nx < 0 || nx >= n){
                        continue;
                    }
                    for(int v = 0; v < 4; v++){
                        if(map[ny][nx] == like[map[i][j]][v]){
                            friendCount++;
                            break;
                        }
                    }
                }
                if(friendCount != 0){
                    ans += Math.pow(10 , friendCount - 1);
                }
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(input.readLine());
        map = new int[n][n];
        like = new int[(n * n) + 1][4];
        sequence = new int[n * n];

        for(int i = 0; i < (n * n); i++){
            st = new StringTokenizer(input.readLine());
            int studentNumber = Integer.parseInt(st.nextToken());
            like[studentNumber][0] = Integer.parseInt(st.nextToken());
            like[studentNumber][1] = Integer.parseInt(st.nextToken());
            like[studentNumber][2] = Integer.parseInt(st.nextToken());
            like[studentNumber][3] = Integer.parseInt(st.nextToken());
            sequence[i] = studentNumber;
        }

        for(int i = 0; i < (n * n); i++){
            insert(sequence[i]);
        }

        calAns();

        System.out.println(ans);
    }
}
