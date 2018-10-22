# Sistema Mimetizador Visualizador de Mensajes
## Presentación 
<img src="https://github.com/JorgeJasso/SistemaMimetizadorGuzmanJasso/blob/master/Imagenes/Presentacion.jpg">
## Introducción
>El siguiente proyecto consiste en un sistema para el despliegue de mensajes en una pantalla LCD, el objetivo es que al llegar un individuo pueda ver un pequeño tablero electronico con una serie de mensajes o notas almacenados en dicho sistema. De la misma manera el individuo podra Insertar y almacenar nuevos mensajes, eliminarlos, incluso  navegar entre ellos.

Estas son algunas de las características del sistema:
-	Cada mensaje puede incluir hasta 140 caracteres.
-	Adicional al mensaje se muestra la fecha y hora en la que fue emitido.
-	Incluye un mensaje con el estado del tiempo (temperatura, humedad, luminosidad).
-	La interfaz del hardware permite que el individuo pueda navegar entre los mensajes.
-	La interfaz del software permite enviar los mensajes desde la computadora al arduino y asi mismo a la pantalla LCD via serial.
-	La interfaz del software permite agregar y eliminar mensajes.

## Materiales
De Hardware
* Protoboard
* Arduino Mega 2560
* Display LCD 16x2
* Sensor DHT11
* Fotoresistencia LDR
* Potenciometro 10k
* 2 Push-Buttons
* Resistencias 330 ohms, 470 ohms, 10 Kohms 
* Cables Dupont(Jumpers) macho macho
* Cables de corriente
* Cable USB para arduino
* Computadora

De Software
* Arduino IDE
* OpenJDK
* OpenJRE
* Netbeans IDE
* Librería <LiquidCrystal.h>
* Librería  <DHT.h> 
* Librería RXTX

## Instalación de herramientas

### Instalación de Arduino IDE
Arduino IDE no se encuentra en los repositorios oficiales de Ubuntu, al menos la última versión, por lo que tenemos que utilizar la web oficial del Proyecto para conseguir este IDE. 
 https://www.arduino.cc/en/Main/OldSoftwareReleases 
 El archivo está comprimido y hay que extraerlo en una carpeta adecuada, recordando que se ejecutará desde allí.
 Una vez que tengamos esto, en la terminal escribimos lo siguiente:
```sh
$ sudo chmod +x install.sh 
```
Esta orden hará que el archivo de instalación pueda ejecutarse sin tener que ser root. Ahora ejecutamos en la terminal lo siguiente:
``` sh
$ ./install.sh 
```
Esto hará que comience la instalación de Arduino IDE en nuestro Ubuntu. Tras obedecer las órdenes del asistente y esperar varios segundos (o minutos, dependiendo del ordenador). Y ya está, ya tendremos Arduino IDE instalado en nuestro Ubuntu y un acceso directo en nuestro escritorio.

```sh
$ npm install --production
$ NODE_ENV=production node app
```

### Instalación de OpenJDK y OpenJRE

Abrimos la terminal y ejecutamos las siguientes instrucciones:
``` sh 
$ sudo apt-get install openjdk-8-jre openjdk-8-jdk
```
Al finalizar ejecutamos la siguiente instruccion para verificar la version instalada.
```sh
$ java -version
```
En caso de presentar el siguiente error:
No se pudo bloquear /var/lib/dpkg/lock – open (11: Recurso no disponible temporalmente). EJECUTAR los siguientes comandos: y volver a intentar la instalación.
``` sh
$ sudo fuser -vki  /var/lib/dpkg/lock
$ sudo rm -f /var/lib/dpkg/lock
$ sudo dpkg --configure -a
```

### Instalación de Netbeans IDE
Ir a la página oficial y descargar la última version de netbeans en  https://netbeans.org/downloads/start.html?platform=linux&lang=es&option=all.
Se extrae el archivo de la carpeta y se ejecuta en la terminal
```sh
$ sudo chmod +x netbeans-8.2-linux.sh
$ sudo ./netbeans-8.2-linux.sh
```
Ya iniciado el instalador nos permite realizar en un entorno gráfico, se recomienda quitar las opciones para instalar Apache Tomcat y GlassFish por el momento. y dar clic en “Next” hasta terminar el proceso de instalación.
Por ultimo Ya en nuestras aplicaciones buscamos “NetBeans IDE 8.2”, esperamos que inicie.

