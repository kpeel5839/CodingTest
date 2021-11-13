size = int(input())
for _ in range(size):
    start, finish = map(int,(input().split()))
    distance = finish - start
    count = 0
    move = 1
    move_plus = 0
    while move_plus < distance:
        count+=1
        move_plus+=move
        if count % 2 ==0: #각 디스턴스 거리마다 카운트가 증가하는데 그것이 카운트가 2의 배수일 때마다 바뀜 그래서 2의 배수 일때에 move를 증가시킴 그러면 그 빈도수 만큼 카운트가 나옴
            move +=1
    print(count)
    #거리가 1=1번 2=2번 3,4 3번 5,6 4번 7,8,9 5번 10 11 12 6번 13 14 15 16 7번 17 18 19 20 8번
    #이런식으로 카운트가 2의 배수가 될 때마다 디스턴스가 1씩 늘어난다. 그래서 count % 2 ==0 일 때에는 move += 1를 해주어 각 카운트마다의 해당되는 디스턴스를 다르게 함 