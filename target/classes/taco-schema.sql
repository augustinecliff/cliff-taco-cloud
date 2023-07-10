create table if not exists Taco (
                                    id bigint primary key auto_increment,
                                    name varchar(50) not null,
                                    createdAt timestamp not null
);
create table if not exists Taco_Ingredients (
                                                taco bigint not null,
                                                ingredient varchar(4) not null
);


