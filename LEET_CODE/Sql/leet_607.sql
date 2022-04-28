# RED 회사의 id 값 뽑아내기 (Company 테이블)
# Order 테이블에서 그 id 값을 com_id 로 하는 sales_id 알아내기
# 그 sales_id의 record를 이용해서, name 값 얻어내기

SELECT name
FROM SalesPerson
WHERE sales_id NOT IN (SELECT sales_id
                   FROM Company c
                   JOIN Orders o ON c.com_id = o.com_id
                   WHERE c.name = 'RED');
