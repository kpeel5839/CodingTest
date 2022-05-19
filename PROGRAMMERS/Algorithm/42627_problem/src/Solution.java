import java.util.*;
import java.io.*;
import java.util.function.Function;

// 디스크 컨트롤러

/**
 * -- 해결 방법
 * 일단, 지금 현재 실행 가능한 것들, 그것들에서 가장 짧은 것을 선택하는 것이 전체 평균적인 요청 해결 평균시간을 줄이는데 엄청나게 효과적이다.
 * 왜냐하면, 기다리는 애들이 많을 수록, 평균치는 기하급수적으로 늘게 된다. 하지만, 짧은 요청부터 처리하면, 기다리는 애들이 확연히 빠르게 줄어든다.
 * 그래서, shortest jobs first 방법을 사용하는 것이다.
 *
 * 그 이후에는 그냥, nextStartTime 이라는 변수를 두어서
 * 현재, 시작 가능한 시각을 명시하였음
 * 그래서, 처음에 queue 가 empty 할 때에는, 일단 내가 정렬을 해놨기 때문에, 가장 앞에것은 무조건 실행시킬 리스트에 들어가야 한다.
 * 만일 이거랑 시작시간이 같은 것이 없다면?
 * 무조건 적으로 이놈을 실행시켜야 한다.
 *
 * 근데, 만약에 같은 애들이 많다면?
 * 그러면 개내들도 다 넣어줘야 한다.
 * 그래서 while 문으로 구성을 해서, queue 가 empty 할 때에는
 * 처음 시작이니까, nextStartTime 에다가 시작시간을 넣어주고
 * continue 를 해서, 같은 애들을 다 넣을 수 있도록 하였다.
 *
 * 그 다음에는, queue 에는 이제 현재 실행 가능한 애들만 남아있으니까, queue.poll() 해서, 실행시간이 가장 짧은애를 선택하여서 실행하면 된다.
 * 그래서, 실행시킨다음에, nextStartTime 을 또 갱신을 해주어서, 이 시간안에 실행 가능한 애들을 탐색한다.
 *
 * 일단, queue 가 empty 할 때에는, nextStartTime 을 jobs[i][0]; continue; 으로 하면서, 시간이 같은 애들을 넣을 수 있도록 하였고,
 * 그 다음에, nextStartTime 보다 작거나 같은애들을 다 넣게하고 (이것은 이제 작업이 연달아서 겹쳐있는 경우에 이렇게 해야 함, 현재 작업 가능한 애들 다 넣어야 하니까)
 * 런타임 에러를 방지하기 위해서, queue 가 empty 할 때에는 작업이 끝난 뒤 한참 있다가 시작하는 것이니까
 * 다시 초기 세팅으로 돌아갈 수 있도록 하였음
 *
 * 근데 처음에는 이것을 모르고 queue.isEmpty() 일 떄를 처리 안해서 해맸었고,
 * while 조건에다가 조건들을 빈약하게 설정하여서도 많이 해맸음
 */
class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        int N = fun.apply(br.readLine());
        int[][] jobs = new int[N][2];

        for (int i = 0; i < N; i++) {
            String[] input = br.readLine().split(" ");
            jobs[i][0] = fun.apply(input[0]);
            jobs[i][1] = fun.apply(input[1]);
        }

        System.out.println(solution(jobs));
    }
    public static int solution(int[][] jobs) {
        /*
        암것도 안하고 있을 때, 요청 들어오면 그래도 함
        왜냐하면, 실제 하드디스크는 들어오는 요청을 전부다 미리 알고 있지 않으니까
        1 4, 3 4, 3 2, 4 5 이러한 입력이 들어왔다고 가정했을 떄,
        1 4 는 무조건 먼저 실행이 됨
        
        그 다음에 (3, 4), (3, 2), (4, 5) 가 실행될 수 있는 리스트들임
        
        아니 이렇게 말고
        이렇게 하면 될 것 같다.
        일단, 첫번째 값을 기준으로, 즉 시작 시간을 기준으로, 정렬을 진행해주고
        PriorityQueue 에다가 Jobs 를 넣어준다.
        근데, 이 때에는 Jobs execute time 을 기준으로 정렬을 해준다 (오름차순으로)
        
        근데, 이때 넣을 때, 이전에 실행한 애가 끝나는 시각을 기준으로 계속 넣어줌
        그런식으로 계속 반복하면 쉽게 해결 가능할 듯
        
        그러면 현재 것 하고, 가장 execute time 이 짧은 것을 실행시킬 수 있고 (그럼 당연히 앞의 요청이 일찍 끝나게 되니까 평균 시간이 줄어듦)
        이것도 그냥 생각이 가능한 것이 앞에 execute time 이 긴 애가 위치하면 기다리는 애들이 많아진다.
        이 때는 이후에 처리되야 할 요청들이 다 기다리고 있음
        근데, 먼저 먼저 빠른 것들 처리하면? 걔내들도 다 기다리긴 하지만, 기다리는 애들의 수가 빠른속도로 줄기 때문에
        무조건 shortest 한 애들만 고르는 것이 최고임
        
        int[0] = start, int[1] = execute time 으로 생각하고 진행하자.
        */
        int answer = 0;

        int div = jobs.length;
        Arrays.sort(jobs, (o1, o2) -> o1[0] - o2[0]); // 시작시간을 기준으로 정렬을 진행한다. (오름차순)
        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]); // 이번에는 실행시간을 기준으로 정렬한다. (오름차순))

        int nextStartTime = jobs[0][0];
        int jobsIndex = 0;

        // 모든 jobs 들을 다 한번씩은 봐야 하기 때문에, jobsIndex < div 무조건임
        while (jobsIndex < div || !queue.isEmpty()) { // 모든 jobs 들은 PriorityQueue 에 한번은 들어갔다가 나와야함, 그래서 queue 는 꼭 empty 할 때 끝날 수 있음
            while ((jobsIndex < div) && (nextStartTime >= jobs[jobsIndex][0])) { // nextStartTime 보다 같거나 작은 애들을 다 집어넣는다.
                queue.add(jobs[jobsIndex++]); // 지금 실행 가능한 애들 다 집어넣기
            }

            if (queue.isEmpty()) { // 바로 연달아서 시작하는 것이 아닌, 이전 것 하고서, 시간이 조금 있다가 하는 경우는
                // 이러한 예외사항들이 발생할 수 있음, 그래서 다시 처음 시작하는 느낌으로 nextStartTime = jobs[jobsIndex][0]; 을 집어넣어준다.
                nextStartTime = jobs[jobsIndex][0];
                queue.add(jobs[jobsIndex++]);
                continue;
            }

            int[] nowJobs = queue.poll(); // 현재로서 실행 가능한 애들 다 들어가있음, 여기서 가장 실행시간 짧은 놈 고름
            answer += (nextStartTime - nowJobs[0]) + nowJobs[1]; // 요청시간부터, 종료시간까지의 시간을 구해서 더해줌
            nextStartTime += nowJobs[1]; // 다음 작업이 실행 가능한 시간을 명시
        }

        answer = answer / div; // 평균 구함
        return answer;
    }
}