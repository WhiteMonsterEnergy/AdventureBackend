-- Pre-populate activity table
#INSERT INTO activity (title) VALUES ('Test Title');
#INSERT INTO activity (title) VALUES ('Another Activity');
#INSERT INTO activity (title) VALUES ('Hiking'), ('Kayaking');

-- Pre-populate equipment table
#INSERT INTO equipment (title, amount, broken, cost) VALUES ('Tent', 10, 1, 100.0), ('Kayak', 5, 0, 300.0);
#INSERT INTO equipment (title, amount, broken, cost) VALUES ('Helmet', 5, 1, 100.0);
#INSERT INTO equipment (title, amount, broken, cost) VALUES ('Axe', 3, 2, 50.0);
#INSERT INTO equipment (title, amount, broken, cost) VALUES ('Shield', 2, 0, 75.0);
#INSERT INTO equipment (title, amount, broken, cost) VALUES ('Rope', 2, 0, 10.0);
#INSERT INTO equipment (title, amount, broken, cost) VALUES ('Lantern', 5, 0, 20.0);

-- Pre-populate equipment_use table
#INSERT INTO equipment_use (equipment_id, activity_id) VALUES (1, 1), (2, 2);
