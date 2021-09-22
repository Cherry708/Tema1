package exercicis

import java.io.File

/*
println("Introdueix un numero (-1 per acabar): ")
                userInput = readLine()!!.toInt()
                if (userInput != -1 && userInput <= f.listFiles().size && f.listFiles().get(userInput).isDirectory){
                    f = f.listFiles().sorted().get(userInput)
 */

fun main() {

    var userInput = 0
    val userInputOffset = 1
    var userSelection = 0
    var f = File.listRoots()[0]


    listarDirectorios(f)

    /*
    TODO:
    S'ha de controlar que hi ha permís de lectura sobre un directori, abans de canviar a ell,
    sinó donarà error (en la imatge, per exemple, segurament no es podrà canviar al directori root,
    ja que no tindrem permís de lectura sobre ell). Aquesta comprovació s'ha de fer abans de canviar
    al directori triat.
     */
    while (userInput != -1) {

        try {
            println("Introduce un numero (-1 para acabar): ")
                userInput = readLine()!!.toInt()
                userSelection = userInput - userInputOffset

            //En esta condicion verificamos que el input sea 0, no debemos usar userSelection
            //Si retrocedo en root peta, prueba try catch

            if (userInput == 0 && f.parentFile.exists()) {

                //f.parentFile
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

        //If end is not a directory, f:FILE will not be reassigned so 'if' in this branch will happen

    }
}

//fun isFile(f: File)

/**
 * Devuelve si el elemento de la lista ordenada de ficheros es o no legible.
 * @param f directorio desde el que listaremos el contenido
 * @param userSelection entrada de usurio junto con desplazamiento empleada para obtener el elemento deseado
 */
fun isReadable(f: File, userSelection:Int): Boolean{
    return f.listFiles().sorted().get(userSelection).canRead()
}

fun isDirectory(f: File, userSelection:Int): Boolean{
    return f.listFiles().sorted().get(userSelection).isDirectory
}

fun listarDirectorios(f: File) {
    val s = "Lista de ficheros y directorios del directorio " + f.getCanonicalPath()
    println(s)
    println("-".repeat(s.length))
    //Starts from 1 as 0 is reserved for root
    var fileCounter = 1
    println("0.- Introduce 0 para volver al parent")
    for (file in f.listFiles().sorted()) {
        if (file.isFile())
            println("$fileCounter.- " + file.getName() + "\t " + "<Fichero>"+"[Tamaño: "+file.length()+"]")
        if (file.isDirectory())
            println("$fileCounter.- " + file.getName() + "\t <Directorio>")
        fileCounter++
    }
}
