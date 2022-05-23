# 그냥 되게 간단하게 IS NULL 만 쓸 줄 알면 풀 수 있는 문제였다
SELECT animal_id
FROM animal_ins
WHERE name IS NULL
ORDER BY animal_id;
