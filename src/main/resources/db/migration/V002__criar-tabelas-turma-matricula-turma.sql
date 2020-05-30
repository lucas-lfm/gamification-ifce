DROP TABLE IF EXISTS `turma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `turma` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(45) NOT NULL,
  `periodo` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_codigo_turma` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `matricula_turma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `matricula_turma` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pontuacao` decimal(5,2),
  `aluno_id` bigint(20) DEFAULT NULL,
  `turma_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_matricula_turma_aluno` (`aluno_id`),
  KEY `fk_matricula_turma_turma` (`turma_id`),
  CONSTRAINT `fk_matricula_turma_aluno` FOREIGN KEY (`aluno_id`) REFERENCES `aluno` (`id`),
  CONSTRAINT `fk_matricula_turma_turma` FOREIGN KEY (`turma_id`) REFERENCES `turma` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;