package codapp.analizador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Character.isDigit;
import java.util.HashMap;
import java.util.Stack;

class Estados {

    //Declaracion variables analizador lexico
    String thisLine = null;
    String lenguaje = "";
    String cadena = "";
    int caracter = 0;
    int posinicial = 0;
    int posfinal = 0;
    String[] Palabras = {"retorno", "nl", "Si", "Sino", "Mientras", "Para", "en", "dec", "tex", "tru", "fal", "Start", "End", "Incio", "Fin", "bol", "error", "areglo", "bol", "Chart", "Imprimir"};
    char[] AregloDig = {'(', ')', '+', '-', '*', '/', '<', '>', '[', ']', ';', ',', ' '};
    char[] AregloIden = {',', ' ', '<', '(', '=', '+', '-', '*', '/', '[', ']', '>', ')', '+', ';'};
    char[] AregloArit = {' ', '(', ')', '[', ']'};
    char[] AregloRel = {'(', ' ', '['};
    char[] AregloLog = {'(', ' ', '['};
    char[] AregloAgru = {' '};

    //Declaracion varaibles analizador
    String S = "0";
    String A = "";
    String a = "";
    String t = "";
    Integer key = 1;
    Integer entrada = 1;
    Stack pila = new Stack();
    HashMap<Integer, String> lexico = new HashMap<Integer, String>();
    HashMap<String, String> ley = new HashMap<String, String>();
    HashMap<String, String> gramatica = new HashMap<String, String>();

