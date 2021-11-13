import sys
number = int(sys.stdin.readline())
star_list = [[" " for _ in range(number*2)] for _ in range(number)]
def starPrint(number, x, y):
    if number == 3:
        star_list[y][x] = "*"
        star_list[y+1][x-1] = "*"
        star_list[y+1][x+1] = "*"
        star_list[y+2][x-2] = "*"
        star_list[y+2][x-1] = "*"
        star_list[y+2][x] = "*"
        star_list[y+2][x+1] = "*"
        star_list[y+2][x+2] = "*"
        return
    number = number // 2
    starPrint(number, x , y)
    starPrint(number, x - number, y + number)
    starPrint(number, x + number, y + number)
st = ['' for _ in range(number)]
starPrint(number,number-1,0)
for i in range(number):
    st[i] = star_list[i][0]
    for j in range(1,number*2-1):
        st[i] += star_list[i][j]
    sys.stdout.write(st[i] + '\n')