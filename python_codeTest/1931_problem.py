def merge_sort(room_list, f_idx, l_idx):
    if f_idx == l_idx:
        return
    m_idx = (l_idx + f_idx) // 2
    merge_sort(room_list, f_idx, m_idx)
    merge_sort(room_list, m_idx + 1, l_idx)
    merge(room_list, f_idx, l_idx, m_idx)

def merge(room_list, f_idx, l_idx, m_idx):
    tmp = [[0 for _ in range(2)] for _ in range(l_idx - f_idx + 1)]
    tmp_idx = 0
    left_idx = f_idx
    right_idx = m_idx + 1
    while left_idx < m_idx + 1 and right_idx < l_idx + 1:
        if room_list[left_idx][1] >= room_list[right_idx][1]:
            if room_list[left_idx][1] == room_list[right_idx][1]:
                if room_list[left_idx][0] > room_list[right_idx][0]:
                    tmp[tmp_idx][0] = room_list[right_idx][0]
                    tmp[tmp_idx][1] = room_list[right_idx][1]
                    tmp_idx += 1
                    right_idx += 1
                else:
                    tmp[tmp_idx][0] = room_list[left_idx][0]
                    tmp[tmp_idx][1] = room_list[left_idx][1]
                    tmp_idx += 1
                    left_idx += 1
            else:
                tmp[tmp_idx][0] = room_list[right_idx][0]
                tmp[tmp_idx][1] = room_list[right_idx][1]
                tmp_idx += 1
                right_idx +=1
        else:
            tmp[tmp_idx][0] = room_list[left_idx][0]
            tmp[tmp_idx][1] = room_list[left_idx][1]
            tmp_idx += 1
            left_idx +=1
    while left_idx < m_idx + 1:
        tmp[tmp_idx][0] = room_list[left_idx][0]
        tmp[tmp_idx][1] = room_list[left_idx][1]
        tmp_idx += 1
        left_idx +=1
    while right_idx < l_idx + 1:
        tmp[tmp_idx][0] = room_list[right_idx][0]
        tmp[tmp_idx][1] = room_list[right_idx][1]
        tmp_idx += 1
        right_idx +=1 
    tmp_idx = 0
    for i in range(f_idx, l_idx + 1):
        room_list[i][0] = tmp[tmp_idx][0]
        room_list[i][1] = tmp[tmp_idx][1]
        tmp_idx += 1      
            
size = int(input())
room_list = [[0 for _ in range(2)] for _ in range(size)]
for i in range(size):
    room_list[i][0] , room_list[i][1] = map(int, input().split())
merge_sort(room_list, 0 , size - 1)
count = 0
for i in range(size):
    if i == 0:
        pre_time = room_list[0][1]
        count += 1
        continue
    if pre_time <= room_list[i][0]:
        pre_time = room_list[i][1]
        count += 1
        continue
print(count)