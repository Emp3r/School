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

void printboard(char** board) {
    int i;
    printf("  1 2 3\n");
    for (i = 0; i < 3; i++) {
        printf("%c %c %c %c\n", ('a' + i), board[i][0], board[i][1], board[i][2]);
    }
}

int win(char** board, char player) {
    int i, j, temp;
    for (i = 0; i < 8; i++) {
        for (j = 0; j < 3; j++) {
            temp = victorylist[i][j] - 1;
            if (board[temp / 3][temp % 3] != player)
                break;
        }
        if (j == 3)
            return 1;
    }
    return 0;
}

int full(char** board) {
    int i, j;
    for (i = 0; i < 3; i++)
        for (j = 0; j < 3; j++)
            if(board[i][j] == '_')
                return 0;
    return 1;
}

int turn(char** board, char* command, char player) {
    int x = command[0] - 'a';
    int y = command[1] - '1';
    
    if (strlen(command) == 2 && (command[0] >= 'a' && command[0] <= 'c') && (command[1] >= '1' && command[1] <= '3')) {
        if (board[x][y] == '_') {
            board[x][y] = player;
            return 1;
        }
        else
            printf("Na zadanych souradnicich uz neco je, zkus to znovu.\n");
    }
    else 
        printf("Spatne zadany prikaz. Zadavej ve tvaru \"a1\" az \"c3\" nebo zadej \"?\" pro napovedu.\n");
    
    return 0;
}

int save(char** board, const char* file, char player) {
    int i, j;
    FILE* output = fopen(file, "w");
    if (output == NULL)
        return 0;
    fputc((player == 'X') ? '1' : '2', output);
    
    for (i = 0; i < 3; i++)
        for (j = 0; j < 3; j++)
            fputc(board[i][j], output);
    
    fclose(output);
    return 1;
}

int load(char** board, const char* file, char* player) {
    int i, j;
    FILE* input = fopen(file, "r");
    if (input == NULL)
        return 0;
    *player = (fgetc(input) == '1') ? 'X' : 'O';
    
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
    char gameplayer = 'X';
    
    if (argc == 2)
        if (load(gameboard, argv[1], &gameplayer))
            printf("Hra byla uspesne nactena.\n");
    
    while (1) {
        printboard(gameboard);
        printf("Na rade je hrac %c (%s)\n", gameplayer, (gameplayer == 'X') ? "krizky" : "kolecka");
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
        else if (strcmp(input, "?") == 0)
            printf("Napoveda:\n \"a1\" az \"c3\" pro zadavani souradni\n \"konec\" pro ukonceni hry\n \"uloz\" pro ulozeni hry\n");
        else
            if (turn(gameboard, input, gameplayer)) {
                if (win(gameboard, gameplayer)) {
                    printboard(gameboard);
                    printf("Vyhral hrac %c (%s). Konec hry.\n", gameplayer, (gameplayer == 'X') ? "krizky" : "kolecka");
                    break;
                }
                else if (full(gameboard)) {
                    printf("Remiza, vsechny policka jsou zaplneny. Konec hry.\n");
                    break;
                }
                gameplayer = (gameplayer == 'X') ? 'O' : 'X';
            }
    }
    return 0;
}
