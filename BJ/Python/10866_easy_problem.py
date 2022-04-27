import sys
import collections
size = int(sys.stdin.readline())
st_list = [['' for _ in range(2)] for _ in range(size)]
queue = collections.deque([])
for i in range(size):
    st = sys.stdin.readline()
    index = 0
    for j in range(len(st)):
        if st[j] == '\n':
            continue
        if st[j] == ' ':
            index += 1
            st_list[index] = ''
            continue
        if j == 0:
            st_list[index] = ''
            st_list[index] += st[j]
            continue
        st_list[index] += st[j]
    if st_list[0] == 'push_back':
        queue.append(int(st_list[1]))
    elif st_list[0] == 'push_front':
        queue.appendleft(int(st_list[1]))
    elif st_list[0] == 'pop_front':
        if len(queue) == 0:
            sys.stdout.write(str(-1) + '\n')
        else:
            sys.stdout.write(str(queue[0]) + '\n')
            queue.popleft()
    elif st_list[0] == 'pop_back':
        if len(queue) == 0:
            sys.stdout.write(str(-1) + '\n')
        else:
            sys.stdout.write(str(queue[len(queue) - 1]) + '\n')
            queue.pop()
    elif st_list[0] == 'size':
        sys.stdout.write(str(len(queue)) +'\n')
    elif st_list[0] == 'empty':
        if len(queue) == 0:
            sys.stdout.write(str(1) + '\n')
        else:
            sys.stdout.write(str(0) + '\n')
    elif st_list[0] == 'front':
        if len(queue) == 0:
            sys.stdout.write(str(-1) +'\n')
        else:
            sys.stdout.write(str(queue[0]) +'\n')
    elif st_list[0] == 'back':
        if len(queue) == 0:
            sys.stdout.write(str(-1) + '\n')
        else:
            sys.stdout.write(str(queue[len(queue) - 1]) + '\n')
