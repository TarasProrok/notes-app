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
    user_id        uuid          primary key,
	username       varchar(50)   not null,
	password       varchar(500)  not null,
    role           varchar(32)   not NULL,
	enabled        boolean       not null,
	nickname       varchar(100)  not null,
	birthday       date,
	gender_id      integer       not null default 0,
	created_date   timestamp,
	updated_date   timestamp,
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
