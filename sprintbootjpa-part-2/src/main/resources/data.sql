-- employee_table

insert into employee_Table(emp_Id, emp_First_Name, emp_Last_Name, emp_Salary, emp_Age)
values (1, 'Corey', 'Schafer', 100.0, 46);
insert into employee_Table(emp_Id, emp_First_Name, emp_Last_Name, emp_Salary, emp_Age)
values (2, 'Micheal', 'Jackson', 140.0, 35);
insert into employee_Table(emp_Id, emp_First_Name, emp_Last_Name, emp_Salary, emp_Age)
values (3, 'Adam', 'Singh', 110.0, 40);
insert into employee_Table(emp_Id, emp_First_Name, emp_Last_Name, emp_Salary, emp_Age)
values (4, 'Jack', 'Sparrow', 120.0, 52);
insert into employee_Table(emp_Id, emp_First_Name, emp_Last_Name, emp_Salary, emp_Age)
values (5, 'Thakur', 'Singh', 130.0, 37);
insert into employee_Table(emp_Id, emp_First_Name, emp_Last_Name, emp_Salary, emp_Age)
values (6, 'Elon', 'Musk', 150.0, 31);
insert into employee_Table(emp_Id, emp_First_Name, emp_Last_Name, emp_Salary, emp_Age)
values (7, 'Kate', 'Darwin', 90.0, 28);
insert into employee_Table(emp_Id, emp_First_Name, emp_Last_Name, emp_Salary, emp_Age)
values (8, 'John', 'Mark', 100.0, 20);
insert into employee_Table(emp_Id, emp_First_Name, emp_Last_Name, emp_Salary, emp_Age)
values (9, 'Raj', 'Singh', 80.0, 50);
insert into employee_Table(emp_Id, emp_First_Name, emp_Last_Name, emp_Salary, emp_Age)
values (10, 'Love', 'Musk', 70.0, 25);


-- payment table
insert into single_table_payment(id, amount, cheque_number, card_number, mode)
values (1, 100, '12345678', null, 'ch');
insert into single_table_payment(id, amount, cheque_number, card_number, mode)
values (2, 90, null, '13003234', 'cc');
insert into single_table_payment(id, amount, cheque_number, card_number, mode)
values (3, 80, '12345699', null, 'ch');
insert into single_table_payment(id, amount, cheque_number, card_number, mode)
values (4, 110, null, '11343210', 'cc');


-- Table Per Class credit_card table
insert into credit_card(id, amount, card_number)
values (1, 100.2, '12121233');
insert into credit_card(id, amount, card_number)
values (2, 90, '11223344');
insert into credit_card(id, amount, card_number)
values (3, 110, '44443333');
insert into credit_card(id, amount, card_number)
values (4, 80, '44445555');


-- Table Per Class bank_cheque table
insert into bank_cheque(id, amount, cheque_number)
values (1, 110, '44443333');
insert into bank_cheque(id, amount, cheque_number)
values (2, 90, '12345678');
insert into bank_cheque(id, amount, cheque_number)
values (3, 80, '55550000');
insert into bank_cheque(id, amount, cheque_number)
values (4, 100, '11112222');


-- ID  AGE  	FIRST_NAME  	LAST_NAME  	BASIC_SALARY  	BONUS_SALARY  	SPECIAL_ALLOWANCE_SALARY  	TAX_AMOUNT
insert into emb_employee(id, first_name, last_name, age, basic_salary, bonus_salary, special_allowance_salary,
                         tax_amount)
values (1, 'John', 'Smith', 45, 100, 40, 20, 10);
insert into emb_employee(id, first_name, last_name, age, basic_salary, bonus_salary, special_allowance_salary,
                         tax_amount)
values (2, 'Micheal', 'Jordan', 34, 150, 30, 10, 8);

insert into emb_employee(id, first_name, last_name, age, basic_salary, bonus_salary, special_allowance_salary,
                         tax_amount)
values (3, 'Adam', 'Smith', 45, 200, 40, 30, 20);
insert into emb_employee(id, first_name, last_name, age, basic_salary, bonus_salary, special_allowance_salary,
                         tax_amount)
values (4, 'Athisii', 'Ekhe', 25, 90, 20, 10, 5);
insert into emb_employee(id, first_name, last_name, age, basic_salary, bonus_salary, special_allowance_salary,
                         tax_amount)
values (5, 'Mathew', 'Tom', 20, 80, 30, 10, 5);
