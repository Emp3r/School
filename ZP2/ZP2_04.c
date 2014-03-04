#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// http://jazykc.inf.upol.cz/ukazatele-na-funkce/index.htm
double na2(double x){
	return x * x;       }
double na3(double x){
    return x * x * x;   }
double soucet(double x, double y){
    return x + y;       }
double soucin(double x, double y){
    return x * y;       }

// 1. Mapování funkce
double *map(double (*fce)(double), double *vstup, int delka) {
    int i;
    double *vysledek = malloc(sizeof(double) * delka);
    
    for (i = 0; i < delka; i++)
        vysledek[i] = fce(vstup[i]);
    
    return vysledek;
}

// 2. Mapování pole funkcí
double **map2(double(*fce[])(double),double *vstup,int pocet_fce, int pocet_vstup) {
    int i, j;
    double **vysledek = (double**)malloc(sizeof(double*) * (pocet_fce + 1));
    
    vysledek[0] = vstup;
    for (i = 1; i < pocet_fce + 1; i++)
        vysledek[i] = (double*)malloc(sizeof(double) * pocet_vstup);
    
    for (i = 0; i < pocet_fce; i++)
        for (j = 0; j < pocet_vstup; j++)
            vysledek[i+1][j] = fce[i](vstup[j]);
    
    return vysledek;
}

// 3. Akumulátor
double akumulator(double (*fce)(double, double), double cisla[], int pocet) {
    int i;
    double vysledek = cisla[0];
    
    for (i = 1; i < pocet; i++)
        vysledek = fce(vysledek, cisla[i]);
        
    return vysledek;
}

// 4. Odstranění znaků ze začátku řetězce (2. úkol od Havrlanta)
char* odstran_ze_zacatku(int (*predikat)(char), const char* retezec) {
    int delka = (int)strlen(retezec);
    
    while ((*retezec) != '\0') {
        if (predikat( (*retezec))) {
            retezec++;
            delka--;
        } else break;
    }
    char *vysledek = (char*)malloc(delka + 1);
    return strcpy(vysledek, retezec);
}
int male_pismeno(char x) {
    return x >= 'a' && x <= 'z';    }
int velke_pismeno(char x) {
    return x >= 'A' && x <= 'Z';    }


int main()
{
    // 1. Mapování funkce
    int i, j;
    double pole1[] = {1, 2, 3, 4, 5};
    double *pole2 = map(na2, pole1, 5);
    double *pole3 = map(na3, pole1, 5);
    
    printf("Vstupní pole:  ");
    for (i = 0; i < 5; i++) printf("%4g, ", pole1[i]);
    printf("\nDruhé mocniny: ");
    for (i = 0; i < 5; i++) printf("%4g, ", pole2[i]);
    printf("\nTřetí mocniny: ");
    for (i = 0; i < 5; i++) printf("%4g, ", pole3[i]);
    
    // 2. Mapování pole funkcí
    double(*pole_fci[])(double) = {na2, na3};
    double **pole = map2(pole_fci, pole1, 2, 5);
    
    printf("\n\nHodnoty výstupního pole:\n");
    for (i = 0; i < 3; i++) {
        for (j = 0; j < 5; j++)
            printf("%5g", pole[i][j]);
        printf("\n");
    }
    
    // 3. Akumulátor
    double p[10];
    for (i = 0; i < 10; i++)
        p[i] = i+1;
    
    printf("\nSuma je: %g\n", akumulator(soucet,p,10));
    printf("Produkt je: %g\n", akumulator(soucin,p,10));
    
    // 4. Odstranění znaků ze začátku řetězce
    printf("\n%s, ", odstran_ze_zacatku(male_pismeno, "abcDEF"));         // DEF
    printf("%s, ", odstran_ze_zacatku(male_pismeno, "ABCdef"));             // ABCdef
    printf("%s, ", odstran_ze_zacatku(male_pismeno, "abcDEFghij"));         // DEFghij
    printf("%s\n", odstran_ze_zacatku(male_pismeno, "baba nebo snih"));     //  nebo snih
    printf("%s, ", odstran_ze_zacatku(velke_pismeno, "abcDEF"));            // abcDEF
    printf("%s, ", odstran_ze_zacatku(velke_pismeno, "ABCdef"));            // def
    printf("%s, ", odstran_ze_zacatku(velke_pismeno, "abcDEFghij"));        // abcDEFghij
    printf("%s, ", odstran_ze_zacatku(velke_pismeno, "baba nebo snih"));    // baba nebo snih
    printf("%s, ", odstran_ze_zacatku(velke_pismeno, "baba"));              // baba
    
    return 0;
}