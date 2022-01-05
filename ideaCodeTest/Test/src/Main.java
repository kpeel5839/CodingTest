import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.io.*;
public class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int[] record;
        Long year = 2022L;
        int[] monthDay = new int[12];
        if(year%4 == 0 && year%100 !=0 || year%400 == 0){
            record = new int[366];
        }
        else {
            record = new int[365];
        }
        LocalDate newDate;
        for(int i = 1; i < 12; i++){ //1월 1일 = 0 1 월 31일 = 30 , 2월 1일? = 31 3월 1일 = 60
            newDate = LocalDate.of(year.intValue() , i , 1);
            monthDay[i] = monthDay[i - 1] + newDate.lengthOfMonth();
        }
        System.out.println(Arrays.toString(monthDay));
        int frontNullCount = LocalDate.of(year.intValue() , 1 ,2).getDayOfWeek().getValue() % 7;
        System.out.println(frontNullCount);
    }
}













































