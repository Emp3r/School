#include <stdio.h>

void VypisPole(int a[], int n) {
    int i;
    printf("%i", a[0]);
    for (i = 1; i < n; i++)
        printf(", %i", a[i]);
}

void InsertSort(int a[], int n) {
    int i, j, temp;
    
    for (i = 0; i < n; i++) {
        temp = a[i];
        for (j = i-1; j >= 0 && temp < a[j]; j--)
            a[j+1] = a[j];
        a[j+1] = temp;
    }
}

void BubbleSort(int a[], int n) {
    int i, j, temp;
    
    for (i = n-1; i > 0; i--) {
        for (j = 0; j < i; j++) {
            if (a[j] > a[j+1]) {
                temp = a[j];
                a[j] = a[j+1];
                a[j+1] = temp;
            }
        }
    }
}

void SelectSort(int a[], int n) {
    int i, j, m, temp;
    
    for (i = 0; i < n-1; i++) {
        temp = a[i];
        m = i;
        for (j = i+1; j < n; j++) {
            if (a[j] < temp) {
                temp = a[j];
                m = j;
            }
        }
        a[m] = a[i];
        a[i] = temp;
    }
}


int main()
{
    int pole1[10] = {7, 1, 2, 0, 8, 4, 5, 3, 9, 6};
    int pole2[10] = {0, 3, 1, 8, 7, 2, 5, 4, 6, 9};
    int pole3[10] = {2, 1, 3, 6, 9, 0, 4, 5, 7, 8};
    
    printf("Původní pole1: ");
    VypisPole(pole1, 10);
    InsertSort(pole1, 10);
    printf("\nSetřízené pole1 (InsertSort): ");
    VypisPole(pole1, 10);
    
    printf("\nPůvodní pole2: ");
    VypisPole(pole2, 10);
    BubbleSort(pole2, 10);
    printf("\nSetřízené pole2 (BubbleSort): ");
    VypisPole(pole2, 10);
    
    printf("\nPůvodní pole3: ");
    VypisPole(pole3, 10);
    SelectSort(pole3, 10);
    printf("\nSetřízené pole3 (SelectSort): ");
    VypisPole(pole3, 10);
    
    return 0;
}

