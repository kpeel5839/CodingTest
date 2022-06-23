import java.util.*;
import java.io.*;

class Solution2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] user_id = br.readLine().split(" ");
        String[] banned_id = br.readLine().split(" ");

        System.out.println(solution(user_id, banned_id));
    }
    /*
    어떻게 해야 하지..
    생각을 더 해봐야 할 것 같다...
    시간을 두고 더 생각해보자.

    분명히 방법이 있을 것 같은데 뭔가 올랑 말랑하다.

    일단 매칭시켜서 각각의 List 를 가지고 있어보자.
    순서가 중요하지 않다 이게 엄청난 키워드인 것 같은데..

    지금 3개의 경우가 나온 것은
    ****** = abc123, frodoc 이 있고
    fr*d* = frodo, fradi
    *rodo = frodo, crodo 가 있다.

    여기서 어떻게 연산하게 되면 3이 나올 수 있을까?
    사실, 테케에서만 맞는 값을 내뱉게는 지금도 그냥 할 수 있다.
    하지만, 그게 문제가 아니다

    -- 해맸던 점
    진짜 개 바보같이 map put 을 user length 만큼해서 user 다 넣어야 하는데
    진짜 개 미친놈 같이 banned_id.length 에다가 해놓고 왜 자꾸 null pointer 뜨지 미치겠네 이러고 있었네
    진짜 개멍청이인가...

    진짜 저거 하고도 엄청 해맸었음
    그냥 그래서 이전에 visited 로서 관리했던 것을 아얘 Set 으로 책임 전가를 했다
    그래서 Solution1 을 보게 되면 훨씬 코드가 길다.
    당연히 저거는 생고생으로 구현한 것이긴 하다
    그래도 나름 좋은 아이디어였던 것 같은데, 내가 구현에서 실수했는지 절대로 정답처리를 받지 못했음

    그래서 결과적으로 HashSet<HashSet<String>> setList = new HashSet 과
    HashSet<String> set = new HashSet 으로 변경하면서 정답처리를 받았다.
    되게 신기하다 HashSet 은 HashSet 도 판별을 하는구나 싶었다.
    어떻게 판별하는 거지 심지어 엄청 빠르게

    분명히 내부의 요소들을 검사해야 하는 부분인데 어떻게 이렇게 빨리 검사할 수 있는지 궁금하긴 하다.
    쨋든 앞의 아이디어는 꽤나 괜찮았다고 생각이 들었지만 결론적으로 정답은 HashSet 을 써서 맞았다.

    HashSet 은 진짜 좋은 라이브러리 인 것 같다라는 생각이 든다. (Set 에다가 담아놨던 정보를 잃지 않기 위해서, clone 을 진행해서 HashSetList 안에 있는 요소들이 dfs 를 진행하면서 변경되지 않도록 신경썼음)
    */
    static int ans = 0;
    static HashSet<HashSet<String>> setList = new HashSet<>();
    static HashSet<String> list = new HashSet<>();
    static List<ArrayList<String>> banList = new ArrayList<>(); // banned list 이다.
    static HashMap<String, Integer> map = new HashMap<>(); // 유저아이디를 저장할 map
    static boolean[] inVisited; // bfs 를 진행할 때, 쓸데 없는 반복을 줄이기 위해서

    static void checkList(int now, String[] user_id, String banName) {
        // i 번째 banned 와 일치하는 것들을 List.get(now).add 해준다.
        for (int i = 0; i < user_id.length; i++) { // banned_id[now] 와 비교해준다.
            String userName = user_id[i];
            boolean equal = true;
            if (userName.length() == banName.length()) { // 글자라도 같아야지 초기 조건이라도 만족한다.
                for (int j = 0; j < userName.length(); j++) {
                    char userChar = userName.charAt(j);
                    char banChar = banName.charAt(j);

                    if (banChar == '*') { // 별이면 비교할 이유도 없음
                        continue;
                    }

                    if (userChar != banChar) { // 다른 경우 더 비교해봤자 의미가 없으니, break 해준다.
                        equal = false;
                        break;
                    }
                }
            } else { // 길이가 다른 경우는 볼 필요도 없이 equal 하지 않다
                equal = false;
            }

            if (equal) {
                banList.get(now).add(userName);
            }
        }
    }

    static void dfs(int depth, int end) {
        if (depth == end) {
            if (check()) { // check 하고 나가리
                ans++;
            }

            return;
        }

        for (int i = 0; i < banList.get(depth).size(); i++) { // 현재 depth 에서 선택할 수 유저를 결정
            String name = banList.get(depth).get(i);
            int index = map.get(name);

            if (!inVisited[index]) { // 아직 고르지 않은 이름이면
                inVisited[index] = true; // 방문 처리
                list.add(name); // 이름 추가
                dfs(depth + 1, end);
                inVisited[index] = false; // 방문 삭제
                list.remove(name); // 이름 삭제
            }
        }
    }

    static boolean check() {
        boolean equal = true; // 일치하지 않는 것이 하나라도 있으면 다른 것으로 취급 바로 false 로 변경한다.

        if (!setList.contains(list)) {
            setList.add(list);
            list = (HashSet) list.clone();
            equal = false;
        }

        return !equal;
    }

    public static int solution(String[] user_id, String[] banned_id) {
        inVisited = new boolean[user_id.length];

        for (int i = 0; i < banned_id.length; i++) {
            banList.add(new ArrayList<>());
        }

        for (int i = 0; i < user_id.length; i++) {
            map.put(user_id[i], i);
        }

        for (int i = 0; i < banned_id.length; i++) {
            checkList(i, user_id, banned_id[i]);
        }

        dfs(0, banned_id.length);

        return ans;
    }
}
