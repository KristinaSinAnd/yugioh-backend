# README

## Table of contents
* [Overview](#overview)
* [Technologies](#technologies)
* [Installation](#installation)
* [Usage](#usage)
* [Project Status](#project-status)
* [Team](#team)
* [Contributing](#contributing)
* [Licence](#licence)
* [Contact](#contact)


## Overview

Yugioh Storage System provides a way to easily manage Yugioh Card inventory in a collector's or seller's storage, and share the information with the investors or potential buyers.
It currently consists of a separate [front-end](https://github.com/gatisi/yugioh-frontend) and [back-end](https://github.com/gatisi/yugioh-backend).

## Technologies
* Java 1.8
* Spring Boot 2.3.2
* MySQL


## Installation

1. Above the list of files, click the green "Download Code" button.
2. To clone the repository using HTTPS, under "Clone with HTTPS", click the "Copy" simbol.
3. Open Git Bash.
4. Change the current working directory to the location where you want the cloned directory.
5. Type git clone, and then paste the URL you copied earlier.
```bash
$ git clone https://github.com/gatisi/yugioh-backend.git
```
6. Press Enter to create your local clone.
```bash
$ git clone https://github.com/gatisi/yugioh-backend.git
> Cloning into `Yugioh-storage-backend`...
> remote: Counting objects: 10, done.
> remote: Compressing objects: 100% (8/8), done.
> remove: Total 10 (delta 1), reused 10 (delta 1)
> Unpacking objects: 100% (10/10), done.
```
7. Create a new file in your Resources folder callled "application.properties".
8. Enter the following information in your "application.properties" file, modifying the variables to match your host, database, usernames and passwords:

```bash
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=none
spring.datasource.url=jdbc:mysql://localhost:3306/yugioh?useSSL=false&serverTimezone=UTC
spring.datasource.username=YOUR LOCALHOST USERNAME HERE
spring.datasource.password=YOUR LOCALHOST PASSWORD HERE
spring.jackson.serialization.fail-on-empty-beans=false
app.authentication.signature.secret=wTNw9IlZePVwppHmbNvghffhwwTNw9IlZePVwppHmbNvw
app.authentication.validity.period=36000000
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=YOUR EMAIL ADDRESS HERE
spring.mail.password=YOUR EMAIL PASSWORD HERE (USE APP PASS WHEREVER POSSIBLE)
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

```
9. Create a new schema in your DB called 'yugioh'.

10. Run the app as normal. This should set up the basic structure of the database system.

11. Check if the DB 'yugioh' has all tables available. You can see which tables should have been created during the initial run of the app in the [SQL file](https://github.com/gatisi/yugioh-backend/blob/master/src/main/Yugioh.sql)
12. Copy the creation of views v_article and v_stock_item as follows (or go to the [SQL file](https://github.com/gatisi/yugioh-backend/blob/master/src/main/Yugioh.sql) to copy):
```sql
CREATE VIEW v_article
		(
		 id_articles,
         booster_set,
		 card_name,
		 card_type,
         edition,
         rarity,
         card_count
		)
AS
	SELECT
		a.id_articles,
		a.booster_set,
        a.card_name,
        a.card_type,
        a.edition,
        a.rarity,
		COUNT(s.id_stock_items)
	FROM article a
	      LEFT JOIN stock_items s ON a.id_articles = s.id_article
	GROUP BY a.id_articles;

CREATE VIEW v_stock_item(
id_stock_items,
card_condition,
card_value,
card_value_when_sold,
comments,
in_shop,
id,
storage_name,
id_articles,
booster_set,
card_name,
card_type,
edition,
rarity) AS SELECT
s.id_stock_items,
s.card_condition,
s.card_value,
s.card_value_when_sold,
s.comments,
s.in_shop,
c.id,
c.storage_name,
a.id_articles,
a.booster_set,
a.card_name,
a.card_type,
a.edition,
a.rarity
FROM stock_items s
	      LEFT JOIN card_storage c ON c.id = s.id_card_storage
          LEFT JOIN article a ON a.id_articles = s.id_article
```
13. Paste the SQL & execute it to add the views to your local DB.


## Usage

This is the back-end to the Yugioh Storage System. To use it, you must also download the [front-end](https://github.com/gatisi/yugioh-frontend).
Once you have the front-end as well, run this application as usual and then follow the instructions of the front end.
Images showcasing the app will soon be available in the [front-end README file](https://github.com/gatisi/yugioh-frontend/blob/master/README.md).


## Project Status
Project is: in progress. It is functional, but  with validation needed and tests written.

## Team
@gatisi,
@KristinaSinAnd, 
@OlegKataev, 
@Lady-Vi


## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.
