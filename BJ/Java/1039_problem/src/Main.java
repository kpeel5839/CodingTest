import java.util.*;
import java.io.*;

// 1039 : 교환
/*
-- 전제조건
0으로 시작하지 않는 정수 N이 주어진다. 이때 자리수를 M이라고 하고 , 1 <= i < j <= M 일 때
이것을 위치를 서로 바꾸는 연산을 수행하는데 , 해당 연산과 같은 연산을 K번 수행해서 나올 수 있는 가장 큰 N값을 출력
-- 틀 설계
일단 모든 위치에서 bfs를 실시 할 것이다.
근데 이제 본인의 위치보다 더 높은 위치만을 탐색해서 그 자리를 바꾼다음에 횟수와 , 현재 string을 queue에다가 저장하고
진행할 것이다.

그리고 ans를 놓고 , Math.max(ans , Integer.parseInt(queue.string)) 를 계속 하면서 횟수 == k 가 되면 말이다.
그리고 항상 선택을 할 떄 두가지의 조건을 고려해야한다, 0인 상태에서는 0과 위치를 바꾸면 안된다.
본인의 뒤에 있는 숫자 중 가장 큰 숫자를 선택한다. (본인을 제외)
for(int i = 0; i < string.length; i++) 로 계속 시도한다.
모든 위치에서 시도해야지 최대 값을 얻을 수 있을 듯하다.

한번 내 가정을 436659 로 실험을 해보자
일단 4와 가장 큰 숫자인 9를 바꾼다
936654
그리고 3이랑 가장 큰 숫자인 6을 바꾼다 , 같은 숫자가 있다면 가장 뒤에있는 숫자를 선태개향 한다
근데 이것은 굳이 넣을 필요가 없고 if(max > number) 일 때 max = string.charAt(i) , index = i
그러면 966354 가 된다.

나머지도 한번 해보자.
16375 는 할 필요도 없고
132 -> 312 , 그럼 여기서 또 엄청 분기한다. 근데 두번째 자리에서 분기하는 것으로 가정 하면 312 -> 321 -> 312 이런식으로 갈 수 있다
432도 동일하다.
근데 이제 여기서 예외사항을 잘 처리해주어야 하는데 , 한자리 수인데 k가 1 보다 큰 경우
혹은 2자리인데 0이 포함이 된 경우를 체크하면 된다.
전처리로 일단 그것에 대한 결과를 -1을 출력하고
그 다음에 순서대로 bfs로 처리하면 될 듯하다.

-- 해맸던 점
설계는 다 좋았으나, visited 배열을 만들어서 방문처리르 했었어야 했는데 , 그것을 안해서 시간초과가 났었음
다음에는 더 좋은 생각을 하기 위해서 노력해야할 듯
 */
public class Main {
    public static class Data{
        String stNum;
        int count;
        public Data(String stNum , int count){
            this.stNum = stNum;
            this.count = count;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        String number = st.nextToken();
        int k = Integer.parseInt(st.nextToken());
        int ans = 0;
        int[][] visited = new int[1000001][11];

        if(k == 0) {
            System.out.println(number);
            return;
        }

        if(k >= 1 && number.length() == 1){
            System.out.println(-1);
            return;
        }

        for(int i = 0; i < number.length(); i++){
            if(k >= 1 && number.length() == 2 && number.charAt(i) == '0') { // 2자리 숫자인데 0이 하나라도 포함이 되어 있는 경우 , k 가 1 이상이면서
                System.out.println(-1);
                return;
            }
        } // 예외에 대한 처리를 다 해줌

        Queue<Data> queue = new LinkedList<>();
        queue.add(new Data(number , 0));
        visited[Integer.parseInt(number)][0] = 1;

        /*
        while(!queue.isEmpty()) 를 걸어놓고서
        count == k 가 되면 ans 를 갱신하고 continue 한다 , 이제 거기에 대해서 진행하지 않는 것이다.
        그리고 하나하나 진행할 때마다 본인의 2중 for 문으로 i , j 를 적절히 선택해주어서 queue에다가 집어넣을 지 말지를 결정한다.
        그리고 가장 중요한 것은 2중 루프에서 탐색할 때 항상 max 값으로 스위치 해야한다는 것이 중요하다
         */

        while(!queue.isEmpty()){
            Data data = queue.poll();
//            System.out.println(data.stNum);
            if(data.count == k){
                ans = Math.max(ans , Integer.parseInt(data.stNum));
                continue;
            }
            for(int i = 0; i < number.length() - 1; i++){
                int biggest = 0;
                int biggestIndex = 0;
                for(int j = i + 1; j < number.length(); j++){
                    int charNum = data.stNum.charAt(j) - '0';
                    if(biggest <= charNum){ // 같은 것 까지 포함하는 것이 포인트임
                        biggest = charNum;
                        biggestIndex = j;
                    }
                }
                if(i == 0 && biggest == 0) continue; // 첫자리가 0으로 바뀔 때는 continue;
//                System.out.println("biggest : " + biggest + " biggestIndex : " + biggestIndex);
                // 이제 예외 사항들은 다 처리했으니까 i 번째와 j 번째 문자열을 바꿔주기만 한 것을 queue에다가 집어넣어주고 횟수를 + 1 해서 넘겨주면 된다.
                char first = data.stNum.charAt(i); //i 문자열
                char second = data.stNum.charAt(biggestIndex); //j 문자열
//                System.out.println("first : " +first + " second : " + second);
                StringBuilder result = new StringBuilder(data.stNum);
                result.setCharAt(i , second);
                result.setCharAt(biggestIndex , first);
                if(visited[Integer.parseInt(result.toString())][data.count + 1] == 0) {
                    queue.add(new Data(result.toString(), data.count + 1));
                    visited[Integer.parseInt(result.toString())][data.count + 1] = 1;
                }
            }
        }

        System.out.println(ans);
    }
}
