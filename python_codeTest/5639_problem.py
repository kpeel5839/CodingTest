#아얘 클래스를 선언해서 루트부터 트리를 만들어가기 시작하면 될 듯
#그러면 루트에서부터 하나하나 비교하고 계속 append 하면 되니깐
import sys
tmp_list = []
size = 0

while True:
    try:
        tmp_list.append(int(sys.stdin.readline()))
    except:
        break
    size += 1

num_list = [[0 for _ in range(3)] for _ in range(size)]

for i in range(size):
    num_list[i][0] = tmp_list[i]

def build(root , now_value):
    for i in range(size):
        if root == num_list[i][0]:
            root_index = i
            break
    if now_value < root and num_list[root_index][1] == 0:
        num_list[root_index][1] = now_value
        return
        
    if now_value < root and num_list[root_index][1] != 0:
        build(num_list[root_index][1], now_value)
        return
    
    if now_value > root and num_list[root_index][2] == 0:
        num_list[root_index][2] = now_value
        return
    if now_value > root and num_list[root_index][2] != 0:
        build(num_list[root_index][2], now_value)
        return
    
for i in range(1,size):
    build(num_list[0][0],num_list[i][0])
    
def dfs_high(root):
    for i in range(size):
        if num_list[i][0] == root:
            index = i
            break
    
    left = num_list[index][1]
    right = num_list[index][2]
    
    if left != 0:
        dfs_high(left)
    if right != 0:
        dfs_high(right)
    print(root)

dfs_high(num_list[0][0])