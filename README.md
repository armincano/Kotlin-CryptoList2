## Qué es CryptoList2
Es una app Android que visualiza una **lista de criptomonedas** donde cada elemento cuenta con imagen, símbolo y valor actual.<br>🔎 Puedes **buscar** criptomonedas ingresando parte de su nombre.<br>📈 Al presionar un elemento del listado, cambia de pantalla y visualiza **más datos de la criptomoneda,** como la variación de su valor en las últimas 24 hrs.<br>✏️ También puedes añadir un **nombre de usuario** en otra pantalla.

### Imágenes del funcionamiento de la app
|<p align="center"> <img src="readme/ic_launcher-web.png" width="220" alt="app thumbnail">|<p align="center"> <img src="readme/01-main.png" width="220" alt="main screen"> |<p align="center"> <img src="readme/02-search.png" width="220" alt="searching"> |
|--|--|--|
|<p  align="center">Ícono de app. |<p  align="center">Pantalla principal.  |<p  align="center">Buscando criptomonedas que contengan el texto ingresado.  |
|<p align="center"> <img src="readme/03-userProfile.png" width="220" alt="user profile screen">|<p align="center"> <img src="readme/04a-detailGreen.png" width="220" alt="detail screen green"> |<p align="center"> <img src="readme/04b-detailRed.png" width="220" alt="detail screen red"> |
|<p  align="center">Cambiando de usuario luego de presionar el botón con ícono de lápiz en pantalla principal.  |<p  align="center">Pantalla de detalle luego de presionar una criptomoneda de la pantalla de inicio.  |<p  align="center">Pantalla de detalle cuando el valor de cambio de las últimas 24 horas es negativo.  |
### Cápsula del funcionamiento y potenciales clientes de la app
[![Img alt text](https://img.youtube.com/vi/h7ofCVMoT-0/0.jpg)](https://www.youtube.com/watch?v=YouTube_video_ID)

## Características técnicas
- Lenguaje: Kotlin.
- 🖼 Compose toolkit para construir UI nativo.
- Patrón de arquitectura MVVM.
- Consumo de API RESTful con cliente HTTP Retrofit2. https://docs.coincap.io/
- 🚀 Navigation Component para navegar entre pantallas.
- Librería Coil para cargar imágenes.
- 💾 BBDD SQLite con una capa de abstracción Room. Uso de convertidores para Data class personalizado.
- BBDD NoSQL: DataStore. Para guardar preferencias de usuario.
- 👟 Coroutines.
