from multiprocessing.dummy import Value
import sys

'''
-- 틀 설계
caculate() 함수를 선언 numberList , sequence를 주면 계산
dfs() sequence를 만드는 과정
'''

n = int(input())
sequence = [0 for _ in range(n - 1)]

# def calculate():
#     global sequence , numberList , max , min
#     '''
#     리스트의 첫번째 값을 value에다가 넣고
#     sequence 에 있는 0 , 1 , 2 , 3 대로 연산을 하는데
#     0 = 더하기 , 1 = 뺄셈 , 2 = 곱셉 , 3 = 나눗셈이다
#     '''
#     value = numberList[0]
#     for i in range(n - 1):
#         if sequence[i] == 0:
#             value += numberList[i + 1];
#         elif sequence[i] == 1:
#             value -= numberList[i + 1];    
#         elif sequence[i] == 2:
#             value *= numberList[i + 1];
#         else:
#             value = int(value / numberList[i + 1]);
        
#     if value < min:
#         min = value
#     if value > max:
#         max = value

def dfs(depth, score):
    '''
    score들을 그때 그때 맞는 연산자로 계산해가면서 depth == n - 1까지 진행한다.
    연산 순서를 나타내는 sequence와 해당 sequence에 들어가야 할 연산자들의 개수들을 정리한
    oper 배열을 global로 선언한다.
    depth 가 sequence의 크기인 n - 1까지 도달하면 재귀호출을 끝낸다.
    0 , 1 , 2 , 3 각각 덧셈 , 빼기 , 곱셈 , 나눗셈 중 하나를 사용하면 oper 배열에서 하나를 빼주고
    재귀호출을 진행해준다음에 재귀 호출이 끝나게 되면 다시 oper[i] -= 1을 해주었던 것을 oper[i] += 1 해줌으로써 원래의 상태로 되돌려놓는다.
    그러고서 depth == n - 1이 될 때 마다 min , max 를 비교해서 값을 변경해주면 종료
    '''
    global oper , sequence , n , min , max , numberList
    if depth == n - 1:    
        if min > score:
            min = score
        if max < score:
            max = score
        return
    for i in range(4):
        if oper[i] != 0:
            oper[i] -= 1
            if(i == 0):
                dfs(depth + 1 , score + numberList[depth + 1])
            elif(i == 1):
                dfs(depth + 1 , score - numberList[depth + 1])
            elif(i == 2):
                dfs(depth + 1 , score * numberList[depth + 1])
            else:        
                dfs(depth + 1 , int(score / numberList[depth + 1]))
            oper[i] += 1

numberList = list(map(int , input().split()))
oper = list(map(int , input().split()))
max = -2100000000
min = 2100000000



dfs(0 , numberList[0])

print(str(max) + "\n" + str(min))