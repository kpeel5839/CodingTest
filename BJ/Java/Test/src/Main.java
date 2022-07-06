import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static class Point {
        int y;
        int x;

        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        @Override
        public String toString() {
            return "y : " + y + " x : " + x;
        }
    }

    static class Vector extends Point {
        int dir;
        int speed;

        Vector(int y, int x) {
            super(y, x);
        }

        Vector(int y, int x, int dir, int speed) {
            super(y, x);
            this.dir = dir;
            this.speed = speed;
        }

        @Override
        public String toString() {
            return super.toString() + " dir : " + dir + " speed : " + speed;
        }
    }

    static Point makeAVector(int y, int x, int dir, int speed) {
        return new Vector(y, x, dir, speed);
    }

    public static void main(String[] args) throws IOException {
        System.out.println(makeAVector(1, 2, 3, 4));
    }
}
