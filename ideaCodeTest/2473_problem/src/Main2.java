import java.util.*;
import java.io.*;

// 한번 내가 생각했던 걸로 가보자.
// 설마 abs 가 int 만 처리하는 것일까?
public class Main2 {
    public static long N , min = Long.MAX_VALUE;
    public static long[] res = new long[3] , arr;

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Long.parseLong(input.readLine());
        arr = new long[(int)N];

        st = new StringTokenizer(input.readLine());

        for(int i = 0; i < N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr);

        for(int i = 0; i < N - 1; i++){
            for(int j = i + 1; j < N; j++){
                int left = 0;
                int right = (int)(N - 1);

                long sum = arr[i] + arr[j];
                // 이제 mid 값으로 가운데 값을 찾아갈 것이다.
                while(left <= right){
                    int mid = (left + right) / 2;

                    if(abs(sum + arr[mid]) < abs(min) && i != mid && j != mid){ // i , j 가 아니면서 더 작으면 채택
                        min = sum + arr[mid];
                        res[0] = arr[mid];
                        res[1] = arr[i];
                        res[2] = arr[j];
                    }

                    // mid 값을 찾아주고 , 해당 mid 값에 따라서 sum 이 0보다 작아지면 즉 음수가 되면 left = mid + 1
                    // 양수이면 right = mid - 1; 로 찾아주고
                    // 값의 갱신은 계속 sum 을 min 값과 비교해가면서 진행

                    if(sum + arr[mid] == 0 && i != mid && j != mid) break;

                    if(sum < 0) left = mid + 1;

                    else right = mid - 1;
                }
            }
        }

        Arrays.sort(res);

        System.out.println(res[0] + " " + res[1] + " " + res[2]);
    }

    public static long abs(long number){
        return number < 0 ? number * -1l : number; // number 가 음수이면 , -1 을 곱해서 내보내고 , 아니면 그냥 반환
    }
}
