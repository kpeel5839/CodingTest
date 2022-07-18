# 개인적으로 가장 창의적인 정답인 것 같다.
# 당연히 내가 풀지는 못했다.
SELECT cart_id
FROM cart_products
WHERE name in ('Milk', 'Yogurt')
GROUP BY cart_id
HAVING count(DISTINCT name) = 2;
