import sys
size, goal_number = map(int,sys.stdin.readline().split())
line_list = [0 for _ in range(size)]
for i in range(size):
    line_list[i] = int(sys.stdin.readline())
min = 3000000000
sum = 0
for i in range(size):
    if min > line_list[i]:
        min = line_list[i]
    sum += line_list[i]
digit_number = sum // goal_number
def find_number(f_number , l_number):
    global line_list
    global goal_number
    while f_number <= l_number:
        m_number = (f_number + l_number) // 2
        if m_number == 0:
            m_number += 1
        count = 0
        for i in range(size):
            count += line_list[i] // m_number
        if count >= goal_number:
            f_number = m_number + 1
        elif count < goal_number:
            l_number = m_number - 1
    sys.stdout.write(str(l_number) + '\n')
find_number(0, digit_number)