    String[][] Tabla = {
        {"",     "identificador", "Start", "End", "+", "-", "*", "/", "=", ";", "(", ")", "dijito", "$", "I", "C", "U", "E", "T", "F"},
        {"0",    "S4", "S2", "", "", "", "", "", "", "", "S8", "", "S9", "", "1", "", "3", "5", "6", "7"},
        {"1",    "", "", "", "", "", "", "", "", "", "", "", "", "S10", "", "", "", "", "", ""},
        {"2",    "S4", "", "", "", "", "", "", "", "", "", "", "", "", "", "11", "3", "", "", ""},
        {"3",    " ", "", "", "", "", "", "", "", "S12", "", "", "", "", "", "", "", "", "", ""},
        {"4",    "", "", "", "", "", "", "", "S14", "", "", "", "", "", "", "", "", "", "", ""},
        {"5",    "", "", "", "S15", "S16", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
        {"6",    "R7", "", "", "R7", "R7", "S17", "S18", "", "R7", "", "R7", "", "", "", "", "", "", "", ""},
        {"7",    "", "", "", "R10", "R10", "R10", "R10", "", "R10", "", "R10", "", "", "", "", "", "", "", ""},
        {"8",    "", "", "", "", "", "", "", "", "", "S8", "", "S9", "", "", "", "", "19", "6", "7"},
        {"9",    "R12", "", "", "R12", "R12", "R12", "R12", "", "R12", "", "R12", "", "", "", "", "", "", "", ""},
        {"10",   "", "", "", "", "", "", "", "", "", "", "", "", "acc", "", "", "", "", "", ""},
        {"11",   "", "", "S20", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
        {"12",   "S4", "", "R2", "", "", "", "", "", "", "", "", "", "", "", "13", "3", "", "", ""},
        {"13",   "", "", "R3", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
        {"14",   "", "", "", "", "", "", "", "", "", "S8", "", "S9", "", "", "", "", "21", "6", "7"},
        {"15",   "", "", "", "", "", "", "", "", "", "S8", "", "S9", "", "", "", "", "", "22", "7"},
        {"16",   "", "", "", "", "", "", "", "", "", "S8", "", "S9", "", "", "", "", "", "23", "7"},
        {"17",   "", "", "", "", "", "", "", "", "", "S8", "", "S9", "", "", "", "", "", "", "24"},
        {"18",   "", "", "", "", "", "", "", "", "", "S8", "", "S9", "", "", "", "", "", "", "25"},
        {"19",   "", "", "", "S15", "S16", "", "", "", "", "", "S26", "", "", "", "", "", "", "", ""},
        {"20",   "", "", "", "", "", "", "", "", "", "", "", "", "R1", "", "", "", "", "", ""},
        {"21",   "R4", "", "", "S15", "S16", "", "", "", "R4", "", "", "", "", "", "", "", "", "", ""},
        {"22",   "R5", "", "", "R5", "R5", "S17", "S18", "", "R5", "", "R5", "", "", "", "", "", "", "", ""},
        {"23",   "R6", "", "", "R6", "R6", "S17", "S18", "", "R6", "", "R6", "", "", "", "", "", "", "", ""},
        {"24",   "R8", "", "", "R8", "R8", "R8", "R8", "", "R8", "", "R8", "", "", "", "", "", "", "", ""},
        {"25",   "R9", "", "", "R9", "R9", "R9", "R9", "", "R9", "", "R9", "", "", "", "", "", "", "", ""},
        {"26",   "R11", "", "", "R11", "R11", "R11", "R11", "", "R11", "", "R11", "", "", "", "", "", "", "", ""}};

    public void leerArchivo() {

        try {
            FileReader doc = new FileReader("C:\\Users\\MArmijos\\Documents\\NetBeansProjects\\AnalizadorFinal\\src\\main\\java\\codapp\\analizador\\lenguaje.txt");
            try ( BufferedReader obj = new BufferedReader(doc)) {
                while ((thisLine = obj.readLine()) != null) {
                    lenguaje = lenguaje + thisLine + " ";
                }
                System.out.println(lenguaje);
                obj.close();
                estado1();
            }
        } catch (IOException e) {
            System.out.println("Archivo no encontrado");
        } finally {

        }
    }

    public void estado1() {
        if (isDigit(lenguaje.charAt(caracter))) {
            caracter = caracter + 1;
            estado2();
        }
        if (lenguaje.charAt(caracter) == '.') {
            caracter = caracter + 1;
            estado3();
        } else {
            if (Character.isLetter(lenguaje.charAt(caracter))) {
                caracter = caracter + 1;
                estado5();
            } else {
                //Aritmeticos
                if (lenguaje.charAt(caracter) == '+') {

                    caracter = caracter + 1;
                    estado6();
                }
                if (lenguaje.charAt(caracter) == '-') {

                    caracter = caracter + 1;
                    estado7();
                }
                if (lenguaje.charAt(caracter) == '*') {

                    caracter = caracter + 1;
                    estado8();
                }
                if (lenguaje.charAt(caracter) == '/') {

                    caracter = caracter + 1;
                    estado9();
                } else {
                    //Relacionales
                    if (lenguaje.charAt(caracter) == '<') {

                        caracter = caracter + 1;
                        estado10();
                    }
                    if (lenguaje.charAt(caracter) == '=') {
                        caracter = caracter + 1;
                        estado11();
                    }
                    if (lenguaje.charAt(caracter) == '>') {

                        caracter = caracter + 1;
                        estado12();
                    } else {
                        //Logicos
                        if (lenguaje.charAt(caracter) == '&') {

                            caracter = caracter + 1;
                            estado15();
                        }
                        if (lenguaje.charAt(caracter) == '|') {
                            caracter = caracter + 1;
                            estado16();
                        }
                        if (lenguaje.charAt(caracter) == '!') {
                            caracter = caracter + 1;
                            estado17();
                        } else {
                            //Agrupacion
                            if (lenguaje.charAt(caracter) == '(') {
                                caracter = caracter + 1;
                                simboloAgrupacion1();
                            }
                            if (lenguaje.charAt(caracter) == ')') {
                                caracter = caracter + 1;
                                simboloAgrupacion2();
                            }
                             if (lenguaje.charAt(caracter) == ';') {
                                caracter = caracter + 1;
                                simboloAgrupacion3();
                            }
                            if (lenguaje.charAt(caracter) == '$') {
                                caracter = caracter + 1;
                                simboloAgrupacion4();
                            }
                            AgrupacionSentencia();
                        }
                        comentarios();
                    }
                }
            }
        }
    }

    public void estado2() {

        if (isDigit(lenguaje.charAt(caracter))) {
            caracter = caracter + 1;
            estado2();
        } else {
            if (lenguaje.charAt(caracter) == '.') {
                caracter = caracter + 1;
                estado3();
            } else {
                if (buscarSepDig(AregloDig, caracter) == 1) {
                    System.out.println("------------------------------------------");
                    System.out.println("----------------- DIGITO -----------------");
                    System.out.print("OK estado 2" + " " + "===" + " ");
                    cadena(cadena);
                    System.out.println(cadena);
                    lexico.put(key, "dijito");
                    key = key + 1;
                    AgrupacionSentencia();
                } else {
                    System.out.println("error estado 2");
                    System.exit(0);
                }
            }
        }
    }

    public void estado3() {
        if (isDigit(lenguaje.charAt(caracter))) {
            caracter = caracter + 1;
            estado4();
        } else {
            System.out.println("error estado 3");
            System.exit(0);
        }

    }

    public void estado4() {
        if (isDigit(lenguaje.charAt(caracter))) {
            caracter = caracter + 1;
            estado4();
        } else {
            if (buscarSepDig(AregloDig, caracter) == 1) {
                System.out.println("------------------------------------------");
                System.out.println("----------------- DIGITO -----------------");
                System.out.print("OK estado 4" + " " + "===" + " ");
                cadena(cadena);
                System.out.println(cadena);
                lexico.put(key, "dijito");
                key = key + 1;
                AgrupacionSentencia();
            } else {
                System.out.println("error estado 4");
                System.exit(0);
            }
        }
    }

    public void estado5() {
        if (isDigit(lenguaje.charAt(caracter))) {
            caracter = caracter + 1;
            estado5();
        }
        if (Character.isLetter(lenguaje.charAt(caracter))) {
            caracter = caracter + 1;
            estado5();
        } else {
            if (buscarSepIden(AregloIden, caracter) == 1) {
                cadena(cadena);
                if (palabraReservada(Palabras, cadena) == 1) {
                    System.out.println("------------------------------------------");
                    System.out.println("------------- IDENTIFICADOR --------------");
                    System.out.print("OK estado 5 palabra reservada" + " " + "===" + " ");
                    lexico.put(key, cadena );
                    key = key + 1;
                    System.out.println(cadena);
                    AgrupacionSentencia();
                } else {
                    System.out.println("------------------------------------------");
                    System.out.println("------------- IDENTIFICADOR --------------");
                    System.out.print("OK estado 5" + " " + "===" + " ");
                    lexico.put(key, "identificador");
                    key = key + 1;
                    System.out.println(cadena);
                    AgrupacionSentencia();
                }
               
            } else {
                System.out.println("error estado 5");
                System.exit(0);
            }
        }
    }

    public void estado6() {
        if (buscarSepArit(AregloArit, caracter) == 1) {
            System.out.println("------------------------------------------");
            System.out.println("--------------- ARITMETICO ---------------");
            System.out.println("------------------ SUMA ------------------");
            System.out.print("OK estado 6" + " " + "===" + " ");
            cadena(cadena);
            System.out.println(cadena);
            lexico.put(key, cadena);
            key = key + 1;
            AgrupacionSentencia();
        } else {
            System.out.println("error estado 6");
            System.exit(0);
        }

    }

    public void estado7() {
        if (buscarSepArit(AregloArit, caracter) == 1) {
            System.out.println("------------------------------------------");
            System.out.println("--------------- ARITMETICO ---------------");
            System.out.println("----------------- RESTA ------------------");
            System.out.print("OK estado 7" + " " + "===" + " ");
            cadena(cadena);
            System.out.println(cadena);
            lexico.put(key, cadena);
            key = key + 1;
            AgrupacionSentencia();
        } else {
            System.out.println("error estado 7");
            System.exit(0);
        }
    }

    public void estado8() {
        if (buscarSepArit(AregloArit, caracter) == 1) {
            System.out.println("------------------------------------------");
            System.out.println("--------------- ARITMETICO ---------------");
            System.out.println("--------------- MULTIPLICACION -----------");
            System.out.print("OK estado 8" + " " + "===" + " ");
            cadena(cadena);
            System.out.println(cadena);
            lexico.put(key, cadena);
            key = key + 1;
            AgrupacionSentencia();
        } else {
            System.out.println("error estado 8");
            System.exit(0);
        }
    }

    public void estado9() {
        if (buscarSepArit(AregloArit, caracter) == 1) {
            System.out.println("------------------------------------------");
            System.out.println("--------------- ARITMETICO ---------------");
            System.out.println("---------------- DIVISION ----------------");
            System.out.print("OK estado 9" + " " + "===" + " ");
            cadena(cadena);
            System.out.println(cadena);
            lexico.put(key, cadena);
            key = key + 1;
            AgrupacionSentencia();
        } else {
            System.out.println("error estado 9");
            System.exit(0);
        }
    }

    public void estado10() {
        if (lenguaje.charAt(caracter) == '=') {
            caracter = caracter + 1;
            estado13();
        } else {
            if (buscarSepRel(AregloRel, caracter) == 1) {
                System.out.println("------------------------------------------");
                System.out.println("--------------- RELACIONAL ---------------");
                System.out.println("--------------- MENOR QUE ----------------");
                System.out.print("OK estado 10" + " " + "===" + " ");
                cadena(cadena);
                System.out.println(cadena);
                lexico.put(key, cadena);
                key = key + 1;
                AgrupacionSentencia();
            } else {
                System.out.println("error estado 10");
                System.exit(0);
            }
        }
    }

    public void estado11() {
        if (buscarSepRel(AregloRel, caracter) == 1) {
            System.out.println("------------------------------------------");
            System.out.println("-------------- RELACIONAL ----------------");
            System.out.println("---------------- IGUAL -------------------");
            System.out.print("OK estado 11" + " " + "===" + " ");
            cadena(cadena);
            System.out.println(cadena);
            lexico.put(key, cadena);
            key = key + 1;
            AgrupacionSentencia();
        } else {
            System.out.println("error estado 11");
            System.exit(0);
        }
    }

    public void estado12() {
        if (lenguaje.charAt(caracter) == '=') {
            caracter = caracter + 1;
            estado14();
        } else {
            if (buscarSepRel(AregloRel, caracter) == 1) {
                System.out.println("------------------------------------------");
                System.out.println("--------------- RELACIONAL ---------------");
                System.out.println("--------------- MAYOR QUE ----------------");
                System.out.print("OK estado 12" + " " + "===" + " ");
                cadena(cadena);
                System.out.println(cadena);
                lexico.put(key, cadena);
                key = key + 1;
                AgrupacionSentencia();
            } else {
                System.out.println("error estado 12");
                System.exit(0);
            }
        }
    }

    public void estado13() {
        if (buscarSepRel(AregloRel, caracter) == 1) {
            System.out.println("------------------------------------------");
            System.out.println("----------- RELACIONAL -------------------");
            System.out.println("----------- MEMOR O IGUAL QUE ------------");
            System.out.print("OK estado 13" + " " + "===" + " ");
            cadena(cadena);
            System.out.println(cadena);
            lexico.put(key, cadena);
            key = key + 1;
            AgrupacionSentencia();
        } else {
            System.out.println("error estado 13");
            System.exit(0);
        }
    }

    public void estado14() {
        if (buscarSepRel(AregloRel, caracter) == 1) {

            System.out.println("------------------------------------------");
            System.out.println("----------- RELACIONAL -------------------");
            System.out.println("----------- MAYOR O IGUAL QUE ------------");
            System.out.print("OK estado 14" + " " + "===" + " ");
            cadena(cadena);
            System.out.println(cadena);
            lexico.put(key, cadena);
            key = key + 1;
            AgrupacionSentencia();
        } else {
            System.out.println("error estado 14");
            System.exit(0);
        }
    }

    public void estado15() {
        if (lenguaje.charAt(caracter) == '&') {
            caracter = caracter + 1;
            estado18();
        } else {
            System.out.println("error estado 15");
            System.exit(0);
        }
    }

    public void estado16() {
        if (lenguaje.charAt(caracter) == '|') {
            caracter = caracter + 1;
            estado19();
        } else {
            System.out.println("error estado 16");
            System.exit(0);
        }
    }

    public void estado17() {
        if (buscarSepLog(AregloLog, caracter) == 1) {
            System.out.println("------------------------------------------");
            System.out.println("---------------- LOGICO ------------------");
            System.out.println("--------------- DIFERENTE ----------------");
            System.out.print("OK estado 17" + " " + "===" + " ");
            cadena(cadena);
            System.out.println(cadena);
            lexico.put(key, cadena);
            key = key + 1;
            AgrupacionSentencia();
        } else {
            System.out.println("error estado 17");
            System.exit(0);
        }
    }

    public void estado18() {
        if (buscarSepLog(AregloLog, caracter) == 1) {
            System.out.println("------------------------------------------");
            System.out.println("---------------- LOGICO ------------------");
            System.out.println("----------------- AND --------------------");
            System.out.print("OK estado 18" + " " + "===" + " ");
            cadena(cadena);
            System.out.println(cadena);
            lexico.put(key, cadena);
            key = key + 1;
            AgrupacionSentencia();
        } else {
            System.out.println("error estado 18");
            System.exit(0);
        }
    }

    public void estado19() {
        if (buscarSepLog(AregloLog, caracter) == 1) {
            System.out.println("------------------------------------------");
            System.out.println("----------------- LOGICO -----------------");
            System.out.println("------------------- OR -------------------");
            System.out.print("OK estado 19" + " " + "===" + " ");
            cadena(cadena);
            System.out.println(cadena);
            lexico.put(key, cadena);
            key = key + 1;
            AgrupacionSentencia();
        } else {
            System.out.println("error estado 19");
            System.exit(0);
        }
    }

    public void simboloAgrupacion1() {
        if (buscarSepAgru(AregloAgru, caracter) == 1) {
            System.out.println("------------------------------------------");
            System.out.println("-------------- AGRUPACCION ---------------");
            System.out.println("---------- PARENTESIS QUE ABRE -----------");
            System.out.print("OK simbolo de agrupacion" + " " + "===" + " ");
            cadena(cadena);
            System.out.println(cadena);
            lexico.put(key, cadena);
            key = key + 1;
            AgrupacionSentencia();
        } else {
            System.out.println("error simbolo de agrupacion (");
            System.exit(0);
        }
    }

    public void simboloAgrupacion2() {
        if (buscarSepAgru(AregloAgru, caracter) == 1) {
            System.out.println("------------------------------------------");
            System.out.println("-------------- AGRUPACCION ---------------");
            System.out.println("---------- PARENTESIS QUE CIERRA ---------");
            System.out.print("OK simbolo de agrupacion" + " " + "===" + " ");
            cadena(cadena);
            System.out.println(cadena);
            lexico.put(key, cadena);
            key = key + 1;
            AgrupacionSentencia();
        } else {
            System.out.println("error simbolo de agrupacion )");
            System.exit(0);
        }
    }

    public void simboloAgrupacion3() {
        if (buscarSepAgru(AregloAgru, caracter) == 1) {
            System.out.println("------------------------------------------");
            System.out.println("--------------- SEPARADOR ----------------");
            System.out.println("--------------- PUNTO Y COMA -------------");
            System.out.print("OK Punto y coma" + " " + "===" + " ");
            cadena(cadena);
            System.out.println(cadena);
            lexico.put(key, cadena);
            key = key + 1;
            AgrupacionSentencia();
        } else {
            System.out.println("error simbolo separado ;");
            System.exit(0);
        }
    }
    public void simboloAgrupacion4() {
        if (buscarSepAgru(AregloAgru, caracter) == 1) {
            System.out.println("------------------------------------------");
            System.out.println("-------------- SEPARADOR  ----------------");
            System.out.println("-------------- SIGNO DOLAR ---------------");
            System.out.print("OK Punto y coma" + " " + "===" + " ");
            cadena(cadena);
            System.out.println(cadena);
            lexico.put(key, cadena);
            key = key + 1;
            AgrupacionSentencia();
        } else {
            System.out.println("error simbolo final de cadena ;");
            System.exit(0);
        }
    }

    public int buscarSepDig(char[] AregloDig, int caracter) {
        for (int i = 0; i < AregloDig.length; i++) {
            if (AregloDig[i] == this.lenguaje.charAt(caracter)) {
                return 1;
            }
        }
        return 0;
    }

    public int buscarSepIden(char[] AregloIden, int caracter) {
        for (int i = 0; i < AregloIden.length; i++) {
            if (AregloIden[i] == lenguaje.charAt(caracter)) {
                return 1;
            }
        }
        return 0;
    }

    public int buscarSepArit(char[] AregloArit, int caracter) {
        for (int i = 0; i < AregloArit.length; i++) {
            if (AregloArit[i] == lenguaje.charAt(caracter)) {
                return 1;
            }
        }
        return 0;
    }

    public int buscarSepRel(char[] AregloRel, int caracter) {
        for (int i = 0; i < AregloRel.length; i++) {
            if (AregloRel[i] == lenguaje.charAt(caracter)) {
                return 1;
            }
        }
        return 0;
    }

    public int buscarSepLog(char[] AregloLog, int caracter) {
        for (int i = 0; i < AregloLog.length; i++) {
            if (AregloLog[i] == lenguaje.charAt(caracter)) {
                return 1;
            }
        }
        return 0;
    }

    public int buscarSepAgru(char[] AregloAgru, int caracter) {
        for (int i = 0; i < AregloLog.length; i++) {
            if (AregloLog[i] == lenguaje.charAt(caracter)) {
                return 1;
            }
        }
        return 0;
    }

    public int palabraReservada(String[] Palabras, String texto) {
        for (int j = 0; j < Palabras.length; j++) {
            if (this.Palabras[j].equals(texto)) {
                return 1;
            }
        }
        return 0;
    }

    public String cadena(String cadena1) {
        cadena = "";
        this.posfinal = caracter;
        for (int i = this.posinicial; i < this.posfinal; i++) {
            if (lenguaje.charAt(i) != ' ') {
                cadena = cadena + lenguaje.charAt(i);
            }
        }
        this.posinicial = this.posfinal + 1;
        return cadena;
    }

    public void AgrupacionSentencia() {
        for (Integer key : lexico.keySet()) {
            System.out.println(key + " = " + lexico.get(key));
        }
        if (lenguaje.charAt(caracter) != 'a') {
            caracter = caracter + 1;
        }
        analizadorSintactico();
    }

    public void comentarios() {
        if (lenguaje.charAt(caracter) == ' ') {
            caracter = caracter + 1;
            estado1();
        } else {
            if (lenguaje.charAt(caracter) == '%') {
                caracter = caracter + 1;
                String comentario = "";
                while (lenguaje.charAt(caracter) != '%') {
                    comentario = comentario + lenguaje.charAt(caracter);
                    caracter = caracter + 1;
                }
                System.out.println("------------------------------------------");
                System.out.println("--------------- COMENTARIO ---------------");
                System.out.println("%" + comentario + "%");
                caracter = caracter + 1;
                posinicial = caracter;
                estado1();
            } else {
                if (lenguaje.charAt(caracter) == '#') {
                    caracter = caracter + 1;
                    String comentario = "";
                    while (lenguaje.charAt(caracter) != '#') {
                        comentario = comentario + lenguaje.charAt(caracter);
                        caracter = caracter + 1;
                    }
                    System.out.println("------------------------------------------");
                    System.out.println("--------------- COMENTARIO ---------------");
                    System.out.println("#" + comentario + "#");
                    caracter = caracter + 1;
                    posinicial = caracter;
                    estado1();
                } else {
                    System.out.println("error" + " " + "===" + " " + lenguaje.charAt(caracter));
                    System.out.println("error estado 1 ");
                    System.exit(0);
                }
            }
        }
    }

    public void analizadorSintactico() {
        a = lexico.get(entrada);
        System.out.println("Primer simbolo de entrada :" + a);
        if (S == "0") {
            pila.push("0");
        }

        while (true) {
            S = (String) pila.peek();
            System.out.println("Llegue aqui");
            System.out.println("S" + "=" + S);
            System.out.println("a" + "=" + a);
            String t = "";
            String q = "";
            if (Accion(S, a).charAt(0) == 'S') {
                q = String.valueOf(Accion(S, a));
                System.out.println(" Esto es Q = " + q);
                int stringLength = q.length();
                //System.out.println(" Esto es stringLength = " + stringLength);
                if (stringLength == 2) {
                    t = q.substring(1) + q.substring(2);
                } else {
                    t = q.substring(1);
                }
                System.out.println("T  =  " + t);
                System.out.println("La pila es :" + pila);
                pila.push(t);
                System.out.println("S" + "=" + S);
                System.out.println("a" + "=" + a); 
               
               
                if (lenguaje.charAt(caracter) != 'a') { 
                    S = t; 
                    entrada = entrada + 1;
                    estado1();
                }
            } else {
                if (Accion(S, a).charAt(0) == 'R') {
                    System.out.println("Estoy");
                    cargarGramatica();
                    String Regla = Accion(S, a);
                    System.out.println("Regla" + " " + Regla);
                    String Obtener = ley.get(Regla);
                    System.out.println("Obtener" + " " + Obtener);
                    String Eliminar = gramatica.get(Obtener);
                    System.out.println("Eliminar" + " " + Eliminar);
                    System.out.println(pila);
                    for (int i = 1; i <= Integer.parseInt(Eliminar); i++) {
                        reducir();
                    }
                    t = (String) pila.peek();
                    System.out.println("t" + "=" + t);
                    A = String.valueOf(Obtener.charAt(0));
                    System.out.println("A" + "=" + A);
                    String ir_A = String.valueOf(Accion(t, A));
                    pila.push(ir_A);
                    System.out.println("ir_A" + "=" + ir_A);
                    System.out.println("a" + "=" + a);
                    if (lenguaje.charAt(caracter) == '$') {
                        estado1();
                    }
                } else if (Accion(S, a) == "acc") {
                    System.out.println("Termino el analizador sintactico");
                    break;
                } else {
                    return;
                }
            }
        }
    }

    public void cargarGramatica() {
        
        ley.put("R1","I->sCe");
        ley.put("R2","C->U;");
        ley.put("R3","C->U;C");
        ley.put("R4","U->i=E");
        ley.put("R5","E->E+T");
        ley.put("R6","E->E-T");
        ley.put("R7","E->T");
        ley.put("R8","T->T*F");
        ley.put("R9","T->T/F");
        ley.put("R10","T->F");
        ley.put("R11","F->(E)");
        ley.put("R12","F->d");

        gramatica.put("I->sCe", "3");
        gramatica.put("C->U;", "2");
        gramatica.put("C->U;C", "3");
        gramatica.put("U->i=E", "3");
        gramatica.put("E->E+T", "3");
        gramatica.put("E->E-T", "3");
        gramatica.put("E->T", "1");
        gramatica.put("T->T*F", "3");
        gramatica.put("T->T/F", "3");
        gramatica.put("T->F", "1");
        gramatica.put("F->(E)", "3");
        gramatica.put("F->d", "1");

    }

    public void reducir() {
        pila.pop();
        System.out.println(pila);
    }

    public String Accion(String S, String a) {
        for (int i = 1; i < 28; i++) {
            if (Tabla[i][0].equals(S)) {
                //System.out.println( "AcS"+ "=" +Tabla[i][0] );
                for (int j = 1; j < 20; j++) {
                    if (Tabla[0][j].equals(a)) {
                        //System.out.println(  "AcA"+ "=" +Tabla[0][j]);
                        System.out.println("Accion" + "=" + Tabla[i][j]);
                        return Tabla[i][j];
                    }
                }
            }
        }
        return "";
    }
}
