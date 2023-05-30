-- hu-1
INSERT INTO `role` (`id`, `description`, `name`) VALUES ('1', 'ROLE_ADMIN', 'ROLE_ADMIN');
INSERT INTO `role` (`id`, `description`, `name`) VALUES ('2', 'ROLE_USER', 'ROLE_USER');
INSERT INTO `role` (`id`, `description`, `name`) VALUES ('3', 'ROLE_OWNER', 'ROLE_OWNER');

INSERT INTO `user` (id, address, dni_number, id_dni_type, id_person_type, mail, name, password, phone, surname, token_password, id_role) VALUES(1, 'st 123 # 456', '123', '1', '1', 'email@some.com', 'Name', '$2a$10$GlsGSNhkbVon6ZOSNMptOu5RikedRzlCAhMa7YpwvUSS0c88WT99S', '1234567890', 'Surname', NULL, 1);
INSERT INTO `user` (id, address, dni_number, id_dni_type, id_person_type, mail, name, password, phone, surname, token_password, id_role) VALUES(2, 'st 123 # 456', '111', '1', '1', 'email2@some.com', 'Name', '$2a$10$GlsGSNhkbVon6ZOSNMptOu5RikedRzlCAhMa7YpwvUSS0c88WT99S', '111111', 'Surname', NULL, 3);

-- hu-5
-- execute this to control that every user has an unique mail
ALTER TABLE powerup.`user` MODIFY mail VARCHAR(255) NOT NULL, ADD CONSTRAINT uk_mail UNIQUE (mail);

-- hu-6
-- create employee role
INSERT INTO `role` (`id`, `description`, `name`) VALUES ('4', 'ROLE_EMPLOYEE', 'ROLE_EMPLOYEE');
-- create constraint to prevent users with same dniNumber + dniType
-- en caso de error favor validar que no hayan usuarios que repitan esta pareja de variables.
ALTER TABLE `user` ADD CONSTRAINT uk_uniqueDni UNIQUE (dni_number , id_dni_type);

-- hu-8
-- update name of role client
UPDATE powerup.`role` SET description='ROLE_CLIENT',name='ROLE_CLIENT' WHERE id=2;
