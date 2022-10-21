import java.time.LocalDateTime;
import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();

        System.out.println(now.getYear());
        System.out.println(now.getMonthValue());
        System.out.println(now.getDayOfMonth());
    }
}
