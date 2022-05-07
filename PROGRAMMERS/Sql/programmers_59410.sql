# CASE, WHEN, THEN, END 를 사용하는 문을 해봤는데, 잘 안되었음
# 그래서, 그냥 IFNULL 로 처리
SELECT animal_type, IFNULL(name, 'No name'), sex_upon_intake
FROM animal_ins
ORDER BY animal_id
