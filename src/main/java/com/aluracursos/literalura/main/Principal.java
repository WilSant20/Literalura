package com.aluracursos.literalura.main;



import com.aluracursos.literalura.service.Features;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Principal {
    private Scanner keyboard = new Scanner(System.in);
    @Autowired
    private Features features;


    @Autowired
    public Principal(Features features) {
        this.features = features;
    }


    public Principal() {
    }

    public void showMenu() {
        var option = -1;
        while (option != 0) {
            var menu = """
                    __________________________________________________________________________________________________
                       --------------------------Bienvenido al buscador de LiterAlura-----------------------------
                    
                                   -----------Selecciona la Opción que deseas realizar-----------
                    
                    ___________________________________________________________________________________________________
                    1  - Buscar un libro en la Web y registrarlo en la db.
                    2  - Buscar un libro en la base de datos por nombre.
                    3  - Buscar los 10 libros mas populares por descargas.
                    4  - Listar todos los Libros Registrados.
                    5  - Buscar libro por ID.
                    6  - Buscar por tema.
                    7  - Buscar libro por el nombre del autor.
                    8  - Buscar libro por lenguaje.
                    9  - Buscar libros con, o sin Copyright.
                    10 - Busqueda de libros por año rango de tiempo donde estuvo vivo el autor
                                  
                    0 - Salir
                    ___________________________________________________________________________________________________
                    """;
            System.out.println(menu);

//            keyboard.nextInt();
            if (keyboard.hasNextInt()){
                option = keyboard.nextInt();
                switch (option) {
                    case 1:
                        features.SearchAndSaveWebBook();
                        break;
                    case 2:
                        features.searchBookByName();
                        break;
                    case 3:
                        features.searchTop10PopularBooks();
                        break;
                    case 4:
                        features.listAllBooks();
                        break;
                    case 5:
                        features.searchBookById();
                        break;
                    case 6:
                        features.searchBooksByTopic();
                        break;
                    case 7:
                        features.searchBooksByAuthor();
                        break;
                    case 8:
                        features.searchBooksByLanguage();
                        break;
                    case 9:
                        features.searchCopyright();
                        break;
                    case 10:
                        features.findAuthorInRange();
                        break;
                    case 13:
                        features.updateAuthorNames();
                        break;
                    case 0:
                        System.out.println("Cerrando la aplicación...");
                        break;
                    default:
                        System.out.println("""
                            -------------------------------------
                            Opción inválida
                            -------------------------------------""");
//                        break;
                }
            }else {
                System.out.println("""
                        ------------------------------------------------------
                        Formato Inválido, la opción ingresada No es un número
                        ------------------------------------------------------""");
                keyboard.next();
            }

        }

    }

}
