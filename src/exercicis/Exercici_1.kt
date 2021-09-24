package exercicis

import java.io.File
import java.text.SimpleDateFormat;

/**
 * Ejercicio 1.
 * Este programa está destinado a listar y facilitar la navegación
 * por los ficheros y directorios del root de un sistema de ficheros
 * Linux o de una unidad de Windows a través del método listRoots()
 * de la clase File.
 * Durante el listado puede mostrar lo siguiente sobre el fichero:
 * 1.- Nombre
 * 2.- Directorio o fichero
 * 3.- Tamaño del fichero
 * 4.- Permisos del directorio o fichero
 * 5.- Fecha de última modificación del directorio o fichero
 *
 * Rubén Serrano Cano 2ºDAM
 */

fun main() {

    var userInput = 0
    val userInputOffset = 1
    var userSelection: Int
    var f = File.listRoots()[0]

    listarDirectorios(f)

    while (userInput != -1) {

        try {
            println("Introduce un numero (-1 para acabar): ")
                userInput = readLine()!!.toInt()
                userSelection = userInput - userInputOffset

            if (userInput == 0 && f.parentFile.exists()) {

                f = f.parentFile
                listarDirectorios(f)

            } else if (userInput == -1) {
                println("Saliendo...")

            } else if (userSelection in 0..f.listFiles().size && isDirectory(f, userSelection) && isReadable(f, userSelection)
            ) {

                f = f.listFiles().sorted().get(userSelection)
                listarDirectorios(f)

            } else if (!isReadable(f, userSelection)) {

                println("No tienes acceso a este directorio.")

            } else if (!f.listFiles().sorted().get(userSelection).isDirectory) {

                println("El destino no es un directorio.")

            }
        } catch (e: AccessDeniedException){
            println("No tienes acceso a este directorio B.")
        } catch (e: NumberFormatException){
            println("Error de entrada.")
            println("Debes introducir el índice del destino.")
        } catch (e: ArrayIndexOutOfBoundsException){
            println("El destino esta fuera de rango. Comprueba el indice.")
        } catch (e: NullPointerException){
            /*
            Windows:
            En ocasiones el usuario requiere permisos del administrador de Windows
            y resulta en esta excepción. La excepción que normalmente lanza el objeto
            al no haber parent también puede ser lanzada por este motivo.
             */
            println("No hay parent o puedes no tener permisos de administrador para ese destino(Windows).")
            println("Estás en: "+f.canonicalPath)

        }
    }
}


/**
 * Devuelve si el elemento de la lista ordenada de ficheros es o no legible.
 * @param f directorio desde el que listaremos el contenido.
 * @param userSelection entrada de usuario junto con desplazamiento, empleada para obtener el elemento deseado.
 */
fun isReadable(f: File, userSelection:Int): Boolean{
    return f.listFiles().sorted().get(userSelection).canRead()
}

/**
 * Devuelve si el elemento de la lista ordenada de ficheros es o no un directorio
 * @param f directorio desde el que listaremos el contenido.
 * @param userSelection entrada de usuario junto con desplazamiento, empleada para obtener el elemento deseado.
 */
fun isDirectory(f: File, userSelection:Int): Boolean{
    return f.listFiles().sorted().get(userSelection).isDirectory
}

/**
 * Muestra información diferente de todos los ficheros del directorio actual por consola,
 * es capaz de especificar el nombre, si es directorio o fichero, el tamaño,
 * mostrar permisos y la última fecha de modificación.
 * @param f directorio desde el que listaremos el contenido.
 */
