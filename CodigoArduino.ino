//--------------------------------------------------------------------LIBRERIAS A UTILIZAR.

#include <LiquidCrystal.h> //Libreria para utilizar el display LCD.
#include <DHT.h> //Incluye librería de control del sensor.


//-------------------------------------------------------------------DECLARACIÓN DE VARIABLES.

//----------Variables para el LCD
LiquidCrystal lcd(13, 12, 11, 10, 9, 8); //Se indican los pines que esta utilizando el LCD.
const char CARACTERES[95] = {' ','!','"','#','$','%','&',' ','(',')','*','+',',','-','.','/','0','1','2','3','4','5','6','7','8','9',':',';','<','=','>','?','@','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','[',' ',']','^','_','`','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','{','|','}','~'}; //Se define el alfabeto para convertir una entrada numerica a el valor que representa.


//----------Variables para el SENSOR y FOTORESISTENCIA
#define DHTPIN 52 //Defiene el pin al que se conectará el sensor.
#define LDR_pin A0 //Define la entrada analogica para la fotorresistencia.
#define DHTTYPE DHT11 //Seleciona el tipo de sensor a utilizar.
DHT dht(DHTPIN, DHTTYPE); //Configura la librería pasando como parametros el pin y tipo de sensor.
int temperatura = 0; //Declara la variable que almacenara la temperatura.
int humedad = 0; //Declara la variable que almacenara la humedad.
int luminosidad = 0; //Declara la variable que almacenara la luminosidad.


//----------Variables para ARREGLOS DINAMICOS
boolean mostrarLista=false; //Determina en que momento se empiezan a visualizar los mensajes en el LCD.
int posicion=0; //Es la posicion de la lista que estará leyendo.
size_t cont; //Se especificara los elementos que tiene la lista.
size_t capacidad; //Variable para saber el tamaño de la lista.
String mensaje=""; //Variable utilizada para almacenar los caracteres que formaran un mensaje.
String* lista; //Declara un arreglo que servira para almacenar todos los mensajes.


//----------Variables para BOTONES DE ANTERIOR Y SIGUIENTE.
const int boton1=20; //Declara el pin 20 para el boton 1
const int boton2=21; //Declara el pin 21 para el boton 2
boolean estadoB1=false; //Verifica si el boton 1 esta presionado o no.
boolean estadoB2=false; //Verifica si el boton 2 esta presionado o no.
volatile boolean estadoAnterior= false; //Se define como volatile para poder modificar su valor en la interrupción cuyo valor cambiara a true cuando entre a la interrupción.
volatile boolean estadoSiguiente= false; //Se define como volatile para poder modificar su valor en la interrupción cuyo valor cambiara a true cuando entre a la interrupción.


//--------------------------------------------------------------------------SETUP

void setup(){
  pinMode(10,OUTPUT); //He establecido el pin 10 como la alimentación del LED de la pantalla LCD.
  digitalWrite(10,HIGH); //Enciende el LED.
  lcd.begin(16, 2); //Se inicia el LCD.
  Serial.begin(9600); //Se inicia la comunicación serial en 9600.
  
  dht.begin();//Inicializa la libreria DHT
  pinMode(LDR_pin,INPUT); //Establece que el LDR sera de entrada.
  
  crearLista(100); //Crea la lista con un valor por defecto de 100.

  pinMode(boton1, INPUT_PULLUP); //Establece la entrada del boton 1.
  pinMode(boton2, INPUT_PULLUP); //Establece la entrada del boton 2.
  attachInterrupt(digitalPinToInterrupt(boton1),anterior,LOW); //Se declara una interrupcion en el pin 20 que llamara la ISR anterior.
  attachInterrupt(digitalPinToInterrupt(boton2),siguiente,LOW); //Se declara una interrupcion en el pin 21 que llamara la ISR siguiente.
}


//----------------------------------------------------------------------------LOOP

