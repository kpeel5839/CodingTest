import java.util.*;
import java.io.*;

/*
- quick sort
-- 전체적인 설계
1. 배열을 입력을 받는다.
2. quickSort(int low , int high) 로 호출을 한다.
3. 각 기준 값은 정확한 위치를 찾을 수 있도록 한다.
4. 그러면 해당 기준의 값이 몇번째 인덱스인지 알아야한다. 그러니까 그 기준의 인덱스를 제외하고 왼쪽 오른쪽으로 다시 나눠서하면 된다.
 */
public class Main {
    public static int[] list;
    public static void swap(int first , int second){
        /*
        1. 이것은 말 그대로 pivot에서 swap을 할 때 쓸 함수임
         */
        int temp = list[first];
        list[first] = list[second];
        list[second] = temp;
    }
    public static void quickSort(int low , int high){
        /*
        1. low , high를 가진 채로 일단 그냥 pivot(low , high); 한다.
        2. 그리고 받은 인덱스 그러니까 그 기준 인덱스를 두고 그것을 기준으로 나눈다.
        3. 이제 근데 low , high 가 차이가 나지 않으면 요소는 하나라는 것이고 그러면 정렬을 더 이상 할 수가 없는 상황이므로 끝낸다. (재귀호출)
        4. pivot이 중요한 함수이다.
         */
        if(low >= high){
            return;
        }
        int pivot = pivot(low , high);

        quickSort(low , pivot - 1);
        quickSort(pivot , high);
    }
    public static int pivot(int low , int high){
        /*
        1. 이 설계는 pivot을 가운데로 하고 가운데를 기준으로 오른쪽은 큰 값 , 왼쪽은 작은 값을 분포하려고 하는 것이다.
        2. 그러면 이 설계에서는 계속 pivot을 기준으로 pivot보다 큰 값이 왼쪽에 보일 때까지 low를 증가시키고
        3. 오른쪽은 pivot보다 작은 값이 보일때까지 high 를 감소시킨다.
        4. 그러면 pivot을 기준으로 low , high가 결정이 된다. 그러면 그것을 서로 swap 하고
        5. 이미 이전에 검사한 것들은 상관이 없으니까 low ++ , high --를 시켜준다.
        6. 이것을 while(low <= high) 가 될때까지 반복한다.
        7. 물론 low > high 가 되면은 swap하지 않는다.
         */
        int pivot = list[(low + high) / 2];
        while (low <= high) {
            while (list[low] < pivot) low++;
            while (list[high] > pivot) high--;
            if (low <= high) {
                swap(low, high);
                low++;
                high--;
            }
        }
        return low;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        list = new int[st.countTokens()];
        int index = 0;

        while(st.hasMoreTokens()){
            list[index++] = Integer.parseInt(st.nextToken());
        }

        quickSort(0 , list.length - 1);
//        System.out.println(Arrays.toString(list));
    }
}