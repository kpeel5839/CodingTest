# distinct 한 다음에 수를 세는 것이다.
SELECT COUNT(DISTINCT name)
FROM animal_ins
WHERE name IS NOT NULL;
