-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema test
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema test
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `test` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `test` ;

-- -----------------------------------------------------
-- Table `test`.`artist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`artist` (
  `ARTIST_ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ARTIST_ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `test`.`language`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`language` (
  `LANGUAGE_ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `LANGUAGE_NAME` VARCHAR(100) NOT NULL,
  `LANGUAGE_LOCALE` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`LANGUAGE_ID`),
  UNIQUE INDEX `LANGUAGE_NAME_UNIQUE` (`LANGUAGE_NAME` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `test`.`artist_translate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`artist_translate` (
  `ARTIST_ID` BIGINT(20) NOT NULL,
  `LANGUAGE_ID` BIGINT(20) NOT NULL,
  `ARTIST_NAME` VARCHAR(100) NOT NULL,
  `ARTIST_DESCRIPTION` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`ARTIST_ID`, `LANGUAGE_ID`),
  INDEX `artist_translaate_fk2_idx` (`LANGUAGE_ID` ASC) VISIBLE,
  CONSTRAINT `artist_translate_fk1`
    FOREIGN KEY (`ARTIST_ID`)
    REFERENCES `test`.`artist` (`ARTIST_ID`)
    ON DELETE CASCADE,
  CONSTRAINT `artist_translate_fk2`
    FOREIGN KEY (`LANGUAGE_ID`)
    REFERENCES `test`.`language` (`LANGUAGE_ID`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `test`.`author`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`author` (
  `AUTHOR_ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`AUTHOR_ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `test`.`author_translate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`author_translate` (
  `AUTHOR_ID` BIGINT(20) NOT NULL,
  `LANGUAGE_ID` BIGINT(20) NOT NULL,
  `AUTHOR_NAME` VARCHAR(100) NOT NULL,
  `AUTHOR_DESCRIPTION` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`AUTHOR_ID`, `LANGUAGE_ID`),
  INDEX `author_translate_fk2_idx` (`LANGUAGE_ID` ASC) VISIBLE,
  CONSTRAINT `author_translate_fk1`
    FOREIGN KEY (`AUTHOR_ID`)
    REFERENCES `test`.`author` (`AUTHOR_ID`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `author_translate_fk2`
    FOREIGN KEY (`LANGUAGE_ID`)
    REFERENCES `test`.`language` (`LANGUAGE_ID`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `test`.`manga`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`manga` (
  `MANGA_ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `MANGA_IMG_URL` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`MANGA_ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `test`.`chapter`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`chapter` (
  `CHAPTER_ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `MANGA_ID` BIGINT(20) NOT NULL,
  PRIMARY KEY (`CHAPTER_ID`),
  INDEX `chapter_fk1_idx` (`MANGA_ID` ASC) VISIBLE,
  CONSTRAINT `chapter_fk1`
    FOREIGN KEY (`MANGA_ID`)
    REFERENCES `test`.`manga` (`MANGA_ID`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `test`.`chapter_translate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`chapter_translate` (
  `CHAPTER_ID` BIGINT(20) NOT NULL,
  `LANGUAGE_ID` BIGINT(20) NOT NULL,
  `CHAPTER_NAME` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`CHAPTER_ID`, `LANGUAGE_ID`),
  INDEX `chapter_translate_fk2_idx` (`LANGUAGE_ID` ASC) VISIBLE,
  CONSTRAINT `chapter_translate_fk1`
    FOREIGN KEY (`CHAPTER_ID`)
    REFERENCES `test`.`chapter` (`CHAPTER_ID`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `chapter_translate_fk2`
    FOREIGN KEY (`LANGUAGE_ID`)
    REFERENCES `test`.`language` (`LANGUAGE_ID`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `test`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`user` (
  `USER_ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `USER_NAME` VARCHAR(100) NOT NULL,
  `USER_EMAIL` VARCHAR(100) NOT NULL,
  `USER_PASSWORD` VARCHAR(100) NOT NULL,
  `USER_ROLE_ID` BIGINT(20) NOT NULL,
  `USER_AVATAR_URL` VARCHAR(100) NOT NULL,
  `USER_REGISTRATION_DATE` DATE NOT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE INDEX `USER_NAME_UNIQUE` (`USER_NAME` ASC) VISIBLE,
  UNIQUE INDEX `USER_EMAIL_UNIQUE` (`USER_EMAIL` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `test`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`comment` (
  `COMMENT_ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `USER_ID` BIGINT(20) NOT NULL,
  `CHAPTER_ID` BIGINT(20) NOT NULL,
  `COMMENT_CONTENT` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`COMMENT_ID`),
  INDEX `comment_fk1_idx` (`USER_ID` ASC) VISIBLE,
  INDEX `comment_fk2_idx` (`CHAPTER_ID` ASC) VISIBLE,
  CONSTRAINT `comment_fk1`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `test`.`user` (`USER_ID`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `comment_fk2`
    FOREIGN KEY (`CHAPTER_ID`)
    REFERENCES `test`.`chapter` (`CHAPTER_ID`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT)
ENGINE = InnoDB
AUTO_INCREMENT = 22
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `test`.`genre`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`genre` (
  `GENRE_ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`GENRE_ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 24
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `test`.`genre_translate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`genre_translate` (
  `GENRE_ID` BIGINT(20) NOT NULL,
  `LANGUAGE_ID` BIGINT(20) NOT NULL,
  `GENRE_NAME` VARCHAR(100) NOT NULL,
  `GENRE_DESCRIPTION` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`GENRE_ID`, `LANGUAGE_ID`),
  INDEX `genre_translate_f2_idx` (`LANGUAGE_ID` ASC) VISIBLE,
  CONSTRAINT `genre_translate_fk1`
    FOREIGN KEY (`GENRE_ID`)
    REFERENCES `test`.`genre` (`GENRE_ID`)
    ON DELETE CASCADE,
  CONSTRAINT `genre_translate_fk2`
    FOREIGN KEY (`LANGUAGE_ID`)
    REFERENCES `test`.`language` (`LANGUAGE_ID`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `test`.`image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`image` (
  `IMAGE_ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `CHAPTER_ID` BIGINT(20) NOT NULL,
  PRIMARY KEY (`IMAGE_ID`),
  INDEX `image_fk1_idx` (`CHAPTER_ID` ASC) VISIBLE,
  CONSTRAINT `image_fk1`
    FOREIGN KEY (`CHAPTER_ID`)
    REFERENCES `test`.`chapter` (`CHAPTER_ID`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT)
ENGINE = InnoDB
AUTO_INCREMENT = 24
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `test`.`image_translate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`image_translate` (
  `IMAGE_ID` BIGINT(20) NOT NULL,
  `LANGUAGE_ID` BIGINT(20) NOT NULL,
  `IMAGE_URL` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`IMAGE_ID`, `LANGUAGE_ID`),
  INDEX `image_translate_fk2_idx` (`LANGUAGE_ID` ASC) VISIBLE,
  CONSTRAINT `image_translate_fk1`
    FOREIGN KEY (`IMAGE_ID`)
    REFERENCES `test`.`image` (`IMAGE_ID`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `image_translate_fk2`
    FOREIGN KEY (`LANGUAGE_ID`)
    REFERENCES `test`.`language` (`LANGUAGE_ID`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `test`.`manga_artist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`manga_artist` (
  `MANGA_ID` BIGINT(20) NOT NULL,
  `ARTIST_ID` BIGINT(20) NOT NULL,
  PRIMARY KEY (`MANGA_ID`, `ARTIST_ID`),
  INDEX `manga_artist_fk2_idx` (`ARTIST_ID` ASC) VISIBLE,
  CONSTRAINT `manga_artist_fk1`
    FOREIGN KEY (`MANGA_ID`)
    REFERENCES `test`.`manga` (`MANGA_ID`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `manga_artist_fk2`
    FOREIGN KEY (`ARTIST_ID`)
    REFERENCES `test`.`artist` (`ARTIST_ID`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `test`.`manga_author`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`manga_author` (
  `MANGA_ID` BIGINT(20) NOT NULL,
  `AUTHOR_ID` BIGINT(20) NOT NULL,
  PRIMARY KEY (`MANGA_ID`, `AUTHOR_ID`),
  INDEX `manga_author_fk2_idx` (`AUTHOR_ID` ASC) VISIBLE,
  CONSTRAINT `manga_author_fk1`
    FOREIGN KEY (`MANGA_ID`)
    REFERENCES `test`.`manga` (`MANGA_ID`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `manga_author_fk2`
    FOREIGN KEY (`AUTHOR_ID`)
    REFERENCES `test`.`author` (`AUTHOR_ID`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `test`.`manga_genre`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`manga_genre` (
  `MANGA_ID` BIGINT(20) NOT NULL,
  `GENRE_ID` BIGINT(20) NOT NULL,
  PRIMARY KEY (`MANGA_ID`, `GENRE_ID`),
  INDEX `manga_genre_fk2_idx` (`GENRE_ID` ASC) VISIBLE,
  CONSTRAINT `manga_genre_fk1`
    FOREIGN KEY (`MANGA_ID`)
    REFERENCES `test`.`manga` (`MANGA_ID`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `manga_genre_fk2`
    FOREIGN KEY (`GENRE_ID`)
    REFERENCES `test`.`genre` (`GENRE_ID`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `test`.`manga_translate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`manga_translate` (
  `MANGA_ID` BIGINT(20) NOT NULL,
  `LANGUAGE_ID` BIGINT(20) NOT NULL,
  `MANGA_NAME` VARCHAR(100) NOT NULL,
  `MANGA_DESCRIPTION` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`MANGA_ID`, `LANGUAGE_ID`),
  INDEX `manga_translate_fk2_idx` (`LANGUAGE_ID` ASC) VISIBLE,
  CONSTRAINT `manga_translate_fk1`
    FOREIGN KEY (`MANGA_ID`)
    REFERENCES `test`.`manga` (`MANGA_ID`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `manga_translate_fk2`
    FOREIGN KEY (`LANGUAGE_ID`)
    REFERENCES `test`.`language` (`LANGUAGE_ID`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `test`.`manga_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`manga_user` (
  `MANGA_ID` BIGINT(20) NOT NULL,
  `USER_ID` BIGINT(20) NOT NULL,
  PRIMARY KEY (`MANGA_ID`, `USER_ID`),
  INDEX `manga_user_fk2_idx` (`USER_ID` ASC) VISIBLE,
  CONSTRAINT `manga_user_fk1`
    FOREIGN KEY (`MANGA_ID`)
    REFERENCES `test`.`manga` (`MANGA_ID`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `manga_user_fk2`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `test`.`user` (`USER_ID`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

LOCK TABLES `test`.`artist` WRITE;
ALTER TABLE `test`.`artist` DISABLE KEYS;
INSERT INTO `test`.`artist` VALUES (1),(2),(3),(4),(5),(6);
ALTER TABLE `test`.`artist` ENABLE KEYS;
UNLOCK TABLES;

LOCK TABLES `test`.`artist_translate` WRITE;
ALTER TABLE `test`.`artist_translate` DISABLE KEYS;
INSERT INTO `test`.`artist_translate` VALUES (1,1,'So-ryeong GI','So-ryeong GI is best known for being the artist of Solo Leveling.'),(1,2,'Со-Рен Джи','Со-Рен Джи наиболее известен как художник Поднятие уровня в одиночку.'),(2,1,'Gwang-Su LEE','Gwang-Su LEE is best known for being the artist of Noblesse S (Light Novel), Ability, Noblesse: Rai\'s Adventure, and Noblesse.'),(2,2,'Гванг-Су Ли','Гванг-Су Ли наиболее известен как художник Дворянство S (Новелла), Способность, Дворянство: Приключение Рея и Дворянство.'),(3,1,'Sou YAYOI','Sou YAYOI is best known for being the author & artist of Blue Hearts, Yowamushi Resound, and ReLIFE.'),(3,2,'Су ЯЙОЙ','Су ЯЙОЙ наиболее известен как автор и художник Синие Сердца, Боящиеся Петь и Повторная Жизнь.'),(4,1,'Nong-nong','Nong-nong is best known for being the artist of Ranker Who Lives a Second Time.'),(4,2,'Нонг-нонг','Нонг-нонг известен тем, что является художником Ранкера, который живет второй раз.'),(5,1,'Yusuke Murata','Yusuke Murata is a Japanese manga artist, best known for illustrating the American football manga Eyeshield 21, in collaboration with writer Riichiro Inagaki. Eyeshield 21 was serialized between July 2002 and June 2009 in Weekly Shōnen Jump, and was later adapted into an anime television series. Murata\'s other major work is his illustration of One\'s One-Punch Man, serialized in the Weekly Young Jump online version.'),(5,2,'Юсуке Мурата','Юсуке Мурата - японский художник по манге, известный благодаря иллюстрированию американской футбольной манги Айшилд 21 в сотрудничестве с писателем Рийчиро Инагаки. Айшилд 21 был сериализован в период с июля 2002 года по июнь 2009 года в Weekly Shōnen Jump, а позже был адаптирован в аниме-сериал. Другая важная работа Мураты - его иллюстрация Ванпанчмен, сериализованная в онлайн-версии Weekly Young Jump.'),(6,1,'Masashi Kishimoto','Masashi Kishimoto is a Japanese manga artist, well known for creating the manga series Naruto which was in serialization from 1999 to 2014. '),(6,2,'Масаси Кишимото','Масаси Кишимото - японский художник по манге, известный тем, что создал серию манги «Наруто», сериал которой проходил с 1999 по 2014 год.\r\n');
ALTER TABLE `test`.`artist_translate` ENABLE KEYS;
UNLOCK TABLES;

LOCK TABLES `test`.`author` WRITE;
ALTER TABLE `test`.`author` DISABLE KEYS;
INSERT INTO `test`.`author` VALUES (1),(2),(3),(4),(5),(6),(7);
ALTER TABLE `test`.`author` ENABLE KEYS;
UNLOCK TABLES;

LOCK TABLES `test`.`author_translate` WRITE;
ALTER TABLE `test`.`author_translate` DISABLE KEYS;
INSERT INTO `test`.`author_translate` VALUES (1,1,'Sung-lak JANG','Sung-lak JANG is best known for being the author of Solo Leveling.'),(1,2,'Сунг-лак ЯНГ','Сунг-лак ЯНГ известен как автор Поднятие уровня в одиночку'),(2,1,'Chugong','Chugong is best known for being the author of Solo Leveling and Solo Leveling (Light Novel).'),(2,2,'Чугонг','Чугонг наиболее известен как автор Solo Leveling и Solo Leveling (Легкий роман).'),(3,1,'Je-Ho SON','Je-Ho SON is best known for being the author of Noblesse S (Light Novel), Eleceed, Ability, Noblesse: Rai\'s Adventure, and Noblesse.'),(3,2,'Джи-Хо Сон','Джи-Хо Сон наиболее известен как автор Дворянство S (Новелла), Способность, Дворянство: Приключение Рея и Дворянство.'),(4,1,'Sou YAYOI','Sou YAYOI is best known for being the author & artist of Blue Hearts, Yowamushi Resound, and ReLIFE.'),(4,2,'Су ЯЙОЙ','Су ЯЙОЙ наиболее известен как автор и художник Синие Сердца, Боящиеся Петь и Повторная Жизнь.'),(5,1,'Do-yeon SA','Do-yeon SA is best known for being the author of Ranker Who Lives a Second Time.'),(5,2,'Да-ён Са','Да-ён Са наиболее известен как автор «Ранкер, который живет второй раз».'),(6,1,'ONE','Manga creator ONE first created One-Punch Man as a web comic, where it quickly went viral, garnering over 10,000,000 hits. Along with One-Punch Man, ONE also writes and draws another series called Mob Psycho 100.'),(6,2,'ВАН','Создатель манги ВАН впервые создал One-Punch Man в виде веб-комикса, где он быстро распространился с невероятной популярностью и собрал более 10 000 000 просмотров. Наряду с One-Punch Man, ONE также пишет и рисует еще одну серию под названием Mob Psycho 100.'),(7,1,'Masashi Kishimoto','Masashi Kishimoto is a Japanese manga artist, well known for creating the manga series Naruto which was in serialization from 1999 to 2014. '),(7,2,'Масаси Кишимото','Масаси Кишимото - японский художник по манге, известный тем, что создал серию манги «Наруто», сериал которой проходил с 1999 по 2014 год.');
ALTER TABLE `test`.`author_translate` ENABLE KEYS;
UNLOCK TABLES;

LOCK TABLES `test`.`chapter` WRITE;
ALTER TABLE `test`.`chapter` DISABLE KEYS;
INSERT INTO `test`.`chapter` VALUES (1,5);
ALTER TABLE `test`.`chapter` ENABLE KEYS;
UNLOCK TABLES;

LOCK TABLES `test`.`chapter_translate` WRITE;
ALTER TABLE `test`.`chapter_translate` DISABLE KEYS;
INSERT INTO `test`.`chapter_translate` VALUES (1,1,'Chapter 1 - One Punch'),(1,2,'Глава 1 - Один Удар');
ALTER TABLE `test`.`chapter_translate` ENABLE KEYS;
UNLOCK TABLES;

LOCK TABLES `test`.`comment` WRITE;
ALTER TABLE `test`.`comment` DISABLE KEYS;
INSERT INTO `test`.`comment` VALUES (15,2,1,'Wow'),(16,3,1,'Cool'),(17,4,1,'That baldy is my hero now *-*'),(18,5,1,'Boring eh'),(21,6,1,'٩(◕‿◕｡)۶');
ALTER TABLE `test`.`comment` ENABLE KEYS;
UNLOCK TABLES;

LOCK TABLES `test`.`genre` WRITE;
ALTER TABLE `test`.`genre` DISABLE KEYS;
INSERT INTO `test`.`genre` VALUES (1),(2),(3),(4),(5),(6),(7),(8),(9),(10),(11),(12),(13),(14),(15),(16),(17),(18),(19),(20),(21),(22),(23);
ALTER TABLE `test`.`genre` ENABLE KEYS;
UNLOCK TABLES;

LOCK TABLES `test`.`genre_translate` WRITE;
ALTER TABLE `test`.`genre_translate` DISABLE KEYS;
INSERT INTO `test`.`genre_translate` VALUES (1,1,'Action','Action is about conflict. Whether with guns, blades, fists, or mysterious powers, these manga feature characters in combat - either to protect themselves or the things or people they value, or simply as a way of life.'),(1,2,'Экшн','Действие о конфликте. Будь то оружие, клинки, кулаки или таинственные способности, эти манги содержат персонажей в бою - либо для защиты себя, либо вещей или людей, которых они ценят, либо просто как образ жизни.'),(2,1,'Adventure','In these manga, characters embark on a journey to explore the world or to search for something. These wanderers travel to many places and meet new people, often encountering hardships along the way, or discovering strengths and weaknesses about themselves that are revealed throughout the adventure.'),(2,2,'Приключения','В этой манге персонажи отправляются в путешествие, чтобы исследовать мир или искать что-то. Эти странники путешествуют по многим местам и знакомятся с новыми людьми, часто сталкиваясь с трудностями на пути или обнаруживая сильные и слабые стороны, которые проявляются во время приключения.'),(3,1,'Fantasy','Fantasy manga take place in a broad range of settings influenced by mythologies, legends, or popular and defining works of the genre such as The Lord of the Rings. They are generally characterized by a low level of technological development, though fantasy stories can just as easily take place in our modern world, or in a Post-apocalyptic society where technology was buried alongside the old world. These manga also tend to feature magic or other extraordinary abilities, strange or mysterious creatures, or humanoid races which coexist with humanity or inhabit their own lands removed from ours.'),(3,2,'Фэнтези','Фэнтезийная манга разворачивается в самых разных местах, на которые влияют мифологии, легенды или популярные и определяющие произведения жанра, такие как «Властелин колец». Они обычно характеризуются низким уровнем технологического развития, хотя фантастические истории могут так же легко происходить в нашем современном мире или в постапокалиптическом обществе, где технологии были похоронены вместе со старым миром. Эта манга также имеет магические или другие необычные способности, странные или таинственные существа или гуманоидные расы, которые сосуществуют с человечеством или населяют их собственные земли, удаленные от наших.'),(4,1,'Manhwa','Manhwa is the Korean word for comics. These titles usually have authors and artists that are Korean, and/or were first published in Korea.'),(4,2,'Манхва','Манхва это корейское слово для комиксов. Эти названия обычно имеют авторов и художников, которые являются корейскими и / или были впервые опубликованы в Корее.'),(5,1,'Webtoon','A Webtoon is a type of online comic where each chapter is published as a single, vertical strip. They typically have Full Color artwork. Webtoons first originated in Korea and have since spread to other Asian and Western countries.'),(5,2,'Веб-комикс','Веб-комикс - это тип онлайн-комиксов, где каждая глава публикуется в виде отдельной вертикальной полосы. Как правило, они имеют полноцветный рисунок. Веб-мультфильмы впервые появились в Корее и с тех пор распространились на другие страны Азии и Запада.'),(6,1,'Dungeon','Dungeons are the focus of these manga. The setting might take place in the dungeon, or the story might be related to one, such as dungeon management or creation. These manga typically follow adventurers who traverse the floors or levels of a dungeon to find treasure, to defeat monsters, or to exit it.'),(6,2,'Подземелье','Подземелья в центре внимания этой манги. Настройка может происходить в темнице, или история может быть связана с темой, такой как управление темницей или создание. Эти манги обычно следуют за искателями приключений, которые пересекают этажи или уровни подземелья, чтобы найти клад, победить монстров или выйти из него.'),(7,1,'Full Color','These manga are fully drawn in color, unlike normal manga which occasionally have a few colored pages, followed by all black and white artwork. Korean and Chinese webcomics, and Film Comics, frequently have Full Color artwork.'),(7,2,'В Цвете','Эта манга полностью нарисована в цвете, в отличие от обычной манги, которая иногда имеет несколько цветных страниц, за которыми следуют все черно-белые рисунки. Корейские и китайские веб-комиксы и кинокомиксы часто имеют полноцветные изображения.'),(8,1,'Magic','Magic is the use of gestures, incantations, or rituals to harness some kind of supernatural power. Characters in Magic manga tend to go by titles such as witch, wizard, mage, or sorcerer, and may be occupied with learning to harness these abilities or with finding magical relics or battling against strange entities... or other magic users.'),(8,2,'Магия','Магия - это использование жестов, заклинаний или ритуалов для обретения сверхъестественной силы. Персонажи в магической манге, как правило, носят названия, такие как ведьма, волшебник, маг или колдун, и могут быть заняты изучением того, как использовать эти способности, или находкой магических реликвий или сражением против странных существ ... или других магических пользователей.'),(9,1,'RPG','These manga were designed to feel like or parody or a role-playing game (RPG), and aren\'t necessarily based on a specific video game. Common themes include characters that level up, visit armories and shops to purchase equipment, use disposable items, and fight monsters.'),(9,2,'РПГ','Эта манга была разработана для того, чтобы чувствовать себя пародией или ролевой игрой (RPG), и не обязательно основана на конкретной видеоигре. Общие темы включают персонажей, которые повышаются, посещают оружейные и магазины, чтобы купить оборудование, использовать одноразовые предметы и сражаться с монстрами.'),(10,1,'Comedy','These manga aim to make you laugh through satire, parody, humorous observations, slapstick scenarios, or absurd antics. Bonus points for spitting your drink all over your screen!'),(10,2,'Комедия','Эти манги направлены на то, чтобы заставить вас смеяться с помощью сатиры, пародии, юмористических наблюдений, сценариев фарса или абсурдных выходок. Бонусные баллы за выплевывание напитка по всему экрану!'),(11,1,'School Life','These manga showcase events that occur on a daily basis in a school, whether from the perspective of a student or of a teacher. Having fun in the classroom, attending School Clubs, spending time with friends or doing daily chores are frequent themes in these manga.'),(11,2,'Школьная Жизнь','Эти манги демонстрируют события, которые происходят ежедневно в школе, с точки зрения ученика или учителя. В этой манге часто встречаются развлечения в классе, посещение школьных клубов, проведение времени с друзьями или выполнение повседневных обязанностей.'),(12,1,'Supernatural','Also called paranormal, supernatural events are those modern science has difficulty explaining. Supernaturally oriented titles are often steeped in folklore, myth, or Urban Legend. They may involve strange or inexplicable phenomena, Psychic Powers or emanations, and/or ghosts or other fantastic creatures. Supernatural events are those that lie at the edge of our understanding - they are easy to believe in, but difficult to prove.'),(12,2,'Сверхъестественое','Сверхъестественные события также называют паранормальными явлениями, которые современная наука с трудом объясняет. Сверхъестественно ориентированные названия часто погружены в фольклор, миф или городскую легенду. Они могут включать странные или необъяснимые явления, психические силы или эманации и / или призраков или других фантастических существ. Сверхъестественные события - это те события, которые лежат на краю нашего понимания - в них легко поверить, но трудно доказать.'),(13,1,'Vampires','These manga showcase traditional vampires - whether they\'re explicitly called vampires or not - who drink blood, can\'t sunbathe or have a pesky sensitivity to garlic.'),(13,2,'Вампиры','Эти манги демонстрируют традиционных вампиров - независимо от того, называются они вампирами или нет - которые пьют кровь, не могут загорать или имеют раздражающую чувствительность к чесноку.'),(14,1,'Overpowered Main Characters','These manga have overpowered protagonists, or overpowered main antagonists. Overpowered manga characters easily rise above the conflicts of their story. When competition is involved, they are typically undefeatable, using their intelligence, fighting prowess or pure raw power to win with ease, always staying ten steps ahead. They might be overpowered because of Cheats.'),(14,2,'Сверхсильный главный герой','В этой манге преобладают главные действующие лица или главные противники. Сильные персонажи манги легко преодолевают конфликты своей истории. Когда речь идет о конкуренции, они, как правило, непобедимы, используя свой интеллект, боевое мастерство или чистую грубую силу, чтобы легко победить, всегда оставаясь на десять шагов впереди. Они могут быть подавлены из-за читов.'),(15,1,'Weak to Strong','Characters in these manga are weak, but through determination, hard work, and rigorous training, they become strong and masters of their field, whether it\'s martial arts, a job class in a game, or a hobby such as sports. These protagonists might even become powerful enough to be Overpowered Main Characters.'),(15,2,'От Слабейшего к Сильнейшему','Персонажи в этой манге слабы, но благодаря целеустремленности, усердному труду и тщательному обучению они становятся сильными и мастерами своего дела, будь то боевые искусства, уроки игры или хобби, например, спорт. Эти главные герои могут даже стать достаточно могущественными, чтобы стать главными героями.'),(16,1,'Based on a Novel','These manga are based on a novel or a children\'s book.'),(16,2,'Основано на Новелле','Эти манги основаны на романе или детской книге.'),(17,1,'Drama','Drama manga heavily emphasize their characters\' emotional development. Whether by experiencing the protagonist’s emotional turmoil, viewing heated character interactions, or exploring a passionate romance, any manga that humanizes its characters through emphasizing their flaws qualifies as a Drama.'),(17,2,'Драмма','Драматические манги сильно подчеркивают эмоциональное развитие их персонажей. Будь то переживание эмоциональной суматохи главного героя, просмотр горячих взаимодействий персонажей или изучение страстного романа, любая манга, которая очеловечивает своих персонажей, подчеркивая их недостатки, квалифицируется как драма.'),(18,1,'Seinen','Seinen (青年), translated as \"youth\", is a demographic aimed at men from late teens to adulthood who understand kanji. Seinen manga rarely include furigana with kanji, and have a wide variety of topics and themes. Seinen is the counterpart of Josei, a demographic aimed at women.'),(18,2,'Сёнен','Сёнен (青年), переводится как «молодежь», является демографическим, ориентированным на мужчин от позднего подросткового возраста до зрелого возраста, которые понимают кандзи. Seinen манга редко включает в себя фуригану с кандзи и имеет широкий спектр тем и тем. Seinen является аналогом Josei, демографического, ориентированного на женщин.'),(19,1,'Slice of Life','Slice of life, or \"nichijou\" in Japanese, refers to events that occur on a daily basis such as getting ready for the day, light chores, enjoying a hobby, or preparing meals. These events differ depending on setting, but above all else are considered to be ordinary, everyday actions that don’t always follow a central plot. The related tags School Life and Work Life focus on ordinary events that take place in a school or work setting, respectively.'),(19,2,'Кусочек жизни','«Кусочек жизни», или «nichijou» на японском языке, относится к событиям, которые происходят ежедневно, например, к подготовке дня, легкой работе, хобби или приготовлению пищи. Эти события различаются в зависимости от обстановки, но, прежде всего, считаются обычными, повседневными действиями, которые не всегда следуют центральному сюжету. Связанные теги Школьная Жизнь и Рабочая Жизнь ориентированы на обычные события, которые происходят в школе или на рабочем месте, соответственно.\r\n'),(20,1,'Age Transformation','Characters in these manga experience transformations that alter their age, be they through magical or other means.'),(20,2,'Смена Возраста','Персонажи в этой манге испытывают преобразования, которые изменяют их возраст, будь то с помощью магических или других средств.'),(21,1,'Adapted to Anime','These manga or light novels were directly adapted to an anime.'),(21,2,'Аниме Адаптация','Эти манга или легкие романы которые были непосредственно адаптированы к аниме.'),(22,1,'Psychological','Psychological manga delve into mental or emotional states of a character in the midst of a difficult situation, letting you observe them change as tension increases. Internal monologues are a key feature, allowing narration to delve into a character\'s mind, revealing their innermost ideas and motivations - even as they may be driven to the brink of sanity.'),(22,2,'Психология','Психологическая манга углубляется в психические или эмоциональные состояния персонажа в трудной ситуации, позволяя вам наблюдать за их изменениями по мере роста напряжения. Внутренние монологи - это ключевая особенность, позволяющая повествованию проникать в сознание персонажа, раскрывать его самые сокровенные идеи и мотивы, даже если они находятся на грани здравомыслия.'),(23,1,'Martial Arts','Martial arts are techniques that heavily involve training and are steeped in tradition. These techniques can have varied applications such as self-defense, psychological health or advanced use of weaponry, amongst others. Characters in these manga often spend much of their time training to defend themselves or others, participate in competitions of skill, honor their culture, or develop their health and spirituality.'),(23,2,'Боевые искусства','Боевые искусства - это техники, которые в значительной степени связаны с тренировками и основаны на традициях. Эти методы могут иметь различные применения, такие как, например, самооборона, психологическое здоровье или расширенное использование оружия. Персонажи этой манги часто проводят большую часть своего времени, тренируясь, чтобы защитить себя или других, участвовать в соревнованиях по мастерству, чтить свою культуру или развивать свое здоровье и духовность.');
ALTER TABLE `test`.`genre_translate` ENABLE KEYS;
UNLOCK TABLES;

LOCK TABLES `test`.`image` WRITE;
ALTER TABLE `test`.`image` DISABLE KEYS;
INSERT INTO `test`.`image` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),(19,1),(20,1),(21,1),(22,1),(23,1);
ALTER TABLE `test`.`image` ENABLE KEYS;
UNLOCK TABLES;

LOCK TABLES `test`.`image_translate` WRITE;
ALTER TABLE `test`.`image_translate` DISABLE KEYS;
INSERT INTO `test`.`image_translate` VALUES (1,1,'Chapter1Image1Language1.jpg'),(1,2,'Chapter1Image1Language2.jpg'),(2,1,'Chapter1Image2Language1.jpg'),(2,2,'Chapter1Image2Language2.jpg'),(3,1,'Chapter1Image3Language1.jpg'),(3,2,'Chapter1Image3Language2.jpg'),(4,1,'Chapter1Image4Language1.jpg'),(4,2,'Chapter1Image4Language2.jpg'),(5,1,'Chapter1Image5Language1.jpg'),(5,2,'Chapter1Image5Language2.jpg'),(6,1,'Chapter1Image6Language1.jpg'),(6,2,'Chapter1Image6Language2.png'),(8,1,'Chapter1Image8Language1.jpg'),(8,2,'Chapter1Image8Language2.png'),(9,1,'Chapter1Image9Language1.jpg'),(9,2,'Chapter1Image9Language2.png'),(10,1,'Chapter1Image10Language1.jpg'),(10,2,'Chapter1Image10Language2.png'),(11,1,'Chapter1Image11Language1.jpg'),(11,2,'Chapter1Image11Language2.png'),(12,1,'Chapter1Image12Language1.jpg'),(12,2,'Chapter1Image12Language2.png'),(13,1,'Chapter1Image13Language1.jpg'),(13,2,'Chapter1Image13Language2.png'),(14,1,'Chapter1Image14Language1.jpg'),(14,2,'Chapter1Image14Language2.png'),(15,1,'Chapter1Image15Language1.jpg'),(15,2,'Chapter1Image15Language2.jpg'),(16,1,'Chapter1Image16Language1.jpg'),(16,2,'Chapter1Image16Language2.jpg'),(17,1,'Chapter1Image17Language1.jpg'),(17,2,'Chapter1Image17Language2.png'),(18,1,'Chapter1Image18Language1.jpg'),(18,2,'Chapter1Image18Language2.png'),(19,1,'Chapter1Image19Language1.jpg'),(19,2,'Chapter1Image19Language2.jpg'),(20,1,'Chapter1Image20Language1.jpg'),(20,2,'Chapter1Image20Language2.jpg'),(21,1,'Chapter1Image21Language1.jpg'),(21,2,'Chapter1Image21Language2.jpg'),(22,1,'Chapter1Image22Language1.jpg'),(22,2,'Chapter1Image22Language2.jpg'),(23,1,'Chapter1Image23Language1.jpg'),(23,2,'Chapter1Image23Language2.png');
ALTER TABLE `test`.`image_translate` ENABLE KEYS;
UNLOCK TABLES;

LOCK TABLES `test`.`language` WRITE;
ALTER TABLE `test`.`language` DISABLE KEYS;
INSERT INTO `test`.`language` VALUES (1,'English','en'),(2,'Русский','ru');
ALTER TABLE `test`.`language` ENABLE KEYS;
UNLOCK TABLES;

LOCK TABLES `test`.`manga` WRITE;
ALTER TABLE `test`.`manga` DISABLE KEYS;
INSERT INTO `test`.`manga` VALUES (1,'MangaImg1.jpg'),(2,'MangaImg2.png'),(3,'MangaImg3.jpg'),(4,'MangaImg4.png'),(5,'MangaImg5.jpg'),(6,'MangaImg6.jpg');
ALTER TABLE `test`.`manga` ENABLE KEYS;
UNLOCK TABLES;

LOCK TABLES `test`.`manga_artist` WRITE;
ALTER TABLE `test`.`manga_artist` DISABLE KEYS;
INSERT INTO `test`.`manga_artist` VALUES (1,1),(2,2),(3,3),(4,4),(5,5),(6,6);
ALTER TABLE `test`.`manga_artist` ENABLE KEYS;
UNLOCK TABLES;

LOCK TABLES `test`.`manga_author` WRITE;
ALTER TABLE `test`.`manga_author` DISABLE KEYS;
INSERT INTO `test`.`manga_author` VALUES (1,1),(1,2),(2,3),(3,4),(4,5),(5,6),(6,7);
ALTER TABLE `test`.`manga_author` ENABLE KEYS;
UNLOCK TABLES;

LOCK TABLES `test`.`manga_genre` WRITE;
ALTER TABLE `test`.`manga_genre` DISABLE KEYS;
INSERT INTO `test`.`manga_genre` VALUES (1,1),(2,1),(4,1),(5,1),(6,1),(1,2),(4,2),(5,2),(6,2),(1,3),(2,3),(4,3),(6,3),(1,4),(2,4),(4,4),(1,5),(2,5),(3,5),(4,5),(1,6),(1,7),(2,7),(3,7),(4,7),(1,8),(1,9),(2,10),(3,10),(5,10),(6,10),(2,11),(3,11),(2,12),(5,12),(6,12),(2,13),(2,14),(5,14),(1,15),(1,16),(4,16),(3,17),(6,17),(3,18),(5,18),(6,18),(3,19),(3,20),(3,21),(3,22),(6,22),(6,23);
ALTER TABLE `test`.`manga_genre` ENABLE KEYS;
UNLOCK TABLES;

LOCK TABLES `test`.`manga_translate` WRITE;
ALTER TABLE `test`.`manga_translate` DISABLE KEYS;
INSERT INTO `test`.`manga_translate` VALUES (1,1,'Solo Leveling','10 years ago, after “the Gate” that connected the real world with the monster world opened. Some of the ordinary, everyday people received the power to hunt monsters within the Gate. They are known as \"Hunters\". However, not all Hunters are powerful. My name is Sung Jin-Woo, an E-rank Hunter. I\'m someone who has to risk his life in the lowliest of dungeons, the \"World\'s Weakest\". Having no skills whatsoever to display, I barely earned the required money by fighting in low-leveled dungeons… at least until I found a hidden dungeon with the hardest difficulty within the D-rank dungeons! In the end, as I was accepting death, I suddenly received a strange power, a quest log that only I could see, a secret to leveling up that only I know about! If I trained in accordance with my quests and hunted monsters, my level would rise. Changing from the weakest Hunter to the strongest S-rank Hunter!'),(1,2,'Поднятие уровня в одиночку','10 лет назад, после того, как открылись «Врата», которые связывали реальный мир с миром монстров. Некоторые обычные, обычные люди получили возможность охотиться на монстров внутри Врат. Они известны как «Охотники». Однако, не все Охотники сильны. Меня зовут Сунг Джин-Ву, Охотник ранга E. Я тот, кто должен рисковать своей жизнью в самых низких подземельях, в \"Слабом мире\". Не имея навыков, которые можно было бы показать, я едва зарабатывал необходимые деньги, сражаясь в низкоуровневых подземельях ... по крайней мере, пока не нашел скрытое подземелье с самой сложной сложностью в подземельях ранга D! В конце, когда я принимал смерть, я внезапно получил странную силу, журнал квестов, который мог видеть только я, секрет прокачки, о котором только я знаю! Если бы я тренировался в соответствии со своими квестами и охотился на монстров, мой уровень повысился бы. Меняется от самого слабого охотника до самого сильного охотника S-ранга!'),(2,1,'Noblesse','Rai wakes up from 820-years long sleep and starts his new life as a student in a high school founded by his loyal servant, Frankenstein. But his peaceful days with other human students are soon interrupted by mysterious attackers known as the \"Unions\".'),(2,2,'Дворянство','Рай просыпается от 820-летнего сна и начинает новую жизнь в старшей школе, основанной его верным слугой Франкенштейном. Но его мирные дни с другими студентами-людьми вскоре прерываются таинственными нападавшими, известными как «Союзы».'),(3,1,'ReLIFE','Arata Kaizaki (27) quit the job he landed after graduation in only three months. His life did not go well after that. Now his parents are threatening to stop sending money, and want him to come back to the country. He has no friend or girlfriend to share his troubles with...as he hits rock bottom a strange man named Ryo Yoake appears. Yoake invites Kaizaki to join a societal rehabilitation program for NEETs called ReLife. This program uses a mysterious drug to make him look younger, and sends him back to high school for a year...'),(3,2,'Повторная жизнь','Арата Кайдзаки (27) уволился с работы, которую он получил после окончания учебы всего за три месяца. Его жизнь не пошла хорошо после этого. Теперь его родители угрожают прекратить посылать деньги и хотят, чтобы он вернулся в страну. У него нет ни друга, ни подруги, с которыми он мог бы поделиться своими проблемами ... когда он добирается до дна, появляется странный человек по имени Рио Йоаке. Yoake приглашает Kaizaki присоединиться к социальной реабилитационной программе для NEET, которая называется ReLife. Эта программа использует загадочный наркотик, чтобы он выглядел моложе, и отправляет его обратно в школу на год ...'),(4,1,'Ranker Who Lives a Second Time','Yeon-woo had a twin brother who disappeared five years ago. One day, a pocket watch left by his brother returned to his possession. Inside, he found a hidden diary in which was recorded “By the time you hear this, I guess I will be already dead….”'),(4,2,'Ранкер, который живет второй раз','У Ён Ву был брат-близнец, который исчез пять лет назад. Однажды карманные часы, оставленные его братом, вернулись к нему. Внутри он нашел скрытый дневник, в котором было записано: «К тому времени, когда вы услышите это, я думаю, я уже буду мертв…»'),(5,1,'One Punch-Man','“I’m just an average guy who serves as an average hero.”\r\nIn a world of superheroes and villains, Saitama appears to be a plain man -- from his lifeless expression to his sluggish personality, nothing stands out. But once danger strikes, he’s an insanely powerful superhero that can take down villains with a single punch! Although his strength keeps the city safe, Saitama can’t help but grow bored with how easily his opponents are defeated.\r\nOne day, his path crosses with the young cyborg, Genos, who wishes to be Saitama’s disciple. Thus a new chapter in Saitama’s life unfolds, opening up this action-packed seinen comedy of an average-looking man craving excitement and popularity in his “hobby” of being a superhero!'),(5,2,'Ванпачмен','«Я просто обычный парень, который служит обычным героем». В мире супергероев и злодеев Сайтама выглядит простым человеком - от его безжизненного выражения лица до вялой личности ничто не выделяется. Но как только появляется опасность, он становится безумно мощным супергероем, который может одолеть злодеев одним ударом! Несмотря на то, что его сила держит город в безопасности, Сайтама не может не устать от того, как легко его противники побеждены. Однажды его путь пересекается с молодым киборгом Геносом, который хочет стать учеником Сайтамы. Таким образом, открывается новая глава в жизни Сайтамы, открывающая эту экшн-комедию с сэйнэном о человеке среднего роста, жаждущем волнения и популярности в своем «хобби» быть супергероем!'),(6,1,'Naruto','Whenever Naruto Uzumaki proclaims that he will someday become the Hokage—a title bestowed upon the best ninja in the Village Hidden in the Leaves—no one takes him seriously. Since his birth, Naruto has been shunned and ridiculed by his fellow villagers. However, their contempt isn\'t because Naruto is loud-mouthed, mischievous, and inept in the ninja arts, it is because there is a demon inside him. Before Naruto was born, the powerful and deadly Nine-Tailed Fox attacked the village. In order to stop the rampage, the Fourth Hokage sacrificed his life to seal the demon inside the body of the newborn Naruto.'),(6,2,'Наруто','Всякий раз, когда Наруто Узумаки заявляет, что когда-нибудь станет Хокаге - титулом лучшего ниндзя в Деревне, скрытой в листьях, - никто не воспринимает его всерьез. Начиная с его рождения Наруто избегали и высмеивали его односельчане. Однако их презрение не потому, что Наруто громко, вредно и неумело в искусстве ниндзя, а потому, что внутри него демон. До рождения Наруто могущественный и смертельный Девятихвостый Лис напал на деревню. Чтобы остановить буйство, Четвертый Хокаге пожертвовал своей жизнью, чтобы запечатать демона в теле новорожденного Наруто.');
ALTER TABLE `test`.`manga_translate` ENABLE KEYS;
UNLOCK TABLES;

LOCK TABLES `test`.`manga_user` WRITE;
ALTER TABLE `test`.`manga_user` DISABLE KEYS;
INSERT INTO `test`.`manga_user` VALUES (1,2),(2,2),(4,2),(5,2),(6,2),(1,3),(2,3),(5,3),(6,3),(2,4),(4,4),(5,4),(1,5),(3,5),(5,5),(6,5),(2,6),(3,6),(4,6),(5,6),(6,6);
ALTER TABLE `test`.`manga_user` ENABLE KEYS;
UNLOCK TABLES;

LOCK TABLES `test`.`user` WRITE;
ALTER TABLE `test`.`user` DISABLE KEYS;
INSERT INTO `test`.`user` VALUES (1,'admin','graograman7@gmail.com','$2a$12$kNfQDI9k3TbcXn.2jXChU..FM8nINd5l0pDERG5nZaMgupjLA7Q8i',2,'UserAvatar1.jpg','2019-09-08'),(2,'Unos','unos@gmail.com','$2a$12$SDAJvP/t4Vs4TxjN4PSgG.v//h29sXqHCrVsfhPEeFD2QJcC8mgpm',1,'UserAvatar2.jpg','2019-09-29'),(3,'Duo','duo@gmail.com','$2a$12$EgjUDoAlZG7HtoEjdXwrF.V0/i6VFo5OAFWvP41tz18HuNRpXA5.q',1,'UserAvatar3.jpg','2019-09-29'),(4,'Tres','tres@gmail.com','$2a$12$EwSScztfQorvBDpDutaVp.2l91i.kw515LhF5QmVEeaRtFC3S.A5S',1,'UserAvatar4.jpg','2019-09-29'),(5,'Quattour','quattour@gmail.com','$2a$12$R9XTzk1/rATuxgJocj9RUOLUcrvY7WwcphqOEJjmoDe1tb2AUSj3i',1,'UserAvatar5.jpg','2019-09-29'),(6,'Quinque','quinque@gmail.com','$2a$12$vlfIg60cjuabfXiftWH/t.c3T35UiUztngVmlh055oWr7TyVziy0i',1,'UserAvatar6.png','2019-09-29');
ALTER TABLE `test`.`user` ENABLE KEYS;
UNLOCK TABLES;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
