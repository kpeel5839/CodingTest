import sys
class Node:
    def __init__(self,value):
        self.value = value
        self.next_node = None
        self.prev_node = None
class Queue:
    def __init__(self):
        self.root_node = None
        self.last_ndoe = None
        self.size = 0
    def push_front(self, value):
        if self.root_node == None:
            self.root_node = Node(value)
            self.last_node = self.root_node
            self.size += 1
            return
        self.root_node.prev_node = Node(value)
        self.root_node.prev_node.next_node = self.root_node
        self.root_node = self.root_node.prev_node
        self.size += 1
    def push_back(self,value):
        if self.root_node == None:
            self.root_node = Node(value)
            self.last_node = self.root_node
            self.size += 1
            return
        self.last_node.next_node = Node(value)
        self.last_node.next_node.prev_node = self.last_node
        self.last_node = self.last_node.next_node
    def pop_front(self):
        if self.root_node == None:
            sys.stdout.write(str(-1) + '\n')
            return
        if self.root_node.next_node == None:
            sys.stdout.write(str(self.root_node.value) + '\n')
            self.root_node = None
            self.last_node = self.root_node
            self.size -= 1
            return
        sys.stdout.write(str(self.root_node.value) + '\n')
        self.root_node = self.root_node.next_node
        self.root_node.prev_node = None
        self.size -= 1
    def pop_back(self):
        if self.root_node == None:
            sys.stdout.write(str(-1) + '\n')
            return
        if self.root_node.next_node == None:
            sys.stdout.write(str(self.root_node.value) + '\n')
            self.root_node = None
            self.last_node = self.root_node
            self.size -= 1
            return
        sys.stdout.write(str(self.last_node.value) + '\n')
        self.last_node = self.last_node.prev_node
        self.last_node.next_node = None
        self.size -= 1
    def print_size(self):
        sys.stdout.write(str(self.size) + '\n')
    def empty(self):
        if self.root_node == None:
            sys.stdout.write(str(1) + '\n')
        else:
            sys.stdout.write(str(0) + '\n')
    def front(self):
        if self.root_node == None:
            sys.stdout.write(str(-1) + '\n')
        else:
            sys.stdout.write(str(self.root_node.value) + '\n')
    def back(self):
        if self.root_node == None:
            sys.stdout.write(str(-1) + '\n')
        else:
            sys.stdout.write(str(self.last_node.value) + '\n')
size = int(sys.stdin.readline())
st_list = [[0 for _ in range(2)] for _ in range(size)]
index = 0
queue = Queue()
for i in range(size):
    st = sys.stdin.readline()

    for j in range(len(st)):
        if st[j] == '\n':
            continue
        if st[j] == ' ':
            index += 1
            st_list[1] = ''
            continue
        if j == 0:
            index = 0 
            st_list[0] = ''
            st_list[0] += st[j]
            continue
        st_list[index] += st[j]
        
    if st_list[0] == 'push_front':
        queue.push_front(int(st_list[1]))
    elif st_list[0] == 'push_back':
        queue.push_back(int(st_list[1]))
    elif st_list[0] == 'pop_front':
        queue.pop_front()
    elif st_list[0] == 'pop_back':
        queue.pop_back()
    elif st_list[0] == 'size':
        queue.print_size()
    elif st_list[0] == 'empty':
        queue.empty()
    elif st_list[0] == 'front':
        queue.front()
    elif st_list[0] == 'back':
        queue.back()