#include <stdio.h>

int main()
{
    // http://jazykc.inf.upol.cz/vetveni-programu/index.htm
    // Deklarace
    char znak;
    int a, b, c, max;
    float mzda;
    
    // 1. Rozpoznání znaku
    printf("Zadej znak: ");
    scanf("%c", &znak);
    if (znak >= 'a' && znak <= 'z') printf("Znak je malé písmeno %c.", znak);
    else if (znak >= 'A' && znak <= 'Z') printf("Znak je velké písmeno %c.", znak);
    else if (znak >= '0' && znak <= '9') printf("Znak je číslo %c.", znak);
    else {
        switch (znak) {
            case '!': printf("Znak je vykřičník."); break;
            case '?': printf("Znak je otazník."); break;
            case '*': printf("Znak je hvězdička."); break;
            case '@': printf("Znak je zavináč."); break;
            case '^': printf("Znak je stříška."); break;
            default: printf("Jiný znak."); break;
        }
    }
    
    // 2. Maximum (největší číslo ze 3 zadaných)
    printf("\nZadej 3 čísla: ");
    scanf("%i, %i, %i", &a, &b, &c);
    if (a >= b && a >= c) max = a;
    if (b >= a && b >= c) max = b;
    if (c >= b && c >= a) max = c;
    printf("Největší číslo je %i.", max);
    
    // 3. Výpočet progresivní daně
    printf("\nZadejte mzdu: ");
    scanf("%fl", &mzda);
    if (mzda <= 10000) printf("Daň je %.2f", mzda * 0.1);
    if (mzda > 10000 && mzda <= 20000) printf("Daň je %.2f", 1000 + ((mzda - 10000) * 0.2));
    if (mzda > 20000) printf("Daň je %.2f", 3000 + ((mzda - 20000) * 0.3));
    
    
    return 0;
}