import java.util.*;
import java.io.*;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        int N = fun.apply(input[0]);
        int M = fun.apply(input[1]);

        HashMap<String, Integer> hear = new HashMap<>();
        HashMap<String, Integer> see = new HashMap<>();
        String[] nameList = new String[N];

        for (int i = 0; i < N; i++) {
            String string = br.readLine();
            nameList[i] = string;
            hear.put(string, 1);
        }

        for (int i = 0; i < M; i++) {
            String string = br.readLine();
            see.put(string, 1);
        }

        int count = 0;
        Arrays.sort(nameList);
        List<String> resList = new ArrayList<>();

        for (String name : nameList) {
            if (hear.containsKey(name) && see.containsKey(name)) {
                count++;
                resList.add(name);
            }
        }

        bw.write(count + "\n");

        for (String name : resList) {
            bw.write(name + "\n");
        }

        bw.flush();
        bw.close();
    }
}
