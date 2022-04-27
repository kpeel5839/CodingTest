import java.util.*;
import java.io.*;

/*
1 2 3 4 5 6 7 10 11
7

1 2 3 4 5 6 7 10 11
8

1 2 3 4 5 6 7 10 11
9

1 2 3 4 5 6 7 10 11
10
 */
public class Practice2 {
    public static void main(String[] args) throws IOException{
        System.out.println("찾으려는 값보다 , 최대한 가까운 더 큰 수를 구하시오 , 같은 경우 같은 값을 고르시오.");

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        int[] arr = new int[st.countTokens()];

        for(int i = 0; i < arr.length; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int left = 0;
        int right = arr.length - 1;

        int findNumber = Integer.parseInt(input.readLine());

        while(left <= right){
            int mid = (left + right) / 2;

            System.out.println(mid);
            System.out.println("arr[mid] : " + arr[mid] + " findNumber : " + findNumber);
            if(arr[mid] < findNumber) left = mid + 1;
            else right = mid - 1;
        }

        System.out.println(arr[left]);
    }
}
