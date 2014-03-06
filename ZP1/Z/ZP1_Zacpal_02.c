#include <stdio.h>
#include <stdlib.h>

int main()
{
    int i, delka, max = 0;
    char string[200];
    int cetnost[256];
    
    printf("Zadej retezec: ");
    scanf("%200s", string);
    
    delka = strlen(string);
    
    for (i = 0; i < 256; i++)
        cetnost[i] = 0;
    
    for (i = 0; i < delka; i++)
        cetnost[string[i]]++;
    
    for (i = 65; i <= 90; i++) {
        cetnost[i+32] = cetnost[i+32] + cetnost[i];
        cetnost[i] = 0;
    }
    
    for (i = 0; i < 256; i++) {
        if (cetnost[i] > 0) {
            printf("Znak %c se v retezci vyskytuje %ix.\n", i, cetnost[i]);
            if (cetnost[i] > max)
                max = cetnost[i];
        }
    }

    printf("Nejčastěji se v řetězci vyskytuje znak: ");
    for (i = 0; i < 256; i++) {
        if (cetnost[i] == max)
            printf("%c, ", i);
    }
    return 0;
}