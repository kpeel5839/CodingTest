import java.util.*;
import java.io.*;

// 1406 : 에디터

/**
 * Example
 * abc
 * 9
 * L
 * L
 * L
 * L
 * L
 * P x
 * L
 * B
 * P y
 */
public class Main {
    static class Node {
        char v;
        Node n;
        Node p;

        Node(char v) {
            this.v = v;
            this.n = null;
            this.p = null;
        }

        void insert(char c) {
            Node node = new Node(c);
            Node pNode = this.p;

            node.n = this;
            node.p = pNode;
            this.p = node;

            if (pNode != null) {
                pNode.n = node;
            }
        }

        void delete() {
            Node pNode = this.p;
            Node ppNode = null;

            if (pNode != null) {
                ppNode = pNode.p;
                this.p = ppNode;
            }

            if (ppNode != null) {
                ppNode.n = this;
            }
        }

        Node pre() {
            if (this.p != null) {
                return this.p;
            }

            return this;
        }

        Node next() {
            if (this.n != null) {
                return this.n;
            }

            return this;
        }
    }
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1406_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String s = br.readLine();
        Node c = new Node(s.charAt(0));

        for (int i = 1; i < s.length(); i++) {
            c.n = new Node(s.charAt(i));
            c.n.p = c;
            c = c.next();
        }

        c.n = new Node('-');
        c.n.p = c;
        c = c.next();

        int n = Integer.parseInt(br.readLine());
        for (int i = 0; i < n; i++) {
            String ss = br.readLine();

            if (ss.charAt(0) == 'L') {
                c = c.pre();
            } else if (ss.charAt(0) == 'D') {
                c = c.next();
            } else if (ss.charAt(0) == 'B') {
                c.delete();
            } else {
                char cc = ss.charAt(2);
                c.insert(cc);
            }
        }

        while (c.p != null) {
            c = c.pre();
        }

        StringBuilder sb = new StringBuilder();

        while (c.n != null) {
            sb.append(c.v);
            c = c.next();
        }

        System.out.println(sb);
    }
}