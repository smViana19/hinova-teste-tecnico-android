# Hinova Oficinas

Aplicação Android desenvolvida para o teste tecnico da Hinova Mobile, consumindo API de oficinas. O projeto foi construído com foco em escalabilidade, separação de responsabilidades e boas práticas de desenvolvimento Android moderno.

---

## Tecnologias e Arquitetura

### Linguagem

- **Kotlin**

### Padrão de Arquitetura

- **MVVM (Model-View-ViewModel)** combinado com **Clean Architecture**, organizando o projeto em camadas bem definidas: `data`, `domain`,`core` e `presentation`. Essa separação garante que cada camada tenha uma única responsabilidade e que as dependências fluam em apenas uma direção.

### Interface de Usuário

- **Jetpack Compose** — UI declarativa nativa do Android, utilizada para a construção de todas as telas e componentes visuais da aplicação.

### Consumo de API

- **Retrofit** — biblioteca HTTP utilizada para realizar as requisições à API da Hinova, configurada com conversor Gson para serialização e desserialização dos dados.

### Injeção de Dependências

- **Dagger Hilt** — framework de injeção de dependências baseado no Dagger, utilizado para gerenciar o ciclo de vida dos objetos e desacoplar as camadas da aplicação.

---
## Prompts de Ia utilizados 
- [Ir para documentacao e prompts](docs/prompts.md)

## Requisitos

- **Android Studio**
- **JDK 11** ou superior
- Dispositivo ou emulador com **Android API 24** (Android 7.0 Nougat) ou superior

---

## Clonando o Projeto

Abra o terminal e execute o comando abaixo para clonar o repositório:

```bash
git clone https://github.com/smViana19/hinova-teste-tecnico-android.git
```

Em seguida, acesse o diretório do projeto:

```bash
cd hinova-teste-tecnico-android
```


## Compilando e Buildando

### Via Android Studio

1. Abra o Android Studio.
2. Selecione **File > Open** e navegue até o diretório do projeto clonado.
3. Aguarde a sincronização do Gradle ser concluída.
4. Para compilar, selecione **Build > Make Project** ou utilize o atalho `Ctrl + F9` (Windows/Linux) ou `Cmd + F9` (macOS).

---

## Executando no Dispositivo

### Via Android Studio

1. Conecte um dispositivo Android via USB com a **depuração USB ativada**, ou inicie um emulador com API 24 ou superior.
2. Selecione o dispositivo de destino na barra de ferramentas.
3. Clique em **Run > Run 'app'** ou utilize o atalho `Shift + F10`.

## Estrutura do Projeto

```
com.smvtech.testetecnico/
├── core/                # Classes e utilitários compartilhados
│   ├── di/
│   └── utils/
├── data/
│   ├── local/           # Configuração do Room, DAOs e entidades de banco de dados
│   ├── location/        # Implementação do provider de localização do usuário
│   ├── remote/          # Configuração do Retrofit, endpoints e DTOs
│   ├── mocks/           # Mocks para aplicação
│   └── repository/      # Implementações dos repositórios
├── domain/
│   ├── model/           # Entidades de domínio
│   ├── location/        # Entidades de domínio
│   ├── repository/      # Interfaces dos repositórios
│   └── usecase/         # Casos de uso da aplicação
├── presentation/
│   ├── navigation/      # Navegação entre telas
│   ├── components       # Componentes Jetpack Compose
│   └── viewmodel/       # ViewModels por funcionalidade
│   ├── ui/              # Telas e componentes Jetpack Compose
│   └── viewmodel/       # ViewModels por funcionalidade
└── docs/                # Documentação do projeto
```

---

## Autor

Desenvolvido por **Samuel Viana**.