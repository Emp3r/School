#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

typedef struct Node {
    int order;
    int item[3];
    struct Node *child[4];
} node;

int nodes, elements;

node* CreateNode(int value, node *left, node *right) {
    node *newnode = new Node;
    
    newnode->order = 2;
    newnode->item[0] = value;
    newnode->child[0] = left;
    newnode->child[1] = right;
    newnode->child[2] = NULL;
    newnode->child[3] = NULL;
    
    nodes++;                //////////
    return newnode;
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
        
        elements++;         //////////
        return 1;
    }
    while (1) {
        if (temp->order == 4)
            temp = SplitNode(temp, parent);
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
            
            elements++;     //////////
            return 1;
        }
    }
}

int Heigh(node *tree) {
    if (tree == NULL)
        return -1;
    return Heigh(tree->child[0]) + 1;
}

void InorderTraversal(node *tree) {
    static int temp = 0;
    int i = 0;
    
    if (tree == NULL)
        return;
    InorderTraversal(tree->child[0]);
    
    do {
        temp++;
        if ( (temp <= 10) || (temp >= 99991) ) {
            if (temp == 1)
                printf("Prvnich deset prvku:\n");
            if (temp == 99991)
                printf("\nPoslednich deset prvku:\n");
            printf("%i ", tree->item[i]);
        }
        i++;
        
        InorderTraversal(tree->child[i]);
    } while (i < (tree->order - 1));
}

int main()
{
    srand((int)time(0));
    int i, j, pocet, vyska;
    node *strom;
    
    int cisla[7] = {100, 300, 1000, 3000, 10000, 30000, 100000};
    
    printf("%7s %7s %6s %6s", "Pocet", "Prumer", "Vyska", "Podil");
    printf("\n-----------------------------\n");
    
    for (i = 0; i < 7; i++) {
        pocet = cisla[i];
        strom = NULL;
        nodes = 0;
        elements = 0;
        
        for (j = 0; j < pocet; j++)
            Insert(&strom, rand());
        
        vyska = Heigh(strom);
        
        printf("%7i %7.2f %6i %6.2f\n", pocet, ((float)elements / (float)nodes), vyska, (vyska / (log((float)elements)/log(2))));
    }
    
    InorderTraversal(strom);
    
    return 0;
}

