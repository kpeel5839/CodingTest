import sys
size = int(sys.stdin.readline())
class Node:
    def __init__(self,value):
        self.value = value
        self.next_node = None
        self.prev_node = None
class Stack:
    global size
    def __init__(self):
        self.root_node = None
        self.last_node = None
        self.now_num = 1
        self.print_list = ['' for _ in range(size*2)]
        self.list_index = 0
    def oper(self,value):
        if self.now_num <= value:
            while self.now_num <= value:
                if self.root_node == None:
                    self.root_node = Node(self.now_num)
                    self.last_node = self.root_node
                    self.print_list[self.list_index] = '+'
                    self.now_num += 1
                    self.list_index += 1
                    continue

                self.last_node.next_node = Node(self.now_num)
                self.last_node.next_node.prev_node = self.last_node
                self.last_node = self.last_node.next_node
                self.print_list[self.list_index] = '+'
                self.now_num += 1
                self.list_index += 1

        if self.now_num > value:
            if self.root_node == None:
                return False
            if self.last_node == self.root_node:
                if self.last_node.value == value:
                    self.root_node = None
                    self.last_node = self.root_node
                    self.print_list[self.list_index] = '-'
                    self.list_index += 1
                    return True
                else:
                    return False
            if self.last_node.value == value:
                self.last_node = self.last_node.prev_node
                self.last_node.next_node = None
                self.print_list[self.list_index] = '-'
                self.list_index += 1
            else:
                return False
    def print(self):
        for i in range(size * 2):
            sys.stdout.write(str(self.print_list[i]) + '\n')
stack = Stack()
judge = True
for i in range(size):
    number = int(sys.stdin.readline())
    if stack.oper(number) == False:
        judge = False

if judge == True:   
    stack.print()
else:
    sys.stdout.write("NO" + '\n')
    