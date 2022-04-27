import java.util.*;
import java.io.*;

public class Main {
    public static int[] list;
    public static void selectionSort(int low , int high){
        /*
        1. 하나씩 검사하면서 max 값을 찾는다.
        2. 첨에는 마지막까지 , 그 다음에는 하나씩 줄여가면서 1번째 인덱스까지 간다.
        3. 이중 for 문으로 가는데 int i = high; i != 0; i-- 이렇게 가고 하나하나 swap(i , max_index) 를 해준다.
         */
        for(int i = high; i != 0; i--){
            int maxIndex = 0;
            for(int j = 1; j <= i; j++){
                if(list[maxIndex] < list[j]){
                    maxIndex = j;
                }
            }
            swap(i , maxIndex);
        }
    }
    public static void swap(int first, int second){
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

        selectionSort(0 , size - 1);

        for(int i = 0; i < size; i++){
            System.out.print(list[i] + " ");
        }
    }
}
