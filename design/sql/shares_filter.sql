/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/2/18 16:51:32                           */
/*==============================================================*/


alter table base_info
   drop primary key;

drop table if exists base_info;

alter table filter_result
   drop primary key;

drop table if exists filter_result;

alter table shares
   drop primary key;

drop table if exists shares;

alter table simulate_trade
   drop primary key;

drop table if exists simulate_trade;

alter table three_red
   drop primary key;

drop table if exists three_red;

/*==============================================================*/
/* Table: base_info                                             */
/*==============================================================*/
create table base_info
(
   code                 varchar(10) not null,
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
   updateTime           date
);

alter table base_info
   add primary key (code);

/*==============================================================*/
/* Table: filter_result                                         */
/*==============================================================*/
create table filter_result
(
   id                   int not null,
   result               text,
   reason               int,
   update_time          date
);

alter table filter_result
   add primary key (id);

/*==============================================================*/
/* Table: shares                                                */
/*==============================================================*/
create table shares
(
   code                 varchar(10) not null,
   name                 varchar(10),
   update_time          date
);

alter table shares comment '股票列表';

alter table shares
   add primary key (code);

/*==============================================================*/
/* Table: simulate_trade                                        */
/*==============================================================*/
create table simulate_trade
(
   id                   int not null,
   code                 varchar(10),
   order_num            varchar(20),
   buy_price            float,
   buy_time             date,
   buy_num              int,
   buy_amount           float,
   current_earn         float,
   sale_price           float,
   sale_time            date,
   update_time          date,
   reason               int
);

alter table simulate_trade
   add primary key (id);

/*==============================================================*/
/* Table: three_red                                             */
/*==============================================================*/
create table three_red
(
   code                 varchar(10) not null,
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
   update_time          date
);

alter table three_red
   add primary key (code);

