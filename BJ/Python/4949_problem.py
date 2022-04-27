import sys # 1 = '()' 2 = '[]'
class Node:
    def __init__(self,value):
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
        self.last_node.next_node = Node(value)
        self.last_node.next_node.prev_node =self.last_node
        self.last_node = self.last_node.next_node
    def pop(self,value):
        if self.root_node == None:
            return True
        if self.last_node == self.root_node:
            if self.root_node.value == value:
                self.root_node = None
                self.last_node = self.root_node
                return
            else:
                return True
        if self.last_node.value == value:
            self.last_node = self.last_node.prev_node
            self.last_node.next_node = None
        else:
            return True
    def empty(self):
        if self.root_node == None:
            return True
        else:
            return False
        
while True:
    st = sys.stdin.readline()
    if st[0] == '.':
        break
    stack = Stack()
    correct = True
    for i in range(len(st)):
        if st[i] == '\n':
            continue
        if st[i] == '(':
            stack.push(1)
            continue
        if st[i] == ')':
            if stack.pop(1):
                correct = False
                sys.stdout.write("no" + '\n')
                break
            continue
        if st[i] == '[':
            stack.push(2)
            continue
        if st[i] == ']':
            if stack.pop(2):
                correct = False
                sys.stdout.write("no" + '\n')
                break
            continue
    if stack.empty() and correct == True:
        sys.stdout.write("yes" + '\n')
    if not stack.empty() and correct == True:
        sys.stdout.write("no" + '\n')