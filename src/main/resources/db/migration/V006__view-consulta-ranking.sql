CREATE VIEW ranking AS SELECT
	ROW_NUMBER() OVER (partition by m.turma_id ORDER BY pontuacao DESC) AS posicao, m.id as id_matricula, m.pontuacao as pontuacao, a.id as id_aluno,
	a.nome as nome_aluno, t.id as id_turma, t.codigo as codigo_turma
FROM matricula_turma m
INNER JOIN aluno a ON m.aluno_id = a.id
INNER JOIN turma t ON m.turma_id = t.id;