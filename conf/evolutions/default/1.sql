# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table energie_deal (
  id                            bigserial not null,
  resource_id                   bigint,
  supplier                      varchar(255),
  name                          varchar(255),
  start_at                      timestamptz,
  end_at                        timestamptz,
  unit_price                    float,
  postal_code                   integer,
  subscription_price            float,
  user_id                       bigint,
  constraint pk_energie_deal primary key (id)
);

create table energie_resource (
  id                            bigserial not null,
  key                           varchar(255),
  name                          varchar(255),
  unit                          varchar(255),
  constraint uq_energie_resource_key unique (key),
  constraint pk_energie_resource primary key (id)
);

create table energie_state (
  id                            bigserial not null,
  value                         bigint,
  coef                          bigint,
  diff                          bigint,
  unit_price                    float,
  subscription_price            float,
  supplier                      varchar(255),
  deal_id                       bigint,
  date                          timestamptz,
  constraint pk_energie_state primary key (id)
);

create table energie_user (
  id                            bigserial not null,
  email                         varchar(255) not null,
  name                          varchar(255),
  first_name                    varchar(255),
  last_name                     varchar(255),
  password                      varchar(255),
  access_token                  varchar(1000),
  constraint uq_energie_user_email unique (email),
  constraint pk_energie_user primary key (id)
);

alter table energie_deal add constraint fk_energie_deal_resource_id foreign key (resource_id) references energie_resource (id) on delete restrict on update restrict;
create index ix_energie_deal_resource_id on energie_deal (resource_id);

alter table energie_deal add constraint fk_energie_deal_user_id foreign key (user_id) references energie_user (id) on delete restrict on update restrict;
create index ix_energie_deal_user_id on energie_deal (user_id);

alter table energie_state add constraint fk_energie_state_deal_id foreign key (deal_id) references energie_deal (id) on delete restrict on update restrict;
create index ix_energie_state_deal_id on energie_state (deal_id);


# --- !Downs

alter table if exists energie_deal drop constraint if exists fk_energie_deal_resource_id;
drop index if exists ix_energie_deal_resource_id;

alter table if exists energie_deal drop constraint if exists fk_energie_deal_user_id;
drop index if exists ix_energie_deal_user_id;

alter table if exists energie_state drop constraint if exists fk_energie_state_deal_id;
drop index if exists ix_energie_state_deal_id;

drop table if exists energie_deal cascade;

drop table if exists energie_resource cascade;

drop table if exists energie_state cascade;

drop table if exists energie_user cascade;

