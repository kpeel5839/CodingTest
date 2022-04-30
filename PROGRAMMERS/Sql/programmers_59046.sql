# 처음에는 그냥 Spayed, 즉 중성화 한 애들만 그런 줄 알았는데
# 알고보니까, 얘내들 중에 중성화 한 애들 찾는 거였음
SELECT animal_id, name, sex_upon_intake
FROM animal_ins
WHERE name IN ('Lucy', 'Ella', 'Pickle', 'Rogan', 'Sabrina', 'Mitty') AND sex_upon_intake like 'Spayed%'
ORDER BY animal_id;
