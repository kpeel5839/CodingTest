import java.util.*;
import java.io.*;

// 4358 : 생태학
/*
-- 전체 설계
나무 그루가 주어지고 , 순서대로 , 종의 출력과 , 종이 차지하는 백분율과
종의 이름을 출력한다.
-- 틀 설계
Hash 에다가 저장하면서,
지금까지 나오지 않았던 나무들은 tree list에다가 저장하고
아니면 그냥 treeInfo 만 수정한다.
 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        HashMap<String , Integer> treeInfo = new HashMap<>();
        List<String> treeList = new ArrayList<>();
        double size = 0;

        while(true){
            String tree = input.readLine();
            if(tree == null || tree.isBlank()){
                break;
            }
            size++; // tree 에 정보가 들어오면 무조건 size 올라감
            if(!treeInfo.containsKey(tree)){ // 이미 키가 존재하지 않을 때 , 그니까 키가 없을 때
                treeInfo.put(tree , 1);
                treeList.add(tree);
            }else{
                treeInfo.put(tree , treeInfo.get(tree) + 1);
            }
        }

        Collections.sort(treeList);

        for(String tree : treeList){
            double value = treeInfo.get(tree);
            System.out.println(tree + " " + String.format("%.4f" , (double)Math.round((value / size) * 1000000) / 10000d));
        }
    }
}