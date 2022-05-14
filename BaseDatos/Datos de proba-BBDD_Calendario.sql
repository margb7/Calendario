
## Problema -> garda a base de datos as contrasinais case sensitive?

INSERT INTO USUARIOS(NOME, PASSWD) VALUES   
    ("administrador", "renaido"),               -- renaido non cumple como contrasinal válida
    ("user2", "abc123");
    
INSERT INTO EVENTOS(NOME, DATA_EVENTO, HORA) VALUES ("NAVIDAD", "2022-12-25", CURRENT_TIME()),
                                                    ("EVENTO 1", "2022-06-02", CURRENT_TIME()),
                                                    ("EVENTO 2", "2022-06-01", CURRENT_TIME()),
                                                    ("EVENTO 3", "2022-05-15", CURRENT_TIME()),
                                                    ("EVENTO 4", "2022-05-22", CURRENT_TIME());


## TEMPORAL -> despois úsanse as funcions para crear eventos

SELECT ID_EVENTO INTO @CLAVE_EVENTO 
    FROM EVENTOS 
    WHERE NOME = "EVENTO 1";
    
SELECT ID_USUARIO INTO @CLAVE_USER 
    FROM USUARIOS
    WHERE NOME = "ADMINISTRADOR";

INSERT INTO EVENTOS_PRIVADOS(EVENTO, USUARIO) VALUES (@CLAVE_EVENTO, @CLAVE_USER);

SELECT ID_EVENTO INTO @CLAVE_EVENTO 
    FROM EVENTOS
    WHERE NOME = "EVENTO 2";
    
INSERT INTO EVENTOS_PRIVADOS(EVENTO, USUARIO) VALUES (@CLAVE_EVENTO, @CLAVE_USER);


SELECT ID_EVENTO INTO @CLAVE_EVENTO 
    FROM EVENTOS
    WHERE NOME = "EVENTO 3";
INSERT INTO EVENTOS_PUBLICOS(EVENTO, CREADOR) VALUES (@CLAVE_EVENTO, @CLAVE_USER);



SELECT ID_EVENTO INTO @CLAVE_EVENTO 
    FROM EVENTOS
    WHERE NOME = "EVENTO 4";
SELECT ID_USUARIO INTO @CLAVE_USER 
    FROM USUARIOS
    WHERE NOME = "user2";

INSERT INTO EVENTOS_PRIVADOS(EVENTO, USUARIO) VALUES (@CLAVE_EVENTO, @CLAVE_USER);