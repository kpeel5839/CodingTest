import sys
test_size = int(sys.stdin.readline())
class Node:
    def __init__(self, value):
        self.value = value
        self.next_node = None
        self.prev_node = None
class Queue:
    def __init__(self, target_number):
        self.root_node = None
        self.last_node = None
        self.target_node = None
        self.target_number = target_number
        self.target_count = 0
        self.count = 0
        self.size = 0
    def push(self,value):
        if self.root_node == None:
            self.size += 1
            self.root_node = Node(value)
            self.last_node = self.root_node
            self.target_count += 1
            if self.target_count == self.target_number:
                self.target_node = self.root_node
            return
        self.target_count += 1
        self.size += 1
        self.last_node.next_node = Node(value)
        self.last_node.next_node.prev_node = self.last_node
        self.last_node = self.last_node.next_node
        if self.target_count == self.target_number:
            self.target_node = self.last_node
            return
    def operation(self): 
        if self.root_node.next_node == None:
            sys.stdout.write(str(1) + '\n')
            return
        while True:
            max = self.root_node.value
            current = self.root_node
            while True:
                if current == self.last_node:
                    if max < current.value:
                        max = current.value
                    break
                if max < current.value:
                    max = current.value
                current = current.next_node
            if max != self.root_node.value:
                self.last_node.next_node = self.root_node
                self.root_node.prev_node = self.last_node
                self.root_node = self.root_node.next_node
                self.last_node = self.last_node.next_node
                self.root_node.prev_node = None
                self.last_node.next_node = None
                continue
            if max == self.root_node.value:
                self.count += 1
                if self.root_node == self.target_node:
                    sys.stdout.write(str(self.count) + '\n')
                    break
                self.root_node = self.root_node.next_node
                self.root_node.prev_node = None
                
for i in range(test_size):
    size , pivot = map(int,sys.stdin.readline().split())
    num_list = list(map(int,sys.stdin.readline().split()))
    queue = Queue(pivot + 1)
    for i in range(size):
        queue.push(num_list[i])
    queue.operation()