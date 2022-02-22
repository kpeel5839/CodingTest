import java.util.*;
import java.io.*;

public class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(input.readLine());
        int[] numberList = new int[n];
        int[] countList = new int[8001]; // 0 == -4000 , 4000 = 0 , 8000 = 4000
        double sum = 0;
        int modeResult = 0;

        for(int i = 0; i < n; i++){
            int number = Integer.parseInt(input.readLine());
            countList[number + 4000]++;
            numberList[i] = number;
            sum += number;
        } // 범위 , 가장 큰 값 - 가장 작은 값 + 1 본인도 포함이라서
        // 중앙 값 정렬후 중간값 , 무조건 n 홀수
        // 최빈 값은 countList에서 가장 높은 숫자 선택
        // 산술평균 sum / n 하고 반올림

        int maxMode = 0;
        int modeIndex = 0;
        int modeCount = 1;
        Arrays.sort(numberList);

        for(int i = 0; i <= 8000; i++){
            if(maxMode < countList[i]) {
                maxMode = countList[i];
                modeIndex = i;
                modeCount = 1;
            }
            else if(maxMode == countList[i]){
                modeCount++;
            }
        }

        if(modeCount == 1){
            modeResult = modeIndex;
        }else{
            modeCount = 0;
            for(int i = 0; i <= 8000; i++){
                if(maxMode == countList[i]){
                    modeCount++;
                    if(modeCount == 2){
                        modeResult = i;
                        break;
                    }
                }
            }
        }

        int range = numberList[numberList.length - 1] - numberList[0];

        System.out.println(Math.round(sum / n));
        System.out.println(numberList[numberList.length / 2]);
        System.out.println(modeResult - 4000);
        System.out.println(range);
    }
}