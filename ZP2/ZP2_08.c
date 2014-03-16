#include <stdio.h>
#include <errno.h>

void soucet_cisel(const char* read, const char* write) {
    int symbol, temp, line;
    
    FILE* input = fopen(read, "r");
    if (input == NULL) {
        perror("Error with INPUT file");
        printf("Error ID: %i\n", errno);
        return;
    }
    FILE* output = fopen(write, "w");
    if (output == NULL) {
        perror("Error with OUTPUT file");
        printf("Error ID: %i\n", errno);
        return;
    }
    
    while (symbol != EOF) {
        line = 0;
        
        while ((symbol != EOF) && (symbol != '\n')) {
            fscanf(input, "%i", &temp);
            line += temp;
            symbol = fgetc(input);
            
            while ((symbol != EOF) && (symbol != '\n') && (symbol != ','))
                symbol = fgetc(input);
        }
        if (symbol == '\n') {
            symbol = fgetc(input);
            
            if ((symbol >= '0') && (symbol <= '9'))
                ungetc(symbol, input);
        }
        fprintf(output, "%i\n", line);
    }
    
    fclose(input);
    fclose(output);
}

int main ()
{
    soucet_cisel("soubor.txt", "vysledek.txt");
    
    return 0; 
}