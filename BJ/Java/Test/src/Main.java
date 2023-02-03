import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String url = "http://localhost:8088/user/point/basket/confirm";
        System.out.println(url.lastIndexOf("/"));
        System.out.println(url.substring(0, url.lastIndexOf("/")));
    }
}