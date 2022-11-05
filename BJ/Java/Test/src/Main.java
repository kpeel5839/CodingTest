import java.util.*;
import java.io.*;

public class Main {
    public static class Ball {
        Integer number;
        Ball (int number) {
            this.number = number;
        }

        @Override
        public boolean equals(Object object) {
            Ball ball = (Ball) object;
            if (ball.number == this.number) {
                return true;
            }

            return false;
        }

        @Override
        public int hashCode() {
            return this.number.hashCode();
        }

        @Override
        public String toString() {
            return "number : " + number;
        }
    }
    public static void main(String[] args) {
        List<Ball> list = new ArrayList<>();

        HashSet<Ball> set = new HashSet<>();
        set.add(new Ball(1));
        set.add(new Ball(2));
        set.add(new Ball(3));
        System.out.println(set.size() == 3);
        set.add(new Ball(3));
        System.out.println(set.size() == 3);
        System.out.println(set);
    }
}
