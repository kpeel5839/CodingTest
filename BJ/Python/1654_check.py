import sys
size , digit_number = map(int,sys.stdin.readline().split())
line_list = [0 for _ in range(size)]
count = 0
for i in range(size):
    line_list[i] = int(sys.stdin.readline())
for i in range(size):
    count += line_list[i] // digit_number
sys.stdout.write(str(count) + '\n')