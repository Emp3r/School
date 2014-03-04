#include <stdio.h>

int main()
{
    // Deklarace
    int zadane_cislo, vypocet;
    int mez_nasobilky, mez_prvocisel;
    int i, j;
    int hrana, a, b;
    
    
    // 1. Násobilka
    // 1.1 Násobky menší nebo rovny číslu 100
    printf("Zadejte číslo: ");
    scanf("%d", &zadane_cislo);
    vypocet = zadane_cislo;
    printf("Násobky zadaného čísla (menší nebo rovny číslu 100): ");
    while (vypocet <= 100) {
        printf("%i, ", vypocet);
        vypocet = vypocet + zadane_cislo;
    }
    // 1.2 Výpis prvních 100 násobků zadaného čísla
    printf("\nZadejte číslo: ");
    scanf("%d", &zadane_cislo);
    vypocet = zadane_cislo;
    printf("Prvních 100 násobků zadaného čísla: ");
    for (int i = 1; i<=100; i++) {
        printf("%i, ", vypocet);
        vypocet = vypocet + zadane_cislo;
    }
    // 1.3 Možnost specifikovat mezní hodnotu uživatelem
    printf("\nZadejte číslo: ");
    scanf("%d", &zadane_cislo);
    vypocet = zadane_cislo;
    printf("Zadejte mezní hodnotu: ");
    scanf("%d", &mez_nasobilky);
    while (vypocet <= mez_nasobilky) {
        printf("%i, ", vypocet);
        vypocet = vypocet + zadane_cislo;
    } 
    
    // 2. Prvočísla
    // 2.1 Všechna prvočísla menší než 100
    printf("\nPrvočísla menší než 100: ");
    printf("2");
    for (i = 3; i < 100; i += 2) {
        for (j = 3; j <= i; j++) {
            if (i % j == 0) break;
        }
        if (i == j) printf(", %i", i);
    }
    // 2.2 Možnost specifikovat mezní hodnotu
    printf("\nZadejte mezní hodnotu prvočísel: ");
    scanf("%d", &mez_prvocisel);
    printf("2");
    for (i = 3; i <= mez_prvocisel; i += 2) {
        for (j = 3; j <= i; j++) {
            if (i % j == 0) break;
        }
        if (i == j) printf(", %i", i);
    }
    
    // 3. Vykreslování
    // 3.1 Čtverec
    printf("\nZadejte velikost čtverce: ");
    scanf("%i", &hrana);
    for (int i = 1; i <= hrana; i++) {
        printf("*");
    }
    for (int i = 1; i <= (hrana - 2); i++) {
        printf("\n*");
        for (int i = 1; i <= (hrana - 2); i++) {
            printf(" ");
        }
        printf("*");
    }
    printf("\n");
    for (int i = 1; i <= hrana; i++) {
        printf("*");
    } 
    // 3.2 Obdelník
    printf("\nZadejte strany obdelníku (a, b): ");
    scanf("%i, %i", &a, &b);
    for (int i = 1; i <= a; i++) {
        printf("*");
    }
    for (int i = 1; i <= (b - 2); i++) {
        printf("\n*");
        for (int i = 1; i <= (a - 2); i++) {
            printf(" ");
        }
        printf("*");
    }
    printf("\n");
    for (int i = 1; i <= a; i++) {
        printf("*");
    }
    // 3.3 Trojúhelník 
    printf("\nZadejte výšku trojúhelníku: ");
    scanf("%i", &hrana);
    for (i = 0; i <= hrana; i++) {
        for (j = 0; j < i; j++) {
            printf("*");
        }
        printf("\n");
    }
    
    return 0;
}