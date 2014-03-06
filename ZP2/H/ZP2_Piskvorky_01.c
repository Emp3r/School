#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int victorylist[][3] = {
    {1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {1, 4, 7},
    {2, 5, 8}, {3, 6, 9}, {7, 5, 3}, {9, 5, 1}};

char** newboard() {
    int i, j;
    
    char** board = (char**)malloc(sizeof(char*) * 3);
    for (i = 0; i < 3; i++)
        board[i] = (char*)malloc(sizeof(char) * 3);
    
    for (i = 0; i < 3; i++)
        for (j = 0; j < 3; j++)
            board[i][j] = '_';
    
    return board;
}

static int colors = 0;

void printboard(char** board) {
    int i, j;
    
    printf("  1 2 3\n");
    for (i = 0; i < 3; i++) {
        if (colors) {
            printf("%s%c ", "\x1B[0m", ('a' + i));
            for (j = 0; j < 3; j++) {
                char* color = (board[i][j] == '_') ? "\x1B[0m" : ((board[i][j] == 'X') ? "\x1B[34m" : "\x1B[32m");
                printf("%s%c ", color, board[i][j]);
            }
            printf("\n");
            }
        else {
            printf("%c %c %c %c\n", ('a' + i), board[i][0], board[i][1], board[i][2]);
        }
    }
}

int win(char** board, int player) {   // vraci 1 pokud hrac "player" vyhral
    int i, j, temp;
    char p = (player == 1) ? 'X' : 'O';
    
    for (i = 0; i < 8; i++) {
        for (j = 0; j < 3; j++) {
            temp = victorylist[i][j] - 1;
            if (board[temp / 3][temp % 3] != p)
                break;
        }
        if (j == 3)
            return 1;
    }
    return 0;
}

int full(char** board) {   // vraci 1 pokud je hraci deska zaplnena
    int i, j;
    
    for (i = 0; i < 3; i++)
        for (j = 0; j < 3; j++)
            if(board[i][j] == '_')
                return 0;
    return 1;
}

int turn(char** board, char* command, int player) {   // vraci 1 pokud zapsani na souradnice probehlo v poradku
    if (strlen(command) == 2 && (command[0] >= 'a' && command[0] <= 'c') && (command[1] >= '1' && command[1] <= '3')) {
        if (board[command[0] - 97][command[1] - 49] == '_') {
            if (player == 1)
                board[command[0] - 97][command[1] - 49] = 'X';
            else
                board[command[0] - 97][command[1] - 49] = 'O';
            
            return 1;
        }
        else
            printf("Na zadanych souradnicich uz neco je, zkus to znovu.\n");
    }
    else 
        printf("Spatne zadany prikaz. Zadavej ve tvaru \"a1\" az \"c3\" nebo zadej \"?\" pro napovedu.\n");
    
    return 0;
}

int save(char** board, const char* file, int player) {   // vraci 1 pokud byl soubor uspesne ulozen
    int i, j;
    FILE* output = fopen(file, "w");
    if (output == NULL)
        return 0;
    
    fputc((player == 1) ? '1' : '2', output);
    
    for (i = 0; i < 3; i++)
        for (j = 0; j < 3; j++)
            fputc(board[i][j], output);
    
    fclose(output);
    return 1;
}

int load(char** board, const char* file, int* player) {   // vraci 1 pokud se hra uspesne nacetla
    int i, j;
    FILE* input = fopen(file, "r");
    if (input == NULL)
        return 0;
    
    *player = (fgetc(input) == '1') ? 1 : 2;
    
    for (i = 0; i < 3; i++)
        for (j = 0; j < 3; j++)
            board[i][j] = fgetc(input);
    
    fclose(input);
    return 1;
}


int main(int argc, const char * argv[])
{
    char* input = (char*)malloc(sizeof(char) * 10);
    char** gameboard = newboard();
    int gameplayer = 1;
    
    if (argc == 2) {
        if (load(gameboard, argv[1], &gameplayer))
            printf("Hra byla uspesne nactena.\n");
    }
    
    if (colors == 0) {
        printf("Chcete zapnout barvy? (jen pro Unix)\n> ");
        scanf("%s", input);
        if (strcmp(input, "ano") == 0)
            colors = 1;
    }
    
    while (1) {
        printboard(gameboard);
        
        printf("Na rade je hrac %i (%s)\n", gameplayer, (gameplayer - 1) ? "kolecka" : "krizky");
        printf("> ");
        scanf("%s", input);
        
        if (strcmp(input, "konec") == 0) {
            printf("Konec hry.\n");
            break;
        }
        else if (strcmp(input, "uloz") == 0) {
            if (save(gameboard, "piskvorky.txt", gameplayer)) {
                printf("Hra byla uspesne ulozena. Konec hry.\n");
                break;
            }
        }
        else if (strcmp(input, "novy") == 0) {
            gameboard = newboard();
            gameplayer = 1;
            printf("\nVitej v nove hre.\n");
        }
        else if (strcmp(input, "?") == 0)
            printf("Napoveda:\n \"a1\" az \"c3\" pro zadavani souradnic\n \"novy\" pro novou hru\n \"konec\" pro ukonceni hry\n \"uloz\" pro ulozeni hry\n");
        else
            if (turn(gameboard, input, gameplayer)) {
                if (win(gameboard, gameplayer)) {
                    printboard(gameboard);
                    printf("Vyhral hrac %i (%s). Konec hry.\n", gameplayer, (gameplayer - 1) ? "kolecka" : "krizky");
                    break;
                }
                else if (full(gameboard)) {
                    printf("Remiza, vsechny policka jsou zaplneny. Konec hry.\n");
                    break;
                }
                gameplayer = (gameplayer == 1) ? 2 : 1;
            }
    }
    return 0;
}