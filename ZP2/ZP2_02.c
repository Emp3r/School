#include <stdio.h>
#include <stdlib.h>

// http://jazykc.inf.upol.cz/staticka-vicerozmerna-pole/index.htm
// 1.1 Maximum dvojrozměrného pole
int maximum(int prvky[][4], int radku) {
    int i, j, vysledek = prvky[0][0];
    for (i = 0; i < radku; i++)
        for (j = 0; j < 4; j++) 
            if (vysledek < prvky[i][j])
                vysledek = prvky[i][j];
    return vysledek;
}
// 1.2 Suma dvojrozměrného pole
int suma(int prvky[][4], int radku) {
    int i, j, vysledek = 0;
    for (i = 0; i < radku; i++)
        for (j = 0; j < 4; j++)
            vysledek += prvky[i][j];
    return vysledek;
}

// 2. Suma řádků dvojrozměrného pole
int *suma_radku(int prvky[][4], int radku) {
    int i, *vysledek = (int*)malloc(radku * sizeof(int));
    
    for (i = 0; i < radku; i++) {
        vysledek[i] = prvky[i][0] + prvky[i][1] + prvky[i][2] + prvky[i][3];
    }
    return vysledek;
}

// 3. Aritmetický průměr čísel v matici (první úkol od Havrlanta)
double prumer(int matice[][5], int pocet_radku) {
    int i, j;
    double vysledek = 0;
    for (i = 0; i < pocet_radku; i++)
        for (j = 0; j < 5; j++)
            vysledek += matice[i][j];
    vysledek /= (pocet_radku * 5);
    return vysledek;
}

int main()
{
    int i, j;
    int pole[][4] = {{10, 2, 15, -2}, {-52, 41, 0, 12}, {15, 3, 1, -8}};
    printf("Výpis pole:\n");
    for (i = 0; i < 3; i++) {
        for (j = 0; j < 4; j++)
            printf("%5i", pole[i][j]);
        printf("\n");
    }
    // 1.1 Maximum dvojrozměrného pole
    printf("\nMaximum pole je: %i\n", maximum(pole, 3));
    // 1.2 Suma dvojrozměrného pole
    printf("Suma pole je: %i\n", suma(pole, 3));
    
    // 2. Suma řádků
    printf("\nSuma řádků je: %i, %i, %i\n", suma_radku(pole, 3)[0], suma_radku(pole, 3)[1], suma_radku(pole, 3)[2]);
    
    // 3. Aritmetický průměr čísel v matici
    int pole_prumer[][5] = {{1, 2, -3, 4, 5}, {6, 1, 45, 12, 0}};
    printf("\nPrůměr čísel v matici {{1, 2, -3, 4, 5}, {6, 1, 45, 12, 0}} je: %.2f\n\n", prumer(pole_prumer, 2));
    
    return 0;
}