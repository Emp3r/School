#include <stdio.h>

int main()
{
    // http://jazykc.inf.upol.cz/cykly/index.htm
    // Deklarace
    int zadane_cislo, vypocet;          // proměnné pro první úkol
    int hrana;                          // strana čtverce
    int i, j;                           // proměnné pro cykly
    
    // 1. Násobilka
    printf("Zadej číslo: ");
    scanf("%i", &zadane_cislo);             // načtení čísla do proměnné zadane_cislo
    vypocet = zadane_cislo;                 // přiřazení aby vypocet byl stejná hodnota
    printf("Násobky zadaného čísla: ");
    
    while (vypocet <= 100) {                // cyklus while se bude opakovat dokud vypocet bude menší nebo roven číslu 100
        printf("%i ", vypocet);             // výpis násobku zadaného čísla (první bude jen zadané číslo)
        vypocet = vypocet + zadane_cislo;   // navýšení výpočtu o zadané číslo ( = další násobek čísla)
    }
    
    // 2. Prvočísla
    printf("\nPrvočísla menší než 100: ");
    printf("2");                        // první se vypíše číslo 2
    for (i = 3; i < 100; i += 2) {      // cyklus pak už kontroluje jen lichá čísla
        for (j = 3; j <= i; j++) {      // každé liché číslo se zkontroluje jestli je dělitelné čísly menšími než zkoumané číslo
            if (i % j == 0) break;      // pokud je dělitelné, cyklus se zastaví (% = modulo = zbytek po celočíselném dělení)
        }
        if (i == j) printf(", %i", i);  // pokud je zkoumané číslo stejné jako první číslo kterým se může dělit, 
    }                                   // je zkoumané číslo prvočíslo a vypíše se

    // 3. Čtverec
    printf("\n\nZadej stranu čtverce: ");
    scanf("%i", &hrana);                    // načtení zadané strany do proměnné hrana
    for (i = 0; i < hrana; i++) {           // cyklus na vypsání tolika hvězdiček, kolik sme zadali
        printf("*");                        // (jen horní řádek)
    }
    printf("\n");
    for (i = 0; i < hrana - 2; i++) {       // cyklus pro vykreslení bočních stran
        printf("*");                        // výpis hvězdičky nalevo
        for (j = 0; j < hrana - 2; j++){    // cyklus pro výpis mezer uprostřed
            printf(" ");                    // (bude jich o 2 méňe než strana čtverce, protože 2 hvězdičky budou na bocích)
        }
        printf("*\n"); 
    }
    if (hrana > 1) {
        for (i = 0; i < hrana; i++) {       // výpis posledního řádku hvězdiček (jen když je strana víc jak 1)
            printf("*");
        }
    }

    return 0;
}