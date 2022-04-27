import sys #10000000 부터 0임 그래서 실제 수보다 10000000 더하면 됨
number_list_size = int(sys.stdin.readline())
number_list = list(map(int,sys.stdin.readline().split()))
find_number_list_size = int(sys.stdin.readline())
find_number_list = list(map(int,sys.stdin.readline().split()))
add_number = 10000000
array_list = [0 for _ in range(20000001)]
for i in range(number_list_size):
    array_list[number_list[i] + add_number] += 1
for i in range(find_number_list_size):
    sys.stdout.write(str(array_list[find_number_list[i] + add_number]) + ' ')
