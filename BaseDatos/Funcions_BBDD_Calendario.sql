## Tabla con códigos de erro ##
-- 45001 -> usuario non atopado

##
##  FUNCIÓNS DE LECTURA
##

DELIMITER $$

USE BBDD_CALENDARIO$$

DROP FUNCTION OBTER_NUMERO_USUARIO$$
CREATE FUNCTION OBTER_NUMERO_USUARIO(NOM VARCHAR(20))
    RETURNS INT UNSIGNED
    READS SQL DATA
    BEGIN
    
		DECLARE SAIDA INT UNSIGNED;
        
        SET SAIDA = (
						SELECT ID_USUARIO
							FROM USUARIOS
                            WHERE NOMBRE = NOM
					);
                    
		IF SAIDA = NULL
        THEN
        
			SIGNAL SQLSTATE "45001" SET MESSAGE_TEXT = "NON SE ATOPOU O USUARIO";
		
        END IF;
        
        RETURN SAIDA;
        
	END$$





# TODO: devolver a clave do evento creado 

/*
## Usado para crear un evento.
## @throws 45001 se non atopa o usuario polo nome proporcionado
CREATE PROCEDURE CREAR_EVENTO(IN NOM_EV VARCHAR(25), IN DATA_EV DATE, IN HORA_EV TIME, IN NOM_USER VARCHAR(25), IN TIPO TINYINT, OUT OK BOOLEAN)
	MODIFIES SQL DATA
    BEGIN

        CASE TIPO
			WHEN 1				-- Privado
				THEN 
					
					-- Falta obter a clave do evento creado
					INSERT INTO EVENTOS (NOME, DATA_EVENTO, HORA) VALUES (NOM_EV, DATA_EV, HORA_EV);
					
        
			WHEN 2				-- Grupal
				THEN
        
					-- Falta obter a clave do evento creado
					INSERT INTO EVENTOS (NOME, DATA_EVENTO, HORA) VALUES (NOM_EV, DATA_EV, HORA_EV);
					INSERT INTO EVENTOS_GRUPAIS (CLAVE_EVENTO, OBTER_NUMERO_USUARIO(NOM_USER));
        
			WHEN 3				-- Público
				THEN
                
					-- Falta obter a clave do evento creado
					INSERT INTO EVENTOS (NOME, DATA_EVENTO, HORA) VALUES (NOM_EV, DATA_EV, HORA_EV);
					INSERT INTO EVENTOS_PUBLICOS (CLAVE_EVENTO, OBTER_NUMERO_USUARIO(NOM_USER));
                
        END CASE;
    
	END$$

DELIMITER ;*/