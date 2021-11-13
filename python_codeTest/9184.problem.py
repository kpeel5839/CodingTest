wa = [[[0 for _ in range(110)] for _ in range(110)] for _ in range(110)]
def w(a, b, c):
    if wa[a+50][b+50][c+50] != 0:
        return wa[a+50][b+50][c+50]

    if a <= 0 or b <= 0 or c <= 0:
        wa[a+50][b+50][c+50] = 1
        return wa[a+50][b+50][c+50]

    if a > 20 or b > 20 or c > 20:
        wa[70][70][70] = w(20,20,20)
        return wa[70][70][70]

    if a < b and b < c:
        wa[a + 50][b + 50][c + 50] = w(a, b, c-1) + w(a, b - 1, c - 1) - w(a, b - 1, c)
        return wa[a + 50][b + 50][c + 50]

    else: 
        wa[a+50][b+50][c+50] = w(a-1, b, c) + w(a-1, b-1, c) + w(a-1, b, c-1) - w(a-1, b-1, c-1)
        return wa[a+50][b+50][c+50]
    
num_list = [[0 for _ in range(3)] for _ in range(100000)]

index = 0
while True:
    num_list[index][0:3] = map(int,input().split())
    if num_list[index][0] == -1 and num_list[index][1] == -1 and num_list[index][2] == -1:
        break
    index += 1

#index 는 실제로 하나 더 낮음

for i in range(index):
    a = num_list[i][0]
    b = num_list[i][1]
    c = num_list[i][2]

    print("w({0}, {1}, {2}) = {3}".format(a, b, c ,w(a,b,c)))