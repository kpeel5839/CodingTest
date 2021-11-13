import sys
import collections
size , number_list_count = map(int,sys.stdin.readline().split())
number_list = list(map(int,sys.stdin.readline().split()))
move_count = 0
inner_move_count = 0
number_list_index = 0
queue = collections.deque([])
for i in range(1, size + 1):
    queue.append(i)
while True: 
    if number_list_index == number_list_count:
        sys.stdout.write(str(move_count) + '\n')
        break
    inner_move_count = 0
    left_move = queue.index(number_list[number_list_index])
    if left_move == 0:
        number_list_index += 1
        queue.popleft()
        continue
    right_move = len(queue) - left_move
    if left_move < right_move:
        while inner_move_count != left_move:
            inner_move_count += 1
            move_count += 1
            queue.append(queue[0])
            queue.popleft()
    else:
        while inner_move_count != right_move:
            inner_move_count += 1
            move_count += 1
            queue.appendleft(queue[len(queue) - 1])
            queue.pop()
    number_list_index += 1
    queue.popleft()