/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/2/19 0:34:48                            */
/*==============================================================*/


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

alter table trade_detail
   drop primary key;

drop table if exists trade_detail;

/*==============================================================*/
/* Table: filter_result                                         */
/*==============================================================*/
create table filter_result
(
   id                   int not null,
   result               text,
   reason               int,
   update_time          datetime
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
   update_time          datetime
);

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
   cur_increase         float,
   cur_start_price      float,
   cur_end_price        float,
   cur_trade_num        bigint,
   cur_max_price        float,
   cur_min_price        float,
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
   cur_trade_date       date,
   pre_trade_date       date,
   prepre_trade_date    date,
   update_time          datetime
);

alter table three_red
   add primary key (code);

/*==============================================================*/
/* Table: trade_detail                                          */
/*==============================================================*/
create table trade_detail
(
   code                 varchar(10) not null,
   traDate              date not null,
   name                 varchar(10),
   increPer             float,
   increase             float,
   todayStartPri        float,
   yestodEndPri         float,
   nowPri               float,
   todayMax             float,
   todayMin             float,
   traNumber            bigint,
   traAmount            double,
   minurl               varchar(200),
   dayurl               varchar(200),
   weekurl              varchar(200),
   monthurl             varchar(200),
   status               int,
   updateTime           datetime
);

alter table trade_detail
   add primary key (code, traDate);

