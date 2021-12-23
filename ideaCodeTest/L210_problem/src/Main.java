import java.util.*;
import java.io.*;

//210 : Course Schedule II
/*

- 전제 조건
1. 수강해야 하는 총 과목 개수를 주어짐
2. 그리고서 이제 수강해야 과목 쌍이 주어짐
3. [Ai , Bi] 쌍이 주어지면 무조건 Bi 를 먼저 하고 Ai를 수강할 수 있음
4. 이렇게 하였을 때 모든 과목을 들을 수 있는 유효한 수강 순서를 반환하면 됨 int[] array
5. 만약 유효한 순서가 없을 경우에는 empty array 반환하면 된다.
6. numCourses : 들어야 할 강의 개수
7. prerequisites : 강의 쌍

- 설계
1. 가정 자체를 numCourses 보다 작은 숫자의 강의들만 존재한다고 가정하자.
2. 가장 먼저 시작해야 할 놈은 0번째에 존재하지 않는 강의다.
3. 첫번 째 numCourses 1일 경우에는 아무런 쌍이 주어지지 않는다. 그러면 그냥 0을 반환하면 됌 int[]{0};
4. 가장 작은 테스트 케이스를 실험해보자 numCourses : 4 , 강의 : 0 , 1 , 2 , 3 존재 , prerequisites = [[1,0],[2,0],[3,1],[3,2]] (Tc)
5. 여기서 일단 첫번째에 없는 놈은 0뿐이다 시작을 0으로 할 수밖에 없는 상황이다. [1 , 0 : true] [2, 0 : true] , [3 , 1] , [3 , 2]
6. 일단 numCourses의 배열을 만들고 해당 index는 해당 숫자를 의미하도록 한다.
7. 그리고 기본적으로 0으로 초기화 되어있는 것들을 안되는 것들은 -1로 만든다.
8. 그리고서 -1이 아닌 것들을 실행시켜서 HashMap -> key : number value : true 로 만든다.
9. 그리고서 다음에 실행할 때에도 첫번째에 있는 것들은 제외 시키되 2번째 것을 항상 확인하면서 2번째에 있는 숫자가 true 면 제외시키지 않는다. 근데 그 true인 숫자는 -1시킨다.
10. 그렇게 계속 반복해서 실행시킨다. 언제까지? List.size() == numCourses 가 될때까지
11. 만일 근데 검사했을 때 possible을 모든 요소가 -1이다? 그러면 빈 배열을 반환한다.
12. 그리고 항상 한번 진행했을 때에는 tempResult.size() 를 검사해주고 , 그리고 possible 배열을 전부다 0으로 초기화 시켜주면 될 듯
13. 그리고서 일단 HashMap을 loof를 돌면서 이미 수강한 강의는 -1을 넣어준다.
*/
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int numCourses = Integer.parseInt(input.readLine());
        st = new StringTokenizer(input.readLine() , ",");
        int[][] prerequisites = new int[st.countTokens() / 2][2];
        for(int i = 0; i < prerequisites.length; i++){
            prerequisites[i][0] = Integer.parseInt(st.nextToken());
            prerequisites[i][1] = Integer.parseInt(st.nextToken());
        }
        System.out.println(numCourses);
        for(int i = 0; i < prerequisites.length; i++){
            System.out.println(Arrays.toString(prerequisites[i]));
        }
        findOrder(numCourses , prerequisites);
    }
    public static int[] findOrder(int numCourses , int[][] prerequisites){
        HashMap<Integer, Boolean> judge = new HashMap<>();
        HashSet<Integer> sizeChecking = new HashSet<>();
        for(int i = 0; i < prerequisites.length; i++){
            sizeChecking.add(prerequisites[i][0]);
            sizeChecking.add(prerequisites[i][1]);
        }
        int[] possible = new int[numCourses];
        int[] result = new int[numCourses];
        for(Integer number : sizeChecking){
            possible[number] = -1;
        }
        List<Integer> tempResult = new ArrayList<>();
        for(int i = 0; i < possible.length; i++){
            if(possible[i] != -1){
                tempResult.add(i);
                judge.put(i , true);
            }
        }
        if(prerequisites.length == 0){
            int index = 0;
            for(Integer number : tempResult){
                result[index++] = number;
            }
            return result;
        }
        boolean noE = true;
        while(true){
            for(int i = 0; i < possible.length; i++){
                possible[i] = 0;
            }
            if(tempResult.size() == numCourses){
                break;
            }
            Iterator it = judge.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry entry = (Map.Entry)it.next();
                possible[(int)entry.getKey()] = -1;
            }
            noE = true;
            for(int i = 0; i < prerequisites.length; i++){
                /*
                1. 첫번째 요소가 true 인지는 상관 없다 그냥 첫번째 요소로 나오면 2번째꺼 true인지 확인
                2. 두번째 요소가 true 이면 그거는 -1 시켜주고 첫번째 요소는 -1을 넣어주지 않는다.
                3. 그니까 첫번째 요소로 있다고 해서 바로 제하는 게 아니라 두번째 요소를 먼저 검색해봐야 한다는 것
                4. 그냥 containsKey로 확인하면 될 듯
                5. 둘다 있을 경우에는? 그러면 짜피 뒤에서 제외시켜지니깐 상관 없을 듯
                 */
                if(judge.containsKey(prerequisites[i][1])){
                    possible[prerequisites[i][1]] = -1;
                }
                else{
                    possible[prerequisites[i][0]] = -1;
                }
            }
            for(int i = 0; i < possible.length; i++){
                if(possible[i] != -1){
                    judge.put(i , true);
                    noE = false;
                    tempResult.add(i);
                }
            }
            if(noE){
                break;
            }
        }
        int index = 0;
        for(Integer number : tempResult){
            result[index++] = number;
        }
        if(noE){
            return new int[]{};
        }
        else{
            return result;
        }
    }
}
