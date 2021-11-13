import sys
#라스트 노드의 넥스트 노드를 루트노드로 이어서 형상을 원으로 만들고 count 를 만들어서 queue 를 생성할 때 받아버린다.
size, pivot_number = map(int, sys.stdin.readline().split())
class Node:
    def __init__(self,value):
        self.value = value
        self.next_node = None
        self.prev_node = None
class Queue:
    global size
    def __init__(self,count):
        self.root_node = None
        self.last_node = None
        self.pivot_count = count
        self.move_count = 0
        self.result_list = [0 for _ in range(size)]
        self.index = 0
    def push(self,value):
        if self.root_node == None:
            self.root_node = Node(value)
            self.last_node = self.root_node
            return
        self.last_node.next_node = Node(value)
        self.last_node.next_node.prev_node = self.last_node
        self.last_node = self.last_node.next_node
        self.last_node.next_node = self.root_node
    def operation(self):
        if self.root_node == None:
            return
        if self.root_node.next_node == None:
            self.result_list[0] = self.root_node.value
            return
        current = self.root_node
        self.move_count = 1
        while True:
            if self.index == size:
                break
            if self.move_count == self.pivot_count:
                self.move_count = 0
                self.result_list[self.index] = current.value
                self.index += 1
                if current == self.root_node: # delete process
                    self.last_node.next_node = self.root_node.next_node
                    self.root_node = self.root_node.next_node
                    self.root_node.prev_node = None
                    continue
                if current == self.last_node: # delete process
                    self.last_node.prev_node.next_node = self.last_node.next_node
                    self.last_node = self.last_node.prev_node
                    continue
                if current != self.root_node and current != self.last_node:
                    current.prev_node.next_node = current.next_node
                    current.next_node.prev_node = current.prev_node
                    continue
            self.move_count += 1
            current = current.next_node
    def print(self):
        if size == 1:
            sys.stdout.write("<" + str(self.result_list[0]) + ">")
            return
        for i in range(size):
            if i == 0:
                sys.stdout.write("<" + str(self.result_list[i]) + ', ')
                continue
            if i == size - 1:
                sys.stdout.write(str(self.result_list[i]) + '>')
                continue
            sys.stdout.write(str(self.result_list[i]) + ', ')
queue = Queue(pivot_number)
for i in range(1, size + 1):
    queue.push(i)
queue.operation()
queue.print()