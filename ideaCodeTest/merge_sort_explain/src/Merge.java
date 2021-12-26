import java.util.*;
import java.io.*;

public class Merge{
    public static int[] l;
    /*
    -- MergeSort 정의
    1. 일단 두 구간을 나눌 수 있는 공간까지 나눈다. 그 기준은 1 2 3 4 5 그러면 처음은 low = 0 , high = 4 , middle = (low + high) / 2 = 2
    2. 그러면 1번째는 mergeSort로 반으로 나눠주고 , 2번째는 그 후 3번째가 병합해주는 과정을 하게 되면 size 가 1이 되었을 때 그때 진행하게 될 듯
    3. 예를 들어서 위와 같이 5인 경우가 있다고 가정하자 첨에는 (0 , 2) , (3 , 4) 이런식으로 나눠지게 될 것이다. 이전에는 (0 , 4)를 가지고 있고
    4. (0 , 4) -> (0 , 2) -> (0 , 1) -> (0 , 0) , (1, 1) -> ...
    5. 이런식으로 흘러가게 되면서 가장 작은 단위로 나뉘게 되고 그 작은 단위는 바로 size가 2일때이다 그러면 이제 이렇게 해서 서로 영역을 나누어서 순서를 지켜서 swap을 하면서 가는 것이다.
     */
    public static void mergeSort(int low , int high){
        if(low >= high){
            return;
        }
        int middle = (low + high) / 2;
        mergeSort(low , middle);
        mergeSort(middle + 1, high);
        merge(low, high , middle);
    }
    public static void merge(int low , int high , int middle){
        /*
        -- 설계
        1. 그럼 이렇게 넘어오게 되면 low , high 가장 작은 단위인 0 ,1을 따져보았을 때에는 low = 0 , high = 1 , middle = 0이다. 이런 경우에는 당연히 low <= middle 이면 진행이고 , right <= high이면 진행이다.
        2. 이렇게 되면 int left = low; int right = middle + 1;이렇게 하면되는 것이다.
        3. 이렇게 해서 새로운 배열을 선언을 한다. high - low + 1 이 size이다.
         */
        int right = middle + 1;
        int left = low;
        int size = high - low + 1;
        int[] temp = new int[size];
        int tempIndex = 0;
        while(left <= middle && right <= high){
            /*
            1. left에 있는놈이랑 만약에 같게 되면 left가 먼저 들어가야함
            2. 그래야지 안전정렬이 지켜짐
             */
            if(l[left] <= l[right]){
                temp[tempIndex++] = l[left++];
            }
            else{
                temp[tempIndex++] = l[right++];
            }
        }
        while(left <= middle){
            temp[tempIndex++] = l[left++];
        }
        while(right <= high){
            temp[tempIndex++] = l[right++];
        }
        tempIndex = 0;
        for(int i = low; i <= high; i++){
            l[i] = temp[tempIndex++];
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());
        /*
        1. 일단은 merge sort를 실험할 것이기에 1차원 배열로 진행할 것임
         */
        int index = 0;
        int size = st.countTokens();
        l = new int[size];
        while(st.hasMoreTokens()){
            l[index++] = Integer.parseInt(st.nextToken());
        }
        mergeSort(0 , size - 1);
        System.out.println(Arrays.toString(l));
    }
}
