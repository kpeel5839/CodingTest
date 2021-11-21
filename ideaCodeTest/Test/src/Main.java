public class Main{
    public static void main(String[] args){
        int dir = 1;
        dir = --dir < 0 ? 3 : 0;
        System.out.println(dir);
    }
}
