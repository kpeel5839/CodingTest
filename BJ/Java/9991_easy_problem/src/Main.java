import java.util.*;
import java.io.*;

// 9991 : Auto-Complete
/*
-- 전제조건
소가 발굽이 너무 커서 오타가 많이남
그래서 사전 단어들을 입력받고
소가 치면 자동완성하는데
자동 완성할 때 (정수 , 문자열) 쌍을 줌
그러면 사전순으로 정렬된 문자열 중에서 해당 문자열이 포함이 되어 있는 것들을 쫙 가져오고
거기서 해당 정수의 인덱스의 것을 가져오는데 이게 사전에서 몇번째에 있는지를 반환한다.
-- 틀 설계
trie 알고리즘을 쓸 것임
일단 노드의 요소는 Map 변수와 , boolean (이 노드가 종료 노드인지를 나타내도록 한다.)
HashMap 사용 법은 , size() , put() , get() 등이 있고 contains() - (해당 key 가 있는지 확인) 가 있다.
그리고 단어를 받으면 insert에 삽입해서 root 노드부터 시작해서 계속 삽입한다.
그리고 find로 찾으면 rootNode부터 재귀적으로 해서 count 와 찾는 접두사를 주면 찾도록 한다.
만약 못찾으면 -1 , 찾으면 해당 Node가 가지고 있는 번호를 반환한다.
-- 해맸던 점
일단 dfs로 순회하면서 답을 찾으면 이제 거기서 끝냈어야 했는데 , 근데 거기서 안끝내고 게속 count가 같으면 ans 에 답을 삽입하도록 하였음 그래서 틀린 답이 나왔었음
그리고 사전에 있는 단어들보다 긴 단어가 나온 것을 고려하지 못했음
이제 그것을 다 고려하고 나서는 메모리 초과로 통과 못함...
 */
public class Main {
    public static int count = 0;
    public static boolean find = false;
    public static Long ans = 0L;
    public static class Node{
        boolean finish;
        Long wordNumber = -1L;
        Map<Character , Node> child;
        public Node(boolean finish){
            this.finish = finish;
            child = new HashMap<>();
        }
        public Node(boolean finish , Long wordNumber){
            this.finish = finish;
            this.wordNumber = wordNumber;
            this.child = new HashMap<>();
        }
    }
    public static Node rootNode = new Node(false);
    public static void insert(String string , int index){
        /*
        일단 받으면 첫번째 글자부터 루트 노드부터 시작해서 집어넣는다.
        현재 문자가 트리에 이미 존재한다면 다음으로 넘어간다 , map.get(key) 해서
        만일 없다면 ? 그러면 node를 추가한다. (마지막 글자가 아니면 그냥 finish 변수만 주면 됨
        마지막 글자는 finish 와 wordNumber를 해당 index로 준다.
        contains 로 계속 확인하면서 가능하다.
         */
        Node curNode = rootNode;
        for(int i = 0; i < string.length(); i++){
            Character character = string.charAt(i);
            if(i == string.length() - 1){ //마지막 문자
                if(curNode.child.containsKey(character)){
                    curNode = curNode.child.get(character);
                    curNode.finish = true;
                    curNode.wordNumber = new Long(index);
                }else{
                    curNode.child.put(character , new Node(true , new Long(index)));
                }
            } else{ //마지막 문자가 아니면 해당 문자열이 존재하는 지 안하는 지 확인을 먼저 해줘야함
                if(curNode.child.containsKey(character)){
                    curNode = curNode.child.get(character);
                }else{
                    curNode.child.put(character , new Node(false));
                    curNode = curNode.child.get(character);
                }
            }
        }
    }
    public static Long find(int findNumber , String prefix){
        /*
        일단 해당 prefix까지는 간 다음에
        거기서 부터 재귀적으로 해결해야하는데...
        일단 해당 지점까지 갔다하면 a ~ z 까지 contains key를 해서 계속 재귀적으로 그것을 반복하는 방법 밖에 없음
        일단 이 함수에서 해결해야 할 문제는 딱 하나임 , 해당 지점까지 이동하는 것, 그것을 해결해야함
        그럴려면 일단 findNumber는 계속 가지고 간다음 해당 prefix가 끝나는 지점까지 이동해서 dfs로 넘겨주면
        dfs에서 해결해서 해당 index를 반환하는 것으로 그 다음에 그것을 출력하기만 하면 된다.
        일단 해당 지점까지 가려면 prefix를 한 글자 한글자 씩 가면서 해당 지점까지 가야함
         */
        Node curNode = rootNode;
        for(int i = 0; i < prefix.length(); i++){
            Character character = prefix.charAt(i);
            if(curNode.child.containsKey(character)){
                curNode = curNode.child.get(character);
            }else{
                return -1L;
            }
        }
        count = 0;
        ans = -1L;
        find = false;
        dfs(curNode , findNumber);
        return ans;
    }
    public static void dfs(Node startNode , int findNumber){
        /*
        find에서 해당 prefix 간 것을 줄 것임
        그럼 얘는 boolean = true 를 만날 경우에 그냥 count++ 를 해주면 된다.
        일단은 preOrder를 사용해야 할 듯하다
        먼저 finish 가 true인 것 즉 끝 문자를 만나게 되면 count를 증가시켜주고
        그 다음에 왼쪽 것부터 차례대로 호출하면 될 듯하다.
        그니까 결국 여기서 HashMap iterator 로 순회만 하면 되는 것이다.
         */
        if(find){
            return;
        }
        if(startNode.finish){
            count++;
        }

        if(count == findNumber){
            find = true;
            ans = startNode.wordNumber;
            return;
        }
        Iterator iterator = startNode.child.keySet().iterator();
        while(iterator.hasNext()){
            Character key = (Character)iterator.next();
            System.out.println(key);
            dfs(startNode.child.get(key) , findNumber);
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        for(int i = 1; i <= n; i++){
            insert(input.readLine().toLowerCase() , i);
        }

        for(int i = 0; i < m; i++){
            st = new StringTokenizer(input.readLine());
            System.out.println(find(Integer.parseInt(st.nextToken()) , st.nextToken().toLowerCase()));
        }
    }
}