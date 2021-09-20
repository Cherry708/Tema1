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
        if (f.exists()) {
            println("Introdueix un numero (-1 per acabar): ")
            userInput = readLine()!!.toInt()

            if (userInput == 0){
                f = File.listRoots()[0]
                listarDirectorios(f)
            } else {
                if (userInput != -1 && userInput <= f.listFiles().size) {
                    if (f.listFiles().get(userInput).isDirectory) {
                        f = f.listFiles().sorted().get(userInput - userInputOffset)
                        listarDirectorios(f)
                    } else {
                        println("El destino no es un directorio.")
                        //If end is not a directory, f:FILE will not be reassigned so 'if' in this branch will happen
                    }
                } else if (userInput != -1)
                    println("El destino esta fuera de rango. Comprueba el indice.")
                    println()
                    listarDirectorios(f)
            }
        } else
            println("El directorio no existe.")
    }
}

fun listarDirectorios(f: File) {
    val s = "Llista de fitxers i directoris del directori " + f.getCanonicalPath()
    println(s)
    println("-".repeat(s.length))
    //Starts from 1 as 0 is reserved for root
    var fileCounter = 1
    println("0.- Introduce 0 para volver a root")
    for (e in f.listFiles().sorted()) {
        if (e.isFile())
            println("$fileCounter.- " + e.getName() + "\t " + e.length())
        if (e.isDirectory())
            println("$fileCounter.- " + e.getName() + "\t <Directori>")
        fileCounter++
    }
}
