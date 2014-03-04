#include <stdio.h>

int main()
{
    // http://jazykc.inf.upol.cz/jednorozmerna-pole/index.htm
    // Deklarace
    int prvni_pole[7] = {1, 2, 3, 4, 5, 6, 7};  // pole pro první příklad
    int prumer_pole[] = {1, 2, 3, 4, 5, 6, 7};  // pole pro třetí příklad
    float prumer;
    int i, j, temp;             // proměnné pro cykly
    int pole_100[100];          // pole pro eratosthenovo síto
    int mez, delka;
    int pole_suma[] = {1, 2, 3, 5, 8, 1, 1, 1, 3};
    char string[] = "aaa bb ccc ddd fffeeeshdhdkkay23455";
    int cetnost[256];
    
    // 1. Obrácení pole
    printf("Pole vůvodně: ");
    for (int i = 0; i < 7; i++) {           // výpis prvků v poli
    printf("%i, ", prvni_pole[i]); 
    }
    for (int i = 0; i < 3; i++) {           // algoritmus pro obrácení sedmi prvků v poli
        prvni_pole[i] = prvni_pole[6-i];    // pokud se změní počet prvků, musí se přepsat
        prvni_pole[6-i] = i+1;
    }
    printf("\nObrácené pole: ");            // zase výpis prvků v poli
    for (int i = 0; i < 7; i++) {
        printf("%i, ", prvni_pole[i]);
    }
    
    // 2. Eratosthenovo síto
    // 2.1 Eratosthenovo síto pro čísla menší než 100
    printf("\n\nPrvočísla menší než 100 (pomocí Erasthenova síta): ");
    for (i = 0; i < 100; i++) {                 // cyklus pro naplnění pole čísly
        pole_100[i] = i;                        // přesně podle indexu
    }
    pole_100[0] = pole_100[1] = 0;              // čísla 0 a 1 nejsou prvočísly
    
    for (i = 0; i < 100; i++) {                 // procházení celého pole
        if (pole_100[i] == 0) continue;         // když je číslo 0, přeskočí se na další
        printf("%i, ", pole_100[i]);            // když není 0, vypíše se
        for (j = 2 * i; j < 100; j = j + i) {   // a všechny jeho násobky se přepíšou na 0
            pole_100[j] = 0;
        }
    }
    // 2.2 Eratosthenovo síto s určením meze
    printf("\nZadej mez Erasthenova síta: ");
    scanf("%i", &mez);
    int e_pole[mez+1];              // deklarace nového pole podle zadané meze
                                    // v tomhle případě nejde deklarovat na začátku
    for (i = 0; i <= mez; i++) {
        e_pole[i] = i;
    }
    e_pole[0] = e_pole[1] = 0;
    
    for (i = 0; i <= mez; i++) {
        if (e_pole[i] == 0) continue;
        printf("%i, ", e_pole[i]);
        for (j = 2 * i; j <= mez; j = j + i) {
            e_pole[j] = 0;
        }
    }
    
    // 3. Aritmetický průměr pole
    printf("\n\nPole obsahuje čísla: ");
    delka = -1;                                 // způsob jak vypočítat délku pole
    while (pole_suma[++delka]);                 // (pokud je deklarováno jako pole[], bez hodnoty v [])
    
    for (int i = 0; i < 7; i++) {               // výpis pole (podle zadání jen 7 čísel)
        printf("%i, ", prumer_pole[i]);
    }
    
    for (int i = 0; i < 7; i++) {               // sečtení všech čísel pole 
        prumer = prumer + prumer_pole[i];       // do proměnné prumer
    }
    prumer = prumer / 7;                        // součet čísel se vydělí počtem čísel
    printf("\nPrůměr pole je %.2f", prumer);    // vypíše se průměr (%.2f vypíše jen 2 desetiná místa)
    
    // 4. Sumy prefixů (1. úkol od Havrlanta)
    printf("\n\nVstupni pole: ");
    delka = -1;                           // zase, způsob jak vypočítat délku pole
    while (pole_suma[++delka]);           // (pokud je na začátku jako pole[], bez hodnoty v [])
    
    for (i = 0; i < delka; i++)
        printf("%i, ", pole_suma[i]);
    
    printf("\nSumy prefixů: ");
    for (i = 0; i < delka; i++){
        temp = 0;
        for (j = 0; j <= i; j++) {
            temp = temp + pole_suma[j];
        }
        printf("%i, ", temp);
    }
    
    // 5. Četnost znaků (2. úkol od Havrlanta)
    printf("\n\nVstupni retezec: %s\n", string);
    for (i = 0; i < 256; i++)
        cetnost[i] = 0;              // naplnění pole cetnost hodnotami 0
    
    delka = -1;                      // zase vypočítání délky pole string (textový řetězec)
    while (string[++delka]);
    
    for (i = 0; i < delka; i++) {
        cetnost[string[i]]++;        // přičtení znaku do pole cetnost
    }
    
    for (i = 0; i < 256; i++) {
        if (cetnost[i] != 0)                        // pokud znak na pozici v poli cetnost je ruzny od nuly,
            printf("'%c' : %i\n", i, cetnost[i]);   // vypise se znak a jeho cetnost - kolikrat je v textu
    }
    
    return 0;
}