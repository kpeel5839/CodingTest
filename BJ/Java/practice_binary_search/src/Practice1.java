import java.util.*;
import java.io.*;

/*
가정을 한번 해보자.

1 2 5 라고 하고 , 4보다 작은 최대의 수를 찾는다고 가정하자
그러면
while(left <= right) 로 진행한다고 하였을 때
현재 값이 , find 값보다 작다면 당연히 left를 증가시켜야 한다.
if(arr[mid] < find) left = mid + 1;

그리고 , arr[mid] > find 라면 ? 값을 작게 만들어야 하니 , right = mid - 1;

if(arr[mid > find) right = mid - 1;

이런식으로 진행하게 될 것이다.
그래서 결과적으로
while(left <= right){
	mid = (left + right) / 2;
 	if(arr[mid] < find) left = mid + 1;
	if(arr[mid] > find) right = mid - 1;
}

이런식으로 진행한다고 가정하였을 때, 그냥 같은 경우는 일단 지금은 고려하지 않는다고 하자

1 2 5 라면
첨에는 2
그 5를 탐색 한다.
이 때 , left = 3 , right = 3 이다.

5가 더 작으니까 , right = mid - 1; 로진행해서
결국 left = 3 , right = 2 로 끝나게 된다.

여기서 할 수 있는 것이 , 어떻게 해서 같아졌을 때
이 경우 5가 커서 right = mid - 1; 이 되었지만
만일 left 이 경우도 5 -> 3 이였다면?

그러면 left = mid + 1; 이 되어서
left = 4 , right = 3 이 되어서 끝났을 것이다,
이 경우에서도 right 가 정답이다,

한번 이분탐색으로 코딩을 진행해보자.

너무 이분탐색이 약한 것 같다.

이분 탐색이 너무 약해서 , 한번 나 혼자서 예제를 만들어서 진행해보자.


1 2 3 4 5 6 7 10 11
9

답 제대로 나옴

1 2 3 4 5 6 7 10 11
7

답 제대로 나옴

1 2 3 4 5 10 12 13
13
 */
public class Practice1 {
    public static void main(String[] args) throws IOException{
        System.out.println("찾으려는 값보다 , 최대한 가까운 더 작은 수를 구하시오 , 같은 경우 같은 값을 고르시오.");

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

            if(arr[mid] <=findNumber) left = mid + 1;
            else right = mid - 1;
        }

        System.out.println(arr[right]);
    }
}
