#include <stdio.h>

int main()
{
    // http://jazykc.inf.upol.cz/zaklady-jazyka-c/index.htm
    // Deklarace
    int cislo = 3;                  // int je typ pro celá čísla
    double des_cislo = 3.45;        // double pro číslo s desetinou čárkou, stejně tak float
    char znak = '+';                // char pro jeden znak
    double male_cislo = 1.2e-10;
    int zadane_cislo;
    
    // 1. Hello world
    printf("Hello, World! (Ahoj světe!)\n");                    // funkce printf("cokoli") vytiskne slovo "cokoli" na obrazovku
    
    // 2. Práce s proměnnými
    printf("Hodnota proměnné cislo je: %d\n", cislo);           // %d vypíše proměnnou v desítkové soustavě jako celé číslo, stejně jako %i
    printf("Hodnota proměnné des_cislo je: %g\n", des_cislo);   // %g vypíše proměnnou s desetinnou čárkou (%g pro double, %fl pro float)
    printf("Hodnota proměnné znak je: %c\n", znak);             // %c vypíše znak
    printf("Hodnota proměnné male_cislo je: %g\n", male_cislo);
    
    // 3. Osmičkový a šestnáctkový výstup
    printf("Zadej číslo: ");
    scanf("%i", &zadane_cislo);         // funkce scanf() načte číslo zadané uživatelem do proměnné, důležité je, že 
                                        // %i se většinou nemění pokud očekáváme celá čísla, 
                                        // %g/%fl pokud zadáváme čísla s des. čárkou a %c pro jeden znak
                                        // před názvem proměnné nikdy nesmí chybět znak &!
    printf("Číslo %i odpovída číslu %o v osmičkové soustavě a číslu %X v šestnáctkové soustavě.\n", zadane_cislo, zadane_cislo, zadane_cislo);
    

    return 0;
}