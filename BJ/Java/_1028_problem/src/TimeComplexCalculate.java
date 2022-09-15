import java.util.*;
import java.io.*;

public class TimeComplexCalculate {
    public static void main(String[] args) {
        int R = 11;
        int C = 11;

        int timeComplex = 0;

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                timeComplex += Math.min(i, Math.min(j, C - 1 - j)) + 1;
            }
        }

        System.out.println(timeComplex);
    }
}
