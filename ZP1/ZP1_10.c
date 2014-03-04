#include <stdio.h>
#include <string.h>
#include <stdlib.h>

// http://jazykc.inf.upol.cz/predani-parametru-odkazem/index.htm
// 1. Transformace textu
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

// 2. Celočíselné dělení
int deleni(int a, int b, int *r) {
    int i = 0;
    
    for (; i*b <= a; i++);
    
    *r = a-(i-1)*b;
    return i-1;
}

int main()
{
    // 1. Transformace textu
    char *in = "Ahoj svete 23.", *out;
    set(in, &out);
    printf("%s\n%s\n", in, out);
    
    // 2. Celočíselné dělení
    int zbytek;
    int *r = &zbytek;
    int c1 = deleni(14, 4, r);
    printf("\n14 : 4 = %i (zbytek %i)\n", c1, zbytek);
    
    return 0;
}

