import sys

sudoku = [list(map(int,sys.stdin.readline().split())) for _ in range(9)]
zero_list = [[i,j] for i in range(9) for j in range(9) if sudoku[i][j] == 0]
sorted = False
def dfs(index):
    global sorted
    if sorted == True:
        return
    if index == len(zero_list):
        sys.stdout.write("after sorted " +  '\n')
        sorted = True
        for i in range(9):
            for j in range(9):
                sys.stdout.write(str(sudoku[i][j]) + ' ')
            sys.stdout.write('\n')
    else:
        y,x = zero_list[index]
        number = promising(y,x)
        for i in number:
            sudoku[y][x] = i
            dfs(index + 1)
            sudoku[y][x] = 0
            
def promising(y,x):
    number_list = [1,2,3,4,5,6,7,8,9]
    for i in range(9):
        if sudoku[y][i] in number_list:
            number_list.remove(sudoku[y][i])
        if sudoku[i][x] in number_list:
            number_list.remove(sudoku[i][x])
    startY = y // 3
    startX = x // 3
    startX *= 3
    startY *= 3
    for i in range(startY, startY + 3):
        for j in range(startX, startX + 3):
            if sudoku[i][j] in number_list:
                number_list.remove(sudoku[i][j])
    return number_list
            
dfs(0)