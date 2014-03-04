#include <stdio.h>
#include <string.h>
#include <stdarg.h>
#include <assert.h>

// 1. Průměr čísel
long double prumer(char* format, ...) {
    int i, j;
    int pocet = (int)strlen(format);
    long double vysledek = 0.0;
    va_list parametry;
    va_start(parametry, format);
    
    for (i = pocet, j = 0; i > 0; i--, j++) {
        switch (format[j]) {
            case 'i':
                vysledek += (long double)va_arg(parametry, int);
                break;
            case 'd':
                vysledek += (long double)va_arg(parametry, double);
                break;
            case 'l':
                vysledek += va_arg(parametry, long double);
                break;
            default: break;
        }
    }
    va_end(parametry);
    return vysledek / pocet;
}

// 2. Suma komplexních čísel
typedef struct {double realna, imaginarni;} komplexni;

komplexni suma(int pocet, ...) {
    int i;
    komplexni vysledek = {0.0, 0.0};
    va_list parametry;
    va_start(parametry, pocet);
    
    for (i = 0; i < pocet; i++) {
        komplexni temp = va_arg(parametry, komplexni);
        vysledek.realna += temp.realna;
        vysledek.imaginarni += temp.imaginarni;
    }
    va_end(parametry);
    return vysledek;
}

// 3. Suma čísel (2. úkol od Havrlanta)
int suma2(int zacatek, ...) {
    int vysledek = 0;
    int temp = zacatek;
    va_list parametry;
    va_start(parametry, zacatek);
    
    while (temp != 0) {
        vysledek += temp;
        temp = va_arg(parametry, int);
    }
    va_end(parametry);
    return vysledek;
}

int main()
{
    // 1. Průměr čísel
    long double pr = prumer("idld", 1, (double)3, (long double)2, 3.0);
    printf("Prumer je %Lf. \n", pr);
    
    assert(2.25 == prumer("idld", 1, (double)3, (long double)2, 3.0));
    assert(10 == prumer("iii", 5, 10, 15));
    assert(5.5 == prumer("d", (double)5.5));
    
    // 2. Suma komplexních čísel
    komplexni vysledek;
    komplexni a = {3.1,-2.3};
    komplexni b = {0.5,-3};
    komplexni c = {0,1.2};
    
    vysledek = suma(3,a,b,c);
    printf("Suma je %g + %gi. \n", vysledek.realna, vysledek.imaginarni);
    
    // 3. Suma čísel
    assert(10 == suma2(5, 2, 3, 0));
    assert(5 == suma2(5, 2, 3, -5, 0));
    assert(42 == suma2(42, 0));
    assert(0 == suma2(0));
    assert(0 == suma2(0, 7));
    
    return 0;
}