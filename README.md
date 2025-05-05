📘 Documentação da Fila de Execução de Threads
🔧 Arquitetura Geral
Este sistema implementa uma fila de execução de tarefas assíncronas utilizando ThreadPoolExecutor configurado como um ExecutorService Spring Bean. A fila processa requisições que são submetidas via endpoint REST (/thread/start), garantindo controle de concorrência e limitação de execução paralela.

🧵 Como Funciona a Fila de Execução
1. Componente de Execução: ThreadPoolExecutor
java
Copiar
Editar
int maxThread = 2;
int filaMax = 100;
maxThread = 2: Define o número máximo de threads que podem executar tarefas simultaneamente.

filaMax = 100: Define a capacidade da fila de espera de tarefas antes de serem executadas.

RejectPolicy: A política AbortPolicy irá rejeitar novas tarefas lançando uma RejectedExecutionException caso a fila esteja cheia.

2. Caminho da Requisição
O usuário envia um POST /thread/start com um objeto RequestObjectDTO.

O controller (ThreadController) chama executorService.submit(...), passando uma instância de ServiceExecutor.

ServiceExecutor implementa Runnable e chama requestService.start(...), que:

Imprime início e fim do processamento.

Simula uma tarefa longa com Thread.sleep(10000) (10 segundos).

3. Fila de Execução
Até 2 tarefas são processadas simultaneamente.

As tarefas subsequentes entram na fila de espera (até 100).

Se a fila estiver cheia: RejectedExecutionException → retorna HTTP 429.

✅ Como Testar o Funcionamento da Fila
🔄 Teste 1: Execução Paralela e Fila
Ferramentas Sugeridas:
Postman, cURL ou Apache JMeter

Etapas:
Faça 3 ou mais requisições simultâneas para POST /thread/start.

Observe:

As 2 primeiras começam a ser processadas imediatamente (execução concorrente).

A 3ª entra na fila (executada após uma das 2 primeiras terminar).

A partir da 104ª requisição simultânea, a API deve começar a retornar erro 429.

Exemplo via curl:
bash
Copiar
Editar
curl -X POST http://localhost:8080/thread/start \
     -H "Content-Type: application/json" \
     -d '{"campo1":"valor1"}'
Execute isso várias vezes rapidamente (use shell loop para automatizar).

🔍 Teste 2: Limite de Fila
Configure maxThread = 2 e filaMax = 3 temporariamente para facilitar teste.

Envie 6 requisições simultâneas.

Observe:

2 executam.

3 entram na fila.

1 deve ser rejeitada (RejectedExecutionException).

🧪 Logs Esperados
plaintext
Copiar
Editar
IN EXECUCAO: 2025-05-04T14:12:01.123Z
RequestObjectDTO: RequestObjectDTO{...}
...
OUT EXECUCAO: 2025-05-04T14:12:11.123Z
Isso ajuda a rastrear o tempo de execução de cada tarefa.

📊 Métricas que você pode adicionar (opcional)
Para monitoramento da fila:

java
Copiar
Editar
ThreadPoolExecutor executor = (ThreadPoolExecutor) executorService;
System.out.println("Tarefas ativas: " + executor.getActiveCount());
System.out.println("Na fila: " + executor.getQueue().size());
🧼 Boas práticas recomendadas
Utilize monitoramento com Actuator (/actuator/metrics) se quiser métricas automáticas.

Considere usar CallerRunsPolicy se preferir não rejeitar requisições.

Use logs estruturados com SLF4J e Logback para ambientes produtivos.
