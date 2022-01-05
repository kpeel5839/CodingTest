import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.io.*;
public class Main{
    public static int[] dy = {-1 , -1 , 0 , 1 , 1 , 1 , 0 , -1};
    public static void main(String[] args) throws IOException{
        int y = 4;
        int dir = 5;
        int distance = 1;
        int n = 5;
        y = y + (dy[dir] * distance) % n < 0 ? n + (y + ((dy[dir] * distance) % n)) : (y + (dy[dir] * distance)) % n;
        System.out.println(y);
    }
}













































