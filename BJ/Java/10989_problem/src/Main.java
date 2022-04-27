import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        int N = Integer.parseInt(input.readLine());

        int[] a = new int[N];
        int[] b = new int[N];
        int[] c = new int[10001];

        for(int i = 0; i < N; i++){
            a[i] = Integer.parseInt(input.readLine());
        }

        for(int i = 0; i < N; i++){
            c[a[i]]++;
        }

        for(int i = 2; i < c.length; i++){
            c[i] = c[i] + c[i - 1];
        }

//        System.out.println(Arrays.toString(c));

        for(int i = a.length - 1; i != -1; i--){
            b[c[a[i]] - 1] = a[i];
            c[a[i]]--;
        }

        for(int number : b){
            output.write(number + "\n");
        }

        output.flush();
        output.close();
    }
}
