import java.util.*;
import java.io.*;

//17825 : 주사위 윷놀이
/*
-- 전제조건
1. 주사위 10번을 굴린 것이 주어진다.
2. 윷놀이의 말의 규칙을 따라서 움직인다.
3. 시작 , 도착지점을 제외하고 동시에 같은 공간에 존재할 수 없다.
4. 주사위의 숫자들이 주어졌을 때 최고의 점수를 도출해내기

-- 설계
- 노드 설계 0(시작) -> 2 -> 4 -> 6 -> 8 -> 10 -> 12 -> 14 -> 16 -> 18 -> 20 -> 22 -> 24 -> 26 -> 28 -> 30 -> 32 -> 34 -> 36 -> 38 -> 40 -> 42(도착 : 점수 합산 안할 예정)
- 중간 중간 지름길 , 그냥은 next 로 구현 , 지름 길은 shortNext 로 하자
- 지름길 10(시작) -> 13 -> 16 -> 19 -> 25
- 지름길 20(시작) -> 22 -> 24 -> 25 -> 30 -> 35 -> 40
- 지름길 30(시작) -> 28 -> 27 -> 26 -> 25
- 이런식으로 길을 구현하면 결국 도착 지점으로 이동 가능함
1. Node로 윷놀이의 칸을 구현
2. node 속성 : value , next , shortNext , status(현재 여기에 말이 존재하는 지 판단, 이거는 솔직히 배열로 관리해도 될 듯 , 아니면 0으로 해서
3. 혹은 그냥 가면서 자신이 이전에 있던 곳에 status = false 로 하고 다음 본인이 갈 칸을 true로 하는 것 물론 확인하고 이동
4. order 배열의 끝에 도달해서 끝나거나 아니면 유효한 판이 아닐 때에는 본인의 위치의 status를 다 false로 지우고 이동
5. dfs로 order를 채워주고 , 항상 Math.max(ans , gameStart()) 로 판단한다.
6. 일단 노드들은 List로 관리하자, List.get(i)로 항상 init은 head로
7. 각 말들이 order에 따라서 List.get(order[i])를 하면서 말을 뽑은 뒤 해당 order의 인덱스에 맞는 dice만큼 움직여주는데 일단 움직여보고 거기가 이동이 가능한 상태이면
8. 이동이 가능한 상태이면 자신의 list를 바꾸고 아니면 바로 break , break할 때 즉 게임이 종료되는 경우에는 현재 노드들의 status , 즉 for문으로 List를 다 돌면서 현재의 node 의 status를 다 false로 만들어준다.
9. 게임이 종료되는 경우 조건은 2가지 , 게임이 정상적으로 끝나는 경우 , 혹은 dice대로 움직이는데 말이 갈 수가 없을 때 , 짜피 최대값을 못 찍는다. (다른 비슷한 경우의 효율적인 움직임들이 있기 때문에 (모든 경우라서))
10. 윷놀이판을 만드는 게 중요할 듯 , 나머지는 어렵지 않을 듯
11. 윷놀이판은 일단 for(int i = 2; i <= 42; i+= 2;) 로 진행을 하고 시작점은 만들고 Node startNode = new Node(0)으로 그리고 startNode.start = true 로 만들어주고 시작한다.
12. 그렇게 Node cur = startNode 넣어놓고 Node newNode = new Node(i); cur.next = newNode; cur = newNode; 이런식으로 하나하나 만들어가고
13. ListNode[] shortNodeList = new ListNode[3]; 여기서 하나씩 넣어가서 먼저 20번 만들어서 길 뚫어놓고 , 10 , 30 순으로 길 만들면 될 듯
14. ListNode shortNode = shortNode[1]; 일단 하나 만들어주고 new Node(22) shortNode.next = newNode; 로 뚫어주고 cur = newNode; 로 순서대로 만들면 될 듯
15. 일단 move 함수를 호출함 해당 list의 index 그리고 count 를 넘겨주면 이 움직임이 유효한지 판단하고 유효하면 1 , 아니면 -1을 반환하면 된다.
16. 일단 계속 시작하고 나면 이게 계속 안에 value를 검사해야함 , 그리고 status도 , 그럼 끝지점인지 비어있는지 판단할 수 있고 , 지름길이 뚫려있는 길인지도 알 수 있음
*/
public class Main {
    public static class Node{
        int value;
        Node next;
        Node shortNext;
        boolean shortCut;
        boolean status;
        public Node(int value){
            this.value = value;
            this.next = null;
            this.shortNext = null;
            this.status = false;
            this.shortCut = false;
        }
    }
    public static int[] order = new int[10]; // 일단 주사위들 순서
    public static int[] dice = new int[10];
    public static List<Node> horse = new ArrayList<>() , shortList = new ArrayList<>();
    public static Node startNode = new Node(0);
    public static int ans = 0;
    public static void dfs(int depth){
        if(depth == 10){ //여기서 이제 10까지 다 모이면 return;
            ans = Math.max(ans , gameStart());
            return;
        }
        for(int i = 0; i < 4; i++){
            order[depth] = i; //짜피 해당 요소를 뺄 필요가 없음 다시 집어넣기 때문에 이런 것도 생각할 줄 알아야함
            dfs(depth + 1);
        }
    }
    public static void main(String[] args) throws IOException{
        //노드로 풀어보자
        // node 만들기
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());
        for(int i = 0; i < 10; i++){
            dice[i] = Integer.parseInt(st.nextToken());
        }
        for(int i = 0; i < 4; i++){
            horse.add(startNode);
        }
        Node cur = startNode;
        for(int i = 2; i <= 42; i+=2){
            Node newNode = new Node(i);
            if(i == 10 || i == 20 || i == 30 || i == 40){
                if(i == 10 || i == 20 || i == 30){
                    newNode.shortCut = true;
                }
                shortList.add(newNode);
            }
            cur.next = newNode;
            cur = newNode;
        } // 대략적인 길 만들어 놓기
        /*
        0 : 10 , 1 : 20 , 2 : 30 , 3 : 40 , 4 : 25
         */
        Node shortNode = shortList.get(1);
        shortNode.shortNext = new Node(22);
        cur = shortNode.shortNext;
        cur.next = new Node(24);
        cur = cur.next;
        cur.next = new Node(25);
        shortList.add(cur.next);
        cur = cur.next;
        for(int i = 30; i <= 40; i+=5){
            if(i == 40){
                cur.next = shortList.get(3);
            }
            else{
                cur.next = new Node(i);
                cur = cur.next;
            }
        }
        shortNode = shortList.get(0);
        shortNode.shortNext = new Node(13);
        cur = shortNode.shortNext;
        for(int i = 16; i <= 19; i+= 3){
            cur.next = new Node(i);
            cur = cur.next;
        }
        cur.next = shortList.get(4);

