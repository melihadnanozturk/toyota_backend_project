INSERT INTO tt_user (name, password, roles, is_deleted)
VALUES ('JOKER', 'JOKER', '{0, 1, 2}', false);

INSERT INTO tt_terminal (term_name, soft_delete, pagination, term_ability_create, term_ability_update,
                         term_ability_delete)
VALUES ('Terminal_Service', false, false, false, false, false);