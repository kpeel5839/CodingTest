import java.io.*;

public class Main {
    public static String st;
    public static int[] visited;
    public static void main(String[] args) throws IOException{ //Comparable 시험해보기 객체 정렬 두가지로도
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String s = input.readLine();
        String result = decodeString(s);
        System.out.println(result);
    }
    public static String decodeString(String s) {
        st = s;
        visited = new int[s.length()];
        String result = decode(0);
        return result;
    }
    public static String decode(int index){
        //일단 숫자를 만나면 다다음 칸으로 현재 i + 1;로 넘겨야함
        //그냥 알파벳을 만나면 자기꺼에다가 넣는다.
        //그 과정은 ] 를 만날 때까지
        String result = "";
        String number = "";
        String subString = "";
        for(int i = index; i < st.length(); i++){
            if(visited[i] == 1){
                continue;
            }
            visited[i] = 1;
            if(st.charAt(i) == ']'){
                break;
            }
            if(st.charAt(i) == '['){
                subString = decode(i + 1);
                for(int j = 0; j < Integer.parseInt(number); j++){
                    result += subString;
                }
                number = "";
                subString = "";
                continue;
            }
            if((int)'a' <= (int)st.charAt(i) && (int)st.charAt(i) <= 'z'){
                result += Character.toString(st.charAt(i));
            }
            else if(!(((int)'a' <= (int)st.charAt(i) && (int)st.charAt(i) <= 'z'))){
                number += st.charAt(i);
            }
        }
        return result;
    }
}
