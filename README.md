Desde la clase app.Helper actualizar los tokens de acuerdo al OCP X a conectarse

Las ejecuciones que se deseen hacer desde consola deben ser ejecutadas

> mvn exec:java -Dexec.mainClass={{PAQUETE}}.{{CLASE}}

EJ: Apagar todos los pods de OCP 3 para dev y test de ADT y SUME:

> mvn exec:java -Dexec.mainClass=ocp3.pods.ApagarPodsSUME


Los paquetes teienen la siguiente intencion

 app -> todo aquello comun a la aplicacion
 ocp3 -> ejecuciones, namespace, comunes a OCP 3
   -> pods
 ocp4 -> el mas enrriquesido contiene ejecuciones, namespace, comunes a OCP 4