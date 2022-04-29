# 일단, animal_type 을 묶어서, 몇마리인지 나타내었고
# 고양이를 무조건 먼저 출력하라고 하여서, ORDER BY 를 사용하였음
SELECT ANIMAL_TYPE, COUNT(animal_type) count
FROM animal_ins
GROUP BY animal_type
ORDER BY animal_type;
