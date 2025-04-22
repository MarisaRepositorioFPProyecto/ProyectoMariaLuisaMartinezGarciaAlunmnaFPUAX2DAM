
# APP DIABETES

APP Creada con Kotlin para el proyecto final de FP de DAM.




## Autores
- Maria Luisa Martínez García 2ºDAM
- [@MarisaRepositorioFPProyecto](https://www.github.com/MarisaRepositorioFPProyecto)


## Instalación

Instala el proyecto en Android Studio

```bash
  Clona el proyecto en Master
  Realiza un build
  Grea en android un nuevo Device Manager, recomendado un Pixel 4 API35 ya que tarda menos en cargar
  Para poder subir al repositorio es necesario tener instalado git en windows, se puede descargar en https://git-scm.com/downloads
  Compruebas que la versión se ha instalado bien con git --version
```
    
## Lenguaje

- Kotlin


## Herramientas a usar

 - Android Studio
 - SourceTree para los despliegues
 -


## Licencia

Es Gratuito y de código libre

[MIT](https://choosealicense.com/licenses/mit/)

## Color Reference


| Tipo            | Color          | Hex       |
|-----------------|----------------|-----------|
| Primario        | Marrón         | `#4B2B20` |
| Secundario      | Naranja claro  | `#FFC975` |
| Terciario       | Naranja oscuro | `#D77103` |
| Auxiliar 0      | Gris oscuro    | `#242424` |
| Auxiliar 1      | Gris claro     | `#BAACA1` |
| Auxiliar 2      | Rojo suave     | `#D06E3F` |
| Auxiliar 3      | Beige          | `#EFC49A` |
| Auxiliar 4      | Crema          | `#F4E3C7` |
| Auxiliar 5      | Marrón claro   | `#99443C` |
| Auxiliar 6      | Blanco         | `#FFFFFF` |

**Fuente tipografica:** [Source Sans Pro](https://fonts.google.com/specimen/Source+Sans+Pro)


## Base de Datos

La app usará una base de datos local (por implementar). El diseño se está elaborando con un enfoque sencillo, centrado en el registro de usuario y almacenamiento de frases.


|      Usuarios       |
|---------------------|
| id        (PK) INT  |
| nombre     STRING   |
| password   STRING   |



|     MensajesAnimo        |
|---------------------------|
| id        (PK) INT        |
| texto      STRING         |


       
|        GlucosaRegistro       |
|-------------------------------|
| id           (PK) INT         |
| usuario_id    (FK) INT        |
| nivel         FLOAT/INT       |
| fecha         DATE            |
| hora          TIME            |
| nota_libre    STRING          |



## Flujo de usuario

- El usuario abre la app.
- Se visualiza la pantalla de login - inicio de sesión.
   - Si ya está registrada, puede iniciar sesión directamente.
   - Si no, puede registrarse con su nombre y contraseña.
- Una vez dentro, accede a la pantalla principal:
   - Aparecen mensajes de ánimo personalizados.
   - Puede registrar glucosa, etc
   - Puede descargar en un documento sus registros


## LOGO

![Logo](https://drive.google.com/file/d/1UNKP2OkRdUAtdFyKon8AXfm2U_31W1ej/view?usp=sharing)

