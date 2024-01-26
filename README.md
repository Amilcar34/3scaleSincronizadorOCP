Las ejecuciones que se deseen hacer desde consola deben ser ejecutadas

Desde la clase app.Helper actualizar los tokens de acuerdo al OCP X a conectarse

> mvn exec:java -Dexec.mainClass={{PAQUETE}}.{{CLASE}}

EJ:

> mvn exec:java -Dexec.mainClass=ocp4.scale.AseAutorizaciones