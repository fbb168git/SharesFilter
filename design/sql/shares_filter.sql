/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/2/18 13:29:12                           */
/*==============================================================*/


drop table if exists base_info;

drop table if exists filter_result;

drop table if exists shares;

drop table if exists simulate_trade;

drop table if exists three_red;

/*==============================================================*/
/* Table: base_info                                             */
/*==============================================================*/
create table base_info
(
   code                 int not null,
   name                 varchar(10),
   increPer             float,
   increase             float,
   todayStartPri        float,
   yestodEndPri         float,
   nowPri               float,
   todayMax             float,
   todayMin             float,
   traNumber            bigint,
   traAmount            bigint,
   updateTime           date,
   primary key (code)
);

/*==============================================================*/
/* Table: filter_result                                         */
/*==============================================================*/
create table filter_result
(
   id                   int not null,
   result               text,
   reason               int,
   update_time          date,
   primary key (id)
);

/*==============================================================*/
/* Table: shares                                                */
/*==============================================================*/
create table shares
(
   code                 int not null,
   name                 varchar(10),
   update_time          date,
   primary key (code)
);

alter table shares comment '¹ÉÆ±ÁÐ±í';

/*==============================================================*/
/* Table: simulate_trade                                        */
/*==============================================================*/
create table simulate_trade
(
   id                   int not null,
   code                 int,
   order_num            varchar(20),
   buy_price            float,
   buy_time             date,
   buy_num              int,
   buy_amount           float,
   current_earn         float,
   sale_price           float,
   sale_time            date,
   update_time          date,
   reason               int,
   primary key (id)
);

/*==============================================================*/
/* Table: three_red                                             */
/*==============================================================*/
create table three_red
(
   code                 int not null,
   name                 varchar(10),
   today_increase       float,
   today_start_price    float,
   today_end_price      float,
   today_trade_num      bigint,
   today_max_price      float,
   today_min_price      float,
   pre_increase         float,
   pre_start_price      float,
   pre_end_price        float,
   pre_trade_num        bigint,
   pre_max_price        float,
   pre_min_price        float,
   prepre_increase      float,
   prepre_start_price   float,
   prepre_end_price     float,
   prepre_trade_num     bigint,
   prepre_max_price     float,
   prepre_min_price     float,
   update_time          date,
   primary key (code)
);

alter table three_red add constraint FK_Reference_1 foreign key (code)
      references shares (code) on delete restrict on update restrict;

