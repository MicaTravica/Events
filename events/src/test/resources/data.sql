INSERT INTO `events_database`.`user` (`id`, `email`, `name`, `password`, `phone`, `surname`, `user_role`, `username`, `verified`) VALUES ('1', 'mica97@email.com', 'Milica', '$2y$12$NAY37CbhtssuFTQSqwaVfOCGOcB.xKV.O4gutHFHBgdoiGcpSSxbq', '123456789', 'Travica', 'ROLE_ADMIN', 'milovica', b'1');
INSERT INTO `events_database`.`user` (`id`, `email`, `name`, `password`, `phone`, `surname`, `user_role`, `username`, `verified`) VALUES ('2', 'cupka2797@gmail.com', 'Milica', '$2y$12$NAY37CbhtssuFTQSqwaVfOCGOcB.xKV.O4gutHFHBgdoiGcpSSxbq', '123456789', 'Travica', 'ROLE_REGULAR', 'dusan', b'1');

INSERT INTO `events_database`.`place` (`id`, `address`, `latitude`, `longitude`, `name`) VALUES ('1', 'Sime Milosevica ', '1', '1', 'Hala1');

INSERT INTO `events_database`.`hall` (`id`, `name`, `place_id`) VALUES ('1', 'sala1', '1');

INSERT INTO `events_database`.`sector` (`id`, `name`, `sector_columns`, `sector_rows`, `hall_id`) VALUES ('1', 'sektor1', '1', '1', '1');
INSERT INTO `events_database`.`sector` (`id`, `name`, `sector_columns`, `sector_rows`, `hall_id`) VALUES ('2', 'dasfa', '0', '0', '1');

INSERT INTO `events_database`.`seat` (`id`, `seat_column`, `seat_row`, `sector_id`) VALUES ('1', '1', '1', '1');

INSERT INTO `events_database`.`event` (`id`, `description`, `event_state`, `event_type`, `from_date`, `name`, `to_date`) VALUES ('1', 'dfa', 'AVAILABLE', 'SPORT', '2020-01-01', 'UTAKMICA', '2020-01-02');

INSERT INTO `events_database`.`event_hall` (`event_id`, `hall_id`) VALUES ('1', '1');

INSERT INTO `events_database`.`ticket` (`id`, `bar_code`, `price`, `ticket_state`, `version`, `event_id`, `seat_id`) VALUES ('1', 'DFA', '100.00','AVAILABLE', '0', '1', '1');

INSERT INTO `events_database`.`sector_capacity` (`id`, `capacity`, `free`, `sector_id`) VALUES ('1', '100', '100', '1');
INSERT INTO `events_database`.`sector_capacity` (`id`, `capacity`, `free`, `sector_id`) VALUES ('2', '140', '140', '1');
INSERT INTO `events_database`.`sector_capacity` (`id`, `capacity`, `free`, `sector_id`) VALUES ('3', '110', '110', '1');
INSERT INTO `events_database`.`sector_capacity` (`id`, `capacity`, `free`, `sector_id`) VALUES ('4', '100', '100', '2');
