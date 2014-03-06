#include <stdio.h>

int main()
{
    int pocet, i, j, n;
    printf("Zadej cislo n: ");
    scanf("%i", &n);
    
    for (pocet=2; pocet < (n+2);pocet++) {
        for (i = 1; i<pocet; i++) {
            printf("x");
            for (j=1; j<=(2*(pocet-i)+1)-2;j++) {
                printf(" ");
            }
            printf("x\n");
            for (j=1; j<=i;j++) {
                printf(" ");
            }
        }
        printf("x\n");
        for (i = 1; i<pocet; i++) {
            for (j=1; j<=((pocet-i)+1)-2;j++) {
                printf(" ");
            }
            printf("x");
            for (j=1; j<=(2*i+1)-2;j++) {
                printf(" ");
            }
            printf("x\n");
        }
        printf("\n");
        if (pocet==13) break;
    }
    return 0;
}