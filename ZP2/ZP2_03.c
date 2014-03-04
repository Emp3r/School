#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// http://jazykc.inf.upol.cz/dynamicka-vicerozmerna-pole/index.htm
// 1.1 Součin matic
double **soucin(int m, int n, int o, double **A, double **B) {
    int i, j, k, temp;
    double **vysledek = (double**)malloc(sizeof(double*) * m);
    
    for (i = 0; i < m; i++)
        vysledek[i] = (double*)malloc(sizeof(double) * o);
    
    for (i = 0; i < m; i++) {
        for( k= 0; k < m; k++){
            temp = 0;
            for (j = 0; j < n; j++)
                temp += A[i][j] * B[j][k];
            
            vysledek[i][k] = temp;
        }
    }
    return vysledek;
}

// 1.2 Součet matic
double **soucet_matic(int radky, int sloupce, double **A, double **B) {
    int i, j;
    double **vysledek = (double**)malloc(sizeof(double*) * radky);
    
    for (i = 0; i < radky; i++)
        vysledek[i] = (double *)malloc(sloupce * sizeof(double));
    
    for (i = 0; i < radky; i++) 
        for (j = 0; j < sloupce; j++)
            vysledek[i][j] = A[i][j] + B[i][j];
        
    return vysledek;
}

// 2. Četnost znaku v poli řetězců
int vyskyty(char* texty[], int pocet, char hledany) {
    int i, j, vysledek = 0;
    
    for (i = 0; i < pocet; i++) {
        j = 0;
        while (texty[i][j] != '\0') {
            if (texty[i][j] == hledany)
                vysledek++;
            j++;
        }
    }
    return vysledek;
}

// 3. Části abecedy (2. úkol od Havrlanta)
char** casti_abecedy(int* delky, int pocet) {
    int i, j;
    char **vysledek = (char**)malloc(sizeof(char*) * pocet);
    
    for (i = 0; i < pocet; i++) {
        vysledek[i] = (char*)malloc(sizeof(char) * delky[i]);
        for (j = 0; j < delky[i]; j++)
            vysledek[i][j] = 'a' + j;
    }
    return vysledek;
}

int main()
{
    // 1.1 Součin matic
    double **A1 = (double**)malloc(sizeof(double*) * 2);
    A1[0] = (double *)malloc(3 * sizeof(double));
    A1[1] = (double *)malloc(3 * sizeof(double));
    A1[0][0] = 1;    A1[0][1] = 2;    A1[0][2] = 3;
    A1[1][0] = 4;    A1[1][1] = 5;    A1[1][2] = 6;
    
    double **B1 = (double**)malloc(sizeof(double*) * 3);
    B1[0] = (double *)malloc(2 * sizeof(double));
    B1[1] = (double *)malloc(2 * sizeof(double));
    B1[2] = (double *)malloc(2 * sizeof(double));
    B1[0][0] = 1;    B1[0][1] = 0;
    B1[1][0] = 2;    B1[1][1] = 1;
    B1[2][0] = 0;    B1[2][1] = -1;
    
    double **C1 = soucin(2, 3, 2, A1, B1);
    
    printf("Výsledek součinu matic A1 a B1:\n");
    printf("%3g %3g\n%3g %3g\n\n", C1[0][0], C1[0][1], C1[1][0], C1[1][1]);
    
    // 1.2 Součet matic
    double **B2 = (double**)malloc(sizeof(double*) * 2);
    B2[0] = (double *)malloc(3 * sizeof(double));
    B2[1] = (double *)malloc(3 * sizeof(double));
    B2[0][0] = 5;    B2[0][1] = 2;    B2[0][2] = 1;
    B2[1][0] = 3;    B2[1][1] = 6;    B2[1][2] = 9;
    
    double **C2 = soucet_matic(2, 3, A1, B2);
    
    printf("Výsledek součtu matic A1 a B2:\n");
    printf("%3g %3g %3g\n", C2[0][0], C2[0][1], C2[0][2]);
    printf("%3g %3g %3g\n\n", C2[1][0], C2[1][1], C2[1][2]);
    
    // 2. Četnost znaku v poli řetězců
    char *texty[] = {"Ahoj uzivateli, ", "jak se mas?", "Tohle bude snadne, ne?"};
    printf("Znak 'e' se v poli vyskytuje: %ix\n", vyskyty(texty, 3, 'e'));
    
    // 3. Části abecedy
    int delky[] = {2, 5, 7, 1};
    char** casti = casti_abecedy(delky, 4);
    int i;
    for (i = 0; i < 4; i++)
        printf("\n%s", casti[i]);
    
    return 0;
}

