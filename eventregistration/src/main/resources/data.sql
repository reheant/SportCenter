INSERT INTO account (id, email, first_name, last_name, password) VALUES
    (1, 'john.doe@example.com', 'John', 'Doe', 'J0hnD0e!'),                  -- 1
    (2, 'jane.smith@example.com', 'Jane', 'Smith', 'J@neSmith1'),             -- 2
    (3, 'michael.johnson@example.com', 'Michael', 'Johnson', 'M1ch@elJ0hnson!'), -- 3
    (4, 'emily.wilson@example.com', 'Emily', 'Wilson', 'Em1lyW!ls0n'),        -- 4
    (5, 'david.brown@example.com', 'David', 'Brown', 'D@v1dBrown!'),          -- 5
    (6, 'admin@example.com', 'Jesus', 'Christ', 'J3susChr!st');               -- 6


-- INSERT INTO account_role (id) VALUES
--     (1), (2), (3), (4), (5), (6);

INSERT INTO owner (id, account_id) VALUES
    (1, 6);

INSERT INTO customer (id, account_id, wants_email_confirmation) VALUES
    (1, 1, true),
    (2, 3, false),
    (3, 5, true);

INSERT INTO payment_method (id, customer_id, name) VALUES
    (1, 1, 'Card 1'),
    (2, 2, 'Card 2'),
    (3, 3, 'Card 3');

INSERT INTO card (id, ccv, expiration_date, number, payment_card_type) VALUES
    (2, 456, '0924', '23456789', 0),
    (3, 789, '0328', '34567890', 1);

INSERT INTO pay_pal (id, email, password) VALUES
    (1, 'asdja@b.com', 'definitelyextremelystrongpassword');

INSERT INTO location (id, capacity, closing_time, opening_time, name) VALUES
    (1, 200, '21:20', '09:20', 'Sports center');

INSERT INTO instructor (id, account_id) VALUES
    (1, 2),
    (2, 3),
    (3, 4);

INSERT INTO course (id, cost, default_duration, course_status, requires_instructor, description, name)
VALUES
    (1, 79.99, 3, 0, true, 'This course covers the fundamentals of basketball including dribbling, shooting, and defense.', 'Basketball Fundamentals'),
    (2, 59.99, 2, 0, false, 'Learn the basics of soccer including passing, shooting, and tactics.', 'Soccer Basics'),
    (3, 99.99, 4, 1, true, 'This course focuses on improving flexibility, strength, and balance through yoga practice.', 'Yoga for Beginners'),
    (4, 69.99, 200, 2, true, 'Explore the techniques of martial arts including kicks, punches, and blocks.', 'Introduction to Martial Arts'),
    (5, 89.99, 5, 2, true, 'This course introduces students to the principles of tennis including serving, volleying, and strategy.', 'Tennis Fundamentals');

INSERT INTO session (id, course_id, end_time, location_id, start_time) VALUES
    (1, 1, '2024-06-01 10:00:00', 1, '2024-06-01 08:00:00'),
    (2, 1, '2024-07-01 12:00:00', 1, '2024-07-01 10:00:00'),
    (3, 2, '2024-06-01 10:00:00', 1, '2024-06-01 08:00:00'),
    (4, 2, '2024-07-01 12:00:00', 1, '2024-07-01 10:00:00'),
    (5, 3, '2024-07-01 12:00:00', 1, '2024-07-01 10:00:00'),
    (6, 3, '2024-08-01 14:00:00', 1, '2024-08-01 12:00:00'),
    (7, 4, '2024-07-01 12:00:00', 1, '2024-07-01 10:00:00'),
    (8, 4, '2024-09-01 16:00:00', 1, '2024-09-01 14:00:00'),
    (9, 5, '2024-09-01 16:00:00', 1, '2024-09-01 14:00:00'),
    (10, 5, '2024-10-01 18:00:00', 1, '2024-10-01 16:00:00');



INSERT INTO registration (id, customer_id, session_id) VALUES
    (1, 1, 1),
    (2, 2, 2),
    (3, 3, 3),
    (4, 1, 4),
    (5, 2, 5),
    (6, 3, 1),
    (7, 1, 2),
    (8, 2, 3),
    (9, 3, 4),
    (10, 1, 5);

INSERT INTO instructor_assignment (id, instructor_id, session_id) VALUES
    (1, 1, 1),
    (2, 2, 2),
    (3, 1, 3),
    (4, 2, 4),
    (5, 1, 5),
    (6, 2, 1),
    (7, 1, 2),
    (8, 2, 3),
    (9, 1, 4),
    (10, 2, 5);