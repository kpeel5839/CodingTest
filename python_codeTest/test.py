import sys
input=sys.stdin.readline


sudoku=[]
arr=[]
n=9

for i in range(n):
  temp=[]
  ins=input()
  for j in range(n):
    temp.append(int(ins[j]))
  sudoku.append(temp)

for x in range(n):
  for y in range(n):
    if sudoku[x][y]==0:
      arr.append((x,y))

# 열을 검사하는 함수
def check_col(x,a):
  for i in range(n):
    if a==sudoku[x][i]:
      return False
  return True

def check_row(y,a):
  for i in range(n):
    if a==sudoku[i][y]:
      return False
  return True


def check_rect(x,y,a):
    nx = (x // 3) * 3
    ny = (y // 3) * 3
    for i in range(nx , nx + 3):
      for j in range(ny , ny + 3):
        if a == sudoku[i][j]:
          return False
    return True


def dfs(L):
  if L==len(arr):
    for i in range(n):
        for j in range(n):
            print(sudoku[i][j],end="")
        print()
    exit(0)

  for a in range(1,10):
    x=arr[L][0]
    y=arr[L][1]
    if check_col(x,a) and check_row(y,a) and check_rect(x,y,a):
      sudoku[x][y]=a
      dfs(L+1)
      sudoku[x][y]=0

dfs(0)
