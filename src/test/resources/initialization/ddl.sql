-- Create table for product categories
create table product_category
(
    category_id        bigint auto_increment
        primary key,
    category_name      varchar(255) null,
    sale_limitation    varchar(255) null,
    storage_conditions varchar(255) null
);

-- Create table for departments
create table department
(
    department_id bigint auto_increment
        primary key,
    name          varchar(255) null,
    image_path    varchar(255) null
);

-- Create table for products
create table product
(
    category_id  bigint         null,
    product_id   bigint auto_increment
        primary key,
    retail_price decimal(38, 2) null,
    description  varchar(255)   null,
    name         varchar(255)   null,
    image_url    varchar(255)   null,
    constraint FK5cypb0k23bovo3rn1a5jqs6j4
        foreign key (category_id) references product_category (category_id)
);

-- Create table for inventory
create table inventory
(
    inventory_id     bigint auto_increment
        primary key,
    product_id       bigint         null,
    quantity         int            null,
    whole_sale_price decimal(38, 2) null,
    best_before      datetime(6)    null,
    inventory_date   datetime(6)    null,
    constraint FKp7gj4l80fx8v0uap3b2crjwp5
        foreign key (product_id) references product (product_id)
);

-- Create table for accounts
create table account
(
    id         bigint auto_increment
        primary key,
    email      varchar(255) null,
    first_name varchar(255) null,
    last_name  varchar(255) null,
    user_id    bigint       null,
    constraint UKh6dr47em6vg85yuwt4e2roca4
        unique (user_id),
    constraint FK7m8ru44m93ukyb61dfxw0apf6
        foreign key (user_id) references user (id)
);

-- Create table for customer orders
create table customer_order
(
    customer_id    bigint         null,
    order_id       bigint auto_increment
        primary key,
    total_amount   decimal(38, 2) null,
    order_date     datetime(6)    null,
    payment_method varchar(255)   null,
    status         varchar(255)   null,
    constraint FK25ujycrbmy7l2trixj9pd212f
        foreign key (customer_id) references account (id)
);

-- Create table for order lines
create table order_line
(
    allocation_id bigint null,
    order_id      bigint null,
    order_line_id bigint auto_increment
        primary key,
    quantity      int    null,
    constraint FKhx2sh9w4yimwp265ak68pa7i5
        foreign key (order_id) references customer_order (order_id),
    constraint FKmbf4cfqqobti54xy1wpl6hr16
        foreign key (allocation_id) references product (product_id)
);

-- Create table for product allocations
create table productallocation
(
    department_id bigint not null,
    product_id    bigint not null,
    constraint FK2rpc9835k15t1h0xxffxixxt
        foreign key (product_id) references product (product_id),
    constraint FKa876f02x8k420w5ha4jj5shmy
        foreign key (department_id) references department (department_id)
);

-- Create table for users
create table user
(
    id       bigint auto_increment
        primary key,
    password varchar(255) not null,
    username varchar(255) not null,
    constraint UKsb8bbouer5wak8vyiiy4pf2bx
        unique (username)
);

-- Create table for user roles
create table user_roles
(
    user_id bigint       not null,
    role    varchar(255) null,
    constraint FK55itppkw3i07do3h7qoclqd4k
        foreign key (user_id) references user (id)
);

-- Create table for cart
create table cart
(
    cart_id       bigint auto_increment
        primary key,
    user_id       bigint      not null,
    product_id    bigint      not null,
    quantity      int         not null,
    addition_date datetime(6) not null,
    constraint FK3d704slv66tw6x5hmbm6p2x3u
        foreign key (product_id) references product (product_id),
    constraint FKl70asp4l4w0jmbm1tqyofho4o
        foreign key (user_id) references user (id)
);
