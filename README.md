# Avaliativa2-Kotlin

Atividade desenvolvida para a segunda atividade avaliativa para a matéria de Desenvolvimento de Aplicativos Móveis.

## Integrantes:
- Brian de Freitas Santos
- Luiz André Hoffmann Leineker
- Ricardo Polato Bernaski

## Estratégia de navegação utilizada:

Foi usada a "Navigation Compose" para se mover entre as janelas.

## Extras implementados:

### Toast anti-duplicado

Impede o cadastro de jogos com mesmo título (não diferenciado minúsculas e maiúsculas), exibindo "Toast" ao tentar duplicar.

#### Como testar?

Forneça um título ainda não cadastrado no campo adequado, assim que o cadastro for concluído, tente cadastrar outro com o mesmo título,
mas trocando a capitalização das letras.

### Contador

Exibe a quantidade de jogos cadastrados próximo ao botão de adicionar.

#### Como testar?

Vá adicionandos e removendo jogos, verá a quantidade de jogos presentes próximo ao botão se atualizar de acordo.

### Remover por long press

Remove o jogo ao ser pressionado por um certo perídoo de tempo, exibindo um "Toast" de confirmação.

##### Como testar?

Vá para a lista de jogos, pressione algum dos itens e aguarde o pedido de confirmação, aceite e veja o número de jogos cadastrandos sendo diminuído.




