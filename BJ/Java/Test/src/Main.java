import java.util.*;
import java.io.*;
import java.util.function.Supplier;

public class Main {


    public static void main(String[] args) throws Exception {
        Test test = new Test();
        test.repeat(test::input);
    }
}