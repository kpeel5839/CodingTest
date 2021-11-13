def bubble_sort(num_list, first_index, last_index):
    while last_index != 0:
        for i in range(first_index, last_index):
            if num_list[i] > num_list[i+1]:
                tmp = num_list[i]
                num_list[i] = num_list[i+1]
                num_list[i+1] = tmp
        last_index -= 1
        
size = int(input())
num_list = [int(input()) for _ in range(size)]
bubble_sort(num_list, 0 , len(num_list) - 1)
for i in range(size):
    print(num_list[i])
    