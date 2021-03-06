
## Procedementos para a base de datos de Calendario ##

DELIMITER $$

USE CALENDARIO$$

# Rexistra o usuario na base de datos
# devolve: o ID do usuario rexistrado  
DROP PROCEDURE IF EXISTS REXISTRAR_USUARIO$$
CREATE PROCEDURE REXISTRAR_USUARIO(IN NOME_USUARIO VARCHAR(20), IN PASSWD VARCHAR(30))
    MODIFIES SQL DATA
    BEGIN
		
        INSERT INTO USUARIOS(NOME, PASSWD) VALUES (NOME_USUARIO, PASSWD);
		SELECT ID_USUARIO
			FROM USUARIOS
            WHERE NOME = NOME_USUARIO;
        
    END$$
    


# Busca un usuario na base de datos por nome
# devolve: (ID_USUARIO, PASSWD) que son o id e contrasinal do usuario
DROP PROCEDURE IF EXISTS BUSCAR_USUARIO_POR_NOME$$
CREATE PROCEDURE BUSCAR_USUARIO_POR_NOME(IN NOM VARCHAR(25))
    READS SQL DATA
    BEGIN

        SELECT ID_USUARIO,
                PASSWD
            FROM USUARIOS
            WHERE NOME = NOM;

    END$$



# Borra un usuario da base de datos a partir do seu ID
DROP PROCEDURE IF EXISTS BORRAR_USUARIO$$
CREATE PROCEDURE BORRAR_USUARIO(IN ID_USER INT UNSIGNED)
    MODIFIES SQL DATA
    BEGIN
    
        DELETE FROM USUARIOS
            WHERE ID_USUARIO = ID_USER;
    
    END$$


# Crea un evento público.
# Primeiro o evento é rexistrado na táboa de eventos
# e despois na táboa de eventos_publicos
# devolve: ID do novo evento
DROP PROCEDURE IF EXISTS CREAR_EVENTO_PUBLICO$$
CREATE PROCEDURE CREAR_EVENTO_PUBLICO(IN NOME_EV VARCHAR(25), IN ID_CREAD INT UNSIGNED, IN DATA_EV DATE, IN HORA_EV TIME)
    MODIFIES SQL DATA
    BEGIN
    
        INSERT INTO EVENTOS(NOME, DATA_EVENTO, HORA, CREADOR) VALUES (NOME_EV, DATA_EV, HORA_EV, ID_CREAD);
        
        SELECT ID_EVENTO INTO @ID_EV
            FROM EVENTOS
            WHERE NOME = NOME_EV AND CREADOR = ID_CREAD AND DATA_EVENTO = DATA_EV;

        INSERT INTO EVENTOS_PUBLICOS(EVENTO) VALUES (@ID_EV);
        
        SELECT @ID_EV;
        
    END$$
    


# Crea un evento privado.
# - Primeiro o evento é rexistrado na táboa de eventos
# e despois na táboa de eventos_privados
# devolve: ID do novo evento
DROP PROCEDURE IF EXISTS CREAR_EVENTO_PRIVADO$$
CREATE PROCEDURE CREAR_EVENTO_PRIVADO(IN NOME_EV VARCHAR(25), IN ID_CREAD INT UNSIGNED, IN DATA_EV DATE, IN HORA_EV TIME)
    MODIFIES SQL DATA
    BEGIN
    
        INSERT INTO EVENTOS(NOME, DATA_EVENTO, HORA, CREADOR) VALUES (NOME_EV, DATA_EV, HORA_EV, ID_CREAD);
        
        SELECT ID_EVENTO INTO @ID_EV
            FROM EVENTOS
            WHERE NOME = NOME_EV AND CREADOR = ID_CREAD AND DATA_EVENTO = DATA_EV;

        INSERT INTO EVENTOS_PRIVADOS(EVENTO) VALUES (@ID_EV);
        
        SELECT @ID_EV;
        
    END$$



# Crea un evento grupal.
# - Primeiro o evento é rexistrado na táboa de eventos
# e despois na táboa de eventos_grupais
# devolve: ID do novo evento
DROP PROCEDURE IF EXISTS CREAR_EVENTO_GRUPAL$$
CREATE PROCEDURE CREAR_EVENTO_GRUPAL(IN NOME_EV VARCHAR(25), IN ID_CREAD INT UNSIGNED, IN DATA_EV DATE, IN HORA_EV TIME)
    MODIFIES SQL DATA
    BEGIN
    
        INSERT INTO EVENTOS(NOME, DATA_EVENTO, HORA, CREADOR) VALUES (NOME_EV, DATA_EV, HORA_EV, ID_CREAD);
        
        SELECT ID_EVENTO INTO @ID_EV
            FROM EVENTOS
            WHERE NOME = NOME_EV AND CREADOR = ID_CREAD AND DATA_EVENTO = DATA_EV;

        INSERT INTO EVENTOS_GRUPAIS(EVENTO) VALUES (@ID_EV);
        
        INSERT INTO GRUPAIS_USUARIOS(EVENTO_GRUPAL, USUARIO) VALUES (@ID_EV, ID_CREAD);
        
        SELECT @ID_EV;
        
    END$$
    
    

