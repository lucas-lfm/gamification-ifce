DROP TABLE IF EXISTS `professor_turma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `professor_turma` (
  `turma_id` bigint(20) NOT NULL,
  `professor_id` bigint(20) NOT NULL,
  PRIMARY KEY (`turma_id`,`professor_id`),
  KEY `fk_turma_id_turma` (`turma_id`),
  KEY `fk_professor_id_turma` (`professor_id`),
  CONSTRAINT `fk_turma_id_turma` FOREIGN KEY (`turma_id`) REFERENCES `turma` (`id`),
  CONSTRAINT `fk_professor_id_turma` FOREIGN KEY (`professor_id`) REFERENCES `professor` (`id`)
)ENGINE = InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client*/;