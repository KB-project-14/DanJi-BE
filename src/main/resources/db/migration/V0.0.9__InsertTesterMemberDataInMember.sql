ALTER TABLE member
    MODIFY COLUMN role
        ENUM ('ROLE_USER','ROLE_ADMIN')
        NOT NULL
        DEFAULT 'ROLE_USER';


INSERT INTO member (member_id, username, password, role, name, created_at, updated_at)
VALUES (UNHEX(REPLACE('00000000-0000-0000-0000-000000000000', '-', '')),
        'tester',
        '$2b$10$KNHtNcDdCdJ3kMIz3nfwSehFd4nNmYMV9l5u7VrJuw6DXVM280REG', -- 인코딩된 '1234'
        'ROLE_ADMIN',
        'tester',
        '2025-07-28 16:00:00',
        '2025-07-28 16:00:00');