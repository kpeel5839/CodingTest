import java.util.*;
import java.io.*;

// 2002 : 추월
/*
-- 전제조건
1. N개의 차량이 터널을 지나간다
2. 처음에는 터널에 들어간 순서 , 그리고 나중에는 터널에서 나온 순서대로 한다.
3. 반드시 추월을 했을 것으로 여겨지는 차가 몇대인지 출력한다.
-- 틀 설계
1. 입력을 받는다. (n 과 들어온 차의 번호판을 list에다가 저장한다)
2. 그리고 다시 또 입력을 받으면서 List에서 컨트롤 해서 답을 도출해낸다.
 */
public class Main {
    public static void main(String[] args) throws IOException{
        /*
        1. 일단 List로 입력을 받는다 List<String>으로
        2. 그 다음에 입력을 다 받은 다음에
        3. 그 다음 또 n개 만큼 들어오는 입력을 list의 첫번째 값과 계속 비교한다
        4. 만일 들어온 순서대로 나오지 않으면 이 차량은 추월한 차량이기에 list에서 remove한다.
        5. 만약 그냥 그대로이면 0번째 index의 list 요소를 삭제하면서 정상적으로 이어간다 (이 경우는 추월 당한게 아니다.)
        6. 최종적으로 추월한 차량이 나올 때 마다 ans++ 해주고 출력한다.
         */
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine()) , ans = 0;
        List<String> carList = new ArrayList<>();
        for(int i = 0; i < n; i++){
            carList.add(input.readLine());
        }
        for(int i = 0; i < n; i++){
            String license = input.readLine();
            if(carList.get(0).equals(license)){
                carList.remove(0);
            }else{
                ans++;
                carList.remove(license);
            }
        }
        System.out.println(ans);
    }
}
