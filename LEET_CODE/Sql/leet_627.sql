# 반대의 값으로 값을 변경하는 쿼리문, 어떻게 작성해야할까?
# sex 값을 보고서 해야하는 것은 분명함
# 단일 쿼리 문으로 작성을 해야함... WHERE 에서 묶나?
# Sql Set 에는 WHEN, THEN 이 있는 듯, ELSE 랑 근데 이게 과연 WHEN 에만 있을까?
# 그냥, 일반 SQL 문장에서도 사용가능하지 않을까 싶다.
# 쨌든 CASE 로 시작해서, END 로 끝내면 된다.
UPDATE
Salary
SET sex = (CASE
               WHEN sex = 'f' THEN 'm'
               ELSE 'f'
           END);
