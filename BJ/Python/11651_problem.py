def merge_sort(point_list, f_idx , l_idx):
    if f_idx == l_idx:
        return
    m_idx = (f_idx+l_idx) // 2
    merge_sort(point_list,f_idx, m_idx)
    merge_sort(point_list,m_idx+1,l_idx)
    merge(point_list,f_idx,l_idx,m_idx)

def merge(point_list,f_idx,l_idx,m_idx):
    tmp = [[0 for _ in range(2)]for _ in range(l_idx - f_idx + 1)]
    left_idx = f_idx
    right_idx = m_idx + 1
    tmp_idx = 0
    while left_idx < (m_idx + 1) and right_idx < (l_idx + 1):
        if point_list[left_idx][1] <= point_list[right_idx][1]:
            if point_list[left_idx][1] == point_list[right_idx][1]:
                if point_list[left_idx][0] < point_list[right_idx][0]:
                    tmp[tmp_idx][0] = point_list[left_idx][0]
                    tmp[tmp_idx][1] = point_list[left_idx][1]
                    tmp_idx += 1
                    left_idx += 1
                else:
                    tmp[tmp_idx][0] = point_list[right_idx][0]
                    tmp[tmp_idx][1] = point_list[right_idx][1]
                    tmp_idx += 1
                    right_idx += 1
            else:
                tmp[tmp_idx][0] = point_list[left_idx][0]
                tmp[tmp_idx][1] = point_list[left_idx][1]
                tmp_idx += 1
                left_idx += 1
        else:
            tmp[tmp_idx][0] = point_list[right_idx][0]
            tmp[tmp_idx][1] = point_list[right_idx][1]
            tmp_idx += 1
            right_idx += 1
    while left_idx < m_idx + 1:
        tmp[tmp_idx][0] = point_list[left_idx][0]
        tmp[tmp_idx][1] = point_list[left_idx][1]
        left_idx += 1
        tmp_idx += 1
    while right_idx < l_idx + 1:
        tmp[tmp_idx][0] = point_list[right_idx][0]
        tmp[tmp_idx][1] = point_list[right_idx][1]
        right_idx += 1
        tmp_idx += 1
    tmp_idx = 0
    for i in range(f_idx, l_idx + 1):
        point_list[i][0] = tmp[tmp_idx][0]
        point_list[i][1] = tmp[tmp_idx][1]
        tmp_idx += 1
        
size = int(input())
point_list = [[0 for _ in range(2)] for _ in range(size)]
for i in range(size):
    point_list[i][0], point_list[i][1] = map(int, input().split())
merge_sort(point_list, 0 , size - 1)
for i in range(size):
    print(point_list[i][0], point_list[i][1])