import sys
number_list_size = int(sys.stdin.readline())
number_list = list(map(int,sys.stdin.readline().split()))
number_list.sort()
def find_number(number, f_idx, l_idx):
    global number_list
    if f_idx == l_idx:
        if number_list[f_idx] == number:
            return 1
        else:
            return 0
    m_idx = (f_idx + l_idx) // 2
    if number_list[m_idx] == number:
        return 1
    elif number_list[m_idx] > number:
        return find_number(number, f_idx , m_idx - 1)
    elif number_list[m_idx] < number:
        return find_number(number, m_idx + 1, l_idx)
find_number_list_size = int(sys.stdin.readline())
find_number_list = list(map(int,sys.stdin.readline().split()))
for i in range(find_number_list_size):
    sys.stdout.write(str(find_number(find_number_list[i] , 0 , number_list_size - 1)) + '\n')
