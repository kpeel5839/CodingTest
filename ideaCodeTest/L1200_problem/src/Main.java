import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(input.readLine(), ",");
        int[] arr = new int[st.countTokens()];
        int index = 0;
        while(st.hasMoreTokens()){
            arr[index++] = Integer.parseInt(st.nextToken());
        }
        System.out.println(minimumAbsDifference(arr));
    }
    public static List<List<Integer>> minimumAbsDifference(int[] arr){
        Arrays.sort(arr);
        List<List<Integer>> result = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        for(int v = 0; v < 2; v++) {
            for (int i = 0; i < arr.length - 1; i++) {
                int value = Math.abs(arr[i + 1] - arr[i]);
                if(v == 0){
                    if(value < min){
                        min = value;
                    }
                }
                else{
                    if(value == min){
                        List<Integer> innerList = new ArrayList<>();
                        innerList.add(arr[i]);
                        innerList.add(arr[i + 1]);
                        result.add(innerList);
                    }
                }
            }
        }
        return result;
    }
}
