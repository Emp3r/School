#include <stdio.h>
#include <math.h>

// 1.1 Funkce suma pole
double suma_pole(double pole[], int pocet)
{
    double vysledek;
    int i;
    for (i = 0; i < pocet; i++)
        vysledek = vysledek + pole[i];
    return vysledek;
}
// 1.2 Funkce průměr pole
double prumer_pole(double pole[], int pocet)
{
    double vysledek;
    int i;
    for (i = 0; i < pocet; i++)
        vysledek = vysledek + pole[i];
    return vysledek / pocet;
}
// 1.3 Funkce výpočet maxima pole
double max_pole(double pole[], int pocet)
{
    double vysledek = pole[0];
    int i;
    for (i = 0; i < pocet; i++)
        if (pole[i] > vysledek)
            vysledek = pole[i];
    return vysledek;
}
// 1.4 Funkce výpočet minima pole
double min_pole(double pole[], int pocet)
{
    double vysledek = pole[0];
    int i;
    for (i = 0; i < pocet; i++)
        if (pole[i] < vysledek)
            vysledek = pole[i];
    return vysledek;
}

// 2. Funkce převody čísel
int do_desitkove(char cislo[], int zaklad)
{
    int vysledek = 0, delka = -1;
    int i, pomocne;
    while (cislo[++delka]);
    
    for (i = 0; i < delka; i++) {
        if (cislo[delka-1-i] >= '0' && cislo[delka-1-i] <= '9')
            pomocne = cislo[delka-1-i] - 48;
        else if (cislo[delka-1-i] >= 'a' && cislo[delka-1-i] <= 'z')
            pomocne = cislo[delka-1-i] - 87;
        else if (cislo[delka-1-i] >= 'A' && cislo[delka-1-i] <= 'Z')
            pomocne = cislo[delka-1-i] - 55;
        
        vysledek = vysledek + pomocne * pow(zaklad, i);
    }
    return vysledek;
}

// 3. Najdi znak (2. úkol od Havrlanta z 6. cvičení)
int najdi_znak(char retezec[], char znak, int n)
{
    int i;
    char temp;
    for (i = 0; (temp = retezec[i]); i++) {
        if (znak == temp) {
            if (n > 1) {
                n--;
            }
            else if (n == 1) return i;
        }
    }
    return -1;
}

// 4. Příjmy a výdaje (3. úkol od Havrlanta z 6. cvičení)
double zustatek = 0;

double prijem(double castka) {
    static double celkovy_prijem;
    zustatek = zustatek + castka;
    celkovy_prijem = celkovy_prijem + castka;
    return celkovy_prijem;
}
double vydaj(double castka) {
    static double celkovy_vydaj;
    if (zustatek > castka) {
        zustatek = zustatek - castka;
        celkovy_vydaj = celkovy_vydaj + castka;
        return celkovy_vydaj;
    }
    return celkovy_vydaj;
}

// 5. Obrat pole (1. úkol od Havrlanta z 7. cvičení)
void obrat_pole(int pole[], int delka) {
    int i, temp;
    for (i = 0; i < delka / 2; i++) {
        temp = pole[i];
        pole[i] = pole[delka-1-i];
        pole[delka-1-i] = temp;
    }
}
void vypis_pole(int pole[], int delka) {
    int i;
    for (i = 0; i < delka; i++)
        printf("%i", pole[i]);
    printf("\n");
}

// 6. Součet cifer (2. úkol od Havrlanta z 7. cvičení)
int soucet_cifer(int cislo) {
    int i, vysledek = 0, temp = cislo;
    
    for (i = 10; i <= cislo * 10; i = i * 10) {
        vysledek = vysledek + (temp % 10);
        temp = temp / 10;
    }
    
    return vysledek;
}


int main()
{
    // Deklarace
    double pole[] = {15, 2, 3, 4, 5, 6, 7, 8, 9, 10, 23, 44, 33, 11, 3};
    int pocet;
    int i;
    char cislo1[] = "A1B";
    int zaklad = 12;
    
    // 1.
    printf("Pole obsahuje čísla: ");
    pocet = -1;
    while (pole[++pocet]);
    for (i = 0; i < pocet; i++) {
        printf("%g, ", pole[i]);
    }
    // 1.1 Suma pole
    printf("\nSuma pole je: %g", suma_pole(pole, pocet));
    
    // 1.2 Průměr pole
    printf("\nPrůměr pole je: %g", prumer_pole(pole, pocet));
    
    // 1.3 Maximum pole
    printf("\nMaximum pole je: %g", max_pole(pole, pocet));
    
    // 1.3 Minimum pole
    printf("\nMinimum pole je: %g", min_pole(pole, pocet));
    
    
    // 2. Převody čísel do desítkové soustavy
    printf("\n\nČíslo %s v soustavě o základu %i", cislo1, zaklad);
    printf("\nodpovídá číslu %i v desítkové.", do_desitkove(cislo1, zaklad));
    
    
    // 3. Najdi znak
    printf("\n%i", najdi_znak("sekera sobota taxi", 's', 1));
    printf("\n%i", najdi_znak("sekera sobota taxi", 's', 2));
    printf("\n%i", najdi_znak("sekera sobota taxi", 'x', 1));
    printf("\n%i", najdi_znak("sekera sobota taxi", 'x', 2));
    printf("\n%i", najdi_znak("sekera sobota taxi", 'a', 3));
    printf("\n%i", najdi_znak("sekera sobota taxi", '!', 3));
    
    // 4. Příjmy a výdaje
    printf("\n\nZustatek: %g\n", zustatek);
    printf("Prijmy: %g\n", prijem(1500));
    printf("Prijmy: %g\n", prijem(4000));
    printf("Vydaje: %g\n", vydaj(500));
    printf("Zustatek: %g\n", zustatek);
    printf("Prijmy: %g\n", prijem(1000));
    printf("Vydaje: %g\n", vydaj(2500));
    printf("Zustatek: %g\n", zustatek);
    printf("Vydaje: %g\n", vydaj(150000));
    printf("Zustatek: %g\n", zustatek);
    printf("Prijmy: %g\n", prijem(500000));
    printf("Vydaje: %g\n", vydaj(150000));
    printf("Zustatek: %g\n", zustatek);
    
    // 5. Obrat pole
    int pole1[] = {1, 2, 3, 4, 5};
    int pole2[] = {1, 2, 3, 4, 5, 6};
    int pole3[] = {13};
    int delka1 = 5, delka2 = 6, delka3 = 1;
    
    obrat_pole(pole1, delka1);
    vypis_pole(pole1, delka1);
    obrat_pole(pole2, delka2);
    vypis_pole(pole2, delka2);
    obrat_pole(pole3, delka3);
    vypis_pole(pole3, delka3);
    printf("\n");
    
    // 6. Soušet cifer
    printf("%i\n", soucet_cifer(123455678));
    printf("%i\n", soucet_cifer(63));
    printf("%i\n", soucet_cifer(123));
    printf("%i\n", soucet_cifer(10));
    printf("%i\n", soucet_cifer(45328));
    printf("%i\n", soucet_cifer(0));

    return 0;
}
