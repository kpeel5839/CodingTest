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

        System.out.println("low : " + low + " high : " + high + " pivot : " + pivot);
        quickSort(low , pivot - 1);
        quickSort(pivot + 1 , high);
    }
    public static int pivot(int low , int high){
        /*
        1. 그냥 하나임 기준치를 가지고 첫번째 인덱스부터 탐색을 하는데
        2. 근데 pivot 을 돌리게 되면 적어도 10 9 8 2 3 4 이렇게 있다고 했을 때 2 3 4 이렇게는 되어야함 그러니까 자기보다 작은 것들은 앞으로 가야함 그래야지 2 3 4 10 9 8 이런식으로 됨
        3. 이렇게는 적어도 되어야 각자 구역을 나눠서 자기들끼리 정렬이 가능한 것
        4. 그러니까 pivot을 기준으로 하되 끝 값보다 작은 값을 즉 기준 값보다 작은 값을 만나게 되면 현재의 인덱스와 내가 가진 index = 0; 과 swap을 하는 것임 그러면 index++ 이 되서 1이 될 것이고
        5. 최종적으로 index + 1 에 본인의 값이 들어가게 되면 되는 것이다. 그리고 그렇기에 탐색은 무조건 자신과 이전까지만 와야함
        6. 전체적인 설계는 완벽했지만 짜잘한 오류들이 있었음
        7. 그러니까 마지막에도 swap을 하게 되면 어쨌든 index + 1을 하게 되고 그러면 무조건 끝나게 되면 pivot 이 들어갈 위치에 index 값이 위치하게 됨 그렇기 떄문에
        8. 그냥 swap(index , high) 와 return index; 를 하게 되면 됨
         */
        int index = low;
        int pivot = list[high];
        for(int i = low; i < high; i++){
            if(list[i] <= pivot){
                swap(i , index++);
            }
        }
        swap(high , index);
        return index;
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
        for(int i = 0; i < list.length; i++){
            System.out.print(list[i] + " ");
        }
    }
}
