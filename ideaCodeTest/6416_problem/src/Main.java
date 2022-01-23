import java.util.*;
import java.io.*;

// 6416 : 트리인가?
/*
--전제조건
트리의 구조를 만족하는지 판단을 해서 출력을 하여야함
세가지의 조건을 만족해야함

노드가 아얘 없는 경우도 트리의 조건을 만족하는 것이다.

1. 들어오는 간선이 하나도 없는 단 하나의 노드 모든 노드의 출발 지점인 root 노드를 의미한다.
2. 루트 노드를 제외한 모든 노드들은 반드시 단 하나의 들어오는 간선이 존재해야 한다.
3. 루트에서 다른 노드로 가는 경로는 반드시 가능하며 , 유일하다 , 이는 류트를 제외한 모든 노드에 성립해야 한다. (즉 루트 노드에서 모든 노드를 갈 수 있는 경로는 무조건 존재해야함)

입력은 여러개의 TC로 이루어져 있고 , 입력의 끝에는 종료가 가능하도록 음의 정수가 두개가 주어진다.
그리고 각각의 케이스마다 정수들이 주어지는데 2개씩 주어진다.
앞에는 u , 뒤에는 v 정점이고 u -> v 로 갈 수 있다라는 간선을 의미하고 , 단 방향이다.
TC의 번호는 1부터 시작하며 순서대로 받는 트리에 대해서 설명할 때
Case k is [not] a tree. 이런식으로 출력하면 된다. not은 당연히 트리의 조건을 만족하지 않을 때 붙히면 된다.
--틀 설계

 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
    }
}
