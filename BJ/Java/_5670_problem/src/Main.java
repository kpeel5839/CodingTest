import java.util.*;
import java.io.*;

// 5670 : 휴대폰 자판

/**
 * Example
 * 4
 * hello
 * hell
 * heaven
 * goodbye
 * 3
 * hi
 * he
 * h
 * 7
 * structure
 * structures
 * ride
 * riders
 * stress
 * solstice
 * ridiculous
 */
public class Main {
    public static double sum;

    public static class Trie {
        int count;
        Trie[] next;

        Trie() {
            count = 0;
            next = new Trie['z' - 'a' + 1];
        }
    }

    public static void put(Trie root, String word) {
        Trie current = root;

        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';

            if (current.next[index] == null) { // 새로 만들음
                current.next[index] = new Trie();
            }

            current = current.next[index];
            current.count++;
        }
    }

    public static void dfs(Trie current, int previous) {
        sum += current.count;

        if (previous == current.count) {
            sum -= current.count;
        }

        for (int i = 0; i < 'z' - 'a' + 1; i++) {
            if (current.next[i] != null) {
                dfs(current.next[i], current.count);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_5670_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        while (true) {
            String line = br.readLine();

            if (line == null) {
                break;
            }

            int N = Integer.parseInt(line);
            Trie root = new Trie();
            sum = 0;

            for (int i = 0; i < N; i++) {
                put(root, br.readLine());
            }

            for (int i = 0; i < 'z' - 'a' + 1; i++) {
                if (root.next[i] != null) {
                    dfs(root.next[i], -1);
                }
            }

            sum /= N;
            sum = Math.round(sum * 100) / 100d;
            bw.write(String.format("%.2f", sum) + "\n");
        }

        bw.flush();
        bw.close();
    }
}