### Instalación de librería RXTX
Ejecutamos en la terminal los siguientes comandos.
```sh
$ sudo apt-get install librxtx-java
$ sudo cp -r /usr/lib/jni/librxtxSerial.so /usr/lib/x86_64-linux-gnu
$ sudo cp -r /usr/lib/jni/librxtxSerial.so /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/amd64
```
### Instalación de la librería  LiquidCrystal
Descarga la libreria <LiquidCristal.h>, extrae los archivos de la carpeta comprimida y posterirormente pega toda la carpeta en las librerias de arduino.

### Instalación de la librería  DHT
Descarga la libreria <DHT.h>, extrae los archivos de la carpeta comprimida y posterirormente pega toda la carpeta en las librerias de arduino.

## Montaje del circuito
### Display LCD y potenciometro
> 1.- Conecte un jumper en la tierra del Arduino al negativo del Protoboard y otro jumper del voltaje al positivo.
> 2.- Con el cable eléctrico debe puentear el negativo para que pase al otro lado del Protoboard.
> 3.- Conecte el display LCD en el protoboard  conectando con cables de corriente los pines (VSS, RW, D0, D1, D2, D3, K)  a tierra y los pines VDD y A a voltaje, este ultimo mediante una resistencia. 
> 4.- Conectamos el potenciometro al protoboard con cables de corriente la pata izquierda a voltaje y la pata derecha a tierra. la pata central ca conectaremos con el pin V0 del displya LCD.
> 5.- conectar con cables jumpers los pines (RS, E, D4, D5,D6,D7) del display LCD  a los pines PWM (13, 12, 11, 10, 9, 8) del aruino.
### Sensor DHT11
> 1.- Conectamos con cables de corriente la pata izquierda((GND) del sensor DHT11 a tierra y la pata derecha (VCC) al voltaje.
> 2.- Conectamos una resistencia entre la pata central (Data) y la pata derecha(VCC).
>3.- Con un cable jumper conectamos la pata central (Data) al pin PWM 52 del arduino.
### Fotoresistencia LDR
>1.- Conectamos con cables de corriente la pata izquierda de la resistencia con voltaje.
>2.- Conectamos en serie la pata izquierda de la fotoresistencia  junto con la pata derecha de la resistencia.
>3.- Conectamos la pata derecha de la fotoresistencia con cables de corriente a tierra.
>4.- Conectamos con con cables jumper la pata izquiera de la fotoresistencia  al pin analógico A0 del arduino.
### Push-Button
>1.- Conectamos el primer push-button al protoboard y con cables de corriente puenteamos su pata izquierda con tierra y la pata derecha la conectaos en seria con una resitencia y despues a voltaje.
>2.- Realizamos la conexión de la pata derecha del push-button con un cable jumper al pin PWM 20 del arduino.
>3.- De la misma forma conectamos el segundo push-button, pero conectandolo al pin PWM 21 del arduino.
## Imagenes del montaje 
### Protoboard
<img src="https://github.com/JorgeJasso/SistemaMimetizadorGuzmanJasso/blob/master/Imagenes/Img1Proto.jpg" width="400" height="400" >
<img src="https://github.com/JorgeJasso/SistemaMimetizadorGuzmanJasso/blob/master/Imagenes/Img2Proto.jpg" width="400" height="400" >
<img src="https://github.com/JorgeJasso/SistemaMimetizadorGuzmanJasso/blob/master/Imagenes/Img3Proto.jpg" width="400" height="400" >
### Arduino
<img src="https://github.com/JorgeJasso/SistemaMimetizadorGuzmanJasso/blob/master/Imagenes/Img1Arduino.jpg" width="400" height="400" >
<img src="https://github.com/JorgeJasso/SistemaMimetizadorGuzmanJasso/blob/master/Imagenes/Img2Arduino.jpg" width="400" height="400" >
### Interfaz de Software
<img src="https://github.com/JorgeJasso/SistemaMimetizadorGuzmanJasso/blob/master/Imagenes/Img1Interfaz.jpg" width="400" height="400" >
<img src="https://github.com/JorgeJasso/SistemaMimetizadorGuzmanJasso/blob/master/Imagenes/Img2Interfaz.jpg" width="400" height="400" >
<img src="https://github.com/JorgeJasso/SistemaMimetizadorGuzmanJasso/blob/master/Imagenes/Img3Interfaz.jpg" width="400" height="400" >
