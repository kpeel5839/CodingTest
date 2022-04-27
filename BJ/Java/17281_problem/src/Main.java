import java.util.*;
import java.io.*;

// 17281 : ⚾️️
/*
-- 전제 조건
야구 게임을 시작하고,
각 이닝이 주어지는데 , 각 이닝에 해당 번호의 타자들이 어떤 식의 점수를 내게 될지 주어진다.
그러면 이제 그 타자의 순서를 적절히 배합해서 , 최대 점수를 얻을 수 있도록 , 선수들의 순서를 짜면된다.
근데 이것은 원래 같으면 9 ^ 9 로 3 억이 넘는 경우의 수가 있다. 근데 , 사악한 아주 조악한 문제로
1 번 타자는 무조건 4번에다가 고정한다라는 전제조건이 있다.
그렇다라는 것은 8 ^ 8 즉 1600만으로 줄어들게 된다.
아니다 생각해보니까 8 ^ 8 도 아니다 , 하나에 들어가면 못들어가니까 8! 임
-- 틀 설계
위에서 말했듯 , 최대 점수를 출력하면 된다.
흔히 야구의 룰대로 , 안타 , 2루타 , 3루타가 있고 홈런이 있다.
이것은 주자수에 따라서 득점수가 달라지게 된다.

그리고 아웃이 주어지고 , 아웃이 3회가 쌓이게 되면 이제 다음 이닝이 진행되는데,
해당 타자로부터 다시 시작한다.

이러한 조건을 만족한 다음에 , 그냥 브루트 포스로 모든 경우를 주면 , 쉽게 풀릴 듯 하다.

만들어야 할 함수,
일단 gameStart로 게임을 시작하고,
dfs 함수로

-- 해맸던 점
역시 틀릴리가 없는데 , 틀렸던 이유는 queue를 size 만큼만 뺐었어야 했는데 그러지 않아서 그랬었고,
그리고 맨 처음에는 홈런을 쳤을 때를 바로 처리하지 않아서 , 틀렸었었음
 */
public class Main{
    public static int N , ans = 0 , SIZE = 9;
    public static int[][] inning;
    public static boolean[] visited = new boolean[SIZE];
    public static int[] assign = new int[SIZE];
    public static void dfs(int idx){

        if(idx == 3){ // 3번째 타자는 0번으로 고정임 (4번 타자)
            dfs(idx + 1);
            return;
        }

        if(idx == SIZE){
            gameStart();
        }

        else{
            for(int i = 0; i < SIZE; i++){
                if(visited[i]) continue;
                visited[i] = true; // i번째 선수를 선택했음
                assign[idx] = i;//현재는 i를 선택하였음 , assign 은 초기화 안해주어도 됨 , 짜피 무조건 해당 idx 는 선택하니까
                dfs(idx + 1);
                visited[i] = false; // 내가 선택한 애 , 즉 이미 선택한 애만 선택 안했다고 수정해주면 됨

            }
        }
    }
    public static void gameStart(){
        /*
        주어진 assign 대로 게임을 진행한다.
        여기서 얻는 점수를 ans = Math.max(ans , result) 로 진행해준다.
        이제 넘겨주는 것 까지 완벽하게 되었으니까
        게임만 잘 처리하면 된다.

        일단 해당 sequence 즉 assign 순대로 인덱스만 변경해가면서 해당 이닝을 진행하면 된다.
        그리고 아웃이 3개가 쌓이는 지, 그리고 아웃이 3개가 되었을 때 , 다음 이닝으로 넘어가는 것
        주자가 얼마나 쌓여있는지 , 등의 것들을 처리해야한다.

        일단 아웃에 대해 처리하면 아웃은 3개가 쌓이면 그냥 다음 이닝으로 넘어가면 된다.
        즉 index를 뭐 따로 처리하는 것이 아니라 아웃이 3이 되면 아웃을 0으로 바꾸고 다음 이닝으로 넘어가면 되고
        inningCount 가 inning.length 보다 작을 때까지만 진행하면 된다.

        이제 주자 관리는 그냥 1 , 2 , 3 , 4 로 들어오는 것들은 그냥 진짜 더해주면 되고
        이것이 4 이상이 되버리면 더했을 때 , 그 때 점수를 1추가 해주고,
        해당 리스트를 지우면 될 것 같다.
        즉 queue 로 관리해서 , queue 에서 빼서 더한다음에 , 다시 넣어주고 그것을 반복해준다.
        queue.isEmpty() 할 때까지 , fifo 이기 때문에 먼저 쳐서 자리에 간 타수가 먼저 나오게 되니까 이상 없다.
        이런식으로 진행하면 될 것 같다.

        계속 진행하면서 out 3 되면 queue 비워주고 , 이런식으로
         */

        int inningCount = 0; // inning[inningCount][..] 임

        Queue<Integer> queue = new LinkedList<>();
        int out = 0;
        int index = 0; // assign 의 0 번째 인덱스부터 시작해주고 9가 될때마다 0으로 만들어준다.
        int score = 0;

        while(inningCount < inning.length){ // inningCount 가 더 작을 때까지만 진행한다.
            int player = assign[index];
            int value = inning[inningCount][player];

            if(value == 0){
                out++;
            }

            else{ // value == 0 이 아니라면 주자들을 다 뛰게 한다.
                int size = queue.size();

                for(int i = 0; i < size; i++) {
                    int lo = queue.poll() + value;
                    if (lo >= 4) {
                        score++; // 아니면 다시 집어넣지 않고 , 점수에 더해준다.
                    } else {
                        queue.add(lo); // 주자가 아직 존재하면 더한 상태로 놥둬주고
                    }
                }
            }

//            System.out.println(out);
//            System.out.println(queue);

            if(value == 4) score++; // 4점이면 그냥 바로 score 에 집어넣음
            else if(value != 0) queue.add(value);

            index = index + 1 == 9 ? 0 : index + 1;

            if(out == 3){
                inningCount++;
                queue = new LinkedList<>();
                out = 0;
            }
        }

        ans = Math.max(ans , score);
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(input.readLine());

        inning = new int[N][SIZE];
        assign[3] = 0;
        visited[0] = true;
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < SIZE; j++){
                inning[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0);
//        assign = new int[]{4 , 5 , 6 , 0 ,1 , 2 , 3 , 7 , 8};
//        gameStart();
        System.out.println(ans);
    }
}
