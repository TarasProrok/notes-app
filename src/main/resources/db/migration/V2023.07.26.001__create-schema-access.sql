create schema if not exists access;

create table access.gender(
        gender_id    numeric(1)    primary key,
	name_ukr     varchar(50)   not null,
	name_eng     varchar(50)   not null
);

create unique index ix_gender_gender_id on access.gender (gender_id);

comment  on table  access.gender              is 'Гендер згідно з класифікацією ISO/IEC 5218';
comment  on column access.gender.gender_id    is 'ID гендеру';
comment  on column access.gender.name_ukr     is 'Найменування гендеру українською';
comment  on column access.gender.name_eng     is 'Найменування гендеру англійською';

create table access.users(
        user_id      uuid          primary key,
	username     varchar(50)   not null,
	password     varchar(500)  not null,
	enabled      boolean       not null,
	nickname     varchar(100)  not null,
	birthday     date,
	gender_id    numeric(1)    not null default 0,
	created_date date          not null,
	updated_date date          not null,
	constraint fk_users_gender foreign key(gender_id) references access.gender(gender_id)
);
create unique index ix_users_user_id   on access.users (user_id);
create unique index ix_users_username  on access.users (username);
create        index ix_users_gender_id on access.users (gender_id);

comment on table  access.users              is 'Користувачі';
comment on column access.users.user_id      is 'ID користувача';
comment on column access.users.username     is 'Логін користувача';
comment on column access.users.password     is 'Пароль користувача';
comment on column access.users.enabled      is 'Активний користувач';
comment on column access.users.nickname     is 'Псевдонім користувача, що відображається на сайті';
comment on column access.users.birthday     is 'Дата народження користувача';
comment on column access.users.gender_id    is 'ID гендеру користувача згідно з класифікацією ISO/IEC 5218';
comment on column access.users.created_date is 'Дата створення профілю користувача';
comment on column access.users.updated_date is 'Дата останнього коригування профілю користувача';

create table access.authorities (
	authority_id  bigint generated by default as identity(start with 1) primary key,
	user_id       uuid not null,
	authority     varchar(50) not null,
	constraint fk_authorities_users foreign key(user_id) references access.users(user_id)
);
create unique index ix_authorities_authority_id   on access.authorities(authority_id);
create unique index ix_authorities_user_authority on access.authorities(user_id,authority);
create        index ix_authorities_user_id        on access.authorities(user_id);

comment on table  access.authorities               is 'Типи доступу користувачів';
comment on column access.authorities.authority_id  is 'ID типу доступу';
comment on column access.authorities.user_id       is 'ID користувача';
comment on column access.authorities.authority     is 'Тип доступу';