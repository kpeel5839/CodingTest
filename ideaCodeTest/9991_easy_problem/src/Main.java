import java.util.*;
import java.io.*;

// 9991 : Auto-Complete
/*
-- 전제조건
1. 소가 발굽이 너무 커서 오타가 많이남
2. 그래서 사전 단어들을 입력받고
3. 소가 치면 자동완성하는데
4. 자동 완성할 때 (정수 , 문자열) 쌍을 줌
5. 그러면 사전순으로 정렬된 문자열 중에서 해당 문자열이 포함이 되어 있는 것들을 쫙 가져오고
6. 거기서 해당 정수의 인덱스의 것을 가져오는데 이게 사전에서 몇번째에 있는지를 반환한다.
-- 틀 설계
1. 입력을 받는다 (입력을 받으면서 HashMap에 key는 문자열 value는 해당 인덱스로 한다 , 물론 value는 실제 index + 1이다.)
2. 입력을 이제 받으면서 findNumber[i] 만큼 size가 되지 않으면 -1 , 그리고 만일 되면 findNumber[i] - 1 번만큼 remove 하고 poll() 하면 된다.
3. 그것을 HashMap에서 검색해서 출력한다.
 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(input.readLine());

        int w = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        HashMap<String , Integer> dicHash = new HashMap<>();
        PriorityQueue<String> queue;
        String[] dic = new String[w];
        /*
        1. 입력을 받고서 바로 dic에서 찾아가지고 거기서 바로 priorityQueue 에서 뽑아내면서 출력한다.
        2. 그러니까 일단 findWord로 탐색을 하고 그 다음에 만일 맞으면 priorityQueue에다가 집어넣어서 해당 size랑 비교한 뒤 되면 findNumber - 1 만큼 remove 하고 poll하면 된다.
        3. 그러면서 뽑은 걸 가지고 HashMap에 key 값으로 출력하면 된다.
         */

        for(int i = 0; i < w; i++){
            dic[i] = input.readLine();
            dicHash.put(dic[i] , i + 1);
        }

        for(int i = 1; i <= n; i++){
            st = new StringTokenizer(input.readLine());
            int findNumber = Integer.parseInt(st.nextToken());
            String findWord = st.nextToken();
            queue = new PriorityQueue<>();
            for(int j = 0; j < w; j++){
                if(dic[j].length() < findWord.length()){
                    continue;
                }
                String subWord = dic[j].substring(0, findWord.length());
                if(subWord.equals(findWord)) queue.offer(dic[j]);
            }
            if(queue.size() < findNumber){
                output.write(-1 + "\n");
            }else{
                for(int j = 0; j < findNumber - 1; j++){
                    queue.remove();
                }
                output.write(dicHash.get(queue.poll()) + "\n");
            }
        }
        output.flush();
        output.close();
    }
}