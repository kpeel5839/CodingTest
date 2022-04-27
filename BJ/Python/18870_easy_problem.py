import sys

n=int(sys.stdin.readline())
arr1=list(map(int,sys.stdin.readline().split()))
arr2=list(sorted(set(arr1))) #set이 중복된 입력값을 없애줌
arr2={arr2[i]:i for i in range(len(arr2))} #dictionary 를 이용해서 해쉬 테이블을 이용해 원하는 값을 바로 찾음
print(*[arr2[i] for i in arr1])