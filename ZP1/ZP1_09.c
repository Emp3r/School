#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// http://jazykc.inf.upol.cz/dynamicka-prace-s-pameti/index.htm
// 1. Dynamický zásobník
typedef struct prv {int hodnota; struct prv* predchozi; } prvek;

prvek* pridej(prvek* zasobnik, int data) {
    prvek* pridany = malloc(sizeof(prvek));
    
    pridany->hodnota = data;
    pridany->predchozi = zasobnik;
    
    return pridany;
}

int vrchol (prvek* zasobnik) {
    if (zasobnik == NULL)
        return 0;
    return zasobnik->hodnota;
}
prvek* odeber(prvek* zasobnik) {
    if (zasobnik == NULL)
        return NULL;
    prvek* stary = zasobnik->predchozi;
    
    free(zasobnik);
    return stary;
}

// 2. Spojení textových řetězců
char *spojeni(char *t1, char *t2) {
    char *vysledek = malloc(sizeof(t1) + sizeof(t2));
    int i, n = (int)strlen(t1);
    
    for (i = 0; i < n + strlen(t2); i++) {
        if (i < n) {
            vysledek[i] = t1[i];
        }
        else {
            vysledek[i] = t2[i - n];
        }
    }
    return vysledek;
}

int main()
{
    // 1. Dynamický zásobník
    prvek* z=NULL;
    int i;
    
    for (i=1; i<11; i++)
        z = pridej(z, i);
    while (z!=NULL){
        printf("%i\n", vrchol(z));
        z=odeber(z);
    }

    // 2. Spojení textových řetězců
    char *s1 = "Ahoj", *s2 = "Svete";
    printf("\n\nSpojení slov %s a %s je %s", s1, s2, spojeni(s1, s2));
    
    
    return 0;
}

