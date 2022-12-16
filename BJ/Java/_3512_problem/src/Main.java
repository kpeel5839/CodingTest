import java.util.*;
import java.io.*;

// 3512 : Flat

/**
 * Example
 * 6 75000
 * 8 other
 * 3 bathroom
 * 2 bathroom
 * 10 kitchen
 * 16 bedroom
 * 7 balcony
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_3512_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int numberOfTotalRoom = Integer.parseInt(st.nextToken());
        int squareMetre = Integer.parseInt(st.nextToken());
        int totalArea = 0;
        int areaOfAllBedroom = 0;
        int areaOfAllBalcony = 0;

        for (int i = 0; i < numberOfTotalRoom; i++) {
            st = new StringTokenizer(br.readLine());
            int areaOfThisRoom = Integer.parseInt(st.nextToken());
            String kindOfRoom = st.nextToken();
            totalArea += areaOfThisRoom;

            if (kindOfRoom.equals("bedroom")) {
                areaOfAllBedroom = areaOfThisRoom;
            }

            if (kindOfRoom.equals("balcony")) {
                 areaOfAllBalcony += areaOfThisRoom;
            }
        }

        System.out.println(totalArea);
        System.out.println(areaOfAllBedroom);
        System.out.println((totalArea - (areaOfAllBalcony * 0.5)) * squareMetre);
    }
}
