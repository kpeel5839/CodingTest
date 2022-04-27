import sys
size = int(sys.stdin.readline())
class Node:
    def __init__(self, value):
        self.value = value
        self.next_node = None
        self.prev_node = None
class Queue:
    def __init__(self):
        self.root_node = None
        self.last_node = None
    def push(self,value):
        if self.root_node == None:
            self.root_node = Node(value)
            self.last_node = self.root_node
            return
        self.last_node.next_node = Node(value)
        self.last_node.next_node.prev_node = self.last_node
        self.last_node = self.last_node.next_node
    def operation(self):
        if self.root_node == None:
            return
        if self.root_node.next_node == None:
            sys.stdout.write(str(self.root_node.value) + '\n')
            return
        while True:
            if self.root_node.next_node == None:
                sys.stdout.write(str(self.root_node.value) + '\n')
                break
            self.root_node = self.root_node.next_node
            self.root_node.prev_node = None #pop
            self.last_node.next_node = self.root_node
            self.root_node.prev_node = self.last_node
            self.last_node = self.last_node.next_node
            self.root_node = self.root_node.next_node
            self.last_node.next_node = None
queue = Queue()
for i in range(1, size + 1):
    queue.push(i)
queue.operation()
