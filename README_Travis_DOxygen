TRAVIS CI: 
  - buildea y testea el codigo automaticamente cada vez que hacemos un commit a github
  - https://travis-ci.com/ nos metemos en la web iniciamos sesión con nuestra cuenta de GitHub y autorizamos los permisos
  - elegimos que repositorios queremos usar con travis
  - se configura a través del .travis.yml 
  - indicamos en ese file que scripts queremos runnear (en esta caso lo compila, genera el esquema y testea los junits)
  - esta configurado de modo que nos llegue un email notificandonos de ha habido un error
  - en https://travis-ci.org/github/SPQ19-20/BSPQ20-E4 o si damos click al icono 'build' debajo del logo en el README nos lleva a la pagina de travis donde podemos ver el historial de builds
 
 DOxygen:
 - descargarse Ghostscripts, mikTek y el propio doxygen para que funcione
 - hay q meter los esos 3 directorios bin en el Path de las variables del sistema
 - se añaden plugins y properties al pom.xml (ya está hecho)
 - es necesario crear un config file (YA ESTÁ HECHO - Doxyfile (en resources))
 se utiliza el comando: doxygen -g nombrequequeramosparaelfile (si ponemos solo doxygen -g el nombre por defecto es Doxyfile)
 a continuación se utiliza el comando para terminar de crear el file (si nos da un error de OUTPUT DIRECTORY nicaso, funciona igual) : doxygen Doxyfile
 (NO SE VUELVEN A USAR ESTOS COMANDOS)
 
 - GitHub pages (???) por lo visto hay que crear un nuevo repositorio (usuario.github.io) para comentar los documentos con doxygen 
 - cada vez que hagamos una modificación en el config file o añadamos documentación hay que usar el comando : 
 mvn doxygen:report (donde esta el pom.xml)
 mvn validate
 
 CREAMOS LA CARPETA ' doxygen ' dentro de target\site 
 nos crea 2 carpetas (html e latex) dentro de doxygen (en donde hemos expecificado el OUTPUT_DIRECTORY en Doxyfile (osea en target\site\doxygen)
 html\index.html -> nos enseña como queda la documentación de cada clase
 latex (???) -> to be discovered
 
 
  
