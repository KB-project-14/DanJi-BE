UPDATE local_currency SET region_id = (SELECT region_id FROM region WHERE city = '군산시') WHERE name = '군산사랑상품권';
UPDATE local_currency SET region_id = (SELECT region_id FROM region WHERE city = '익산시') WHERE name = '익산다이로움카드';


