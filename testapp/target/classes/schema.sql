CREATE TABLE "employee" (
	"employee_id" serial NOT NULL,
	"first_name" varchar(255) NOT NULL,
	"last_name" varchar(255) NOT NULL,
	"department_id" integer NOT NULL,
	"job_title" varchar(255) NOT NULL,
	"gender" varchar(255) NOT NULL,
	"date_of_birth" DATE NOT NULL,
	CONSTRAINT "employee_pk" PRIMARY KEY ("employee_id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "department" (
	"id" serial NOT NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT "department_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



ALTER TABLE "employee" ADD CONSTRAINT "employee_fk0" FOREIGN KEY ("department_id") REFERENCES "department"("id");

