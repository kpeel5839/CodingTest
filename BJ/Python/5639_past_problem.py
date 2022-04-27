#어떤 방식으로 더욱 빠르게 할 수 있을까
import sys

sys.setrecursionlimit(100000)

class Node:
    def __init__(self, value):
        self.value = value
        self.left = None
        self.right = None

class Make_Tree:
    def __init__(self):
        self.root = None
    
    def insert_value(self, value):
        current = self.root
        if self.root == None:
            self.root = Node(value)
        else:
            while True:
                if current.value > value:
                    if current.left == None:
                        current.left = Node(value)
                        break
                    if current.left != None:
                        current = current.left
                        continue
                    
                elif current.value < value:
                    if current.right == None:
                        current.right = Node(value)
                        break
                    if current.right != None:
                        current = current.right
                        continue
    def postorder(self,node):
        current = node

        if current == None:
            return

        self.postorder(current.left)
        self.postorder(current.right)
        print(current.value)

tree = Make_Tree()

while True:
    try:
        tree.insert_value(int(input()))
    except:
        break

tree.postorder(tree.root)
