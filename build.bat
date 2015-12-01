cd %~d0
cd %~dp0

set JAVA_HOME=D:\Program Files\Java\jdk1.6.0_45
set MAVEN_HOME=D:\apache-maven-3.2.1

set path=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%path%
mvn clean package
pause