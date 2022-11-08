# MovieCatalog

Proyecto muestra con arquitectura MVVM desarrollado haciendo uso de la API TMDB para demostración de habilidades actuales en arquitectura y diseño de UI.

## Características

#### La aplicación tiene 3 pantallas:

1. Vista principal con listado de peliculas más populares; 
    carga mas contenido conforma llegas al fondo de la lista.

2. Pantalla de detalles de películas con la información más relevante del título seleccionado; 
    permite la opción de agregar a lista de favoritos.

3. Pantalla con listado de las películas en lista de fvoritos; 
    implementa barra de busqueda para filtrado de contenido.

#### Dependencias principales

- Hilt - Inyección de dependencias
- Room - Persistencia de datos local (SQLite)
- Retrofit - Cliente HTTP
- Glide - Carga de imágenes

## Requisitos

> Por ahora el proyecto tiene una API Key funcional pero en el futuro puede cambiar si se abusa de la misma. 

Se recomienda crear una cuenta en TMDB para obtener una API Key propia.

Este campo puede ser modificado en **<build.gradle>** a nivel de app en el campo **"TMDB_API_KEY"**
