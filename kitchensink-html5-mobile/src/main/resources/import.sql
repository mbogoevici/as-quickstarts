--
-- JBoss, Home of Professional Open Source
-- Copyright 2012, Red Hat, Inc., and individual contributors
-- by the @authors tag. See the copyright.txt in the distribution for a
-- full listing of individual contributors.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- http://www.apache.org/licenses/LICENSE-2.0
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

-- You can use this file to load seed data into the database using SQL statements

insert into Country (id, name, code) values (1, 'United Kingdom', 'GB')
insert into Country (id, name, code) values (2, 'United States of America', 'US')
insert into Country (id, name, code) values (3, 'Canada', 'CA')

insert into Member (id, name, email, phoneNumber, address1, address2, city, postalCode, country_id) values (1, 'John Smith', 'john.smith@gov.uk', '02011234567', '10 Downing Street', 'Westminster', 'London', 'SW1A 2AA', 1)