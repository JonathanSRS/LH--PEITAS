Esse repositório contém: 
Documentos do projeto na Branch document
Banco: Schema para criação das tabelas e primeira inclusão de dados ao banco
BE: Aplicação

Integração com banco
Criar a pasta .properties com arquivo config.properties dentro diretorio do projeto
Esse arquivo deve conter as informações para conexão com banco de dados
<br>Banco = 
<br>Host =<br>
Porta = <br>
Login = <br>
Senha = <br>
Sid = <br>

Integrar Server 
No arquivo Context.xml adicionar os valores para conexeção com banco  
<br>
-<
-Resource name="jdbc/nameRep" auth="Container" type="javax.sql.DataSource"
-              maxActive="100" maxIdle="30" maxWait="10000"
-              username="login" password="senha" driverClassName="driver"
-             url="jdbc:oracle:thin:@localhost:porta:sid"
-/>

A pasta WebContent contém as primeiras telas projeto