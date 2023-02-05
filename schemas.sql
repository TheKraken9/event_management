create database ticketPlace;

create table place(
    id serial not null primary key ,
    namePlace varchar(100) not null ,
    maxFreePlace int not null ,
    maxVipPlace int not null
);

insert into place(namePlace, maxFreePlace, maxVipPlace) VALUES ('Colesium',3000, 7000);
insert into place(namePlace, maxFreePlace, maxVipPlace) VALUES ('Palais des sports',2000, 6000);
insert into place(namePlace, maxFreePlace, maxVipPlace) VALUES ('Antsahamanitra',1500, 2300);
insert into place(namePlace, maxFreePlace, maxVipPlace) VALUES ('Esca',700, 1200);

create table event(
    id serial primary key not null ,
    nameEvent varchar(100) not null ,
    idPlace int references place(id) not null ,
    dateof timestamp not null ,
    nbZone int not null
);

create table zoneProgramming(
    id serial primary key not null ,
    idEvent int references event(id) not null ,
    idPlace int references place(id) not null ,
    nbOfZone int not null ,
    minSize int not null ,
    maxSize int not null ,
    price numeric(10, 2) not null
);

create table subZone(
    idEvent int references event(id) not null ,
    zoneNb int not null ,
    placeNb int not null
);

create table freePlace(
    id serial primary key not null ,
    idEvent int references event(id) not null ,
    idPlace int references place(id) not null ,
    price numeric(10, 2) not null
);

create table sellingFree(
    id serial primary key not null ,
    idEvent int references event(id) not null ,
    nbPlaces int not null
);

create table sellingVip(
    id serial primary key not null ,
    idEvent int references event(id) not null ,
    nameClient varchar(50) not null ,
    beginDate timestamp not null ,
    lastDate timestamp not null,
    nbOfZone int not null
);

create table listOfReservation(
    id serial primary key not null ,
    idEvent int references event(id) not null ,
    nameClient varchar(50) not null ,
    nbOfPlace int not null ,
    stateOfReservation int references colorCode(stateOfReservation) not null
);

create table colorCode(
    id serial primary key not null ,
    stateOfReservation int unique not null ,
    colorCode varchar(50) not null,
    state varchar(50) not null
);

insert into colorCode(stateOfReservation, colorCode, state) values (1, 'gray', 'disponible');
insert into colorCode(stateOfReservation, colorCode, state) values (10, 'green', 'reservé');
insert into colorCode(stateOfReservation, colorCode, state) values (20, 'red', 'en attente');
insert into colorCode(stateOfReservation, colorCode, state) values (30, 'blue', 'pas disponible');

create table customTime(
    id serial primary key not null ,
    idEvent int references event(id) not null ,
    idZone int not null ,
    minBasedTime timestamp not null ,
    timer bigserial not null ,
    maxBasedTime timestamp not null
);

create view color as
    select listOfReservation.idEvent as idEvent, listOfReservation.nameClient as nameClient, listOfReservation.nbOfPlace as nbOfPlace, cC.colorCode as color, cC.state as state, listOfReservation.stateOfReservation as stateofreservation
    from listOfReservation
    join colorCode cC on cC.stateOfReservation = listOfReservation.stateOfReservation;

create view checkFreeSell as
    select idEvent, p.maxFreePlace as maxfreeplace, p.maxVipPlace as maxvipplace, sum(nbPlaces) as somme
    from sellingFree
    join event e on e.id = sellingFree.idEvent
    join place p on p.id = e.idPlace
    group by p.maxFreePlace, p.maxVipPlace, idEvent;

create view lastEvent as
    select * from event order by id desc limit 1;
    select *
    from lastEvent;

create view infoEvent as
    select event.id as idEvent, p.namePlace as place, p.id as idPlace, event.dateof as dateof , event.nameEvent as nameEvent, event.nbZone as nbZone, p.maxFreePlace as maxfreeplace, p.maxVipPlace as maxvipplace
    from event
    join place p on event.idPlace = p.id;

create view reservation as
    select event.id as id
    from event
    join zoneProgramming zP on event.id = zP.idEvent
    join listOfReservation lOR on event.id = lOR.idEvent;

truncate event cascade ;
truncate freePlace cascade ;
truncate listOfReservation cascade ;
truncate sellingfree cascade ;
truncate sellingVip cascade ;
truncate subzone cascade;
truncate zoneProgramming cascade ;


/*
select event.nameEvent as nameEvent, event.dateof as timeof, event.nbZone as nbzone
    from event
    join zoneProgramming zP on event.id = zP.idEvent
    join subZone sZ on event.id = sZ.idEvent
    order by sZ.idEvent, event.nameEvent, timeof;
*/

--select * from subzone where idEvent = 10;

--select * from place;

/*
select * from event;
insert into event(nameEvent, idPlace, dateof, nbZone) values('kabaka',1,'2023-02-05 12:00',5);
drop table event cascade ;
*/

--drop table sellingVip cascade;

/*
select * from listOfReservation;
drop table listOfReservation;
*/

/*
drop table colorCode cascade ;
select * from colorCode;
*/

/*
drop view infoEvent;
select * from infoEvent;
*/

--drop table customTime;

--drop view checkFreeSell;

/*
select * from sellingFree;
select * from event;
select * from checkFreeSell;
select * from checkFreeSell where idEvent = 1;
*/


/*
insert into sellingFree(idEvent, nbPlaces) VALUES (1,5);
insert into sellingFree(idEvent, nbPlaces) VALUES (1,15);
insert into sellingFree(idEvent, nbPlaces) VALUES (1,2);

insert into sellingFree(idEvent, nbPlaces) VALUES (2,15);
insert into sellingFree(idEvent, nbPlaces) VALUES (2,2);
*/