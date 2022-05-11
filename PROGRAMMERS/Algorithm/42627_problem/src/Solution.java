import java.util.*;
import java.io.*;
import java.util.function.Function;

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

        while (jobsIndex < div || !queue.isEmpty()) {
            while ((jobsIndex < div) && (nextStartTime >= jobs[jobsIndex][0])) { // nextStartTime 보다 같거나 작은 애들을 다 집어넣는다.
                queue.add(jobs[jobsIndex++]);
            }

            if (queue.isEmpty()) { // 바로 연달아서 시작하는 것이 아닌, 이전 것 하고서, 시간이 조금 있다가 하는 경우는
                // 이러한 예외사항들이 발생할 수 있음, 그래서 다시 처음 시작하는 느낌으로 nextStartTime = jobs[jobsIndex][0]; 을 집어넣어준다.
                nextStartTime = jobs[jobsIndex][0];
                queue.add(jobs[jobsIndex++]);
                continue;
            }

            int[] nowJobs = queue.poll();
            answer += (nextStartTime - nowJobs[0]) + nowJobs[1];
            nextStartTime += nowJobs[1];
        }

        answer = answer / div;
        return answer;
    }
}