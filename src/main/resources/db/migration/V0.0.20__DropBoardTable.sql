-- Step 1: Disable FK checks
SET FOREIGN_KEY_CHECKS = 0;

-- Step 2: Drop tables
DROP TABLE IF EXISTS tbl_board_attachment;
DROP TABLE IF EXISTS tbl_board;
DROP TABLE IF EXISTS cashback;

-- Step 3: Re-enable FK checks
SET FOREIGN_KEY_CHECKS = 1;