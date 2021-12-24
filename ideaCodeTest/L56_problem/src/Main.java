import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine() , ",");
        int[][] intervals = new int[st.countTokens() / 2][2];
        for(int i = 0; i < intervals.length; i++){
            intervals[i][0] = Integer.parseInt(st.nextToken());
            intervals[i][1] = Integer.parseInt(st.nextToken());
        }
        int[][] result = merge(intervals);
        System.out.print("[");
        for(int i = 0; i < result.length; i++){
            System.out.print("[");
            for(int j = 0; j < 2; j++){
                if(j == 1){
                    System.out.print(result[i][j] + "]");
                }
                else{
                    System.out.print(result[i][j] + ",");
                }
            }
            if(i != result.length - 1){
                System.out.print(",");
            }
        }
        System.out.print("]");
    }
    /*
    -- 설계
    1. 일단 정렬을 해준다.
    2. 그러고서 순서대로 읽는다.
    3. 일단 처음에는 intervals[0][1] 을 넣고 들어간다.
    4. 그 다음에 [i][0] , [i][1] 을 들어간다.
    5. 이제 여기서 자기보다 큰 거를 만나는데 첫 번째 요소에서 큰 것을 만나면 이전에 가지고 있던 요소를 그냥 list.add 하고
    6. 두 번째 요소에서 큰 것을 만나면 두 번째 요소를 add해서 넣지 말고 이제 이전에 가지고 있는 요소로 집어 넣는다.
    7. 그니까 처음에 List.add(arr[i][0])을 해줘서 첫번째 요소는 무조건 넣고 , int pre = arr[i][1]; 을 넣어준다.
    8. 그리고서 for문에 들어가서 for(int i = 0; i < intervals.length; i++) 으로 간 다음에 if(i == intervals.length - 1) 이 아닌 경우에는 if(pre < arr[i][0]) 이면 list.add(pre) 해주고
    9. 만약에 아니다 그러면 if(pre < arr[i][1]){pre = arr[i][1];} 을 해줘서 pre를 교체해준다.
    10. 근데 만약 if (i == intervals.length - 1){}라면 그냥 if(pre < arri[i][0]) 만 해주고 list.add(pre) , list.add(arr[i][0]) 해준다음에 list.add(arr[i][1])까지 만약에 이게 아니다
    pre = arr[i][1]; 해준다음 list.add(pre)만 추가해주면 된다.
    */
    public static int[][] merge(int[][] intervals) {
        List<Integer> tempResult = new ArrayList<>();
        Arrays.sort(intervals , new Comparator<int[]>(){
            @Override
            public int compare(int[] a , int[] b){
                if(a[0] > b[0]){
                    return 1;
                }
                else if(a[0] == b[0]){
                    return a[1] - b[1];
                }
                else{
                    return -1;
                }
            }
        });
        tempResult.add(intervals[0][0]);
        int pre = intervals[0][1];
        if(intervals.length == 1){
            tempResult.add(pre);
        }
        for(int i = 1; i < intervals.length; i++){
            if(i == intervals.length - 1){
                if(pre < intervals[i][0]){
                    tempResult.add(pre);
                    tempResult.add(intervals[i][0]);
                    tempResult.add(intervals[i][1]);
                }
                else{
                    if(pre < intervals[i][1]){
                        tempResult.add(intervals[i][1]);
                    }
                    else{
                        tempResult.add(pre);
                    }
                }
            }
            else{
                if(pre < intervals[i][0]){
                    tempResult.add(pre);
                    tempResult.add(intervals[i][0]);
                    pre = intervals[i][1];
                }
                else if(pre < intervals[i][1]){
                    pre = intervals[i][1];
                }
            }
        }
        int[][] result = new int[tempResult.size() / 2][2];
        int index = 0;
        int arrayIndex = 0;
        for(Integer number : tempResult){
            index++;
            if(index % 2 == 0){
                result[arrayIndex++][1] = number;
            }else{
                result[arrayIndex][0] = number;
            }
        }
        return result;
    }

}
