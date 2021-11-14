import java.io.*;
import java.util.*;

public class Main {
    public static Double scoreCalculate(Double previousScore , Long previousCount , Long score , Long presentCount){ //별점 주기
        Double resultScore = (double)Math.round(((previousScore * previousCount) + score) / presentCount * 100) / 100;
        return resultScore;
    }
    public static void main(String[] args) {
        Double previousScore = 4D;
        Long previousCount = 123L;
        Long score = 5L;
        Long presentCount = 122L;
        System.out.println(scoreCalculate(previousScore , previousCount , score * -1 , presentCount));
    }
}
