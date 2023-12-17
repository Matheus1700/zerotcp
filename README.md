# Zerinho ou um

Este repositório contém um projeto feito em grupo para a disciplina de Redes de Computadores, parte do curso de Análise e Desenvolvimento de Sistemas.

## Contexto do jogo 🎮
Zerinho ou Um é um jogo popular que pode ser jogado por até três pessoas. Cada uma delas deve escolher entre "zerinho" (0) ou "um" (1) e mostrar sua escolha simultaneamente. O objetivo do jogo é determinar o vencedor com base nas escolhas dos jogadores, sendo o vencedor quem fizer uma escolha diferente dos demais (escolher 1 enquanto os outros dois escolheram 0, por exemplo).
Este projeto é uma simulação digital que permite aos jogadores participarem inserindo suas escolhas ao mesmo tempo, graças a uma conexão TCP e à programação multithread.

## Componentes do Projeto ⚙️

### TCPClient

O `TCPClient` é responsável por representar um cliente no jogo. Ele se conecta ao servidor TCP e permite que o usuário insira suas escolhas (0 ou 1) através da entrada do console. As escolhas são enviadas para o servidor, e o cliente aguarda as respostas do servidor.

### TCPServer

O `TCPServer` funciona como o servidor central do jogo. Ele aceita conexões de clientes e delega o processamento das escolhas dos jogadores a instâncias de `ClientHandler` em um pool de threads. O servidor determina o vencedor ou empate com base nas escolhas dos jogadores e envia a mensagem apropriada de volta para os clientes.

### ClientHandler

Cada instância de `ClientHandler` lida com as interações de um cliente específico. Ele recebe a escolha do cliente, armazena as escolhas e endereços IP dos jogadores, determina o vencedor ou empate após todos os jogadores fornecerem suas escolhas e envia a mensagem de resultado de volta ao respectivo cliente.

## Como posso rodar este projeto? 🛠️

Antes de tudo, clone o projeto na sua IDE favorita e certifique-se de ter JDK e Java 17+ instalados.

1. **Compilação:**
   - Compile ambos os arquivos (`TCPClient.java` e `TCPServer.java`) utilizando um compilador Java. Você pode usar o comando `javac TCPClient.java` e `javac TCPServer.java`.

2. **Execução:**
   - Inicie o servidor executando o comando `java TCPServer`.
   - Em seguida, inicie três instâncias do cliente, cada uma em um terminal, executando `java TCPClient`.

3. **Interagindo com o Jogo:**
   - Os clientes serão solicitados a inserir "zero" ou "um" no console.
   - Após todos jogarem, o servidor determinará o vencedor ou empate e enviará a mensagem correspondente a todos os clientes.
   - Os cllientes recebem a mensagem do servidor, exibem no terminal e têm a possibilidade de inserir uma nova jogada.

## Contribuidores: 🤝


| [<img loading="lazy" src="https://avatars.githubusercontent.com/u/45037642?v=4" width=115><br><sub>Matheus Melo</sub>](https://github.com/Matheus1700) |  [<img loading="lazy" src="https://avatars.githubusercontent.com/u/35618372?v=4" width=115><br><sub>Caio Cezar</sub>](https://github.com/caiotuchi) |  [<img loading="lazy" src="https://avatars.githubusercontent.com/u/81446987?v=4" width=115><br><sub>Kallyne Rocha</sub>](https://github.com/kallynerocha) |
| :---: | :---: | :---: |
