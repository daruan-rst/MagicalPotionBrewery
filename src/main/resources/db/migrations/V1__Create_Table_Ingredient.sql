CREATE TABLE IF NOT EXISTS `ingredients` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(80) NOT NULL,
  `flavor` varchar(80) NOT NULL,
  PRIMARY KEY (`id`)
)