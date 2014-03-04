#include <stdio.h>
#include <math.h>

// http://jazykc.inf.upol.cz/funkce/index.htm

// 1. Funkce suma pole
double suma_pole(double pole[], int pocet)
{
    double vysledek;
    int i;
    for (i = 0; i < pocet; i++) {
        vysledek = vysledek + pole[i];
    }
    return vysledek;
}

// 2. Funkce převody čísel
int do_desitkove(char cislo[], int zaklad)
{
    int vysledek = 0, delka = -1;
    int i, pomocne;
    while (cislo[++delka]);         // zjištění počtu míst a uložení do proměnné delka (předtím musí být -1)
    
    for (i = 0; i < delka; i++) {
        if (cislo[delka-1-i] >= '0' && cislo[delka-1-i] <= '9')      // podmínky pro převedení čísla zapsaného
            pomocne = cislo[delka-1-i] - 48;                         // jako char na int (včetně písmen),
        else if (cislo[delka-1-i] >= 'a' && cislo[delka-1-i] <= 'z') // např: char '3' = int 3, char 'F' = int 15
            pomocne = cislo[delka-1-i] - 87;                         // 'a' = 10, 'b' = 11, 'c' = 12 ... atd
        else if (cislo[delka-1-i] >= 'A' && cislo[delka-1-i] <= 'Z')
            pomocne = cislo[delka-1-i] - 55;
        
        vysledek = vysledek + pomocne * pow(zaklad, i);     // podle vzorce na převádění do desítkové soustavy,
    }                                                       // pro funkci pow('číslo', 'exponent')
                                                            // musíme přidat knihovnu <math.h>
    return vysledek;
}

int main()
{
    // Deklarace
    double pole_suma[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};      // podle zadání jen 10 čísel
    int pocet = -1;                     // počet braných čísel, později se vypočítá podle počtu čísel v poli
    int i;
    char cislo1[] = "A1B";              // číslo zapsané jako textový řetězec
    char cislo2[] = "10aFd4A";
    int zaklad = 12;
    
    // 1. Suma pole
    printf("Pole obsahuje čísla: ");
    while (pole_suma[++pocet]);         // cyklus pro vypočítání počtu míst (délky řetězce)
    for (i = 0; i < pocet; i++) {       // vypsání čísel v poli
        printf("%g, ", pole_suma[i]);
    }
    printf("\nSuma pole je: %g", suma_pole(pole_suma, pocet));  // vypsání sumy pomocí zavolání funkce suma_pole()
    
    
    // 2. Převody čísel do desítkové soustavy
    printf("\n\nČíslo %s v soustavě o základu %i", cislo1, zaklad);
    printf("\nodpovídá číslu %i v desítkové.", do_desitkove(cislo1, zaklad));     // volání funkce do_desitkove()
    
    printf("\nA číslo %s v soustavě o základu %i", cislo2, zaklad + 4);           // druhý pokus s číslem cislo2[]
    printf("\nodpovídá číslu %i v desítkové.", do_desitkove(cislo2, zaklad+4));   // chceme základ 16
    
    return 0;
}