void loop(){
  if(digitalRead(boton1)==HIGH and estadoAnterior==true){ //Si el boton1 no esta precionado y ya hubo una interrupción entra en el if. 
    posicion=posicion-2; //Como posicion ya incremento en uno y se desea retroceder de donde anteriormente estaba se restan 2.
    if(posicion<0){ //Para evitar acceder a una posicion que no existe.
	  posicion=cont-1;
    }
    estadoAnterior=false; //Regrese el estado a false indicando que ya se trato la interrupcion.
  }else{
    if(digitalRead(boton2)==HIGH and estadoSiguiente==true){ //Si el boton1 no esta precionado y ya hubo una interrupción entra en el if. 
       estadoSiguiente=false; //Regresa el estado a false indicando que ya se trato la interrupcion.
    }  
  }
  while (Serial.available() > 0) { //Indica que miesntras haya caracteres en el Serial por leer se repita.
    delay(1); //Espera un poco en dado caso que sean demasiados caracteres y los pueda leer todos.
    int c = (Serial.read()); //Almacena el caracter leido en c.
    if (c == 33) { //Si es 33 en valor ASCCI significa que se debe mensaje ya finalizo y se debe agregar.
      agregar(mensaje); //Agrega el mensaje a la lista.
      mostrarLista=true;
      mensaje=""; //Se limpia la variable mensaje.
    } else {
		if(c==126){ //Si es 126 en valor ASCCI significa que se debe eliminar un mensaje en la lista.
			int eliminar=0;
			for(eliminar=0;eliminar<cont;eliminar++){
				if(mensaje==lista[eliminar]){ //Cuando encuentra la coincidencia sale guardado el valor en eliminar de donde encontro dicha coincidencia.
					break;
				}
			}
			if(eliminar==(cont-1)){
				cont--;
				mostrarLista=false;
			}else{
				for(int i=eliminar;i<(cont-1);i++){ //Recorre la lista y disminuye contador en uno para indicar que ya se elimino el elemento deseado.
					lista[i]=lista[i+1];
				}
				cont--;
			}
			mensaje=""; //Se limpia la variable mensaje.
		}else{
			mensaje=mensaje+Decimal_a_ASCII(c); //Si no es ninguna de estos dos opciones concatena el caracter con el mensaje.
		}
    }
  }
  if(mostrarLista){ //Si ya hay elementos la lista se puede mostrar.
	int caracteres=(lista[posicion].length())-16; //resta 16 porque siempre los ultimos 16 caracteres seran de la fecha y hora en que se creo el mensaje.
	int tope=0;
	if(caracteres>16){ //Si el mensaje tiene mas de 16 caracteres (que son los que puede mostrar el LCD) entra en esta parte.
		for(tope=0;tope<(caracteres-16);tope++){
			lcd.clear(); //Se limpia el LCD
			lcd.setCursor(0,0); //Se posiciona en el renglon 1
			lcd.print(lista[posicion].substring(tope,(tope+16))); //Imprime 16 caracteres del mensaje y se iran recorriendo.
			lcd.setCursor(0,1); //Se posiciona en el renglon 2
			lcd.print(lista[posicion].substring(caracteres,(caracteres+16))); //Se imprime los caracteres que hallan sobrado del primer renglón, que seran los de la fecha y hora.
			delay(500); //Espera 500 milisegundos para ir recorriendo el mensaje a mostrar.
		}
	}
	lcd.clear(); //Se limpia el LCD
	lcd.setCursor(0,0); //Se posiciona en el renglon 1
	lcd.print(lista[posicion].substring(tope,caracteres)); //Imprime los ultimos 16 caracter del mensaje.
	lcd.setCursor(0,1); //Se posiciona en el renglon 2
	lcd.print(lista[posicion].substring(caracteres,(caracteres+16))); //Se imprime los caracteres que hallan sobrado del primer renglón, que seran los de la fecha y hora.
	delay(3000); //Se espera 3 segundos.
		if(posicion==(cont-1)){ //Sirve para mostrar la humedad, temperatura y luminosidad.
			posicion=0;
			humedad = dht.readHumidity();// Lee la humedad.
			temperatura = dht.readTemperature();//Lee la temperatura.
			luminosidad=analogRead(LDR_pin); //Lee la luminosidad.
			lcd.clear(); //Se limpia el LCD
			lcd.setCursor(0,0); //Se posiciona en el renglon 1
			lcd.print(String("T:")+temperatura+String("C H:")+humedad+String("% L:")+luminosidad); //Imprime la humedad, temperatura y luminosidad.
			delay(3000); //Se espera 3 segundos.
		}else{
		  posicion++; //En caso contrario que no sea la ultima posicion simplemente aumenta en 1.
		}
	}
}

//--------------------------------------------------------------------CREACIÓN DE FUNCIONES

/*
	La función crearLista recibe un parametro de la capacidad que sera el tamaño por defecto con la que empezará la lista, asi mismo se establece el cont
	a 0 indicando que aun no se encuentra ningún elemento en la lista.
*/
void crearLista(size_t parametroCapacidad){
  lista = new String[parametroCapacidad];
  capacidad = parametroCapacidad;
  cont = 0;
}
 
/*
	La función agregar permite como su nombre lo indica agregar nuevos elemntos a la lista sin antes verificar si el cont (elementos existentes en la lista)
	es menor a la capacidad, de ser asi se agrega el elemento a la lista aumentando cont, y en caso contrario se llama a la función de rescalar la lista 
	para permitir que se puedan almacenar más elementos.
*/ 
void agregar(String mensajeNuevo){
  ++cont;
  if (cont > capacidad){
    size_t nuevoTam = capacidad * 2;
    rescalar(nuevoTam);
  } 
  lista[cont - 1] = mensajeNuevo;
}
 
/*
	Una vez que se haya comprobado que no hay espacio disponible en la lista para agregar elementos se procede a rescalarla esto para que tenga un 
	tamaño mayor y asi nuevos elementos se puedan agregar sin problemas. Para realizar esto se borra crea una lista temporal con una mayor capacidad, se borra
	la lista anterior y por ultimo se asigna la lista temporal a la original.
*/
void rescalar(size_t nuevaCapacidad){
  String* listaTemporal = new String[nuevaCapacidad];
  memmove(listaTemporal, lista, cont  * sizeof(int));
  delete[] lista;
  capacidad = nuevaCapacidad;
  lista = listaTemporal;
}

/*
	Debido a que las entradas recibidas por el Serial son numeros se tiene que realizar una conversion para devolver el valor original, esto se logra por el 
	arreglo CARACTERES donde se definen los caracteres que el usuario puede agregar.
	Se resta 32 a la entrada debido a que en el codigo ASCCI el primer valor que el usuario puede agregar es el 32 y como nuestra lista comienza en 0 
	se debe restar 32 para acceder al caracter que el Serial nos manda.
*/
char Decimal_a_ASCII(int entrada){
  entrada=entrada-32;
  return CARACTERES[entrada];
}

/*
	Ambas funciones "anterior()" y "siguiente()" asignar el valor true a su respectiva variable para indicar que ya hubo una interrupcion y asi el programa
	pueda mostrar el anterior o siguiente valor en la lista.
*/

void anterior(){
  estadoAnterior=true;
}

void siguiente(){
  estadoSiguiente=true;
}