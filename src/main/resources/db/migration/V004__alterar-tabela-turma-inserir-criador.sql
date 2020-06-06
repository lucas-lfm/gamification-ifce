ALTER TABLE `turma` add column `criador_id` bigint(20) not null;
ALTER TABLE `turma` add constraint `fk_criador_turma_id` foreign key (`criador_id`) references `professor` (`id`);