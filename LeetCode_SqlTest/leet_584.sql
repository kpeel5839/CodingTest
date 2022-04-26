# 그냥 referee_id == 2 인 애들 빼고 고르는 것이였음
SELECT name
FROM Customer
WHERE referee_id != 2 OR referee_id IS NULL;
