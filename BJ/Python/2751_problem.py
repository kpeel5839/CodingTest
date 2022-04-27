def merge_sort(num_list , first_index , last_index):
    if first_index == last_index:
        return
    middle_index = (first_index + last_index) // 2
    merge_sort(num_list, first_index, middle_index)
    merge_sort(num_list, middle_index + 1, last_index)
    merge(num_list, first_index, last_index, middle_index)

def merge(num_list, first_index , last_index, middle_index):
    left_index = first_index
    right_index = middle_index + 1
    tmp = [0 for _ in range(last_index - first_index + 1)]
    tmp_index = 0
    while left_index < middle_index + 1 and right_index < last_index + 1:
        if num_list[left_index] > num_list[right_index]:
            tmp[tmp_index] = num_list[right_index]
            right_index += 1
            tmp_index += 1
        else:
            tmp[tmp_index] = num_list[left_index]
            left_index += 1
            tmp_index += 1
    while left_index < middle_index + 1:
        tmp[tmp_index] = num_list[left_index]
        left_index += 1
        tmp_index += 1
    while right_index < last_index + 1:
        tmp[tmp_index]  = num_list[right_index]
        right_index += 1
        tmp_index += 1
    tmp_index = 0
    for i in range(first_index, last_index + 1):
        num_list[i] = tmp[tmp_index]
        tmp_index += 1
size = int(input())
num_list = [int(input()) for _ in range(size)]
merge_sort(num_list , 0 , len(num_list) - 1)
for i in range(size):
    print(num_list[i])