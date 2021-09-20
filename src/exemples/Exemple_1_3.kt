package exemples

import java.io.File

fun main(args: Array<String>) {
    val f = File("fitxers/../fitxers/f1.txt")
    println("Nom del fitxer: " + f.getName())
    println("Ruta del fitxer: " + f.getPath())
    println("Ruta absoluta del fitxer: " + f.getAbsolutePath())
    println("Ruta canònica del fitxer: " + f.getCanonicalPath())
    if (f.exists())
        println("Si existe")
    else
        println("No existe")
}

