/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/2/22 0:17:05                            */
/*==============================================================*/


drop table if exists filter_result;

drop table if exists shares;

drop table if exists simulate_trade;

drop table if exists three_red;

drop table if exists trade_detail;

/*==============================================================*/
/* Table: filter_result                                         */
/*==============================================================*/
create table filter_result
(
   id                   int not null auto_increment,
   code                 varchar(20),
   filter_name          char(50),
   level                int,
   trade_date           date,
   update_time          datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: shares                                                */
/*==============================================================*/
create table shares
(
   code                 varchar(20) not null,
   name                 varchar(20),
   update_time          datetime,
   primary key (code)
);

/*==============================================================*/
/* Table: simulate_trade                                        */
/*==============================================================*/
create table simulate_trade
(
   id                   int not null,
   code                 varchar(10),
   order_num            varchar(20),
   buy_price            float,
   buy_time             datetime,
   buy_num              int,
   buy_amount           float,
   sale_price           float,
   sale_time            date,
   current_earn         float,
   auto_buy             boolean,
   auto_sale            boolean,
   filter_result_id     int,
   update_time          datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: three_red                                             */
/*==============================================================*/
create table three_red
(
   code                 varchar(20) not null,
   name                 varchar(20),
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
   update_time          datetime,
   primary key (code)
);

/*==============================================================*/
/* Table: trade_detail                                          */
/*==============================================================*/
create table trade_detail
(
   code                 varchar(20) not null,
   traDate              date not null,
   name                 varchar(20),
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
   updateTime           datetime,
   primary key (code, traDate)
);

