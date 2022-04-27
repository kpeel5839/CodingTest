import sys
number_list_size = int(sys.stdin.readline())
number_list = list(map(int,sys.stdin.readline().split()))
find_number_list_size = int(sys.stdin.readline())
find_number_list = list(map(int,sys.stdin.readline().split()))
result_list = [0 for _ in range(find_number_list_size)]
result_index = 0
def find_number(number):
    global number_list

    find_number_count = 0
    f_idx = 0
    l_idx = number_list_size - 1
    find_idx = -1
    while f_idx <= l_idx:
        m_idx = (f_idx + l_idx) // 2
        if number_list[m_idx] > number:
            l_idx = m_idx - 1
        elif number_list[m_idx] < number:
            f_idx = m_idx + 1
        else:
            find_idx = m_idx
            find_number_count += 1
            break
    if find_idx == -1:
        return 0
    left_move_idx = find_idx
    right_move_idx = find_idx
    while left_move_idx != 0:
        if number_list[left_move_idx - 1] == number_list[find_idx]:
            left_move_idx -= 1
            find_number_count += 1
            continue
        else:
            break
    while right_move_idx != len(number_list) - 1:
        if number_list[right_move_idx + 1] == number_list[find_idx]:
            right_move_idx += 1
            find_number_count += 1
            continue
        else:
            break
    return find_number_count
count = 0
number_list.sort()
for i in range(find_number_list_size):
    count = find_number(find_number_list[i])
    sys.stdout.write(str(count) + ' ')
