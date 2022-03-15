n , row , col= map(int , input().split())
count = -1
def dfs(number , y,  x):
    global row
    global col
    global count

    if number == 2:
        if y <= row <= y + 1 and x <= col <= x + 1:
            if y == row and x == col:  
                count += 1
            elif y == row and x + 1 == col:
                count += 2
            elif y + 1 == row and x == col:
                count += 3
            elif y + 1 == row and x + 1 == col:
                count += 4
    else:
        number = number // 2
        if y <= row < y + number and x <= col < x + number:
            count += number * number * 0
            dfs(number , y , x)
        elif y <= row < y + number and x + number <= col < x + (number * 2):
            count += number * number * 1
            dfs(number , y , x + number)
        elif y + number <= row < y + (number*2) and x <= col < x + number:
            count += number * number * 2
            dfs(number , y + number , x)
        elif y + number <= row < y + (number * 2) and x + number <= col < x + (number * 2): 
            count += number * number * 3
            dfs(number , y + number , x + number)

dfs(2 ** n , 0 , 0)
print(count)