Esse repositório contém: 
Digrama do banco
Banco os dados para criação das tabelas e primeira inclusão de dados ao banco
BE: Aplicação

Necessario criar a pasta .properties e o config.properties inclui-lo na pasta dentro do projeto
Esse arquivo deve conter as informações para conexão com banco de dados
<br>Banco = 
<br>Host = <br>Porta = <br>
Login = <br>
Senha = <br>
Sid = <br>

Context.xml  <br>

<Resource name="jdbc/nameRep" auth="Container" type="javax.sql.DataSource"
              maxActive="100" maxIdle="30" maxWait="10000"
              username="login" password="senha" driverClassName="driver"
              url="jdbc:oracle:thin:@localhost:porta:sid"/>