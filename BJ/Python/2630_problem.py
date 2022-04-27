n = int(input())
paper_list = [list(map(int, input().split())) for _ in range(n)]
blue = 0
white = 0
judge = True
def dfs(number , y , x):
    global blue
    global white

    start_number = paper_list[y][x]
    if number == 1:
        if start_number == 1:
            blue += 1
        else:
            white += 1
        return

    judge = True

    for i in range(y, y + number):
        for j in range(x, x + number):
            if start_number != paper_list[i][j]:
                judge = False
    
    if judge == True:
        if start_number == 1:
            blue += 1
        else:
            white += 1
        return
    else:
        number = number // 2
        dfs(number , y, x)
        dfs(number , y , x + number)
        dfs(number , y + number, x)
        dfs(number , y + number , x + number)

dfs(n , 0 , 0)
print(white)
print(blue)