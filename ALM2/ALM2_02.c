#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Jmena.h"

// Zádání:
// Setřiďte ukazatele v poli Jmena tak, aby jména, na která ukazují, byla uspořádána v abecedním pořadí.
// Použijte k tomu některou funkci pro třídění z minulého semestru, kterou příslušně upravíte pro
// datový typ prvků pole Jmena a požadované setřídění.
// Vypište na obrazovce text "Zadej jmeno:"
// Po vložení jména z klávesnice vyhledejte binárním vyhledáváním v poli Jmena ukazatel na shodné jméno.
// Pokud byl nalezen, vypište pozici (index) nalezeného ukazatele a pro kontrolu vypište jméno, na který
// ukazatel ukazuje, například:
// pozice: 125 jmeno: Jana
// Nebylo-li zadané jméno nalezeno, vypište zprávu ve tvaru:
// jmeno Valina nebylo nalezeno
// Vkládání jmen a jejich vyhledávání bude probíhat tak dlouho, dokud nebude místo jména zadán jiný znak,
// než je písmeno. Po něm se program ukončí.

void HledatJmeno(const char *names[], const unsigned size, const char *nm) {
    int s, k = 0;
    int not_found = 1;
    int l = size - 1;
    
    while (k <= l) {
        s = (k + l) / 2;
        if (strcmp(nm, names[s]) < 0)
            l = s - 1;
        else if (strcmp(nm, names[s]) > 0)
            k = s + 1;
        else {
            printf("Jméno %s je na indexu %d\n\n", nm, s);
            not_found = 0;
            break;
        }
    }
    if (not_found) {
        printf("Jméno %s nebylo nalezeno.\n\n", nm);
    } 
} 


int main()
{
    int i, j;
    char jmeno[30];
    const char *temp;
    
    for (i = Pocet - 1; i > 0; --i) {
        for (j = 0; j < i; ++j) {
            if (strcmp(Jmena[j], Jmena[j+1]) > 0) {
                temp = Jmena[j];
                Jmena[j] = Jmena[j+1];
                Jmena[j+1] = temp;
            }
        }
    }
    
    for (i = 0; i < 10; i++) {
        printf("Zadej jméno: ");
        scanf("%s", jmeno);
        HledatJmeno(Jmena, Pocet, jmeno);
    }
    
    
    return 0;
}