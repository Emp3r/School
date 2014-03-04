#include <stdio.h>

int main()
{
    // http://jazykc.inf.upol.cz/operatory/index.htm
    // Deklarace
    int krychle;
    float des_cislo;
    signed int absolutka;
    int a, b, c;
    float zaokrouhlit;
    
    // 1. Krychle
    printf("Zadej stranu krychle: ");
    scanf("%d", &krychle);
    printf("Povrch krychle je %d m^2 a objem je %d m^3\n", krychle * krychle * 6, krychle * krychle * krychle);
    
    // 2. Desetinné číslo - celá část čísla
    printf("Zadej desetiné číslo: ");
    scanf("%fl", &des_cislo);
    printf("Celá část je %i\n", (int)des_cislo);
    
    // 3. Absolutní hodnota
    printf("Zadej (záporné) číslo: ");
    scanf("%i", &absolutka);
    absolutka = absolutka < 0 ? absolutka * (-1) : absolutka; // -promenna == promena*(-1)
    printf("Absolutní hodnota je %d\n", absolutka);
    
    // 4. Sestrojitelnost trojúhelníku
    printf("Zadej strany trojúhelníku: ");
    scanf("%i, %i, %i", &a, &b, &c);
    (a + b) > c && (a + c) > b && (b + c) > a ? printf("Lze sestrojit\n") : printf("Nelze sestrojit\n");
    
    //5. Zaokrouhlení (chybí zadat přesnost)
    printf("Zadej desetiné číslo: ");
    scanf("%fl", &zaokrouhlit);
    (zaokrouhlit - (int)zaokrouhlit) >= 0.5 ? printf("%i", ((int)zaokrouhlit+1)) : printf("%i", (int)zaokrouhlit);
    
    return 0;
}