# Rexistra a un usuario como participante nun evento grupal
DROP PROCEDURE IF EXISTS REXISTRAR_EN_EVENTO_GRUPAL$$
CREATE PROCEDURE REXISTRAR_EN_EVENTO_GRUPAL(IN ID_EV INT UNSIGNED,IN ID_USER INT UNSIGNED )
    MODIFIES SQL DATA
    BEGIN
    
        INSERT INTO GRUPAIS_USUARIOS(EVENTO_GRUPAL, USUARIO) VALUES (ID_EV, ID_USER);
        
    END$$



# Devolve os eventos publicos para unha data determinada
DROP PROCEDURE IF EXISTS OBTER_EVENTOS_PUBLICOS$$
CREATE PROCEDURE OBTER_EVENTOS_PUBLICOS(IN DIA_EV DATE)
	READS SQL DATA
    BEGIN
    
		SELECT ID_EVENTO, 
				NOME, 
                CREADOR, 
                HORA 
			FROM VISTA_EVENTOS_PUBLICOS 
            WHERE DATA_EVENTO = DIA_EV;
    
    END$$
    


# Devolve os eventos privados dun usuario para unha data determinada
DROP PROCEDURE IF EXISTS OBTER_EVENTOS_PRIVADOS$$
CREATE PROCEDURE OBTER_EVENTOS_PRIVADOS(IN ID_USER INT UNSIGNED, IN DIA_EV DATE)
	READS SQL DATA
    BEGIN
    
		SELECT ID_EVENTO, 
				NOME, 
                CREADOR, 
                HORA 
			FROM VISTA_EVENTOS_PRIVADOS 
            WHERE CREADOR = ID_USER AND DATA_EVENTO = DIA_EV;
    
    END$$
    
    

# Devolve os eventos grupais dun usuario para unha data determinada
DROP PROCEDURE IF EXISTS OBTER_EVENTOS_GRUPAIS$$
CREATE PROCEDURE OBTER_EVENTOS_GRUPAIS(IN ID_USER INT UNSIGNED, IN DIA_EV DATE)
	READS SQL DATA
    BEGIN
    
		SELECT ID_EVENTO, 
				NOME, 
                CREADOR, 
                HORA 
			FROM VISTA_EVENTOS_GRUPAIS 
            WHERE USUARIO = ID_USER AND DATA_EVENTO = DIA_EV;
    
    END$$



# Devolve o nome dun usuario a partir do seu ID
DROP PROCEDURE IF EXISTS OBTER_NOME_USUARIO$$
CREATE PROCEDURE OBTER_NOME_USUARIO(IN ID_USER INT UNSIGNED)
	READS SQL DATA
    BEGIN
    
		SELECT NOME
			FROM USUARIOS 
            WHERE ID_USUARIO = ID_USER;
    
    END$$
    
    
# Devolve o ID do usuario polo nome. Se non está
# rexistrado devolverá un 0.
DROP PROCEDURE IF EXISTS ESTA_USUARIO_REXISTRADO$$
CREATE PROCEDURE ESTA_USUARIO_REXISTRADO(IN NOM VARCHAR(25))
	READS SQL DATA
    BEGIN
    
		DECLARE SALIDA INT UNSIGNED;
        
        SET SALIDA = (SELECT ID_USUARIO
						FROM USUARIOS
                        WHERE NOME = NOM
                        );
		
        IF SALIDA IS NULL
        THEN 
        
			SET SALIDA = 0;
            
		END IF;
        
        SELECT SALIDA;
        
	END$$


# 
DROP PROCEDURE IF EXISTS BUSCAR_EVENTO$$
CREATE PROCEDURE BUSCAR_EVENTO(IN NOME_EVENTO VARCHAR(25),IN DATA_EV DATE, IN ID_CREAD INT UNSIGNED)
    READS SQL DATA
    BEGIN
    
        DECLARE SALIDA INT UNSIGNED;
        
        SET SALIDA = (SELECT ID_EVENTO
						FROM EVENTOS
                        WHERE NOME like NOME_EVENTO AND DATA_EVENTO = DATA_EV AND CREADOR = ID_CREAD
					);
		
        IF SALIDA IS NULL
        THEN 
        
			SET SALIDA = 0;
            
		END IF;
        
        SELECT SALIDA;
    
    END$$
    


DROP PROCEDURE IF EXISTS BUSCAR_EVENTO_POR_USUARIO$$
CREATE PROCEDURE BUSCAR_EVENTO_POR_USUARIO(IN NOME_EVENTO VARCHAR(25),IN ID_USER DATE)
    READS SQL DATA
    BEGIN
    
        DECLARE SALIDA INT UNSIGNED;
        
        SET SALIDA = (SELECT ID_EVENTO
						FROM EVENTOS
                        WHERE NOME like NOME_EVENTO AND CREADOR = ID_USER
                        );
		
        IF SALIDA IS NULL
        THEN 
        
			SET SALIDA = 0;
            
		END IF;
        
        SELECT SALIDA;
    
    END$$

    
    
DROP PROCEDURE IF EXISTS BORRAR_EVENTO$$
CREATE PROCEDURE BORRAR_EVENTO(IN ID_EV INT UNSIGNED)
    MODIFIES SQL DATA
    BEGIN
    
        IF ID_EV IN (
                    SELECT ID_EVENTO
                        FROM EVENTOS)
        THEN
        
            DELETE FROM EVENTOS
            WHERE ID_EVENTO = ID_EV;
        
        ELSE 
        
            SIGNAL SQLSTATE "45001" SET MESSAGE_TEXT = "O evento que se intenta borrar non existe";
            
        END IF;
    
    END$$

DELIMITER ;
