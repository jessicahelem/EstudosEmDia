<h1>Estudos em Dia</h1>

Aplicativo android

<h2>Visão geral</h2>

	

<p>
O projeto proposto é um aplicativo android com o intuito de acompanhar o rendimento escolar do aluno. O usuário poderá fazer seu cadastro e consequentemente seu login, inserir suas disciplinas, notas escolares, a média da escola e sua própria média, além de inserir lembretes. Para a persistência dos dados será utilizado o banco de dados objectbox. </p> 

<h2>Classes</h2>

**Usuário**
    -Id
    -Nome
    -Senha
    -Instituição
    -Média da escola
    -Média pessoal 
    -Quantidade de provas 
    -Lista de disciplina
    -Lista de lembretes.
    
**Eventos**
    -Id
    -Título
    -Data 
    -Hora 
    -Anotação
    -Lista de Usuários
    
**Disciplina** 
    -Id
    -Nome
    -Professor
    -Média 
    -Disciplina extra
    -Lista de notas
    -Prova final
    -Lista de usuários
    
    
 **Nota**
    - Id
    - Nota bimestral
    - Lista disciplina
    -Lista de notas
    
