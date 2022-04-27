import sys
size = int(sys.stdin.readline())
measure_list = list(map(int, sys.stdin.readline().split()))
measure_list.sort()
result = 0 
if size == 1:
    result = measure_list[0] * measure_list[0]
else:
    result = measure_list[0] * measure_list[size - 1]
sys.stdout.write(str(result))

