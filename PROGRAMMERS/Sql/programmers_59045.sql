# 그냥 꼼수 써서 join 한다음 다르면 중성했다고 가정하고 풀었음
select i.animal_id, i.animal_type, i.name
from animal_ins AS i JOIN animal_outs AS o ON i.animal_id = o.animal_id
WHERE i.sex_upon_intake != o.sex_upon_outcome
ORDER BY i.animal_id;
