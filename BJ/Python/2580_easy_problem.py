sudoku = [list(map(int, input().split())) for _ in range(9)]
zero = [(i, j) for i in range(9) for j in range(9) if sudoku[i][j] == 0]
    
def is_promising(row, col):
    promising = [1,2,3,4,5,6,7,8,9]
    for k in range(9):
        if sudoku[row][k] in promising:
            promising.remove(sudoku[row][k])
        if sudoku[k][col] in promising:
            promising.remove(sudoku[k][col])
        
    y = row // 3
    x = col // 3
    y *= 3
    x *= 3

    for i in range(y, y+3):
        for j in range(x, x+3):
            if sudoku[i][j] in promising:
                promising.remove(sudoku[i][j])

    return promising    

exit = False        
def dfs(index):
    global exit

    if exit == True:
        return

    if index == len(zero):
        exit = True
        print("after sorted")
        for i in range(9):
            for j in range(9):
                print(sudoku[i][j] , end =" ")
            print()
    else:
        y,x = zero[index]
        promising = is_promising(y, x)

        for i in promising:
            sudoku[y][x] = i
            dfs(index + 1)
            sudoku[y][x] = 0
        
dfs(0)





