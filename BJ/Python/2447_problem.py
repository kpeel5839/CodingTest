star_point = [[" " for _ in range(2187)] for _ in range(2187)]
def starPrint(number, x , y):
    if number == 3:
        star_point[y][x] = "*"
        star_point[y][x+1] = "*"
        star_point[y][x+2] = "*"
        star_point[y+1][x] = "*"
        star_point[y+1][x+1] = " "
        star_point[y+1][x+2] = "*"
        star_point[y+2][x] = "*"
        star_point[y+2][x+1] = "*"
        star_point[y+2][x+2] = "*"
        return
    
    starPrint(number//3, x,y)
    starPrint(number//3, x + (number//3),y)
    starPrint(number//3, x + (number//3)*2,y)
    starPrint(number//3, x,y + (number//3))
    starPrint(number//3, x + (number//3)*2,y + (number//3))
    starPrint(number//3, x,y + (number//3)*2)
    starPrint(number//3, x + (number//3),y + (number//3)*2)
    starPrint(number//3, x + (number//3)*2,y + (number//3)*2)

number = int(input())
starPrint(number,0,0)
for i in range(number):
    for j in range(number):
        print(star_point[i][j],end = "")
    print(end = "\n")
