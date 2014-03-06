#include <stdio.h>
#include <stdlib.h>

// 1.
void int2bits(char *ch, int n) {
    int i, bit;
    for (i = 0; i < 32; i++) {
        bit = (n & ( 1 << i )) >> i;
        ch[32 - (i + 1)] = 48 + bit;
    }
    ch[i+1] = '\0';
}

// 2.
short encode_date(char day, char month, short year) {
    short result = year;
    result <<= 4;
    result += month;
    result <<= 5;
    result += day;
    return result;
}

// 3.
// 4.
// 5.
// 6.


int main(int argc, const char * argv[])
{
    // 1.
    int cislo1 = 511;
    char* vysledek1 = malloc(33);
    int2bits(vysledek1, cislo1);
    printf(" %s \n", vysledek1);
    
    // 2.
    //unsigned short viysledek2 = encode_date(28, 7, 2001);
    //char* stream2 = malloc(33);
    //int2bits(stream2, vysledek2);
    //printf(" %i \n", vysledek2);
    
    return 0;
}