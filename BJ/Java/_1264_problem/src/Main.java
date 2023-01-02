import java.util.*;
import java.io.*;

// 1264 : 모음의 개수

/**
 * Example
 * How are you today?
 * Quite well, thank you, how about yourself?
 * I live at number twenty four.
 * #
 */
public class Main {
    public static char[] smallLetterVowel = {'a', 'e', 'i', 'o', 'u'};

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1264_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String line = br.readLine();

            if (line.equals("#")) {
                break;
            }

            int numberOfVowel = 0;

            for (int i = 0; i < line.length(); i++) {
                char smallCharacter = Character.toLowerCase(line.charAt(i));
                for (int j = 0; j < smallLetterVowel.length; j++) {
                    if (smallCharacter == smallLetterVowel[j]) {
                        numberOfVowel++;
                        break;
                    }
                }
            }

            System.out.println(numberOfVowel);
        }
    }
}
