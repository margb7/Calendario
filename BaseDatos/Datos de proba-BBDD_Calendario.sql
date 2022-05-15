
## Problema -> garda a base de datos as contrasinais case sensitive?

CALL REXISTRAR_USUARIO("administrador", "renaido"); -- renaido non cumple como contrasinal válida
CALL REXISTRAR_USUARIO("user2", "abc123");


SELECT ID_USUARIO INTO @CLAVE1 
    FROM USUARIOS
    WHERE NOME = "ADMINISTRADOR";
    
SELECT ID_USUARIO INTO @CLAVE2
    FROM USUARIOS
    WHERE NOME = "USER2";
    
INSERT INTO EVENTOS(NOME, DATA_EVENTO, HORA, CREADOR) VALUES 
                                                    ("EVENTO 1", "2022-06-02", CURRENT_TIME(), @CLAVE1),
                                                    ("EVENTO 2", "2022-06-01", CURRENT_TIME(), @CLAVE2),
                                                    ("EVENTO 4", "2022-05-22", CURRENT_TIME(), @CLAVE1),
                                                    ("Evento grupal", "2022-06-02", CURRENT_TIME(), @CLAVE1);
                                                    
CALL CREAR_EVENTO_PUBLICO("EVENTO PUBLICO" , @CLAVE2, "2022-05-15", CURRENT_TIME());
CALL CREAR_EVENTO_PUBLICO("Navidad" , @CLAVE1, "2022-12-25", CURRENT_TIME());
CALL CREAR_EVENTO_PUBLICO("Fin de año" , @CLAVE1, "2022-12-31", CURRENT_TIME());
CALL CREAR_EVENTO_PUBLICO("Junioooo" , @CLAVE1, "2022-06-02", CURRENT_TIME());

## TEMPORAL -> despois úsanse as funcions para crear eventos

SELECT ID_EVENTO INTO @CLAVE_EVENTO 
    FROM EVENTOS 
    WHERE NOME = "Evento grupal";

INSERT INTO EVENTOS_GRUPAIS(EVENTO) VALUES (@CLAVE_EVENTO);
INSERT INTO GRUPAIS_USUARIOS(EVENTO_GRUPAL, USUARIO) VALUES (@CLAVE_EVENTO, @CLAVE1);
INSERT INTO GRUPAIS_USUARIOS(EVENTO_GRUPAL, USUARIO) VALUES (@CLAVE_EVENTO, @CLAVE2);


SELECT ID_EVENTO INTO @CLAVE_EVENTO 
    FROM EVENTOS 
    WHERE NOME = "EVENTO 1";

INSERT INTO EVENTOS_PRIVADOS(EVENTO) VALUES (@CLAVE_EVENTO);

SELECT ID_EVENTO INTO @CLAVE_EVENTO 
    FROM EVENTOS
    WHERE NOME = "EVENTO 2";
    
INSERT INTO EVENTOS_PRIVADOS(EVENTO) VALUES (@CLAVE_EVENTO);




SELECT ID_EVENTO INTO @CLAVE_EVENTO 
    FROM EVENTOS
    WHERE NOME = "EVENTO 4";

INSERT INTO EVENTOS_PRIVADOS(EVENTO) VALUES (@CLAVE_EVENTO);

