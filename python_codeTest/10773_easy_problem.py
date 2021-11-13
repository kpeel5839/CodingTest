import sys

size = int(sys.stdin.readline())
num_list = []
for i in range(size):
    number = int(sys.stdin.readline())
    if number == 0:
        num_list.pop()

    if number != 0:
        num_list.append(number)
sum = 0
for i in range(len(num_list)):
    sum += num_list[i]
sys.stdout.write(str(sum))