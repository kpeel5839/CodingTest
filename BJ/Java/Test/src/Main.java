import java.awt.geom.AffineTransform;
import java.util.*;
import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static enum Const {
        ONE(1), TWO(2);

        private final int number;

        Const(int number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return this.number + "";
        }

        public int getNumber() {
            return this.number;
        }
    }
    public static void main(String[] args) {
        System.out.println(Const.valueOf("ONE"));
    }
}
