import java.util.*;
import java.io.*;

public class Main{
    /*
    -- 2차원 배열 정렬
    1. 2차원 배열 선언
    2. Arrays.sort 와 new Comparator<int[]>(){ ~ } ); Override 로 정의
     */
    public static void main(String[] args) throws IOException{
        int [][] arr = {{5,10}, {8,9}, {5,6}};

        Arrays.sort(arr, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                /*
                1. 이전 내 기억으로는 양수가 나올 수록 즉 더 큰 수가 나올 수록 뒤에 갔음 그러면 크면 뒤에 가야함
                 */
                if(o1[0] > o2[0]){
                    return 1;
                }
                else if(o1[0] == o2[0]){
                    return o1[1] - o2[1];
                }
                else{
                    return -1;
                }
            }
        });
        for(int i = 0; i < 3; i++) {
            System.out.print("[");
            for (int j = 0; j < 2; j++) {
                if (j == 1) {
                    System.out.println(arr[i][j] + "]");
                }
                else{
                    System.out.print(arr[i][j] + ",");
                }
            }
        }
    }
}














































