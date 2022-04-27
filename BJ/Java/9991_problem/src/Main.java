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
2. 그러고서 받은 사전을 정렬한다.
3. 그리고서 List<ArrayList<String>> 을 선언하고 이제 그 후에 입력으로 들어온 것들을
4. 이전에 받은 사전에서 indexOf로 탐색하면서 list.add를 해준다.
5. 그러고서 이제 나중에 돌면서 정수가지고 해당 size가 해당 index 와 같거나 큰지 보고
6. 같거나 크면 -1을 반환하고 , 아니면 HashMap.get(list.get(정수)) 하면서 출력한다.
-- 해맸던 점
1. 그냥 findNumber를 -1 을 해서 받았어야 했는데 순간 헷갈려서 안해서 조금 지연됨
2. 그리고 이게 주어지는 단어가 시작 지점에 위치해야함 (안에 해당 문자열이 포함이 되어 있기만 한게 아니라 처음에 위치하여야함)
3. 근데 시간초과
 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(input.readLine());

        int w = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        HashMap<String , Integer> dicHash = new HashMap<>();
        List<ArrayList<String>> autoList = new ArrayList<>();
        String[] dic = new String[w];
        String[] findWord = new String[n + 1];
        int[] findNumber = new int[n + 1];
        /*
        1. dic 배열을 Arrays.sort를 하여서 나중에 따로 autoList를 정렬할 필요가 없게 한다.
        2. 그리고서 dicHash 에다가 해당 string 과 해당 string의 index + 1 을 넣어논다 , 나중에 결과 값으로 출력할 것이다.
        3. 그러고서 findWord의 요소를 dic을 차례대로 쫙 돌면서 indexOf를 해서 -1이 아니면 해당 findWord의 index 값으로 autoList에다가 집어넣는다.
        4. 그걸 끝까지 반복하고서 , 나중에 findNumber로 해당 index의 list 사이즈를 확인하고 size가 같거나 크면 -1 아니면 해당 string 을 HashMap에서 찾아서 번호를 출력한다.
         */
        for(int i = 0 ; i <= n; i++){
            autoList.add(new ArrayList<>());
        }

        for(int i = 0; i < w; i++){
            dic[i] = input.readLine();
            dicHash.put(dic[i] , i + 1);
        }

        Arrays.sort(dic);

        for(int i = 1; i <= n; i++){
            st = new StringTokenizer(input.readLine());
            findNumber[i] = Integer.parseInt(st.nextToken()) - 1;
            findWord[i] = st.nextToken();
        }

        for(int i = 1; i <= n; i++){
            for(int j = 0; j < w; j++){
                if(dic[j].length() < findWord[i].length()){
                    continue;
                }
                String subWord = dic[j].substring(0, findWord[i].length());
                if(subWord.equals(findWord[i])) autoList.get(i).add(dic[j]);
            }
        }

//        for(int i = 1; i <= n; i++){
//            System.out.println(autoList.get(i));
//        }

        for(int i = 1; i <= n; i++){
            if(autoList.get(i).size() > findNumber[i]){
                output.write((dicHash.get(autoList.get(i).get(findNumber[i]))) + "\n");
            }else{
                output.write(-1 + "\n");
            }
        }
        output.flush();
        output.close();
    }
}
