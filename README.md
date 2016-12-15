# Locadora de veiculos web

# Configuração do DataSource no Apache tomcat

Arquivo context.xml do servidor

 	<Resource name="jdbc/locadoraDB" auth="Container"
	    factory="org.apache.naming.factory.BeanFactory" 
	    type="com.mchange.v2.c3p0.ComboPooledDataSource"
	    driverClass="com.mysql.jdbc.Driver" 
	    jdbcUrl="jdbc:mysql://localhost:3306/locadoradb"
	    user="root" 
	    password="*******" 
	    minPoolSize="3" 
	    maxPoolSize="3"/>

Arquivo web.xml da aplicação

	<resource-ref>
		<description>DB Connection</description>
		<res-ref-name>jdbc/locadoraDB</res-ref-name>
		<res-type>javax.sql.DataSource</res-type>
		<res-auth>Container</res-auth>
	</resource-ref>	    
	
Obs:

	Você deve copiar as bibliotecas do C3P0 para a pasta lib do tomcat.
	Bibliotecas estas:
		* c3p0-0.9.5.1.jar
		* mchange-commons-java-0.2.10.jar
		* mysql-connector-java-5.1.35.jar (Drive do banco que você esta utilizando)