size = int(input())
zero = 0
one = 0
minus = 0
paper_list = [list(map(int,input().split())) for _ in range(size)]
judge = True
def paper(size , y, x):
    global minus
    global zero
    global one
    
    number = paper_list[y][x]

    if size == 1:
        if number == 0:
            zero += 1
        elif number == 1:
            one += 1
        elif number == -1:
            minus += 1
        return

    judge = True

    for i in range(y , y + size):
        for j in range(x , x + size):
            if number != paper_list[i][j]:
                judge = False   

    if judge == True:
        if number == 0:
            zero += 1
        elif number == 1:
            one += 1
        elif number == -1:
            minus += 1 
        return
    
    else:
        size = size // 3
        paper(size, y, x)
        paper(size, y, x + size)
        paper(size, y ,x + (size * 2))
        paper(size, y + size, x)
        paper(size, y + size, x + size)
        paper(size, y + size ,x + (size * 2))
        paper(size, y + (size * 2), x)
        paper(size, y+ (size * 2), x + size)
        paper(size, y + (size * 2),x + (size * 2))

paper(size ,0 , 0)
print(minus)
print(zero)
print(one)