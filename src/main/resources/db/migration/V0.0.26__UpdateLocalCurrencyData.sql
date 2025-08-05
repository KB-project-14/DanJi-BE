SET SQL_SAFE_UPDATES = 0;

UPDATE local_currency
SET benefit_type = 'INCENTIVE'
WHERE name IN ('울산페이', '탐나는전', '익산다이로움카드');

SET SQL_SAFE_UPDATES = 1