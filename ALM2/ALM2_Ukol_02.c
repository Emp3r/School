#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>

int uzly = 0, prvky = 0;
typedef struct Node {
    int order;
    int item[3];
    struct Node *child[4];
} node;

node* CreateNode(int hodnota, node *leva, node *prava) {
    node *novy_uzel = (node*)malloc(sizeof(node));
    
    uzly++;
    
    novy_uzel->order = 2;
    novy_uzel->item[0] = hodnota;
    novy_uzel->child[0] = leva;
    novy_uzel->child[1] = prava;
    novy_uzel->child[2] = NULL;
    novy_uzel->child[3] = NULL;
    
    return novy_uzel;
}

node* SplitNode(node *rozdeleny, node *rodic) {
    int j;
    
    if (rodic == NULL) {
        rozdeleny->child[0] = CreateNode(rozdeleny->item[0], rozdeleny->child[0], rozdeleny->child[1]);
        rozdeleny->child[1] = CreateNode(rozdeleny->item[2], rozdeleny->child[2], rozdeleny->child[3]);
        rozdeleny->order = 2;
        rozdeleny->item[0] = rozdeleny->item[1];
        
        return rozdeleny;
    }
    j = rodic->order - 1;
    
    while ((j > 0) && (rozdeleny->item[1] < rodic->item[j-1])) {
        rodic->item[j] = rodic->item[j-1];
        rodic->child[j+1] = rodic->child[j];
        j--;
    }
    rodic->item[j] = rozdeleny->item[1];
    rodic->child[j] = rozdeleny;
    rozdeleny->order = 2;
    rodic->child[j+1] = CreateNode(rozdeleny->item[2], rozdeleny->child[2], rozdeleny->child[3]);
    rodic->order++;
    
    return rodic;
    
}

int Insert(node **strom, int hodnota) {
    int i, j;
    node *temp = *strom;
    node *rodic = NULL;
    
    if (*strom == NULL) {
        prvky++;
        
        *strom = CreateNode(hodnota, NULL, NULL);
        
        return 1;
    }
    while (1) {
        if (temp->order == 4) {
            temp = SplitNode(temp, rodic);
        }
        
        i = 0;
        while ((i < (temp->order - 1)) && (hodnota >= temp->item[i])) {
            if (hodnota == temp->item[i]) {
                return 0;
            }
            i++;
        }
        
        if (temp->child[i] != NULL) {
            rodic = temp;
            temp = temp->child[i];
        }
        else {
            prvky++;
            
            j = temp->order - 1;
            
            while (j > i) {
                temp->item[j] = temp->item[j-1];
                j--;
            }
            temp->item[i] = hodnota;
            temp->order++;
            
            return 1;
        }
    }
}

void InorderTraversal(node *strom) {
    static int temp = 0;
    int i = 0;
    
    if (strom == NULL) {
        return;
    }
    InorderTraversal(strom->child[0]);
    
    do {
        temp++;
        if ( (temp <= 10) || (temp >= 99991) ) {
            if (temp == 1) {
                printf("Prvnich deset prvku:\n");
            }
            if (temp == 99991) {
                printf("\nPoslednich deset prvku:\n");
            }
            printf("%i ", strom->item[i]);
        }
        i++;
        
        InorderTraversal(strom->child[i]);
        
    } while (i < (strom->order - 1)); 
}

int Heigh(node *strom) {
    if (strom == NULL) {
        return -1;
    }
    return Heigh(strom->child[0]) + 1;
}

int main()
{
    srand((int)time(NULL));
    int i, j;
    int pole[7] = {100, 300, 1000, 3000, 10000, 30000, 100000};
    node *strom;
    int vyska_stromu;
    
    printf("    Pocet      Prum     Vyska     Podil\n----------------------------------------\n");
    
    for (i = 0; i < 7; i++) {
        strom = NULL;
        
        for (j = 0; j < pole[i]; j++) {
            Insert(&strom, rand());
        }
        
        vyska_stromu = Heigh(strom);
        
        printf("%9i %9.2f %9i %9.2f\n", pole[i], ((float)prvky / uzly), vyska_stromu, (vyska_stromu / (float)(log((float)prvky)/log(2))));
        
        uzly = 0;
        prvky = 0;
    }
    
    InorderTraversal(strom);
    
    return 0;
}