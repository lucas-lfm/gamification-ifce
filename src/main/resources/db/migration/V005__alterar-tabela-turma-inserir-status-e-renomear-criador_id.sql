ALTER TABLE `turma` CHANGE `criador_id` `responsavel_id` BIGINT(20) NOT NULL;
ALTER TABLE `turma` ADD COLUMN `status` VARCHAR(20) NOT NULL;