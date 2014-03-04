#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Jmena.h"

// Zadání:
// Sestavte hašovací tabulku pro uložení jmen obsažených v poli Jmena (počet jmen v poli je 127).
// Pro řešení konfliktů použijte metodu otevřeného adresování, kvadratické hledání dalších pozic.
// Zkuste v tabulce najít jména obsažená v souboru. U každého jména, pokud bylo nalezeno, vypište
// pro kontrolu jméno uložené v hašovací tabulce, jinak vypište, že nebylo nalezeno.
// Zrušte v tabulce jména obsažená v souboru.
// Zkuste nyní v tabulce najít jména obsažená v souboru.

#define FREE ""
#define SIZE 159

unsigned Hash(const char *string) {
    unsigned h = 0;
    for (const unsigned char *r = (const unsigned char*)string; *r; ++r) {
        h += *r;
        h += h << 10;
        h ^= h >> 6;
    }
    h += h << 3;
    h ^= h >> 11;
    h += h << 15;
    return h;
}

char* SearchQuadr(const char **table, int size, const char *x) {
    int i;
    unsigned h, h0 = Hash(x);
    
    for (i = 0; i < size; i++) {
        h = (h0 + i*i) % size;
        
        if (table[h] == NULL)
            return NULL;
        if ((table[h] == x) && (strcmp(table[h], FREE) != 0))
            return (char*)table[h];
    }
    return NULL;
}

int InsertQuadr(const char **table, int size, const char *x) {
    int i;
    unsigned h, h0 = Hash(x);
    
    for (i = 0; i < size; i++) {
        h = (h0 + i*i) % size;
        
        if ((table[h] == NULL) || (strcmp(table[h], FREE) == 0)) {
            table[h] = x;
            return 1;
        }
    }
    return 0;
}

int DeleteQuadr(const char **table, int size, const char *x) {
    int i;
    unsigned h, h0 = Hash(x);
    
    for (i = 0; i < size; i++) {
        h = (h0 + i*i) % size;
        
        if (table[h] == NULL)
            return 0;
        if ((strcmp(table[h], FREE) != 0) && (strcmp(table[h], x) == 0)) {
            table[h] = FREE;
            return 1;
        }
    }
    return 0;
}


int main()
{
    int i;
    const char* temp;
    
    const char* hashtable[SIZE];
    for (i = 0; i < SIZE; i++)
        hashtable[i] = NULL;
    
    for (i = 0; i < Pocet; i++)
        if (InsertQuadr(hashtable, SIZE, Jmena[i]) == 0)
            printf("Chyba.\n");
    
    for (i = 0; i < PocetN; i++) {
        temp = SearchQuadr(hashtable, SIZE, JmenaN[i]);
        
        if (temp == NULL)
            printf("Nenalezeno.\n");
        else
            printf("%s\n", temp);
    }
    
    for (i = 0; i < PocetZ; i++) {
        if (DeleteQuadr(hashtable, SIZE, JmenaZ[i]) == 0)
            printf("\nDoslo k selhani pri odebirani z tabulky.\n");
    }
    printf("\n");
    
    for (i = 0; i < PocetZN; i++) {
        temp = SearchQuadr(hashtable, SIZE, JmenaZN[i]);
        
        if (temp == NULL)
            printf("Nenalezeno.\n");
        else 
            printf("%s\n", temp);
    }
    
    return 0;
}