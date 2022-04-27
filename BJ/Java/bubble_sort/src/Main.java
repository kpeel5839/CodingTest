import java.util.*;
import java.io.*;

public class Main {
    public static int[] list;
    /*
    -bubble sort
    - 설계
    -- 전체적인 설계
    1. 일단 리스트를 받는다.
    2. 서로 위치를 계속 swap하게 될 건데 last에서 하나씩 끊으면서 갈거다.
    3. swap이 한번도 일어나지 않았을 때에는 바로 sort를 종료한다.
     */
    public static void bubbleSort(int start , int last){
        /*
        1. last 이전까지 할 것이니까 last는 딱 index = 1 까지만
        2. 처음에도 j < i 까지만 해야지 딱 마지막에 걸치게 된다. (indexing error x)
         */
        for(int i = last; i != 0; i--){
            boolean sort = true;
            for(int j = 0; j < last; j++){
                if(list[j] > list[j + 1]){swap(j , j + 1); sort = false;}
            }
            if(sort){
                break;
            }
        }
    }
    public static void swap(int first , int second){
        int temp = list[first];
        list[first] = list[second];
        list[second] = temp;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        int size = st.countTokens();
        list = new int[size];
        int index = 0;
        while(st.hasMoreTokens()){
            list[index++] = Integer.parseInt(st.nextToken());
        }
        bubbleSort(0 , size - 1);
        for(int i = 0; i < size; i++){
            System.out.print(list[i] + " ");
        }
    }
}
