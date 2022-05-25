import java.util.*;
import java.io.*;

class Solution {
    /*
    일단 center 를 기준으로 그래프를 구성하자.
    그리고 계속 본인의 parent 로 타고 올라가야 하기에,
    Node 를 따로 만들어서 관리해야 할 것 같기도하다.
    일단, 각각의 노드들은 본인이 받은 돈의 총액수를 저장하고 있어야 한다.
    
    그러면 노드에 필요한 정보는?
    value, parent, child 들이 있을 것 같다.
    
    그리고 결과 값을 출력할 떄에는 enroll 에서 들어온 순서대로 출력을 하는 것을 볼 수 있다.
    그러면 일단 enroll 을 통해서 각 Node 들을 형성해야 할 것 같다.
    그리고, 각 graph 로 그냥 부모 노드만 알 수 있으면 될 것 같다 나중에 탐색도 필요가 없으니..
    
    그러면 일단 enroll 을 가지고, hashMap 을 통해서 Node 를 선언해보자.

    해맸던 점은
    Math.floor 를 이용했었어야 함..
    Math.ceil 이 아니라
    나머지 로직은 전혀 문제 없었음
    */
    static int MUL;
    static float DIV;
    static String root;
    static HashMap<String, Node> nodeMap = new HashMap();

    static class Node {
        Node parent = null;
        int money;

        Node(int money) {
            this.money = money;
        }
    } // Node class

    static void distribute(Node cur, int money) { // 거슬러 올라가면서 돈을 나눔
        if (cur.parent == null) { // 최상위 노드를 만나면, 그냥 끝내줌, 짜피 center 의 벌이를 출력하는 파트는 없는 것 같음, 있으면 수정하자
            return;
        }

        int share = 0; // 나눌 돈이 없을 때를 대비해서 미리 0으로 선언

        if ((float) money * DIV >= 1.0f) { // 나머지가 있는 경우는
            share = (int) Math.floor((float) money * DIV); // 나눠줄 돈 만듦
            money -= share;
        } // 나눠주고 Math.ceil 임, 나눴을 때 0 이면 이거 하지 않음

        cur.money += money; // 돈 더해줌

        if (share != 0) { // share 할 돈이 있을 때
            distribute(cur.parent, share);
        }
    }

    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        root = "center";
        MUL = 100;
        DIV = 0.1f;
        List<Integer> res = new ArrayList<>();
        int[] answer;

        nodeMap.put(root, new Node(0)); // 얘는 부모 노드가 없음, 사실 얘 안만드는 게 더 간단할 것 같긴한데..

        for (int i = 0; i < enroll.length; i++) {
            nodeMap.put(enroll[i], new Node(0));
        }

        for (int i = 0; i < referral.length; i++) { // 순서에 맞게
            String name = referral[i];
            if (name.equals("-")) {
                nodeMap.get(enroll[i]).parent = nodeMap.get(root);
            } else {
                nodeMap.get(enroll[i]).parent = nodeMap.get(name);
            }
        }

        for (int i = 0; i < seller.length; i++) {
            distribute(nodeMap.get(seller[i]), amount[i] * MUL);
        }

        for (int i = 0; i < enroll.length; i++) {
            res.add(nodeMap.get(enroll[i]).money);
        }

        answer = new int[res.size()];

        for (int i = 0; i < res.size(); i++) {
            answer[i] = res.get(i);
        }

        return answer;
    }
}