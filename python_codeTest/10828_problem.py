import sys
class Node:
    def __init__(self, value):
        self.value = value
        self.next_node = None
        self.prev_node = None
class Stack:
    def __init__(self):
        self.root_node = None
        self.last_node = None
    
    def push(self,value):
        if self.root_node == None:
            self.root_node = Node(value)
            self.last_node = self.root_node
            return
        current = self.root_node
        while True:
            if current.next_node == None:
                current.next_node = Node(value)
                self.last_node = current.next_node
                (current.next_node).prev_node = current
                break
            current = current.next_node
                
    def pop(self):
        if self.root_node == None:
            sys.stdout.write(str(-1) + '\n')
            return
        if self.root_node.next_node == None:
            sys.stdout.write(str(self.root_node.value) + '\n')
            self.root_node = None
            self.last_node = None
            return
        sys.stdout.write(str(self.last_node.value) + '\n')
        self.last_node = self.last_node.prev_node
        self.last_node.next_node = None
        return

    def size(self):
        if self.root_node == None:
            sys.stdout.write(str(0) +'\n')
            return
        current = self.root_node
        count = 1
        while True:
            if current.next_node == None:
                sys.stdout.write(str(count) +'\n')
                break
            current = current.next_node
            count += 1
    def empty(self):
        if self.root_node == None:
            sys.stdout.write(str(1) + '\n')
            return
        else:
            sys.stdout.write(str(0) +'\n')
            return
    def top(self):
        if self.root_node == None:
            sys.stdout.write(str(-1) + '\n')
            return
        sys.stdout.write(str(self.last_node.value) + '\n')
    def print(self):
        if self.root_node == None:
            sys.stdout.write("stack is empty" + '\n')
            return
        current = self.root_node
        while True:
            if current.next_node == None:
                sys.stdout.write(str(current.value) + '\n')
                break
            sys.stdout.write(str(current.value) + '\n')
            current = current.next_node
            
            
size = int(sys.stdin.readline())
st = ''
st_list = ['' for _ in range(2)]
index = 0
stack = Stack()
for i in range(size):
    st = sys.stdin.readline()
    for j in range(len(st)):
        if j == 0:
            index = 0
            st_list[index] = ''
            st_list[index] += st[j]
            continue
        if st[j] == ' ':
            index += 1
            st_list[index] = ''
            continue
        if st[j] == '\n':
            continue
        st_list[index] += st[j]
    if st_list[0] == "push":
        stack.push(int(st_list[1]))
    if st_list[0] == "top":
        stack.top()
    if st_list[0] == 'pop':
        stack.pop()
    if st_list[0] == 'empty':
        stack.empty()
    if st_list[0] == 'size':
        stack.size()