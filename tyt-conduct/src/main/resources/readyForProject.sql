INSERT INTO tt_user (name, password, roles, is_deleted)
VALUES ('JOKER', 'JOKER', '{0, 1, 2}', false);

INSERT INTO tt_terminal(term_name, term_ability_create, term_ability_delete, term_ability_update, soft_delete,
                        pagination)
VALUES ('Auth_Service', false, false, false, false, false);
INSERT INTO tt_terminal(term_name, term_ability_create, term_ability_delete, term_ability_update, soft_delete,
                        pagination)
VALUES ('Terminal_Service', false, false, false, false, false);
INSERT INTO tt_terminal(term_name, term_ability_create, term_ability_delete, term_ability_update, soft_delete,
                        pagination)
VALUES ('Conduct_User_Service', true, true, true, true, false);
INSERT INTO tt_terminal(term_name, term_ability_create, term_ability_delete, term_ability_update, soft_delete,
                        pagination)
VALUES ('Mistake_Enter_Service', true, true, true, true, false);
INSERT INTO tt_terminal(term_name, term_ability_create, term_ability_delete, term_ability_update, soft_delete,
                        pagination)
VALUES ('Mistake_Pagination_Service', false, false, false, false, true);