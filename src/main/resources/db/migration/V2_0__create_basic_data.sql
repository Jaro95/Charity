INSERT INTO user(email, name, last_name, password, enabled, token)
VALUES ('superAdmin@admin',
        'Jarek',
        'Marciniak',
        '$2a$10$ju/OVn0M9gRaERwvsEGD3uboKU8psigrVOZ0JN8S3sdscLEdK8OUy',
        1,
        'verified'),
       ('admin@admin',
        'Krzysztof',
        'Wysocki',
        '$2a$10$noj9xJbK1O24Qp54AEYtR.cDiQbl9/MGcMJUCGQaCFN0yO2vtbzWK',
        1,
        'verified'),
        ('user@user',
        'Gohan',
        'Saiyan',
        '$2a$10$RAQmc7JXKC4VQgNr7F.cxO7dUXcdtXwriFmyC9ZdC8F18ZWiD0Mwe',
        1,
        'verified');

INSERT INTO role(name)
VALUES ('ROLE_SUPER_ADMIN'),
       ('ROLE_ADMIN'),
       ('ROLE_USER');

INSERT INTO user_role
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 2),
       (2, 3),
       (3, 3);

INSERT INTO category(name)
VALUES ('ubrania, które nadają się do ponownego użycia'),
       ('ubrania, do wyrzucenia'),
       ('zabawki'),
       ('książki'),
       ('inne');

INSERT INTO institution(name, description)
VALUES ('Dbam o Zdrowie', 'Pomoc dzieciom z ubogich rodzin.'),
       ('A kogo', 'Pomoc wybudzaniu dzieci ze śpiączki.'),
       ('Dla dzieci', 'Pomoc osobom znajdującym się w trudnej sytuacji życiowej.'),
       ('Bez domu', 'Pomoc dla osób nie posiadających miejsca zamieszkania.');