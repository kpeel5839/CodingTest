import java.util.*;
import java.io.*;
import java.util.function.Supplier;

public class Main {


    public static void main(String[] args) throws Exception {
        System.out.println(new Test.Builder()
                .a(1)
                .b(2)
                .build());
    }
}