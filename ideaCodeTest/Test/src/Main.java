import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;


        int target = Integer.parseInt(input.readLine());
        int[] nums = new int[3];
        int[] result = new int[2];
        st = new StringTokenizer(input.readLine());
        for(int i = 0; i < nums.length; i++){
            nums[i] = Integer.parseInt(st.nextToken());
        }
        Loop1:
        for(int i = 0; i < nums.length; i++){
            int goal = target - nums[i];
            result[0] = i;
            for(int j = 0; j < nums.length; j++){
                if(i == j){
                    continue;
                }
                if(goal == nums[j]){
                    result[1] = j;
                    break Loop1;
                }
            }
        }
        System.out.println(Arrays.toString(result));
    }
}
















































