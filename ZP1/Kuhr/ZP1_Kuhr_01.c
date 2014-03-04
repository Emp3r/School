#include <stdio.h>

int main()
{
    // http://www.inf.upol.cz/downloads/studium/ZP1/SP1a.pdf
    // V jazyce C vytvořte program, který po zadání znaku (libovolný tisknutelný znak) 
    // a velikosti obrazce (liché číslo větší nebo rovno než 3) vykreslí za pomoci 
    // daného znaku v konzoli „domeček“ zadané velikosti.

    int i, j, strana;
    char znak;
    
    printf("zadej znak: ");
    scanf("%c", &znak);
    printf("zadej delku: ");
    scanf("%i", &strana);
    
    if (strana % 2 == 1 && strana >= 3) {
    
        for (i = 0; i < strana/2; i++) {
            printf(" ");
        } 
        printf("%c\n", znak);
        for (i = 0; i < strana/2; i++) {
            for (j = 0; j < (strana/2 - i - 1); j++)
                printf(" ");
            printf("%c", znak);
            for (j = 0; j < (i+i+1); j++) {
                printf(" ");
            }
            printf("%c\n", znak);
        }
        for (i = 0; i < strana; i++) {
            printf("%c", znak);
        }
        printf("\n");
        for (i = 0; i < strana-2; i++) {
            printf("%c", znak);
            for (j = 0; j < strana - 2; j++)
                printf(" ");
            printf("%c\n", znak);
        }
        for (i= 0; i < strana; i++) {
            printf("%c", znak);
        }
    }
    else {
        printf("Chyba, velikost musí být liché číslo větší nebo rovno 3.");
    }

    return 0;
}