#include <stdio.h>

#define je_cislice(zaklad, znak)                    \
((((znak) >= 'A') && ((znak) <= 'Z'))     ?         \
(((znak) - ('A' - 10)) < (zaklad))        :         \
((((znak) >= '0') && ((znak) <= '9')) && ((znak) - '0') < (zaklad)))

#define cti_int(i)              \
(scanf("%i", &i), i)


int main()
{
    if (je_cislice(8,'8')!=0) printf("Ano\n"); else printf("Ne\n");
    if (je_cislice(10+6,'0'+4)!=0) printf("Ano\n"); else printf("Ne\n");
    if (je_cislice(30,'@')!=0) printf("Ano\n"); else printf("Ne\n");
    if (je_cislice(16,'A')!=0) printf("Ano\n"); else printf("Ne\n");
    
    int j, k;
    printf("\nZadejte cele cislo: ");
    if ((j = cti_int(k)) == 0) printf("nula\n");
    else printf("%i %i\n", j,k);
    
    return 0;
}