        shortNode = shortList.get(2);
        shortNode.shortNext = new Node(28);
        cur = shortNode.shortNext;
        for(int i =27; i > 24; i--){
            if(i == 25){
                cur.next = shortList.get(4);
            }
            else{
                cur.next = new Node(i);
                cur = cur.next;
            }
        } //맵은 다 만듦
        dfs(0);
        gameStart();
        System.out.println(ans);
    }
    public static int gameStart(){ //다 돌고나서 결과 반환하기 , order 여기다가 표시
        int sum = 0;
        init();
        /*
        1. 여기에는 결과 값 즉 max값을 반환
        2. move 함수 호출 뒤 return value 가 -1이면 유효하지 않은 것이기 때문에 바로 return -1을 하면 될 것 같다 거기서 바로 move를 끝내고
        3. 일단 그렇게 init()도 따로 만들어서 List를 수정하는
        4. 10 , 20 , 30 으로 시작하는 지 항상 확인하고 42를 return 값으로 받으면 sum에다가 더하면 안됨 그리고 return 값을 항상 확인해주어서 -1을 return 값으로 받게 된다면 바로 sum = -1 쳐박고 끝
        */
        for(int i = 0; i < 10; i++){
            int value = move(order[i], dice[i]);
            if(value == -1){
                sum = -1;
                break;
            }
            if(value != 42){
                sum += value;
            }
        }
        for(Node node : horse){ //지금까지 틀렸던 이유는 -1이 뜨지 않으면 이전에 status들을 초기화 하지 않았기 때문이야
            node.status = false;
        }
        return sum;
    }
    public static int move(int index, int count){
        /*
        1. 여기는 움직임을 관할 하는 함수이다 index : 말번호(0 , 1 , 2 , 3) horse 접근하면 되고 , count : 움직이는 칸 수
        2. 움직이면 거기 해당 score 반환하고 42 반환이면 그냥 끝지점인거고 -1이면 유효하지 않은 움직임
        3. 그러니까 score를 반환한다고 생각하면 된다.
        4. 항상 10 , 20 , 30인지 확인해야함 그리고 자신이 도착한 지점이 끝지점인지 확인하고 거기서 끝내던가 자신이 간 곳이 비어있는지 확인함
        */

        /*
        --고치기
        1. 정상적이라면 0 -> 10 -> 30으로 가야함
         */
        Node startNode = horse.get(index);
        Node cur;
        if(startNode.value == 42){
            return -1;
        }
        else if(startNode.shortCut == true){
            cur = startNode.shortNext;
        }
        else {
            cur = startNode.next;
        }
        count--;
        while(count != 0){
            if(cur.value == 42){
                horse.set(index , cur);
                startNode.status = false;
                return 42;
            }
            cur = cur.next;
            count--;
        }
        if(cur.status == true){
            return -1;
        }
        /*
        1. 일단은 그냥 안끝내고 그대로 가려면 비어있는 상황이나 그런 상황에서 그냥 가만히 있어야함
        2. 비정상적으로 끝나는 경우가 없다는 말임 그렇다는 것은 그냥 init()으로 하는게 가능할 듯
         */
        startNode.status = false;
        cur.status = true;
        horse.set(index , cur);
        return cur.value;
    }

    public static void init(){
        for(int i = 0; i < 4; i++){
            horse.set(i , startNode);
        }
    }
}
