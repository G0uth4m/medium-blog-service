insert into app_user_role (role_id, role_name) values (1, 'ROLE_USER');
insert into app_user_role (role_id, role_name) values (2, 'ROLE_ADMIN');

insert into app_user (username, first_name, last_name, password, email, profile_pic_url, created_at, last_modified_at) values ('admin', 'The', 'Administrator', '$2a$12$bYkGn0CpHDh5HhnYCM1pZOW342oAF.tnGjWemP51Q1UwVPYwIW6MC', 'admin@test.com', 'https://test.com/admin.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into app_user_roles (app_user_user_id, roles_role_id) values (1, 1);
insert into app_user_roles (app_user_user_id, roles_role_id) values (1, 2);
