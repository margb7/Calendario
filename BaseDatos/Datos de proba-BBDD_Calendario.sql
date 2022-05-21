
USE CALENDARIO;

## Datos de proba ##

CALL REXISTRAR_USUARIO("administrador", "renaido"); -- renaido non cumple como contrasinal válida
CALL REXISTRAR_USUARIO("user2", "abc123");
CALL REXISTRAR_USUARIO("sineventos", "abc123");   -- usuario de proba -> solo deberían aparecerlle os eventos públicos


SELECT ID_USUARIO INTO @CLAVE1 
    FROM USUARIOS
    WHERE NOME = "ADMINISTRADOR";
    
SELECT ID_USUARIO INTO @CLAVE2
    FROM USUARIOS
    WHERE NOME = "USER2";
    
                                                    
CALL CREAR_EVENTO_PUBLICO("EVENTO PUBLICO" , @CLAVE2, "2022-05-15", CURRENT_TIME());
CALL CREAR_EVENTO_PUBLICO("Navidad" , @CLAVE1, "2022-12-25", CURRENT_TIME());
CALL CREAR_EVENTO_PUBLICO("Fin de año" , @CLAVE1, "2022-12-31", CURRENT_TIME());
CALL CREAR_EVENTO_PUBLICO("Junioooo" , @CLAVE1, "2022-06-02", CURRENT_TIME());
CALL CREAR_EVENTO_PUBLICO("Primavera" , @CLAVE1, "2022-03-20", CURRENT_TIME());
CALL CREAR_EVENTO_PUBLICO("Verán" , @CLAVE1, "2022-06-21", CURRENT_TIME());
CALL CREAR_EVENTO_PUBLICO("Outono" , @CLAVE1, "2022-09-23", CURRENT_TIME());
CALL CREAR_EVENTO_PUBLICO("Inverno" , @CLAVE1, "2022-12-21", CURRENT_TIME());


CALL CREAR_EVENTO_GRUPAL("Evento grupal", @CLAVE1, "2022-06-02", CURRENT_TIME());
CALL CREAR_EVENTO_GRUPAL("Evento grupal 2", @CLAVE2, "2022-06-02", CURRENT_TIME());

SELECT ID_EVENTO INTO @ID_EV
    FROM EVENTOS
    WHERE NOME = "Evento grupal 2";

CALL REXISTRAR_EN_EVENTO_GRUPAL(@ID_EV,@CLAVE1);	# Tiene que ser llamado en una transacción

CALL CREAR_EVENTO_PRIVADO("Evento privado de admin", @CLAVE1, "2022-06-02", CURRENT_TIME());
CALL CREAR_EVENTO_PRIVADO("Evento privado de user2", @CLAVE2, "2022-06-02", CURRENT_TIME());


CALL BORRAR_EVENTO(14);