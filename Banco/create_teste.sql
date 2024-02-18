--Criando Usuario
ALTER SESSION SET "_ORACLE_SCRIPT" = true;
ALTER SESSION SET CURRENT_SCHEMA = LHP;
CREATE USER LHP IDENTIFIED BY lhp DEFAULT tablespace users;
ALTER USER LHP quota UNLIMITED on users;
GRANT CONNECT, RESOURCE TO LHP;
ALTER SESSION SET current_schema = lhp;

-- Excluir tabelas
DROP TABLE T_LHP_CAMISA;
DROP TABLE T_LHP_TIME;
DROP TABLE T_LHP_IMAGEM;

-- Criar Tabelas
CREATE TABLE T_LHP_TIME(
    cd_time NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
     
    nm_time NVARCHAR2(45),
    nm_pais NVARCHAR2(45),
    ds_liga NVARCHAR2(100)
);

ALTER TABLE T_LHP_TIME ADD CONSTRAINT PK_T_LHP_TIME PRIMARY KEY(cd_time);

CREATE TABLE T_LHP_CAMISA(
cd_camisa NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
dt_ano DATE,
ds_infomativo NVARCHAR2(250), 
nm_camisa NVARCHAR2(100),
ds_cor NVARCHAR2(50),
st_status CHAR(1)
);

ALTER TABLE T_LHP_CAMISA DROP COLUMN cd_camisa;
ALTER TABLE T_LHP_CAMISA ADD CONSTRAINT PK_T_LHP_CAMISA  PRIMARY KEY(cd_camisa);
ALTER TABLE T_LHP_CAMISA ADD cd_time INT CONSTRAINT cd_time_T_LHP_CAMISA_fk 
  REFERENCES T_LHP_TIME(cd_time);
  
 ALTER TABLE T_LHP_IMAGEM
DROP CONSTRAINT fk_cd_camisa; 

ALTER TABLE T_LHP_CAMISA
ADD CONSTRAINT fk_cd_time
  FOREIGN KEY (cd_time)
  REFERENCES T_LHP_TIME(cd_time)
  ON DELETE CASCADE;
  
  ALTER TABLE T_LHP_IMAGEM
ADD CONSTRAINT fk_cd_camisa
  FOREIGN KEY (cd_camisa)
  REFERENCES T_LHP_CAMISA(cd_camisa)
  ON DELETE CASCADE;
  
CREATE TABLE T_LHP_IMAGEM (
cd_imagem NUMERIC GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY
, cd_camisa NUMERIC
, ds_pasta NVARCHAR2(50)
, ds_conteudo NVARCHAR2(50)
, CONSTRAINT fk_cd_camisa FOREIGN KEY (cd_camisa) REFERENCES T_LHP_CAMISA(cd_camisa)
);

ALTER TABLE T_LHP_IMAGEM MODIFY (ds_conteudo NVARCHAR2(500));
ALTER TABLE T_LHP_CAMISA RENAME COLUMN ds_infomativo TO ds_informativo; 

-- INSERT
INSERT INTO T_LHP_TIME(nm_time, nm_pais, ds_liga) VALUES ('al-nassr football club', 'arábia saudita', 'campeonato saudita de futebol');
INSERT INTO T_LHP_CAMISA(dt_ano, ds_informativo, nm_camisa, ds_cor, st_status) VALUES (TO_DATE('2024/05', 'yyyy/mm'),'segundo uniforme', 'away','azul marinho','A');
INSERT INTO T_LHP_IMAGEM(cd_camisa, ds_conteudo) VALUES (1, 'https://images.tcdn.com.br/img/img_prod/1052037/camisa_al_nassr_away_2023_24_4295_1_d3658130c65d5c0b3e8f7891bd74d5c7.jpg');

-- UPDATE
UPDATE T_LHP_TIME  SET ds_liga = 'brasileirão serie a' WHERE cd_time IN (281);
UPDATE T_LHP_TIME SET nm_time = 'real madrid' where cd_time = 81;
UPDATE T_LHP_CAMISA SET ds_liga = 'navy' where ds_cor = 'azul marinho';
-- DELETE
DELETE FROM T_LHP_CAMISA where cd_camisa = 2;
DELETE FROM T_LHP_TIME where CD_TIME IN (21,41);

--Consultas
SELECT * FROM T_LHP_IMAGEM;
SELECT * FROM  T_LHP_CAMISA;
SELECT * FROM  T_LHP_TIME ORDER BY nm_time ASC;
SELECT * FROM T_LHP_TIME WHERE nm_time LIKE '%coritiba%';
SELECT DISTINCT ds_liga from t_lhp_time;
SELECT DISTINCT ds_cor from t_lhp_camisa;
SELECT dt_ano from t_lhp_camisa;
SELECT 
    t.cd_time AS "Identificação"
    ,t.nm_time AS "Nome do Time"
    ,c.nm_camisa AS "Camisa"
    ,c.dt_ano AS "Temporada"
    ,i.ds_conteudo AS "Link da Imagem" 
FROM T_LHP_TIME t
    INNER JOIN T_LHP_CAMISA c ON t.cd_time = c.cd_time
    INNER JOIN T_LHP_IMAGEM i ON c.cd_camisa = i.cd_camisa
WHERE t.nm_time LIKE '%%' AND t.ds_liga like'%%' AND c.ds_cor like '%%'; 
