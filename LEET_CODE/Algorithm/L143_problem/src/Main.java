import java.util.*;
import java.io.*;

class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(input.readLine() , ",");

        ListNode head = new ListNode(Integer.parseInt(st.nextToken()));
        ListNode cur = head;
        while(st.hasMoreElements()){
            cur.next = new ListNode(Integer.parseInt(st.nextToken()));
            cur = cur.next;
        }

        reorderList(head);
    }
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    public static void reorderList(ListNode head) {
        Stack<ListNode> listStack = new Stack<>();
        int size = 0;
        ListNode cur = head;
        while(true){
            size++;
            listStack.push(cur);
            if(cur.next == null){
                break;
            }
            cur = cur.next;
        }
        int index = 0;
        cur = head;
        while(true){
            if(index == (size / 2)){
                cur.next = null;
                break;
            }
            ListNode temp = cur.next;
            cur.next = listStack.pop();
            cur = cur.next;
            cur.next = temp;
            cur = cur.next;
            index++;
        }
        cur = head;
        System.out.print("[");
        while(true){
            System.out.print(cur.val);
            if(cur.next == null){
                System.out.print("]");
                break;
            }
            System.out.print(",");
            cur = cur.next;
        }
    }
}