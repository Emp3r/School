#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define KOLECKO 'O'
#define KRIZEK 'X'
#define NIKDO '_'

#define VELIKOST 3
#define POCET_VITEZNYCH 8

typedef char** HraciDeska;
typedef char Hrac;

int vitezne_pozice[][3] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {1, 4, 7},{2, 5, 8}, {3, 6, 9}, {7, 5, 3}, {9, 5, 1}};

int vyhral_hrac(HraciDeska deska, Hrac hrac) {
    int i, j, temp;
    for (i = 0; i < POCET_VITEZNYCH; i++) {
        for (j = 0; j < VELIKOST; j++) {
            temp = vitezne_pozice[i][j] - 1;
            if (deska[temp / VELIKOST][temp % VELIKOST] != hrac) {
                break;
            }
        }
        if (j == VELIKOST) {
            return 1;
        }
    }
    return 0;
}

int remiza(HraciDeska deska) {
    int i, j;
    
    for (i = 0; i < 3; i++) {
        for (j = 0; j < 3; j++) {
            if(deska[i][j] == NIKDO) {
                return 0;
            }
        }
    }
    return 1;
}

char** nova_deska() {
    int i, j;
    HraciDeska deska = (char**)malloc(sizeof(char*) * VELIKOST);
    
    deska[0] = (char*)malloc(sizeof(char) * VELIKOST);
    deska[1] = (char*)malloc(sizeof(char) * VELIKOST);
    deska[2] = (char*)malloc(sizeof(char) * VELIKOST);
    
    for (i = 0; i < VELIKOST; i++) {
        for (j = 0; j < VELIKOST; j++) {
            deska[i][j] = NIKDO;
        }
    }
    return deska;
}

void vytiskni_hraci_desku(HraciDeska deska) {
    int i, j;
    printf("  ");
    for (i = 0; i < VELIKOST; i++) {
        printf("%i ", i + 1);
    }
    printf("\n");
    for (i = 0; i < VELIKOST; i++) {
        printf("%c ", 'a' + i);
        for (j = 0; j < VELIKOST; j++) {
            printf("%c ", deska[i][j]);
        }
        printf("\n");
    }
}

int hraj(HraciDeska deska, char* prikaz, Hrac hrac) {
    int x = prikaz[0] - 'a';
    int y = prikaz[1] - '1';
    
    if ((prikaz[0] >= 'a' && prikaz[0] <= 'c') && (prikaz[1] >= '1' && prikaz[1] <= '3')) {
        if (deska[x][y] == NIKDO) {
            deska[x][y] = hrac;
        }
        else {
            printf("Nelze prepsat zaplnene policko\n");
            return 0;
        }
    }
    else {
        printf("Prikaz neexistuje, souradnice zadavej pouze ve tvaru a1, b3 atd.\n");
        return 0;
    }
    return 1;
}

void uloz_hru(HraciDeska deska, const char* soubor, Hrac hrac) {
    int i, j;
    FILE* save = fopen(soubor, "w");
    
    if (save == NULL) {
        printf("Nepovedlo se ulozit hru\n");
        return;
    }
    fputc(hrac, save);
    
    for (i = 0; i < 3; i++) {
        for (j = 0; j < 3; j++) {
            fputc(deska[i][j], save);
        }
    }
    
    printf("Ulozeno. Konec hry.\n");
    fclose(save);
}

void nacti_hru(HraciDeska deska, const char* soubor, Hrac* hrac) {
    int i, j;
    
    FILE* load = fopen(soubor, "r");
    if (load == NULL) {
        printf("Nepovedlo se nacist hru\n");
        return;
    }
    
    *hrac = fgetc(load);
    
    for (i = 0; i < 3; i++) {
        for (j = 0; j < 3; j++) {
            deska[i][j] = fgetc(load);
        }
    }
    
    printf("Nacteno ze souboru:\n");
    fclose(load);
}

int main(int argc, const char* argv[])
{
    char* prikaz = (char*)malloc(sizeof(char) * 10);
    HraciDeska deska = nova_deska();
    Hrac hrac = KRIZEK;
    
    if (argc == 2) {
        nacti_hru(deska, argv[1], &hrac);
    }
    
    vytiskni_hraci_desku(deska);
    
    while(1) {
        printf("> ");
        scanf("%s", prikaz);
        if (strcmp(prikaz, "konec") == 0) {
            break;
        }
        else if (strcmp(prikaz, "uloz") == 0) {
            uloz_hru(deska, "piskvorky.txt", hrac);
            break;
        }
        else if (strlen(prikaz) == 2) {
            if (hraj(deska, prikaz, hrac) == 1) {
                
                if (vyhral_hrac(deska, hrac) == 1) {
                    vytiskni_hraci_desku(deska);
                    printf("Vyhral hrac %c.", hrac);
                    break;
                }
                else if (remiza(deska) == 1) {
                    vytiskni_hraci_desku(deska);
                    printf("Remiza, konec\n");
                    break;
                }
                
                if (hrac == KRIZEK) {
                    hrac = KOLECKO;
                }
                else {
                    hrac = KRIZEK;
                }
            }
        }
        else {
            printf("Prikaz neexistuje, souradnice zadavej pouze ve tvaru a1, b3 atd.\n");
        }
        
        vytiskni_hraci_desku(deska);
        
        printf("Na rade je: %c\n", hrac);
    }
    return 0;
}