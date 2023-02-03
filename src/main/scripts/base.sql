\c postgres;

drop database enchere;

create database enchere;

\c enchere;

create table admin (
                       id serial primary key ,
                       email varchar(50) not null unique ,
                       mdp varchar(100) not null ,
                       solde double precision default 0
);
insert into admin (email, mdp) values ('admin@gmail.com', 'admin');

create table client (
                        id serial primary key ,
                        nom varchar(50),
                        prenom varchar(50),
                        email varchar(50),
                        mdp varchar(100)
);
insert into client (nom, prenom, email, mdp) values
                                                 ('Eric', 'Georges', 'eric@gmail.com', 'eric'),
                                                 ('Rodolphe', 'Tsiafara', 'rodolphe@gmail.com', 'rodolphe'),
                                                 ('Hary', 'Lovanomena', 'hary@gmail.com', 'hary'),
                                                 ('Billy', 'Marley', 'billy@gmail.com', 'billy');

create table mouvementSolde (
                                id serial primary key ,
                                nom varchar(50) not null unique
);
insert into mouvementSolde (nom) values
                                     ('depot'),
                                     ('retrait');

create table solde (
                       id serial primary key ,
                       idClient int not null ,
                       idMouvementSolde int not null ,
                       montant double precision default 0,
                       valider boolean default false
);
insert into solde (idClient, idMouvementSolde, montant) values
                                                            (1, 1, 500000),
                                                            (3, 1, 500000),
                                                            (4, 1, 700000),
                                                            (2, 1, 600000),
                                                            (3, 1, 5500000),
                                                            (4, 1, 7070000);


create table categorie (
                           id serial primary key ,
                           categorie varchar(50) not null unique
);
insert into categorie (categorie) values
                                      ('music'),
                                      ('art'),
                                      ('moto'),
                                      ('voiture'),
                                      ('football');


create table enchere (
                         id serial primary key ,
                         produit varchar(100) not null ,
                         description text not null ,
                         prixMin double precision not null ,
                         dateDebut timestamp not null default now(),
                         duree time not null ,
                         idCategorie int not null ,
                         idClient int not null ,
                         fini boolean default false
);
alter table enchere add foreign key (idCategorie) references categorie(id);
alter table enchere add foreign key (idClient) references client(id);


/*delete from vendu;
delete from offre;
delete from enchere;
delete from categorie;*/
insert into enchere (produit, description, prixMin, dateDebut, duree, idCategorie, idClient) values
                                                                                                 ('Maillot', 'lionnel messi', 2000, '2023-01-24 06:00:00', '10:00:00', 5, 1),
                                                                                                 ('Ballon football', 'Coupe du monde 2014', 1000, '2023-01-24 10:00:00', '08:00:00', 5, 2),
                                                                                                 ('Guitare', 'guitare base', 500, '2023-01-23 14:00:00', '05:00:00', 1, 1),
                                                                                                 ('Peinture', 'Couleur varie', 1500, '2023-01-25 07:00:00', '06:00:00', 2, 3);


-- select ((dateDebut+duree) - now()) as dureeRestant from enchere;


create table offre (
                       id serial primary key ,
                       idEnchere int not null ,
                       idClient int not null ,
                       date timestamp default now() ,
                       montant double precision not null
);
alter table offre add foreign key (idClient) references client(id);
alter table offre add foreign key (idEnchere) references enchere(id);
insert into offre (idEnchere, idClient, date, montant) values
                                                           (3, 2, '2023-01-23 09:00:00', 1000),
                                                           (3, 3, '2023-01-23 09:05:00', 2000),
                                                           (3, 4, '2023-01-23 09:10:00', 3000),
                                                           (1, 2, '2023-01-24 09:00:00', 1000),
                                                           (1, 3, '2023-01-24 09:05:00', 2000),
                                                           (1, 4, '2023-01-24 09:10:00', 3000),
                                                           (2, 2, '2023-01-24 09:00:00', 1000),
                                                           (2, 3, '2023-01-24 09:05:00', 2000),
                                                           (2, 4, '2023-01-24 09:10:00', 3000);

create table vendu (
    idOffre int not null unique
);
alter table vendu add foreign key (idOffre) references offre(id);
/*insert into vendu values
                      (3);*/





-- select ((dateDebut+duree) - now()) as dureeRestant from enchere order by id asc;



-- drop table teste;
-- create table teste (
--     id serial primary key ,
--     nom varchar(10) not null
-- );

CREATE TABLE token(
                      id SERIAL PRIMARY KEY ,
                      token VARCHAR(256) NOT NULL,
                      datecreation TIMESTAMP NOT NULL DEFAULT NOW(),
                      dateexpiration TIMESTAMP NOT NULL
);

CREATE TABLE token_user(
                           id SERIAL PRIMARY KEY,
                           id_token INT NOT NULL ,
                           id_admin INT,
                           id_user INT,
                           FOREIGN KEY (id_token) REFERENCES token(id),
                           FOREIGN KEY (id_admin) REFERENCES admin(id),
                           FOREIGN KEY (id_user) REFERENCES client(id)
);

-- SELECT CASE when dateFin > CURRENT_TIMESTAMP THEN 'En cours' when dateFin<CURRENT_TIMESTAMP THEN 'Expire' END as delaiVente FROM Vente WHERE id = '" + this.id + "'"

