#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Jmena2.h"

// Zadání:
// Ve složce VYUKA\ALM-2 je soubor Jmena2.h, zkopírujte ho do složky svého projektu a zařaďte do
// sestavovaného programu (#include "Jmena2.h")
// Vytvořte binární vyhledávací strom pro ukládání jmen a jména uvedená v poli Jmena přidejte do stromu.
// Vypište jména uložená ve stromu funkcí InorderWalk(x)(je uvedená v dokumentu ALM1-02.doc).
// Zjistěte a vypište výšku sestaveného vyhledávacího stromu se jmény.

typedef struct node {
    const char *name;
    struct node *left;
    struct node *right;
} Node;

int Insert(Node **root, const char *x){
    Node **u = root;
    
    while (*u != NULL) {
        if (strcmp(x, (*u)->name) < 0)
            u = &(*u)->left;
        else if (strcmp(x, (*u)->name) > 0)
            u = &(*u)->right;
        else return 0;
    }
    {   Node *v = (malloc(sizeof(Node)));
        v->name = x;
        v->left = v->right = NULL;
        *u = v;
    }
    return 1;
}

void InorderWalk(Node *x) {
    if (x != NULL) {
        InorderWalk(x->left);
        printf("%s, ", x->name);
        InorderWalk(x->right);
    }
}

int max(int a, int b) {
    return a > b ? a : b;   }

int TreeHeight(Node *root) {
    if (root == NULL)
        return -1;
    else
        return max(TreeHeight(root->left), TreeHeight(root->right)) + 1;
}


int main()
{
    int i, pocet = sizeof(Jmena)/sizeof(*Jmena);
    Node *koren = malloc(sizeof(Node));
    
    koren->name = Jmena[0];                 // založení kořene
    
    for (i = 1; i < pocet; i++)
        Insert(&koren, Jmena[i]);           // naplnění stromu
    
    InorderWalk(koren);
    
    printf("\n\nVýška stromu je: %i", TreeHeight(koren));
    
    return 0;
}