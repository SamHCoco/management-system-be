create table if not exists `employee_department` (
    `id` bigint unsigned auto_increment not null,
    `name` varchar(255) not null unique,
    `created_at` datetime,
    `last_modified_at` datetime,
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

create table if not exists `employee` (
    `id` bigint unsigned auto_increment not null,
    `first_name` varchar(255) not null,
    `middle_names` varchar(255),
    `last_name` varchar(255) not null,
    `department_id` bigint unsigned not null,
    `email` varchar(255) not null unique,
    `phone` varchar(20) not null unique,
    `address_first_line` varchar(255) not null,
    `address_second_line` varchar(255),
    `address_city` varchar(255) not null,
    `address_post_code` varchar(15) not null,
    `created_at` datetime,
    `last_modified_at` datetime,
    primary key (id),
    foreign key (`department_id`) references `employee_department` (`id`)
);

create table if not exists `product` (
    `id` bigint unsigned auto_increment not null,
    `name` varchar(255) not null,
    `price` decimal(9,2) not null,
    `created_at` datetime,
    `last_modified_at` datetime,
    `deleted` boolean not null default 0,
    primary key (`id`)
) engine=InnoDB default charset=utf8mb4;

create table if not exists `product_inventory` (
    `id` bigint unsigned auto_increment not null,
    `product_id` bigint unsigned not null,
    `quantity` bigint unsigned not null,
    `created_at` datetime,
    `last_modified_at` datetime,
    `deleted` boolean not null default 0,
    primary key (`id`),
    constraint `fk_product_inventory_product`
        foreign key (`product_id`) references `product` (`id`)
        on update cascade
        on delete restrict
) engine=InnoDB default charset=utf8mb4;

