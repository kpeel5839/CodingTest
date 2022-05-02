# 일단, 첫번째 count >= 2 인 애들만 고른다.
# 두번째, 이름이 없는 동물은 제외한다.
# 세번째, 결과는 이름 순으로 조회한다.

SELECT name, count(name) AS count
FROM animal_ins
WHERE name IS NOT NULL
GROUP BY name
HAVING count(name) >= 2
ORDER BY name;
