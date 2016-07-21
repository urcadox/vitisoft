# --- !Ups

create type userkind as enum('plotowner', 'auditor');
create type measureditem as enum('temperature', 'weather', 'humidity', 'pesticide', 'picture');

create table "user" (
    user_id uuid primary key,
    email text not null,
    kind userkind not null,
    name text not null,
    password_hash text not null,
    created_at timestamptz not null
);

create table plot (
    plot_id uuid primary key,
    name text not null,
    user_id uuid not null,
    position box2d not null,
    created_at timestamptz not null
);

create table plot_audit (
    plot_audit_id uuid primary key,
    plot_id uuid not null,
    date date not null,
    user_id uuid not null
);

create table plot_measurement (
    plot_measurement_id uuid primary key,
    plot_audit_id uuid not null,
    value json not null,
    item measureditem not null,
    timestamp timestamptz not null
);

alter table only 
plot_measurement add constraint plot_audit_plot_audit_id_fkey foreign key (plot_audit_id) references plot_audit(plot_audit_id);
alter table only 
plot add constraint user_user_id_fkey foreign key (user_id) references "user"(user_id);
alter table only 
plot_audit add constraint plot_plot_id_fkey foreign key (plot_id) references plot(plot_id);
alter table only 
plot_audit add constraint user_user_id_fkey foreign key (user_id) references "user"(user_id);

# --- !Downs

drop table plot_measurement;
drop table plot_audit;
drop table plot;
drop table "user";
drop type measureditem;
drop type userkind;
