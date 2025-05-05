
# 📘 Documentação Técnica – Fila de Execução de Threads

## 🔧 Arquitetura Geral

Este sistema implementa uma **fila de execução de tarefas assíncronas** utilizando `ThreadPoolExecutor` configurado como um `ExecutorService` Bean no Spring. As requisições são processadas de forma concorrente, com controle de fila e política de rejeição quando a fila está cheia.

---

## 🧵 Funcionamento da Fila de Execução

### 1. Configuração do Executor

```java
int maxThread = 2;
int filaMax = 100;

return new ThreadPoolExecutor(
    maxThread,
    maxThread,
    0L, TimeUnit.MICROSECONDS,
    new LinkedBlockingDeque<>(filaMax),
    new ThreadPoolExecutor.AbortPolicy()
);
```

- `maxThread = 2`: Número máximo de threads simultâneas.
- `filaMax = 100`: Número máximo de tarefas aguardando execução.
- `AbortPolicy`: Rejeita novas tarefas se a fila estiver cheia.

---

### 2. Fluxo da Requisição

1. O cliente envia um `POST /thread/start` com `RequestObjectDTO`.
2. O `ThreadController` submete a tarefa ao `ExecutorService`.
3. `ServiceExecutor` implementa `Runnable` e chama `requestService.start(...)`.
4. `RequestService` simula o processamento com `Thread.sleep(10000)` e imprime logs de entrada e saída.

---

## ✅ Como Testar a Fila de Execução

### 🔄 Teste 1: Execução Concorrente e Fila

**Ferramentas sugeridas:** Postman, cURL, Apache JMeter

1. Envie **várias requisições simultâneas** para `POST /thread/start`.
2. Observe:
   - 2 tarefas serão executadas imediatamente.
   - As demais entrarão na fila (até 100).
   - A partir da 103ª requisição, retornará HTTP `429 Too Many Requests`.

**Exemplo com cURL:**
```bash
curl -X POST http://localhost:8080/thread/start      -H "Content-Type: application/json"      -d '{"campo1":"valor1"}'
```

Execute múltiplas vezes rapidamente (use `for` no shell, se preferir).

---

### 🔍 Teste 2: Estourar a Capacidade da Fila

1. Modifique temporariamente:
   ```java
   int maxThread = 2;
   int filaMax = 3;
   ```
2. Envie **6 requisições simultâneas**.
3. Esperado:
   - 2 executam.
   - 3 ficam na fila.
   - 1 é rejeitada e retorna erro HTTP `429`.

---

## 📊 Logs Esperados

```plaintext
IN EXECUCAO: 2025-05-04T14:12:01.123Z
RequestObjectDTO: RequestObjectDTO{...}
OUT EXECUCAO: 2025-05-04T14:12:11.123Z
```

Esses logs indicam início e fim da execução da tarefa.

---

## 📈 Monitoramento da Fila (opcional)

Inclua este trecho no controller ou service para depurar:

```java
ThreadPoolExecutor executor = (ThreadPoolExecutor) executorService;
System.out.println("Tarefas ativas: " + executor.getActiveCount());
System.out.println("Na fila: " + executor.getQueue().size());
```

---

## ✅ Boas Práticas

- Use o Spring Boot Actuator (`/actuator/metrics`) para monitoramento avançado.
- Considere outras políticas de rejeição como `CallerRunsPolicy` se preferir evitar falhas.
- Use logs estruturados com SLF4J e Logback para produção.

---

## 📌 Conclusão

Este sistema permite o gerenciamento eficiente de execução de tarefas em segundo plano, com controle de concorrência, capacidade de fila e rejeição segura de requisições quando a fila está cheia.
