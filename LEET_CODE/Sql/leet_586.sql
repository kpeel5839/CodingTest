# 일단 가장 많은 count 값을 뽑아냈음
# count 값을 뽑아냈으니까, 거기서 MAX 를 뽑아내고, 그 Customer number 만 알면 될 것 같은데
# 지금까지 푼 EASY 문제 중에 그래도 가장 어려웠던 것 같음

SELECT customer_number
FROM Orders
GROUP BY customer_number
HAVING COUNT(customer_number) = (SELECT MAX(o1.customer_number)
                                    FROM (SELECT COUNT(customer_number) AS customer_number
                                          FROM Orders
                                          GROUP BY customer_number) o1);
