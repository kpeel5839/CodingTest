import sys #keep going

class Node:
    def __init__(self, value):
        self.value = value
        self.next_node = None
        self.prev_node = None
class Queue:
    def __init__(self,pop_number_list):
        self.root_node = None
        self.last_node = None
        self.size = 0
        self.move_count = 0
        self.index = 0
        self.pop_number_list = pop_number_list.copy()
        self.pop_number_list_size = len(pop_number_list)
        self.left_move_count = 0
        self.right_move_count = 0
        self.oper_move_count = 0
    def push(self,value):
        if self.root_node == None:
            self.root_node = Node(value)
            self.last_node = self.root_node
            self.size += 1
            return
        self.last_node.next_node = Node(value)
        self.last_node.next_node.prev_node = self.last_node
        self.last_node = self.last_node.next_node
        self.size += 1

    def operation(self): #size - left_move_count : right_move
        global queue
        sys.stdout.write(str(self.pop_number_list) + '\n')
        sys.stdout.write(str(self.pop_number_list_size) + '\n')
        if self.root_node == None:
            return
        while True: #break condition self.index == self.pop_number_list_size
            sys.stdout.write("self.index : " + str(self.index) + '\n')
            if self.index == self.pop_number_list_size - 1:
                break
            current = self.root_node
            if current.value == self.pop_number_list[self.index]:
                self.index += 1
                if current.next_node == None:
                    self.root_node = None
                    self.last_node = None
                else:
                    self.root_node = self.root_node.next_node
                    self.root_node.prev_node = None
                    self.size -= 1
                continue
            while True:
                sys.stdout.write("self.oper_move_count : " + str(self.oper_move_count) + '\n')
                self.oper_move_count = 1
                if current.value == self.pop_number_list[self.index]:
                    self.index += 1
                    self.right_move_count = self.size - self.left_move_count
                    if self.right_move_count > self.left_move_count: #left move exe
                        while self.oper_move_count != self.left_move_count:
                            sys.stdout.write("fucking" + '\n')
                            self.oper_move_count += 1
                            self.move_count += 1
                            self.last_node.next_node = self.root_node
                            self.root_node.prev_node = self.last_node
                            self.last_node = self.last_node.next_node
                            self.root_node = self.root_node.next_node
                            self.root_node.prev_node = None
                            self.last_node.next_node = None
                        break
                    else:
                        while self.oper_move_count != self.right_move_count:
                            self.oper_move_count += 1
                            self.move_count += 1
                            self.root_node.prev_node = self.last_node
                            self.last_node.next_node = self.root_node
                            self.root_node = self.last_node
                            self.last_node = self.last_node.prev_node
                            self.last_node.next_node = None
                            self.root_node.prev_node = None
                        break
                else:
                    current = current.next_node
                    self.left_move_count += 1
                queue.print_queue()
                self.root_node = self.root_node.next_node
                self.root_node.prev_node = None
                self.size -= 1
    def print_move_count(self):
        sys.stdout.write(str(self.move_count))
    def print_queue(self):
        if self.root_node == None:
            return
        current = self.root_node
        while True:
            if current.next_node == None:
                sys.stdout.write(str(current.value) +' ')
                sys.stdout.write('\n')
                break
            sys.stdout.write(str(current.value) + ' ')
            current = current.next_node

size , pop_number_count = map(int,sys.stdin.readline().split())
pop_number_list = list(map(int,sys.stdin.readline().split()))
queue = Queue(pop_number_list)

for i in range(1, size + 1):
    queue.push(i)
queue.operation()
queue.print_move_count()