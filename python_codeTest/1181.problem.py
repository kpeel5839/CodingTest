def merge_sort(word_list, f_idx, l_idx):
    if f_idx == l_idx:
        return
    m_idx = (f_idx + l_idx) // 2
    merge_sort(word_list, f_idx, m_idx)
    merge_sort(word_list, m_idx + 1, l_idx)
    merge(word_list, f_idx, l_idx, m_idx)

def merge(word_list, f_idx, l_idx, m_idx):
    left_idx = f_idx
    right_idx = m_idx + 1
    tmp = ["" for _ in range(l_idx - f_idx + 1)]
    tmp_idx = 0
    while left_idx < m_idx + 1 and right_idx < l_idx + 1:
        if len(word_list[left_idx]) >= len(word_list[right_idx]):
            if len(word_list[left_idx]) == len(word_list[right_idx]):
                if word_list[left_idx] > word_list[right_idx]:
                    tmp[tmp_idx] = word_list[right_idx]
                    tmp_idx += 1
                    right_idx += 1
                else:
                    tmp[tmp_idx] = word_list[left_idx]
                    tmp_idx += 1
                    left_idx += 1
            else:
                tmp[tmp_idx] = word_list[right_idx]
                tmp_idx += 1
                right_idx += 1
        else:
            tmp[tmp_idx] = word_list[left_idx]
            tmp_idx += 1
            left_idx += 1
    while left_idx < m_idx + 1:
        tmp[tmp_idx] = word_list[left_idx]
        tmp_idx += 1
        left_idx += 1
    while right_idx < l_idx + 1:
        tmp[tmp_idx] = word_list[right_idx]
        tmp_idx += 1
        right_idx += 1
    tmp_idx = 0
    for i in range(f_idx, l_idx + 1):
        word_list[i] = tmp[tmp_idx]
        tmp_idx += 1

size = int(input())
word_list = [input() for _ in range(size)]
merge_sort(word_list, 0 , size - 1)
for i in range(size):
    if (i != size - 1) and word_list[i] != word_list[i+1]:
        print(word_list[i])
    if i == size - 1:
        print(word_list[i])