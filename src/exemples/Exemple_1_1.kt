package exemples

import java.io.File

fun main(args: Array<String>) {
    val f = File(".") // "." es el root, en este caso Tema1, por lo tanto, veremos lo que contiene
    println("Llista de fitxers i directoris del directori actual")
    println("---------------------------------------------------")
    for (e in f.list().sorted())
        println(e);
}

