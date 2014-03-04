#include <stdio.h>
#include <string.h>

// Zadání:
// Najděte ve větách uložených v řetězci 'v' počet výskytů slov uložených v poli 's'.

void pocet_vyskytu(char* v, char* s) {
    int i, j;
    int vysledek = 0, slovo = (int)strlen(s);
    
    for (i = 0; i < (int)strlen(v); i++) {
        if (v[i] == s[0]) {
            for (j = 1; j < slovo; j++) {
                if (v[i+j] == s[j]) {
                    if (j == slovo-1 && s[slovo - 1] == s[j])
                        vysledek++;
                }
                else break;
            }
        }
    }
    printf("počet výskytů slova \"%s\" je: %i\n", s, vysledek);
}

int main()
{
    char *v = "In the C++ programming language, the string class is a standard representation for a class of text. "
              "The class provides some typical string operations like comparison, concatenation, find and replace.";
    char *s[] = { "string", "class" } ;
    
    pocet_vyskytu(v, s[0]);
    pocet_vyskytu(v, s[1]);
    
    return 0;
}