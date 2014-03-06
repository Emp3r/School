#include <stdio.h>

int main()
{
    // http://www.inf.upol.cz/downloads/studium/ZP1/SP1q.pdf
    // V jazyce C vytvořte program, který po zadání znaku (libovolný tisknutelný znak)
    // a velikosti obrazce (liché číslo větší nebo rovno než 3) vykreslí za pomoci
    // daného znaku v konzoli „přeškrtnutý obdélník“ zadané velikosti.
    
    int i, j, strana;
    char znak;
    
    printf("zadej znak: ");
    scanf("%c", &znak);
    printf("zadej delku: ");
    scanf("%i", &strana);
    
    if (strana % 2 == 1 && strana >= 3) {
        
        for (i = 0; i < strana; i++) {
            printf("%c%c", znak, znak);
        }
        printf("\n");
        
        for (i = 0; i < (strana - 2); i++) {
            printf("%c ", znak);
            for (j = 0; j < i; j++) {
                printf("  ");
            }
            printf("%c%c ", znak, znak);
            for (j = 0; j < strana - 3 - i; j++) {
                printf("  ");
            }
            printf("%c\n", znak);
        }
        for (i = 0; i < strana; i++) {
            printf("%c%c", znak, znak);
        }
    }
    else {
        printf("Chyba, velikost musí být liché číslo větší nebo rovno 3.");
    }
    
    return 0;
}