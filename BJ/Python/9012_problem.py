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

    def push(self):
        if self.root_node == None:
            self.root_node = Node(1)
            self.last_node = self.root_node
            return
        self.last_node.next_node = Node(1)
        self.last_node.next_node.prev_node = self.last_node
        self.last_node = self.last_node.next_node

    def pop(self):
        if self.root_node == None:
            return True
        if self.root_node.next_node == None:
            self.root_node = None
            self.last_node = self.root_node
            return
        self.last_node = self.last_node.prev_node
        self.last_node.next_node = None
    def empty(self):
        if self.root_node == None:
            return True
        else:
            return False
size = int(sys.stdin.readline())
for i in range(size):
    stack = Stack()
    st = sys.stdin.readline()
    correct = True
    for j in range(len(st)):
        if st[j] == '\n':
            continue
        if st[j] == '(':
            stack.push()
            continue
        if st[j] == ')':
            if stack.pop():
                correct = False
                sys.stdout.write('NO' + '\n')
                break
    if stack.empty() and correct == True:
        sys.stdout.write('YES' + '\n')
    elif not stack.empty() and correct == True:
        sys.stdout.write('NO' + '\n')