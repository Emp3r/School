#include <stdio.h>
#include <stdlib.h>

// Zadání:
// Sestavte program pro sestavení 2-3-4 stromu, do kterého jako prvky budou ukládána celá čísla.
// Dle pseudokódu implementujte funkci pro přidání prvku do 2-3-4 stromu.
// Dle pseudokódu implementujte funkci pro průchod 2-3-4 stromem. Při průchodu vypište prvky uložené ve stromu.
// Do stromu uložte několik celých čísel. Čísla uložená ve stromu následně vypište.

typedef struct Node {
    int order;
    int item[3];
    struct Node *child[4];
} node;

node* CreateNode(int value, node *left, node *right) {
    node *new = (node*)malloc(sizeof(node));
    
    new->order = 2;
    new->item[0] = value;
    new->child[0] = left;
    new->child[1] = right;
    new->child[2] = NULL;
    new->child[3] = NULL;
    
    return new;
}

node* SplitNode(node *split, node *parent) {
    int j;
    if (parent == NULL) {
        split->child[0] = CreateNode(split->item[0], split->child[0], split->child[1]);
        split->child[1] = CreateNode(split->item[2], split->child[2], split->child[3]);
        split->order = 2;
        split->item[0] = split->item[1];
        return split;
    }
    j = parent->order - 1;
    while ((j > 0) && (split->item[1] < parent->item[j-1])) {
        parent->item[j] = parent->item[j-1];
        parent->child[j+1] = parent->child[j];
        j--;
    }
    parent->item[j] = split->item[1];
    parent->child[j] = split;
    split->order = 2;
    parent->child[j+1] = CreateNode(split->item[2], split->child[2], split->child[3]);
    parent->order++;
    
    return parent;
}

int Insert(node **tree, int value) {
    int i, j;
    node *temp = *tree;
    node *parent = NULL;
    
    if (*tree == NULL) {
        *tree = CreateNode(value, NULL, NULL);
        return 1;
    }
    while (1) {
        if (temp->order == 4) {
            temp = SplitNode(temp, parent);
        }
        i = 0;
        while ((i < (temp->order - 1)) && (value >= temp->item[i])) {
            if (value == temp->item[i])
                return 0;
            i++;
        }
        if (temp->child[i] != NULL) {
            parent = temp;
            temp = temp->child[i];
        }
        else {
            j = temp->order - 1;
            while (j > i) {
                temp->item[j] = temp->item[j-1];
                j--;
            }
            temp->item[i] = value;
            temp->order++;
            return 1;
        }
    }
}

void InorderTraversal(node *tree) {
    int i;
    if (tree == NULL)
        return;
    InorderTraversal(tree->child[0]);
    i = 0;
    do {
        printf("%i, ", tree->item[i]);
        i++;
        InorderTraversal(tree->child[i]);
    }
    while (i < (tree->order - 1));
}

int main()
{
    int i;
    node *strom = NULL;
    
    for (i = 30; i >= 0; i--)
        Insert(&strom, i);
    
    Insert(&strom, 7);
    Insert(&strom, 1);
    Insert(&strom, 4);
    Insert(&strom, 2);
    Insert(&strom, 9);
    Insert(&strom, 3);
    
    InorderTraversal(strom);
    
    return 0;
}