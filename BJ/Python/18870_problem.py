import sys

def merge_sort(tmp_num_list, f_idx, l_idx):
    if f_idx == l_idx:
        return
    m_idx = (f_idx + l_idx) // 2
    merge_sort(tmp_num_list, f_idx, m_idx)
    merge_sort(tmp_num_list, m_idx + 1, l_idx)
    merge(tmp_num_list, f_idx, l_idx, m_idx)

def merge(tmp_num_list, f_idx, l_idx, m_idx):
    tmp = [0 for _ in range(l_idx - f_idx + 1)]
    tmp_index = 0
    left_idx = f_idx
    right_idx = m_idx + 1
    while left_idx < m_idx + 1 and right_idx < l_idx + 1:
        if tmp_num_list[left_idx] < tmp_num_list[right_idx]:
            tmp[tmp_index] = tmp_num_list[left_idx]
            tmp_index += 1
            left_idx += 1
        else:
            tmp[tmp_index] = tmp_num_list[right_idx]
            tmp_index += 1
            right_idx += 1
    while left_idx < m_idx + 1:
        tmp[tmp_index] = tmp_num_list[left_idx]
        tmp_index += 1
        left_idx +=1
    while right_idx < l_idx + 1:
        tmp[tmp_index] = tmp_num_list[right_idx]
        tmp_index += 1
        right_idx += 1
    tmp_index = 0
    for i in range(f_idx,l_idx + 1):
        tmp_num_list[i] = tmp[tmp_index]
        tmp_index += 1
        
size = int(input())
default_num_list = list(map(int, sys.stdin.readline().split()))
tmp_num_list = [0 for _ in range(size)]
count_index = 0
for i in range(size):
    tmp_num_list[i] = default_num_list[i]
merge_sort(tmp_num_list, 0 , size - 1)
sys.stdout.write(str(tmp_num_list) + " ")
for i in range(size):
    count_index = 0
    for j in range(size):
        if j == 0 and default_num_list[i] == tmp_num_list[j]:
            sys.stdout.write(str(j) + " ")
            break
        if tmp_num_list[j] == default_num_list[i]:
                sys.stdout.write(str(count_index) + " ")
                break
        if j != size - 1 and tmp_num_list[j+1] != tmp_num_list[j]:
            count_index += 1
            continue
        if j == size - 1 and tmp_num_list[j - 1] == tmp_num_list[j]:
            sys.stdout.write(str(count_index) + " ")