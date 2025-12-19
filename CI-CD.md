# CI/CD — GitHub Actions

Este repositório já contém dois workflows em `.github/workflows`:

- `ci.yml`: build + testes (Maven) para pushes e pull requests.
- `cd.yml`: build do artefato, construção de imagem Docker e push para GitHub Container Registry (GHCR). Opcionalmente faz push para Docker Hub se os segredos forem configurados.

## Segredos necessários (Settings → Secrets → Actions)

- `GITHUB_TOKEN` — fornecido automaticamente pelo GitHub Actions (assegure `packages: write` nas permissões do workflow quando necessário).
- `DOCKERHUB_USERNAME` (opcional) — nome do usuário Docker Hub.
- `DOCKERHUB_TOKEN` (opcional) — token/senha do Docker Hub.

## Como funciona

- Push em qualquer branch / PR: executa `ci.yml` (build + testes).
- Push em `main`/`master` ou push de tags que batem com `v*`/`release*`: executa `cd.yml` que compila o projeto e publica imagem em `ghcr.io/${{ github.repository }}` com tags `latest` e `${{ github.sha }}`. Se for um push de tag, também publica com o nome da tag (ex.: `v1.0.0`).
- Se `DOCKERHUB_USERNAME` e `DOCKERHUB_TOKEN` estiverem configurados, o workflow replica a imagem para o Docker Hub.

## Disparar uma release (exemplo)

No seu terminal (PowerShell):

```powershell
# criar tag semântica e enviar
git tag v1.0.0
git push origin v1.0.0
```

Isso acionará o workflow de CD que publicará a imagem com a tag `v1.0.0` em GHCR (e Docker Hub, se configurado).

## Local testing (rápido)

- Executar CI localmente (maven):

```powershell
./mvnw -B -DskipTests=false clean verify
```

- Construir imagem Docker localmente:

```powershell
docker build -t homefinancasserver:latest .
```

## Observações

- Nunca exponha tokens em código-fonte. Use Secrets do GitHub Actions.
- Se quiser deploy direto a um servidor via SSH (ou outro provedor), diga qual destino prefere e eu posso adicionar um job de deploy no workflow.
