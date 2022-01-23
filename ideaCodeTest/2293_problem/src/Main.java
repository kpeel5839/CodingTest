import java.util.*;
import java.io.*;

// 2293 : 동전 1
/*
--전제조건
n 가지 종류의 동전이 있다. 각각의 동전이 나타내는 가치는 다르다.
이 동전을 적당히 사용해서 , 그 가치의 합이 k원이 되도록 하고 싶다. 그 경우의 수를 구하시오. 각각의 동전은 몇개라도 사용할 수 있음
사용한 동전의 구성이 같은데 , 순서만 다른 것은 같은 경우이다.

처음에 n , k 가 주어지고
다음 줄부터 n개의 줄이 주어지는데 각각의 동전의 가치가 나온다.
3 10 \n 1 \n 2 \n 5 같은 경우에는
뭐 [1 ..... 1] , [1 ..... 2] .... [5, 5] 이런식으로 경우가 있을 것이고 답은 10이 나오는 것이다.
동전은 몇번이고 사용이 가능하다. 그냥 몇개쓰든 상관 없고 k 를 만드는 경우를 구하면 되는 것이다.
--틀 설계
 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
    }
}