fun listarDirectorios(f: File) {
    val sdf = SimpleDateFormat("MM-dd-yyyy HH:mm:ss")
    val s = "Lista de ficheros y directorios del directorio " + f.getCanonicalPath()
    println(s)
    println("-".repeat(s.length))
    //Starts from 1 as 0 is reserved for root
    var fileCounter = 1
    println("0.- Introduce 0 para volver al parent")
    for (file in f.listFiles().sorted()) {

        if (file.isFile && file.canRead() && file.canWrite() && file.canExecute())
            println("$fileCounter.- " + file.getName() + "\t "
                    + "<Fichero>"+"[Tamaño: "+file.length()+"]"+" [rwx] "+sdf.format(file.lastModified()))

        else if (file.isFile && file.canRead() && file.canWrite() && !file.canExecute())
            println("$fileCounter.- " + file.getName() + "\t "
                    + "<Fichero>"+"[Tamaño: "+file.length()+"]"+" [rw-] "+sdf.format(file.lastModified()))

        else if (file.isFile && file.canRead() && !file.canWrite() && !file.canExecute())
            println("$fileCounter.- " + file.getName() + "\t "
                    + "<Fichero>"+"[Tamaño: "+file.length()+"]"+" [r--] "+sdf.format(file.lastModified()))

        else if (file.isFile && !file.canRead() && !file.canWrite() && !file.canExecute())
            println("$fileCounter.- " + file.getName() + "\t "
                    + "<Fichero>"+"[Tamaño: "+file.length()+"]"+" [---] "+sdf.format(file.lastModified()))

        else if (file.isFile && !file.canRead() && !file.canWrite() && file.canExecute())
            println("$fileCounter.- " + file.getName() + "\t "
                    + "<Fichero>"+"[Tamaño: "+file.length()+"]"+" [--x] "+sdf.format(file.lastModified()))

        else if (file.isFile && !file.canRead() && file.canWrite() && !file.canExecute())
            println("$fileCounter.- " + file.getName() + "\t "
                    + "<Fichero>"+"[Tamaño: "+file.length()+"]"+" [-w-] "+sdf.format(file.lastModified()))

        else if (file.isFile && file.canRead() && file.canWrite() && !file.canExecute())
            println("$fileCounter.- " + file.getName() + "\t "
                    + "<Fichero>"+"[Tamaño: "+file.length()+"]"+" [rw-] "+sdf.format(file.lastModified()))

        else if (file.isFile && file.canRead() && !file.canWrite() && file.canExecute())
            println("$fileCounter.- " + file.getName() + "\t "
                    + "<Fichero>"+"[Tamaño: "+file.length()+"]"+" [r-x] "+sdf.format(file.lastModified()))

        else if (file.isFile && !file.canRead() && file.canWrite() && file.canExecute())
            println("$fileCounter.- " + file.getName() + "\t "
                    + "<Fichero>"+"[Tamaño: "+file.length()+"]"+" [-wx] "+sdf.format(file.lastModified()))



        if (file.isDirectory && file.canRead() && file.canWrite() && file.canExecute())
            println("$fileCounter.- " + file.getName() + "\t "
                    + "<Directorio>"+" [rwx] "+sdf.format(file.lastModified()))

        else if (file.isDirectory && file.canRead() && file.canWrite() && !file.canExecute())
            println("$fileCounter.- " + file.getName() + "\t "
                    + "<Directorio>"+" [rw-] "+sdf.format(file.lastModified()))

        else if (file.isDirectory && file.canRead() && !file.canWrite() && !file.canExecute())
            println("$fileCounter.- " + file.getName() + "\t "
                    + "<Directorio>"+" [r--] "+sdf.format(file.lastModified()))

        else if (file.isDirectory && !file.canRead() && !file.canWrite() && !file.canExecute())
            println("$fileCounter.- " + file.getName() + "\t "
                    + "<Directorio>"+" [---] "+sdf.format(file.lastModified()))

        else if (file.isDirectory && !file.canRead() && !file.canWrite() && file.canExecute())
            println("$fileCounter.- " + file.getName() + "\t "
                    + "<Directorio>"+" [--x] "+sdf.format(file.lastModified()))

        else if (file.isDirectory && !file.canRead() && file.canWrite() && !file.canExecute())
            println("$fileCounter.- " + file.getName() + "\t "
                    + "<Directorio>"+" [-w-] "+sdf.format(file.lastModified()))

        else if (file.isDirectory && file.canRead() && file.canWrite() && !file.canExecute())
            println("$fileCounter.- " + file.getName() + "\t "
                    + "<Directorio>"+" [rw-] "+sdf.format(file.lastModified()))

        else if (file.isDirectory && file.canRead() && !file.canWrite() && file.canExecute())
            println("$fileCounter.- " + file.getName() + "\t "
                    + "<Directorio>"+" [r-x] "+sdf.format(file.lastModified()))

        else if (file.isDirectory && !file.canRead() && file.canWrite() && file.canExecute())
            println("$fileCounter.- " + file.getName() + "\t "
                    + "<Directorio>"+" [-wx] "+sdf.format(file.lastModified()))

        /*
        if (file.isDirectory && file.canRead() && file.canWrite() && file.canExecute())
            println("$fileCounter.- " + file.getName() +
                    "\t <Directorio>"+" [rwx] "+sdf.format(file.lastModified()))

        else if (file.isDirectory && file.canRead() && file.canWrite() && !file.canExecute())
            println("$fileCounter.- " + file.getName() +
                    "\t <Directorio>"+" [rw-] "+sdf.format(file.lastModified()))

        else if (file.isDirectory && file.canRead() && !file.canWrite() && !file.canExecute())
            println("$fileCounter.- " + file.getName() +
                    "\t <Directorio>"+" [r--] "+sdf.format(file.lastModified()))

        else if (file.isDirectory && !file.canRead() && !file.canWrite() && !file.canExecute())
            println("$fileCounter.- " + file.getName() +
                    "\t <Directorio>"+" [---] "+sdf.format(file.lastModified()))

        else if (file.isDirectory && !file.canRead() && !file.canWrite() && !file.canExecute())
            println("$fileCounter.- " + file.getName() +
                    "\t <Directorio>"+" [---] "+sdf.format(file.lastModified()))
        /*
        else if (file.isDirectory){
            println("$fileCounter.- " + file.getName() + "\t "
                    + "<Directory>"+"[Tamaño: "+file.length()+"]"+" [---] "+sdf.format(file.lastModified()))
        }

         */

         */
        fileCounter++
    }
}
