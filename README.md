# 📸 Foto Disparador

## Sobre o projeto
Foto Disparador é um projeto que une software e hardware para permitir o disparo remoto de uma câmera Canon T5 via sinal infravermelho. A solução consiste em um aplicativo Android que envia o sinal IR a um circuito físico que interpreta esse sinal e aciona a câmera através do conector P1.

O sistema foi desenvolvido com o objetivo de facilitar a captura de fotos em situações como timelapses, longas exposições ou qualquer cenário onde o disparo remoto seja necessário.

---
### ⚠ Nota

Este projeto representa minha vontade de criar soluções práticas mesmo sem dominar todas as ferramentas.

Mesmo sem conhecimento prévio em Java, usei o *ChatGPT* como ferramenta para gerar e entender partes do código, com base funcionalidades que eu gostaria. Este repositório representa *minha curiosidade, criatividade e persistência em construir algo do zero*.


## ⚙ Funcionalidades do app

- Disparo manual via botão azul
- Disparo automático com intervalo configurável
- Feedback visual com cores diferentes para botões ativos/inativos


## 🔩 Sobre o hardware

O circuito foi desenvolvido utilizando:

- Receptor infravermelho
- Transistores NPN e PNP
- Resistores
- Capacitor
- Led alto brilho
- Bateria 2032 (3V)
- Conector P1 (plug) para conexão com a câmera Canon T5

Esse circuito interpreta o sinal IR enviado pelo app e converte em um pulso elétrico que dispara a câmera via entrada remota.



### 🎥 Demonstração em vídeo

[Ver no YouTube](https://youtu.be/scufi_Z2r9k)
