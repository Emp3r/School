#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// 1. Hledání podřetězce zprava (http://jazykc.inf.upol.cz/ukazatele/hledani-podretezce-zprava.htm)
char *strrstr(char *text, char *hledany) {
    int i, i2, j;
    for (i = (int)strlen(text)-(int)strlen(hledany); i >= 0; i--) {
        
        for (j = 0, i2 = i; hledany[j] != '\0'; j++, i2++) {
            
            if (hledany[j] == text[i2]) {
                if (hledany[j+1] == '\0')
                    return &text[i];
            }
            else break;
        }
    }
    return NULL;
}

// 2. Dynamický zásobník (http://jazykc.inf.upol.cz/dynamicka-prace-s-pameti/dynamicky-zasobnik.htm)
typedef struct prv {int hodnota; struct prv* predchozi; } prvek;

prvek* pridej(prvek* zasobnik, int data) {
    prvek* pridany = malloc(sizeof(prvek));
    
    pridany->hodnota = data;
    pridany->predchozi = zasobnik;
    
    return pridany;
}

int vrchol (prvek* zasobnik) {
    return zasobnik->hodnota;
}

prvek* odeber(prvek* zasobnik) {
    if (zasobnik == NULL)
        return NULL;
    prvek* stary = zasobnik->predchozi;
    
    free(zasobnik);
    return stary;
}

// 3. Transformace textu (http://jazykc.inf.upol.cz/predani-parametru-odkazem/transformace-textu.htm)
int set(char* in, char** out) {
    int i, zmena = 0;
    int delka = (int)strlen(in) + 1;
    *out = malloc(delka);
    for (i = 0; i < delka; i++) {
        if (in[i] >= 'a' && in[i] <= 'z') {
            (*out)[i] = in[i] - 32;
            zmena++;
        }
        else if (in[i] >= 'A' && in[i] <= 'Z') {
            (*out)[i] = in[i] + 32;
            zmena++;
        }
        else (*out)[i] = in[i];
    }
    return zmena;
}


int main()
{
    // 1.
    printf("%s\n", strrstr("Tohle slovo slovo slovo je krasne.", "slovo"));
    printf("%s\n", strrstr("Tohle slovo slovo slovo je krasne.", "slovo s"));
    printf("%s\n", strrstr("Tohle slovo slovo slovo je krasne.", "ohle")); 
    strrstr("Tohle slovo slovo slovo je krasne.", "Nathan"); 
    printf("%s\n", strrstr("To neni jeptiska! Je to podplukovnik matka Tereza!", "podplukovnik")); 
    printf("%s\n", strrstr("To neni jeptiska! Je to podplukovnik matka Tereza!", "ka")); 
    printf("%s\n", strrstr("To neni jeptiska! Je to podplukovnik matka Tereza!", "ka!")); 
    strrstr("To neni jeptiska! Je to podplukovnik matka Tereza!", "Rimmer");
    
    // 2.
    prvek* test = NULL;
    prvek* z = NULL;
    prvek* milion = NULL;
    int i;
    
    test = pridej(test, 5);
    printf("\n%i \n", vrchol(test));
    test = pridej(test, 14);
    printf("%i \n", vrchol(test));
    test = odeber(test);
    printf("%i \n", vrchol(test));
    test = odeber(test);

    for (i = 1; i < 11; i++)
        z = pridej(z, i);
    while (z != NULL) {
        printf("%i ", vrchol(z));
        z = odeber(z);
    }
    
    for (i = 1; i < 1000000; i++)
        milion = pridej(milion, i);
    for (i = 1; i < 1000000 - 1; i++)
        milion = odeber(milion);
    printf("\nmilion: %i\n", vrchol(milion));
    
    // 3.
    char* vstup = "Ahoj svete 23.";
    char* vystup;
    set(vstup, &vystup);
    printf("\n%s\n", vystup);
    
    
    return 0;
}

