# 간단하게, SELECT 에서 조건문을 사용하는 무넺였음, LIKE 조건문을 AND, OR 연산자로 이으려면
# 그때그때 컬럼을 명시해주어야 한다라는 것을 배웠음
SELECT animal_id, name, CASE 
                            WHEN (sex_upon_intake LIKE '%Neutered%' OR sex_upon_intake LIKE '%Spayed%') THEN 'O'
                            ELSE 'X'
                        END AS 중성화
FROM animal_ins
ORDER BY animal_id;
