#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

int pocet_srovnani, pocet_presunu;

void UdelejPole(int A[], int n) {
    int i;
    for (i = 0; i < n; i++)
        A[i] = rand();
}

void BuildHeap(int A[], int i, int k) {
    int temp = A[i];
    int j, l;
    
    for (j = i, l = 2*i+1; l <= k; j = l, l = 2*l+1) {
        pocet_srovnani++;
        if (l < k) {
            pocet_srovnani++;
            if (A[l+1] > A[l])
                ++l;
        }
        if (temp >= A[l])
            break;
        pocet_presunu++;
        A[j] = A[l];
    }
    pocet_presunu++;
    A[j] = temp;
}

void HeapSort(int A[], int n) {
    int i, temp;
    
    for (i = n / 2 - 1; i >= 0; --i)
        BuildHeap(A, i , n - 1);
    
    for (i = n - 1; i > 0; i--) {
        pocet_presunu += 3;
        temp = A[0];
        A[0] = A[i];
        A[i] = temp;
        BuildHeap(A, 0, i - 1);
    }
}

int main()
{
    int i;
    int cisla[8] = {200, 500, 1000, 2000, 5000, 10000, 20000, 50000};
    srand((int)time(0));
    
    printf("%7s %16s %18s\n----------------------------------------------\n", "pocet", "srovnani", "presuny");
    
    for (i = 0; i < 8; i++) {
        int n = cisla[i];
        int pole[n];
        UdelejPole(pole, n);
        HeapSort(pole, n);
        
        printf("%6i %10i %7.2f %10i %7.2f\n", n,  pocet_srovnani, pocet_srovnani / (n * log(n)/log(2)), pocet_presunu, pocet_presunu / (n * log(n)/log(2)));
        pocet_srovnani = 0;
        pocet_presunu = 0;
        
        if (i == 7) {
            for (i = 0; i < 20; i++)
                printf("%i, ", pole[i]);
            printf("..... ");
            for (i = n-10; i < n; i++)
                printf(", %i", pole[i]);
        }
    }
    
        return 0;
}