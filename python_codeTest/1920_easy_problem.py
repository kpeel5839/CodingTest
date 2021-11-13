import sys
number_list_size = int(sys.stdin.readline())
number_list = list(map(int,sys.stdin.readline().split()))
def find_number(number, f_idx, l_idx):
    global number_list
    while f_idx <= l_idx:
        m_idx = (f_idx + l_idx) // 2
        if number_list[m_idx] == number:
            return 1
        elif number_list[m_idx] > number:
            l_idx = m_idx - 1
        else:
            f_idx = m_idx + 1
    return 0
find_number_list_size = int(sys.stdin.readline())
find_number_list = list(map(int,sys.stdin.readline().split()))
number_list.sort()
for i in range(find_number_list_size):
    sys.stdout.write(str(find_number(find_number_list[i] , 0 , number_list_size - 1)) + '\n')