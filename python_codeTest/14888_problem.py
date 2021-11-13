#안되는 경우를 생각해보자 
#일단 my_team 이라고 가정하였을 때 같은 선수가 또 들어가서는 안됨.. 선수 리스트를 만들까? [1,2,3,4] 이런식으로 사이즈가 4이면 0,1,2,3이 있짢아
#잘했다 재연아 이렇게 풀기만 하면 되는 거야 몇 시간 걸리는 거 상관 없어
min = 1000000001
size = int(input())
team_list = [list(map(int,input().split())) for _ in range(size)]
friend_team = []
#my_team을 1차원 리스트로 만들자
def dfs(my_team):
    global min
    global friend_team

    if len(my_team) > 1 and my_team[len(my_team) - 1] < my_team[len(my_team) - 2]: #오름 차순이 아니라면 재귀 끝냄
            return

    if len(my_team) == size / 2: #my team 의 사이즈가 사이즈 / 2 라면 끝난 것임
        my_powers = my_power(my_team) #각각의 점수들 계산
        enemy_powers = enemy_power(my_team) 
        if min > abs(enemy_powers - my_powers): #그래서 차이를 계산하는 것
            min = abs(enemy_powers - my_powers)
            friend_team = my_team.copy()
            
    else: 
        for i in range(size):
            if i not in my_team: #내 마이팀 리스트에 해당 선수가 없으면 그냥 들어가도 됨
                my_team.append(i) #이게 재귀가 끝나서 다시 돌아왔을 떄 영향을 받지 않도록만 하면 됨
                dfs(my_team) #지금 내팀 넘어감
                my_team.pop() #이거 이제 dfs 끝나면 다시 처음부터 시작해야하니깐 pop 해주고 다시 처음부터 넣음

def my_power(my_team): #우리팀 스코어 반환
    tmp_sum = 0
    for i in range(size):
        for j in range(size):
            if i == j: #i == j 는 그냥 지나감
                continue
            if i not in my_team: # i 선수가 우리팀에 없으면 지나감
                continue
            if j not in my_team: # j 선수가 우리팀에 없으면 지나감
                continue
            tmp_sum += team_list[i][j] #우리팀 애들만 다 더해줌 짜피 j i 각각 똑같은 값 한번 씩 돌기 때문에 한번만 써준 것
    return tmp_sum
def enemy_power(enemy_team):
    tmp_sum = 0
    for i in range(size): #이것은 그냥 위에서의 반대
        for j in range(size):
            if i == j:
                continue
            if i in enemy_team:
                continue
            if j in enemy_team:
                continue
            tmp_sum += team_list[i][j]
    return tmp_sum

dfs([]) #우리팀이 없는 상태에서 넘기기
print(min) #가장 작은 값을 출력 여기까지가 정답임 이 다음부터는 그냥 최고의 팀을 뽑은 것임 그냥 내 맘
enemy_team = []
for i in range(size):
    if i not in friend_team:
        enemy_team.append(i + 1)
for i in range(len(friend_team)):
    friend_team[i] += 1
print(friend_team)
print(enemy_team)