import sys
def min(i , j):
    if i > j:
        return j
    else:
        return i
def count_two(i):
    count = 0
    while i != 0:
        i = i // 2
        count += i
    return count

def count_five(i):
    count = 0
    while i != 0:
        i = i // 5
        count += i
    return count
n , m = map(int, sys.stdin.readline().split())
sys.stdout.write(str(min(count_two(n) - count_two(n - m) - count_two(m), count_five(n) - count_five(n - m) - count_five(m))))