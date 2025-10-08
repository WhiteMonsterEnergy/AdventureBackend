CREATE TABLE activity (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          title VARCHAR(255) NOT NULL
);

CREATE TABLE equipment (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           title VARCHAR(255) NOT NULL,
                           amount INT NOT NULL,
                           broken INT NOT NULL,
                           cost DOUBLE NOT NULL
);

CREATE TABLE equipment_use (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               equipment_id INT,
                               activity_id INT,
                               FOREIGN KEY (equipment_id) REFERENCES equipment(id),
                               FOREIGN KEY (activity_id) REFERENCES activity(id)
);
