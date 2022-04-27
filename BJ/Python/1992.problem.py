size = int(input())
tmp_video = [input() for _ in range(size)]
video = [[0 for _ in range(size)] for _ in range(size)]
for i in range(size):
    for j in range(size):
        video[i][j] = int(tmp_video[i][j]) #사전 작업 완료
judge = True
def dfs(number , y ,x):
    start_number = video[y][x]
    if number == 1:
        return start_number
    judge = True
    for i in range(y,y + number):
        for j in range(x, x + number):
            if start_number != video[i][j]:
                judge = False
                break
        if judge == False:
            break
    if judge == True:
        return start_number
    number = number // 2
    print("(", end="")
    first = dfs(number , y, x)
    if first != None:
        print(first, end = "")
    second = dfs(number , y, x + number)
    if second != None:
        print(second, end = "")
    third = dfs(number , y+number, x)
    if third != None:
        print(third, end = "")
    four = dfs(number , y+number, x+number)
    if four != None:
        print(four, end="")
    print(")" , end="")
fucking_number = video[i][j]
fucking_judge = True
for i in range(size):
    for j in range(size):
        if fucking_number != video[i][j]:
            fucking_judge = False
if fucking_judge == False:
    dfs(size, 0 , 0)
else:
    print(fucking_number)