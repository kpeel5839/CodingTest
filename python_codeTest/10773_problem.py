import sys
class Node:
    def __init__(self,value):
        self.value = value
        self.next_node = None
        self.prev_node = None
class Stack:
    def __init__(self):
        self.root_node = None
        self.last_node = self.root_node
        self.sum = 0
    def push(self, value):
        if self.root_node == None:
            self.root_node = Node(value)
            self.sum += value
            self.last_node = self.root_node
            return
        self.last_node.next_node = Node(value)
        self.last_node.next_node.prev_node = self.last_node
        self.sum += value
        self.last_node = self.last_node.next_node
    def pop(self):
        if self.root_node == None:
            return
        if self.root_node.next_node == None:
            self.sum -= self.root_node.value
            self.root_node = None
            self.last_node = self.root_node
            return
        self.last_node = self.last_node.prev_node
        self.sum -= self.last_node.next_node.value
        self.last_node.next_node = None
    def print_sum(self):
        sys.stdout.write(str(self.sum)+'\n')

size = int(sys.stdin.readline())
stack = Stack()
for i in range(size):
    number = int(sys.stdin.readline())
    if number == 0:
        stack.pop()
    else:
        stack.push(number)
stack.print_sum()