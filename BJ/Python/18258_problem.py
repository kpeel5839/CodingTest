import sys
class Node:
    def __init__(self, value):
        self.value = value
        self.next_node = None
        self.prev_node = None
class Queue:
    def __init__(self):
        self.root_node = None #front_node
        self.last_node = None #back_node
        self.queue_size = 0
    def push(self, value):
        if self.root_node == None:
            self.root_node = Node(value)
            self.last_node = self.root_node
            self.queue_size += 1
            return
        self.last_node.next_node = Node(value)
        self.last_node.next_node.prev_node = self.last_node
        self.last_node = self.last_node.next_node
        self.queue_size += 1
    def pop(self):
        if self.root_node == None:
            sys.stdout.write('-1' + '\n')
            return
        if self.root_node.next_node == None:
            sys.stdout.write(str(self.root_node.value) + '\n')
            self.root_node = None
            self.last_node = self.root_node
            self.queue_size -= 1
            return
        sys.stdout.write(str(self.root_node.value) + '\n')
        self.root_node = self.root_node.next_node
        self.root_node.prev_node = None
        self.queue_size -= 1
    def size(self):
        sys.stdout.write(str(self.queue_size) + '\n')
    def empty(self):
        if self.root_node == None:
            sys.stdout.write('1' + '\n')
        else:
            sys.stdout.write('0' + '\n')
    def front(self):
        if self.root_node == None:
            sys.stdout.write('-1' + '\n')
            return
        sys.stdout.write(str(self.root_node.value) + '\n')
    def back(self):
        if self.root_node == None:
            sys.stdout.write('-1' + '\n')
            return
        sys.stdout.write(str(self.last_node.value) + '\n')
    # def print(self):
    #     if self.root_node == None:
    #         return
    #     current = self.root_node
    #     while True:
    #         if current.next_node == None:
    #             sys.stdout.write(str(current.value) + ' ')
    #             sys.stdout.write('\n')
    #             break
    #         sys.stdout.write(str(current.value) + ' ')
    #         current = current.next_node
size = int(sys.stdin.readline())
st_list = ['' for _ in range(2)]
index = 0
queue = Queue()
for i in range(size):
    st = sys.stdin.readline()
    for j in range(len(st)):
        if st[j] == '\n':
            continue
        if st[j] == ' ':
            index = 1
            st_list[index] = ''
            continue
        if j == 0:
            index = 0
            st_list[index] = ''
            st_list[index] += st[j]
            continue
        st_list[index] += st[j]
    # queue.print()
    if st_list[0] == 'push':
        queue.push(int(st_list[1]))
        continue
    if st_list[0] == 'pop':
        queue.pop()
        continue
    if st_list[0] == 'size':
        queue.size()
        continue
    if st_list[0] == 'empty':
        queue.empty()
        continue
    if st_list[0] == 'front':
        queue.front()
        continue
    if st_list[0] == 'back':
        queue.back()
        continue