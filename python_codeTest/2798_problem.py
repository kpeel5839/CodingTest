size, max_sum_number = map(int, input().split())
card_list = list(map(int,input().split()))
min_judge = max_sum_number + 1
for first_card in range(size-2):
    for second_card in range(first_card+1,size-1):
        for third_card in range(second_card+1,size):
            if min_judge > max_sum_number - (card_list[first_card] + card_list[second_card] + card_list[third_card])\
                and 0 <= (max_sum_number - (card_list[first_card] + card_list[second_card] + card_list[third_card])):
                min_judge = max_sum_number - (card_list[first_card] + card_list[second_card] + card_list[third_card])
                min = card_list[first_card] + card_list[second_card] + card_list[third_card]
print(min)