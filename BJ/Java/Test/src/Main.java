import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LocalDate now = LocalDate.now();

        System.out.println(now);
        System.out.println(now.getDayOfMonth());
    }
}
