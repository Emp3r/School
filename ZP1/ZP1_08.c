#include <stdio.h>
#include <stdlib.h>
#include <string.h>         // jen pro strlen()

// http://jazykc.inf.upol.cz/ukazatele/index.htm
// 1. Porovnání textových řetězců
int porovnej(char *t1, char *t2) {
    int i;
    if (t1 == t2) return 0;
    for (i = 0; t1[i] != '\0'; i++) {
        if (t1[i] < t2[i])
            return -1;
        else if (t1[i] > t2[i])
            return 1;
    }
    return -1;
}

// 2. Hledání podřetězce zprava
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

int main()
{
    // 1. Porovnání textových řetězců
    char *retezec1 = "abcde", *retezec2 = "ahoj";
    if (porovnej(retezec1, retezec2) == -1) printf("slovo \"%s\" je větší než slovo \"%s\"", retezec2, retezec1);
    else if (porovnej(retezec1, retezec2) == 1) printf("slovo \"%s\" je větší než slovo \"%s\"", retezec1, retezec2);
    else printf("slovo \"%s\" je stejně velké jako slovo \"%s\"", retezec1, retezec2);
    
    // 2. Hledání podřetězce zprava
    char *text = "Ahoj svete!";
    char *hledame = "svet";
    printf("\n%s", strrstr(text, hledame));
    
    return 0;
}
