import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException{
        int[] monthDay = new int[12]; //1월은 그냥 , 2월 부터 0번째를
        int year = 2021;
        int[] record = new int[365];
        LocalDate newDate;
        for(int i = 1; i < 12; i++){
            newDate = LocalDate.of(year , i , 1);
            monthDay[i] = monthDay[i - 1] + newDate.lengthOfMonth();
        }
        LocalDateTime dateTime = LocalDateTime.of(2021 , 11 , 30 , 0 ,  0);
        record[monthDay[dateTime.getMonthValue() - 1] + dateTime.getDayOfMonth() - 1] += 1;
        int monthDayIndex = 0;
        int month = 1;
        for(int i = 0; i < record.length; i++){
            int count = record[i];
            int decodeDay = i + 1;
            if(monthDayIndex != 11 && decodeDay > monthDay[monthDayIndex + 1]){
                monthDayIndex++;
                month++;
            }
            LocalDateTime innerDate = LocalDateTime.of(year , month , decodeDay - monthDay[monthDayIndex] ,0 , 0 , 0);
            System.out.println("날짜 : " + innerDate + " 필사 쓴 수 : " + count);
        }
    }
}


