#include <stdio.h>

// http://jazykc.inf.upol.cz/rekurzivni-funkce/index.htm
// 1.1 Fibonacciho čísla
int fib(int n) {
    if (n == 0)
        return 0;
    else if (n == 1)
        return  1;
    else
        return fib(n-1) + fib(n-2);
}
// 1.2 Faktoriál
int fact(int n) {
    if (n < 2)
        return 1;
    else
        return n * fact(n-1);
}

// 2. Hledání půlením intervalu 
int puleni(int cisla[], int a, int b, int hledane) {
    if (a > b)
        return -1;
    int p = (a + b) / 2;
    if (cisla[p] == hledane) {
        return p;
    }
    else {
        if (hledane < cisla[p]) 
            return puleni(cisla, a, p, hledane);
        else
            return puleni(cisla, p+1, b, hledane);
    }
}

// 3. Neuvěřitelná vlastnost přirozených čísel (1. úkol od Havrlanta)
int terminator_posloupnosti(int x) {
    int soucet = 0;
    if (x == 1 || x == 89)
        return x;
    while (x > 0) {
        soucet = soucet + (x % 10) * (x % 10);
        x = x / 10;
    }
    return terminator_posloupnosti(soucet);
}

int main()
{
    // 1.1 Fibonacciho čísla
    printf("Fib(8) = %i\n", fib(8));
    printf("Fib(24) = %i\n", fib(24));
    printf("Fib(35) = %i\n", fib(35));
    // 1.2 Faktoriál
    printf("Fact(6) = %i\n\n", fact(6));
    
    // 2.  Hledání půlením intervalu
    int i, p[11];
    for (i=0; i<11; i++)
        p[i] = 2*i+3;
    for (i=0; i<11; i++)
        printf("Cislo %i je na indexu: %i\n", 2*i+3, puleni(p,0,10, 2*i+3));
    
    printf("Cislo %i je na indexu: %i\n", 666, puleni(p, 0, 10, 666)); // Cislo 666 je na indexu: -1
    
    // 3. Neuvěřitelná ...
    printf("\n%i\n", terminator_posloupnosti(1)); // 1
    printf("%i\n", terminator_posloupnosti(44)); // 1
    printf("%i\n", terminator_posloupnosti(32)); // 1
    printf("%i\n", terminator_posloupnosti(44222)); // 1
    printf("%i\n", terminator_posloupnosti(89)); // 89
    printf("%i\n", terminator_posloupnosti(37)); // 89
    printf("%i\n", terminator_posloupnosti(20)); // 89
    printf("%i\n", terminator_posloupnosti(666)); // 89
    
    return 0;
}