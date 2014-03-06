#include <stdio.h>
#include <string.h>

// 1. Součet sudých čísel
int soucet_sudych(int n) {
    int vysledek;
    if (n == 1)
        return 2;
    vysledek = 2*n + soucet_sudych(n-1);

    return vysledek;
}

// 2. Dokonalá čísla
int je_dokonale(int n) {
    int i, d = 0;
    for (i = n/2; i > 0; i--) {
        if (n % i == 0)
            d = d + i;
    }
    return (d == n);
}

// 3. Trojúhelník
void trojuhelnik(int n) {
    int i, j;
    for (i = 0; i < n; i++) {
        for (j = 0; j < i; j++)
            printf(" ");
        for (j = n - i; j > 0; j--)
            printf("*");
        printf("\n");
    }
}

// 4. Palindrom
int je_palindrom(char* retezec) {
    int i, n = (int)strlen(retezec);
    for (i = 0; i < n/2; i++) {
        if (retezec[i] != retezec[n-i-1])
            return 0;
    }
    return 1;
}

int main()
{
    printf("%i\n", soucet_sudych(1)); // 2
    printf("%i\n", soucet_sudych(2)); // 6
    printf("%i\n", soucet_sudych(3)); // 12
    printf("%i\n\n", soucet_sudych(9)); // 90
    
    printf("%i\n", je_dokonale(3)); // 0
    printf("%i\n", je_dokonale(6)); // 1
    printf("%i\n", je_dokonale(10)); // 0
    printf("%i\n", je_dokonale(28)); // 1
    printf("%i\n\n", je_dokonale(496)); // 1
    
    trojuhelnik(4);
    
    printf("\n%i\n", je_palindrom("radar")); // 1
    printf("%i\n", je_palindrom("jelenovipivonelej")); // 1
    printf("%i\n", je_palindrom("hurvinek")); // 0
    
    return 0;
}