import java.util.*;
import java.io.*;

// 한번 내가 생각했던 걸로 가보자.
// 설마 abs 가 int 만 처리하는 것일까?

/*
아얘 그냥 sum 과 반대되는 -sum 과 비슷한 값을 찾고
그 주변에서 탐색을 하니까 맞았음
뭔가 일반된 방향으로 진행할 수가 없었던게 , 해당 값과 비슷한 값으로 가는 것을 찾는 경우라서 , 뭔가 일반되게 특정할 수 없었다.
역시나 left 로 처리해도 맞았음
 */
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
                // 방향을 조금 바꿔보자 , -sum 과 가까운 애를 찾는 다는 개념으로 진행하자.
                int left = 0;
                int right = (int)(N - 1);

                long sum = arr[i] + arr[j];
                // 이제 mid 값으로 가운데 값을 찾아갈 것이다.
//                int index = 0;

                while(left <= right){
                    int mid = (left + right) / 2;

//                    index = mid;
                    // 찾는 값이 sum 의 -1 을 곱한것 과 같다면 0 을 만들 수 있으니까 바로 아웃
                    if(sum == -1 * arr[mid]) break;

                    // arr[mid] 값이 찾으려는 값보다 , 작다면 ? 올린다
                    if(arr[mid] < -1 * sum) left = mid + 1;

                    // arr[mid] 값이 찾으려는 값보다 , 크면 ? 내린다.
                    else right = mid - 1;
                }

                for(int c = left - 2; c < left + 3; c++){
                    if(c == i || c == j || c < 0 || c >= N) continue;

                    if(abs(sum + arr[c]) < abs(min)){
                        min = sum + arr[c];
                        res[0] = arr[i];
                        res[1] = arr[j];
                        res[2] = arr[c];
                    }
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
