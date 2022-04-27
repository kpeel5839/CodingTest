def merge_sort(p_list, f_idx, l_idx):
    if f_idx == l_idx:
        return
    m_idx = (f_idx + l_idx) // 2
    merge_sort(p_list, f_idx, m_idx)
    merge_sort(p_list, m_idx + 1, l_idx)
    merge(p_list, f_idx, l_idx, m_idx)

def merge(p_list, f_idx, l_idx, m_idx):
    left_idx = f_idx
    right_idx = m_idx + 1
    tmp = [["" for _ in range(2)] for _ in range(l_idx - f_idx + 1)]
    tmp_idx = 0
    while left_idx < m_idx + 1 and right_idx < l_idx + 1:
        if int(p_list[left_idx][0]) > int(p_list[right_idx][0]):
            tmp[tmp_idx][0] = p_list[right_idx][0]
            tmp[tmp_idx][1] = p_list[right_idx][1]
            right_idx += 1
            tmp_idx += 1
        else:
            tmp[tmp_idx][0] = p_list[left_idx][0]
            tmp[tmp_idx][1] = p_list[left_idx][1]
            left_idx += 1
            tmp_idx += 1  
    while left_idx < m_idx + 1:
        tmp[tmp_idx][0] = p_list[left_idx][0]
        tmp[tmp_idx][1] = p_list[left_idx][1]
        tmp_idx += 1
        left_idx += 1
    while right_idx < l_idx + 1:
        tmp[tmp_idx][0] = p_list[right_idx][0]
        tmp[tmp_idx][1] = p_list[right_idx][1]
        tmp_idx += 1
        right_idx += 1

    tmp_idx = 0
    for i in range(f_idx, l_idx + 1):
        p_list[i][0] = tmp[tmp_idx][0]
        p_list[i][1] = tmp[tmp_idx][1]
        tmp_idx += 1

size = int(input())
p_list = [["" for _ in range(2)] for _ in range(size)]
for i in range(size):
    p_list[i][0] , p_list[i][1] = input().split() #나이를 문자열로 받음 이건 따로 처리 해주어야 할 듯
merge_sort(p_list, 0 , size - 1)
for i in range(size):
    print(p_list[i][0], p_list[i][1])