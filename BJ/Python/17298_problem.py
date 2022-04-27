import sys
size = int(sys.stdin.readline())
result_list = [-1 for _ in range(size)]
class Node:
    def __init__(self, value, index):
        self.value = value
        self.index = index
        self.next_node = None
        self.prev_node = None
class Stack:
    global result_list
    def __init__(self):
        self.root_node = None
        self.last_node = None
    def push(self, value, index):
        if self.root_node == None:
            self.root_node = Node(value, index)
            self.last_node = self.root_node
            return
        self.last_node.next_node = Node(value,index)
        self.last_node.next_node.prev_node = self.last_node
        self.last_node = self.last_node.next_node
    def find(self,value):
        if self.root_node == None:
            return
        current = self.last_node
        
        while True:
            if current == self.root_node:
                if current.value < value:
                    result_list[current.index] = value
                    if self.root_node.next_node == None:
                        self.root_node = None
                        self.last_node = self.root_node
                        break
                    self.root_node = current.next_node
                    self.root_node.prev_node = None
                    break
                else:
                    break
            if current.value < value:
                result_list[current.index] = value
                current.prev_node.next_node = None
                current = current.prev_node
                self.last_node = current
            else:
                break
num_list = list(map(int,sys.stdin.readline().split()))
stack = Stack()
for i in range(size):
    if i != size - 1 and num_list[i] < num_list[i+1]:
        result_list[i] = num_list[i+1]
        stack.find(num_list[i+1])
        continue
    if i != size - 1 and num_list[i] >= num_list[i+1]:
        stack.push(num_list[i], i)
        continue
    if i == size - 1:
        result_list[i] = -1
        break
for i in range(size):
    sys.stdout.write(str(result_list[i]) + ' ')